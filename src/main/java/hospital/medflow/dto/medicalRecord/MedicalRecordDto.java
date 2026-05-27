package hospital.medflow.dto.medicalRecord;

import hospital.medflow.dto.IdFullNameDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDto {
    private String id;
    private IdFullNameDto doctor;
    private IdFullNameDto patient;
    private String duration;
    private String diagnosis;
    private String treatmentPlan;
}
