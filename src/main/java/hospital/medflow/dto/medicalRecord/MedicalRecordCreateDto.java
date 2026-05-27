package hospital.medflow.dto.medicalRecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordCreateDto {
    @NotBlank
    private String bookingId;
    @NotBlank
    private String patientId;
    @NotBlank
    private String duration;
    @NotBlank
    @Size(min = 3, max = 1000)
    private String diagnosis;
    @NotBlank
    @Size(min = 10, max = 5000)
    private String treatmentPlan;
}
