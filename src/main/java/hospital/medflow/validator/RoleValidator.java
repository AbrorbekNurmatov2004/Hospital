package hospital.medflow.validator;

import hospital.medflow.exception.AlreadyExistsException;
import hospital.medflow.model.Role;
import hospital.medflow.repository.RoleRepository;
import hospital.medflow.utils.ErrorConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.module.ResolutionException;

@Component
@RequiredArgsConstructor
public class RoleValidator {

    private final RoleRepository repository;

    public Role existsAndGet(String id) {
        return repository.findById(id).orElseThrow(
                () -> new ResolutionException(ErrorConstants.s_NOT_FOUND.formatted("Role"))
        );
    }

    public void existByCode(String code) {
        if (repository.existsByCode(code)) {
            throw new AlreadyExistsException(ErrorConstants.ALREADY_EXISTS);
        }
    }

}
