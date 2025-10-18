package cloudnative.spring.domain.appointment.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class AvailableTimeResponseDto {
    private LocalDateTime availableStart;
    private LocalDateTime availableEnd;
    private int priority;
}
