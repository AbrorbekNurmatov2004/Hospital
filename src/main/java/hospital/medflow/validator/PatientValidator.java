package hospital.medflow.validator;

import hospital.medflow.exception.ResourceNotFoundException;
import hospital.medflow.exception.WardCapacityExceededException;
import hospital.medflow.model.Patient;
import hospital.medflow.model.Ward;
import hospital.medflow.repository.PatientRepository;
import hospital.medflow.utils.ErrorConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientValidator {

    private final PatientRepository repository;

    public Patient existAndGet(String id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("Patient"))
        );
    }

    public void checkWard(Integer count, Ward ward) {
        if (count >= ward.getCapacity()) {
            throw new WardCapacityExceededException(ErrorConstants.NOT_EXISTED_WARDS);
        }
    }
}
