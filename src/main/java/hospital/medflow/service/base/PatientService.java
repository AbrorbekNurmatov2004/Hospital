package hospital.medflow.service.base;

import hospital.medflow.dto.patient.PatientCreateDto;
import hospital.medflow.dto.patient.PatientDto;
import hospital.medflow.dto.patient.PatientUpdateDto;
import org.springframework.data.domain.Page;

public interface PatientService {

    Page<PatientDto> getActivePatients(int page, int size, String search);

    Page<PatientDto> getReleasedPatients(int page, int size, String search);

    PatientDto get(String id);

    PatientDto create(PatientCreateDto dto);

    PatientDto update(String id, PatientUpdateDto dto);

    void delete(String id);

    void releasePatient(String id);

}
