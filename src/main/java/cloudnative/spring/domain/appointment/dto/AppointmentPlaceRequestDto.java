package cloudnative.spring.domain.appointment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 약속 생성 요청 DTO
 * 이미 선택된 장소 ID를 참조하여 약속을 생성합니다.
 */
@Getter
@NoArgsConstructor
public class AppointmentPlaceRequestDto {

    private Long placeId;             // 저장된 장소 ID
    private String title;             // 약속 제목
    private String description;       // 약속 설명
    private LocalDateTime startTime;  // 시작 시간
    private LocalDateTime endTime;    // 종료 시간
    private Integer estimatedTravelTime; // 예상 이동 시간(분 단위)
}
