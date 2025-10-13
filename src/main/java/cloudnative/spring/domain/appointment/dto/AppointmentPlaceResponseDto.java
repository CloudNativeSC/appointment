package cloudnative.spring.domain.appointment.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 약속 생성 응답 DTO
 * 약속과 연결된 장소 정보를 함께 반환합니다.
 */
@Getter
@Builder
public class AppointmentPlaceResponseDto {

    private Long appointmentId; // 생성된 약속 ID
    private String title;       // 약속 제목
    private String status;      // 약속 상태 (PENDING, CONFIRMED 등)
    private PlaceDto place;     // 약속 장소 정보

    @Getter
    @Builder
    public static class PlaceDto {
        private Long placeId;
        private String name;
        private String address;
        private double latitude;
        private double longitude;
    }
}
