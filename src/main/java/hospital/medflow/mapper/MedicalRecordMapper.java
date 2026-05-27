package hospital.medflow.mapper;

import hospital.medflow.dto.IdFullNameDto;
import hospital.medflow.dto.medicalRecord.MedicalRecordCreateDto;
import hospital.medflow.dto.medicalRecord.MedicalRecordDto;
import hospital.medflow.model.*;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class MedicalRecordMapper {

    public List<MedicalRecordDto> toDto(List<MedicalRecord> records) {
        if (records == null) return Collections.emptyList();
        return records.stream().map(this::toDto).toList();
    }

    public MedicalRecordDto toDto(MedicalRecord record) {
        if (record == null) return null;
        return MedicalRecordDto.builder()
                .id(record.getId())
                .doctor(doctorFullNameDto(record.getBooking()))
                .patient(patientFullNameDto(record.getBooking()))
                .duration(record.getDuration())
                .diagnosis(record.getDiagnosis())
                .treatmentPlan(record.getTreatmentPlan())
                .build();
    }

    public MedicalRecord fromDto(MedicalRecordCreateDto dto) {
        MedicalRecord record = new MedicalRecord();
        record.setDiagnosis(dto.getDiagnosis());
        record.setTreatmentPlan(dto.getTreatmentPlan());
        record.setDuration(dto.getDuration());
        return record;
    }

    private IdFullNameDto doctorFullNameDto(Booking booking) {
        return Optional.ofNullable(booking)
                .map(Booking::getDoctor)
                .map(doc -> IdFullNameDto.builder()
                        .id(doc.getId())
                        .firstName(doc.getFirstName())
                        .lastName(doc.getLastName())
                        .build())
                .orElse(null);
    }

    private IdFullNameDto patientFullNameDto(Booking booking) {
        return Optional.ofNullable(booking)
                .map(Booking::getPatient)
                .map(doc -> IdFullNameDto.builder()
                        .id(doc.getId())
                        .firstName(doc.getFirstName())
                        .lastName(doc.getLastName())
                        .build())
                .orElse(null);
    }

}
