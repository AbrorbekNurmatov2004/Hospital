package hospital.medflow.dto;

import hospital.medflow.model.Booking;
import lombok.*;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardStatsDto {
    private Long patient;
    private Double ward;
    private Long todayBooking;
    private Map<String,Long> diagnosis;
    private List<Booking> queue;
}
