package cloudnative.spring.domain.appointment.controller;

import cloudnative.spring.domain.appointment.dto.*;
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

    /**
     * 약속 장소 검색
     */
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

    /**
     * 약속 장소 선택
     */
    @PostMapping("/places/select")
    public ResponseEntity<ApiResponse<PlaceResponseDto>> createAppointmentWithPlace(
            @RequestBody PlaceRequestDto requestDto) {

        PlaceResponseDto responseDto = appointmentService.selectAppointmentPlace(requestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
    }

    /**
     * 약속 장소 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentPlaceResponseDto>> createAppointment(
            @RequestParam Long placeId,
            @RequestBody AppointmentPlaceRequestDto requestDto
    ) {
        AppointmentPlaceResponseDto response = appointmentService.createAppointment(placeId, requestDto);

        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }


    /**
     * 약속 장소 삭제
     */
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<String>> deleteAppointment(
            @PathVariable Long appointmentId
    ) {
        appointmentService.deleteAppointment(appointmentId);

        // code, message는 자동으로 ("COMMON200", "성공입니다.") 채워짐
        return ResponseEntity.ok(
                ApiResponse.onSuccess("약속이 삭제되었습니다.")
        );
    }
}
