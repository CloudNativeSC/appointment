package cloudnative.spring.domain.appointment.service;

import cloudnative.spring.domain.appointment.dto.AppointmentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    @Value("${tmap.api.key}")
    private String apiKey;

    @Value("${tmap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<AppointmentResponseDto.PlaceDto> searchPlaces(String keyword) { //응답을 PlaceDto 리스트로 변환해서 반환

        // Tmap API URL 생성하기
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("version", 1)
                .queryParam("format", "json")
                .queryParam("searchKeyword", keyword)
                .queryParam("appKey", apiKey)
                .toUriString();

        //Tmap API 호출, 값 json 으로 받아오기
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        //장소 검색 결과 파싱하기
        List<AppointmentResponseDto.PlaceDto> results = new ArrayList<>();

        if (response != null && response.containsKey("searchPoiInfo")) {//검색 결과
            Map<String, Object> searchPoiInfo = (Map<String, Object>) response.get("searchPoiInfo");
            Map<String, Object> pois = (Map<String, Object>) searchPoiInfo.get("pois");

            if (pois != null && pois.containsKey("poi")) {
                List<Map<String, Object>> poiList = (List<Map<String, Object>>) pois.get("poi");

                for (Map<String, Object> poi : poiList) { // place dto에 값 담기
                    AppointmentResponseDto.PlaceDto dto = AppointmentResponseDto.PlaceDto.builder()
                            .placeId((String) poi.get("id"))
                            .name((String) poi.get("name"))
                            .address((String) poi.get("roadName") != null ? (String) poi.get("roadName") : (String) poi.get("upperAddrName"))
                            .latitude(Double.parseDouble((String) poi.get("frontLat")))
                            .longitude(Double.parseDouble((String) poi.get("frontLon")))
                            .build();

                    results.add(dto);
                }
            }
        }

        return results;
    }
}
