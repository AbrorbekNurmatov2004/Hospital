package hospital.medflow.repository;

import hospital.medflow.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, String> {

    @Query("""
            select r from Role r where
            (:search is null or lower(r.name) ilike lower(concat('%',:search,'%')))
            """)
    Page<Role> findAllRoles(Pageable pageable, String search);

    boolean existsByCode(String code);

}
