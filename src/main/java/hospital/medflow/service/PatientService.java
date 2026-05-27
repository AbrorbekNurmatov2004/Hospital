package hospital.medflow.service;

import hospital.medflow.dto.patient.PatientCreateDto;
import hospital.medflow.dto.patient.PatientDto;
import hospital.medflow.dto.patient.PatientUpdateDto;
import hospital.medflow.mapper.PatientMapper;
import hospital.medflow.model.Patient;
import hospital.medflow.model.Ward;
import hospital.medflow.model.enums.PatientStatus;
import hospital.medflow.repository.PatientRepository;
import hospital.medflow.repository.WardRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.validator.PatientValidator;
import hospital.medflow.validator.WardValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends AbstractService<PatientRepository, PatientMapper, PatientValidator> implements hospital.medflow.service.base.PatientService {

    private final WardValidator wardValidator;
    private final WardRepository wardRepository;

    public PatientService(PatientRepository repository, PatientMapper mapper, PatientValidator validator, WardValidator wardValidator, WardRepository wardRepository) {
        super(repository, mapper, validator);
        this.wardValidator = wardValidator;
        this.wardRepository = wardRepository;
    }

    @Override
    public Page<PatientDto> getActivePatients(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Patient> patients = repository.findAllPatients(PatientStatus.ACTIVE, pageable, search);
        return patients.map(mapper::toDto);
    }

    @Override
    public Page<PatientDto> getReleasedPatients(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Patient> patients = repository.findAllPatients(PatientStatus.RELEASED, pageable, search);
        return patients.map(mapper::toDto);
    }

    @Override
    public PatientDto get(String id) {
        Patient patient = validator.existAndGet(id);
        return mapper.toDto(patient);
    }

    @Override
    public PatientDto create(PatientCreateDto dto) {
        Patient patient = mapper.fromDto(dto);
        Ward ward = wardValidator.existAndGet(dto.getWardId());
        patient.setWard(ward);
        ward.setOccupied(ward.getOccupied() + 1);
        Patient savedPatient = repository.save(patient);
        return mapper.toDto(savedPatient);
    }

    @Override
    public PatientDto update(String id, PatientUpdateDto dto) {
        Patient patient = validator.existAndGet(id);

        if (!patient.getWard().getId().equals(dto.getWardId())) {
            Ward oldWard = patient.getWard();
            oldWard.setOccupied(oldWard.getOccupied() - 1);
            wardRepository.save(oldWard);

            Ward newWard = wardValidator.existAndGet(dto.getWardId());
            validator.checkWard(newWard.getOccupied(), newWard);
            newWard.setOccupied(newWard.getOccupied() + 1);
            patient.setWard(newWard);
        }
        mapper.fromDto(dto, patient);
        return mapper.toDto(repository.save(patient));
    }

    @Override
    public void delete(String id) {
        Patient patient = validator.existAndGet(id);
        patient.setDeleted(true);
        repository.save(patient);
    }

    @Override
    public void releasePatient(String id) {
        Patient patient = validator.existAndGet(id);
        Ward ward = patient.getWard();
        ward.setOccupied(ward.getOccupied() - 1);
        wardRepository.save(ward);
        patient.setPatientStatus(PatientStatus.RELEASED);
        repository.save(patient);
    }
}