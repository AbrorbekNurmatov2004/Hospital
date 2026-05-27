package hospital.medflow.repository;

import hospital.medflow.model.Ward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WardRepository extends JpaRepository<Ward, String> {

    @Query("select sum (w.occupied) from Ward w")
    Optional<Long> getTotalOccupied();

    @Query("select sum(w.capacity) from Ward w")
    Optional<Long> getTotalCapacity();

    @Query("select w from Ward w where w.deleted = false  order by w.createdAt")
    Page<Ward> findAllWards(Pageable pageable);

}