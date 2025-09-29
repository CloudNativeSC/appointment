package cloudnative.spring.domain.appointment.controller;

import cloudnative.spring.domain.appointment.dto.AppointmentResponseDto;
import cloudnative.spring.domain.appointment.repository.AppointmentRepository;
import cloudnative.spring.domain.appointment.service.AppointmentService;
import cloudnative.spring.global.response.ApiResponse;
import cloudnative.spring.global.response.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/appointments") // 약속 api
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/places/search")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto.PlaceDto>>> searchAppointmentPlace(
            @RequestParam String keyword) {

        List<AppointmentResponseDto.PlaceDto> places = appointmentService.searchPlaces(keyword);

        if (places.isEmpty()) {
            return ResponseEntity
                    .status(ErrorCode.PLACE_NOT_FOUND.getHttpStatus())
                    .body(ApiResponse.onFailure(
                            ErrorCode.PLACE_NOT_FOUND.getCode(),
                            ErrorCode.PLACE_NOT_FOUND.getMessage(),
                            null
                    ));
        }

        return ResponseEntity.ok(ApiResponse.onSuccess(places));
    }


}
