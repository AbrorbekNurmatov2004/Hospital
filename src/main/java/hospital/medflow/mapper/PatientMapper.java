package hospital.medflow.mapper;

import hospital.medflow.dto.patient.PatientCreateDto;
import hospital.medflow.dto.patient.PatientDto;
import hospital.medflow.dto.patient.PatientUpdateDto;
import hospital.medflow.model.Patient;
import hospital.medflow.model.enums.PatientStatus;
import org.springframework.stereotype.Component;
import static hospital.medflow.utils.SecurityUtils.DATE_FORMATTER;

@Component
public class PatientMapper {

    public PatientDto toDto(Patient patient) {
        return PatientDto.builder().
                id(patient.getId()).
                firstName(patient.getFirstName()).
                lastName(patient.getLastName()).
                birthDate(patient.getBirthDate() != null ? patient.getBirthDate().format(DATE_FORMATTER) : null).
                bloodGroup(patient.getBloodGroup() != null ? patient.getBloodGroup().name() : null).
                patientStatus(patient.getPatientStatus() != null ? patient.getPatientStatus().name() : null).
                phone(patient.getPhone()).
                wardId(patient.getWard() != null ? patient.getWard().getId() : null).build();
    }

    public Patient fromDto(PatientCreateDto dto) {
        Patient patient = new Patient();
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setBirthDate(dto.getBirthDate());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setPhone(dto.getPhone());
        patient.setPatientStatus(PatientStatus.ACTIVE);
        return patient;
    }

    public void fromDto(PatientUpdateDto dto, Patient patient) {
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setBirthDate(dto.getBirthDate());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setPhone(dto.getPhone());
        patient.setPatientStatus(dto.getPatientStatus());
    }
}
