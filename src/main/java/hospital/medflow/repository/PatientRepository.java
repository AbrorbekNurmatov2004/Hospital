package hospital.medflow.repository;

import hospital.medflow.model.Patient;
import hospital.medflow.model.enums.PatientStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,String> {

    @Query("""
            select p from Patient p where
                p.deleted = false and
                p.ward is not null and
                p.patientStatus = :status and
                (:search is null or
                    (lower(p.firstName) ilike lower(concat('%', :search ,'%')) or
                     lower(p.lastName) ilike lower(concat('%', :search ,'%')))) order by p.createdAt""")
    Page<Patient> findAllPatients(PatientStatus status, Pageable pageable, String search);

    Long countByDeletedFalse();

    Optional<Patient> findByPhone(String phone);

}
