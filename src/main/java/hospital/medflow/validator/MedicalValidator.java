package hospital.medflow.validator;

import hospital.medflow.exception.ResourceNotFoundException;
import hospital.medflow.model.MedicalRecord;
import hospital.medflow.repository.MedicalRecordRepository;
import hospital.medflow.utils.ErrorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicalValidator {

    private final MedicalRecordRepository repository;

    public MedicalRecord existAndGet(String id) {
        return repository.findById(id)
                .or(() -> (repository.findByBookingId(id)))
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("medical record")));
    }
}
