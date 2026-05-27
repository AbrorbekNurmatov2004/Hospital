package hospital.medflow.dto.patient;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private String id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String bloodGroup;
    private String patientStatus;
    private String phone;
    private String wardId;
}
