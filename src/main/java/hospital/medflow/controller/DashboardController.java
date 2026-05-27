package hospital.medflow.controller;

import hospital.medflow.dto.DashboardStatsDto;
import hospital.medflow.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static hospital.medflow.utils.ApiPath.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(API + VERSION + "/dashboard")
public class DashboardController {

    private final DashboardService service;

    @GetMapping
    public ResponseEntity<DashboardStatsDto> getDashboard() {
        return ResponseEntity.ok(service.getStats());
    }
}
