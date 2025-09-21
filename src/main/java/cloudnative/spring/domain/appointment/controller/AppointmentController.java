package cloudnative.spring.domain.appointment.controller;

import cloudnative.spring.domain.appointment.dto.AppointmentResponseDto;
import cloudnative.spring.domain.appointment.repository.AppointmentRepository;
import cloudnative.spring.domain.appointment.service.AppointmentService;
import cloudnative.spring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/appointments") // 약속 api
@RequiredArgsConstructor
public class AppointmentController {


    private final AppointmentService appointmentService;
    //약속 장소 검색
    @GetMapping("/places/search")
    public ApiResponse<List<AppointmentResponseDto.PlaceDto>> searchAppointmentPlace(
            @RequestParam String keyword) {

        List<AppointmentResponseDto.PlaceDto> places = appointmentService.searchPlaces(keyword);
        return ApiResponse.onSuccess(places);
    }


}
