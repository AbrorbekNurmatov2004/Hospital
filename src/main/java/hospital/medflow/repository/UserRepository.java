package hospital.medflow.repository;

import hospital.medflow.model.User;
import hospital.medflow.projection.UsernameAndId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("""
             select u from User u
                where u.deleted = false
                and (:search is null
                    or lower(u.firstName) like lower(concat('%', :search, '%'))
                    or lower(u.lastName)  like lower(concat('%', :search, '%'))
                    or lower(u.username)  like lower(concat('%', :search, '%')))
                order by u.createdAt desc
            """)
    Page<User> findAllUsers(Pageable pageable, String search);

    Optional<UsernameAndId> findProjectedByUsername(String username);

    Optional<User> findByUsernameAndDeletedFalse(String username);

}
