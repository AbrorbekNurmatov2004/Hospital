package hospital.medflow.validator;

import hospital.medflow.exception.ResourceNotFoundException;
import hospital.medflow.model.Ward;
import hospital.medflow.repository.WardRepository;
import hospital.medflow.utils.ErrorConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WardValidator {

    private final WardRepository repository;

    public Ward existAndGet(String id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("Ward"))
        );
    }
}
