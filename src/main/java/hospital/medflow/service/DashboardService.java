package hospital.medflow.service;

import hospital.medflow.dto.DashboardStatsDto;
import hospital.medflow.model.Booking;
import hospital.medflow.projection.DiagnosisCount;
import hospital.medflow.repository.BookingRepository;
import hospital.medflow.repository.MedicalRecordRepository;
import hospital.medflow.repository.PatientRepository;
import hospital.medflow.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final WardRepository wardRepository;
    private final BookingRepository bookingRepository;

    public DashboardStatsDto getStats() {
        Long patients = patientRepository.countByDeletedFalse();

        long occupied = wardRepository.getTotalOccupied().orElse(0L);
        long capacity = wardRepository.getTotalCapacity().orElse(0L);
        double rate = capacity > 0 ? (double) occupied / capacity * 100 : 0L;

        ZoneId zoneId = ZoneId.of("Asia/Tashkent");
        LocalDateTime start = LocalDate.now(zoneId).atStartOfDay();
        LocalDateTime end = LocalDate.now(zoneId).atTime(23, 59, 59);
        long todayBookings = bookingRepository.countByDateTimeBetweenAndDeletedFalse(start, end);

        List<Booking> smartQueue = bookingRepository.findTop5ByDateTimeBetweenAndDeletedFalseOrderByCreatedAtAsc(start, end);

        return mapper(patients, rate, todayBookings, getDiagnosisStates(), smartQueue);
    }

    private Map<String, Long> getDiagnosisStates() {
        List<DiagnosisCount> results = medicalRecordRepository.countDiagnosisByGroup();
        if (results == null || results.isEmpty()) {
            return Map.of();
        }
        return results.stream().
                collect(Collectors.toMap(
                        res -> res.getDiagnosis() != null ? res.getDiagnosis() : "-",
                        res -> res.getCount() != null ? res.getCount() : 0L
                ));
    }

    private DashboardStatsDto mapper(Long patients, double ward, long todayBooking, Map<String, Long> diagnosis, List<Booking> queue) {
        return DashboardStatsDto.builder()
                .patient(patients)
                .ward(ward)
                .todayBooking(todayBooking)
                .diagnosis(diagnosis)
                .queue(queue)
                .build();
    }
}