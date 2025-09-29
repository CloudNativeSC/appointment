package cloudnative.spring.domain.appointment.service;

import cloudnative.spring.domain.appointment.dto.AppointmentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

/**
 * AppointmentService
 *
 * <p>약속 관리 개발</p>
 *
 * @author  wonee1
 * @version 1.0
 * @since   2025-09-29
 * @see <a href="https://apis.openapi.sk.com/tmap/">Tmap API 공식 문서</a>
 */
@Service
@RequiredArgsConstructor
public class AppointmentService {

    /** Tmap API Key (application.yml / properties에서 주입) */
    @Value("${tmap.api.key}")
    private String apiKey;

    /** Tmap API URL (ex: https://apis.openapi.sk.com/tmap/pois) */
    @Value("${tmap.api.url}")
    private String apiUrl;

    /** Spring RestTemplate (외부 API 호출용) */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 키워드 기반 장소 검색 메서드.
     *
     * @param keyword 검색 키워드 (예: "강남")
     * @return 검색된 장소 목록 (없을 경우 빈 리스트 반환)
     * @throws org.springframework.web.client.RestClientException 외부 API 호출 실패 시 발생
     * @see AppointmentResponseDto.PlaceDto
     * @implNote {@code UriComponentsBuilder.build(false)} 로 설정하여
     *           <b>이중 인코딩(double encoding)</b> 문제를 방지합니다.
     *           (안 해주면 "강남" → "%EA%B0..." → "%25EA%25B0..." 형태로 인코딩되어
     *           서버가 204 No Content 응답을 반환합니다.)
     */
    public List<AppointmentResponseDto.PlaceDto> searchPlaces(String keyword) {
        keyword = (keyword == null) ? "" : keyword.trim();

        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("version", 1)
                .queryParam("format", "json")
                .queryParam("searchKeyword", keyword)
                .queryParam("page", 1)
                .queryParam("count", 5)
                .queryParam("searchType", "all")
                .queryParam("searchtypCd", "A")
                .queryParam("reqCoordType", "WGS84GEO")
                .queryParam("resCoordType", "WGS84GEO")
                .queryParam("appKey", apiKey)
                .build(false) // 이중 인코딩 방지하기 (안 하면 204 No Content 발생 .. 이거 고치느라 힘들었음)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("User-Agent", "TMapAPI-Client/1.0");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            return List.of();
        }

        Map<String, Object> response = responseEntity.getBody();
        if (response == null || !response.containsKey("searchPoiInfo")) {
            return List.of();
        }

        return parseSearchResponse(response);
    }

    /**
     * Tmap API 응답(Map)을 PlaceDto 리스트로 변환합니다.
     *
     * @param response Tmap API Raw 응답
     * @return 변환된 PlaceDto 리스트
     * @see AppointmentResponseDto.PlaceDto
     */
    private List<AppointmentResponseDto.PlaceDto> parseSearchResponse(Map<String, Object> response) {
        Map<String, Object> searchPoiInfo = (Map<String, Object>) response.get("searchPoiInfo");

        Map<String, Object> pois = (Map<String, Object>) searchPoiInfo.get("pois");
        if (pois == null || !pois.containsKey("poi")) {
            return List.of();
        }

        Object poiObj = pois.get("poi");
        List<Map<String, Object>> poiList = new ArrayList<>();

        if (poiObj instanceof List) {
            poiList = (List<Map<String, Object>>) poiObj;
        } else if (poiObj instanceof Map) {
            poiList = List.of((Map<String, Object>) poiObj);
        }

        List<AppointmentResponseDto.PlaceDto> results = new ArrayList<>();
        for (Map<String, Object> poi : poiList) {
            results.add(
                    AppointmentResponseDto.PlaceDto.builder()
                            .placeId((String) poi.get("id"))
                            .name((String) poi.get("name"))
                            .address(getAddress(poi))
                            .latitude(parseDouble(poi.get("frontLat")))
                            .longitude(parseDouble(poi.get("frontLon")))
                            .build()
            );
        }

        return results;
    }

    /**
     * 주소 정보 파싱
     * <p>우선순위: 도로명 주소 → upperAddrName → 지번 주소 순</p>
     *
     * @param poi POI 데이터 맵
     * @return 최종 주소 문자열
     */
    private String getAddress(Map<String, Object> poi) {
        String roadName = (String) poi.get("roadName");
        if (roadName != null && !roadName.trim().isEmpty()) {
            return roadName;
        }

        String upperAddrName = (String) poi.get("upperAddrName");
        if (upperAddrName != null && !upperAddrName.trim().isEmpty()) {
            return upperAddrName;
        }

        return (String) poi.get("address");
    }

    /**
     * Object 값을 Double로 변환 (문자열 또는 숫자 처리).
     * <p>변환 실패 시 기본값 0.0 반환</p>
     *
     * @param value 변환 대상 값
     * @return 변환된 Double 값
     * @throws NumberFormatException 문자열이 숫자로 변환될 수 없을 때 발생
     */
    private Double parseDouble(Object value) {
        if (value == null) return 0.0;

        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        return 0.0;
    }
}
