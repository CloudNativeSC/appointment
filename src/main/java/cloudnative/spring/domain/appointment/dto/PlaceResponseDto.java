package cloudnative.spring.domain.appointment.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 장소 저장 후 반환되는 응답 DTO
 */
@Getter
@Builder
public class PlaceResponseDto {

    private Long placeId;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
}
