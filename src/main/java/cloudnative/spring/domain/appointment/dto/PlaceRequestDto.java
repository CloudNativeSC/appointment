package cloudnative.spring.domain.appointment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장소 선택 시 전달되는 요청 DTO
 * 사용자가 Tmap 검색 결과 중 하나를 선택하면,
 * 해당 장소 정보를 서버에 전달하여 DB에 저장합니다.
 */
@Getter
@NoArgsConstructor
public class PlaceRequestDto {

    private String name;      // 장소 이름 (예: 성신여자대학교 정문)
    private String address;   // 주소
    private Double latitude;  // 위도
    private Double longitude; // 경도
}
