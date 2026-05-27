package hospital.medflow.repository;

import hospital.medflow.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query("""
            select b from Booking b where b.deleted = false
            and (:search is null or
                (lower(b.patient.firstName) ilike lower(concat('%', :search, '%')) or
                 lower(b.patient.lastName) ilike lower(concat('%', :search, '%'))))
            order by b.queueNumber""")
    Page<Booking> findAllBookings(Pageable pageable, String search);

    long countByDateTimeBetweenAndDeletedFalse(LocalDateTime start, LocalDateTime end);

    List<Booking> findTop5ByDateTimeBetweenAndDeletedFalseOrderByCreatedAtAsc(LocalDateTime start, LocalDateTime end);

    int countByDoctorIdAndDateTimeBetween(String doctorId, LocalDateTime start, LocalDateTime end);

    List<Booking> findByDoctorIdAndDeletedFalse(String id);

}

