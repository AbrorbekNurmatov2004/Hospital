package hospital.medflow.repository;

import hospital.medflow.model.Doctor;
import hospital.medflow.projection.DoctorIdOnly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

    @Query("""
            select d from Doctor d
            where d.deleted = false
            and (:search is null
                or lower(d.firstName) like lower(concat('%', :search, '%'))
                or lower(d.lastName)  like lower(concat('%', :search, '%'))
                or lower(d.username)  like lower(concat('%', :search, '%')))
            order by d.createdAt desc
            """)
    Page<Doctor> findAllDoctors(Pageable pageable, String search);

    Optional<DoctorIdOnly> findProjectedByUsername(String username);

    boolean existsByRoomNumber(String roomNumber);

    Optional<DoctorIdOnly> findProjectedByRoomNumber(String roomNumber);

}
