package cloudnative.spring.domain.appointment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppointmentResponseDto {
    private Long appointmentId;
    private String title;
    private String description;
    private String date;
    private TimeDto time;
    private PlaceDto place;
    private String createdAt;

    @Getter
    @Builder
    public static class TimeDto {
        private String startTime;
        private String endTime;
        private String duration;
    }// 약속 시작 시간, 끝나는 시간, 걸리는 정도

    @Getter
    @Builder
    public static class PlaceDto {
        private String placeId;
        private String name;
        private String address;
        private double latitude;
        private double longitude;
    }// 장소
}
