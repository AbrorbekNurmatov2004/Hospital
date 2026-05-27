package hospital.medflow.repository;

import hospital.medflow.model.MedicalRecord;
import hospital.medflow.projection.DiagnosisCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, String> {

    @Query("select m from MedicalRecord m where m.patient.id = :id order by m.createdAt desc")
    List<MedicalRecord> findPatient(String id);

    @Query("""
            select m from MedicalRecord m
            where m.deleted = false and (:search is null
            or lower(m.patient.firstName) like lower(concat('%', :search, '%'))
            or lower(m.patient.lastName) like lower(concat('%', :search, '%'))
            or lower(m.diagnosis) like lower(concat('%', :search, '%')))
            """)
    Page<MedicalRecord> findAllPatients(String search, Pageable pageable);

    Optional<MedicalRecord> findByBookingId(String id);

    @Query("""
            select m.diagnosis as diagnosis, count(m) as count from MedicalRecord m
            where m.deleted = false
            group by m.diagnosis
            """)
    List<DiagnosisCount> countDiagnosisByGroup();

}
