package hospital.medflow.dto.patient;

import hospital.medflow.model.enums.BloodGroup;
import hospital.medflow.model.enums.PatientStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;
    @Past
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private BloodGroup bloodGroup;
    @NotNull
    private PatientStatus patientStatus;
    @NotBlank
    @Pattern(regexp = "^\\+998\\d{9}$",
            message = "Phone number must be 12 numbers")
    private String phone;
    @NotBlank
    private String wardId;
}
