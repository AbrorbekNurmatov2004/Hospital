package hospital.medflow.dto.doctor;

import hospital.medflow.model.enums.Specialization;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUpdateDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String username;
    @NotNull
    private Specialization specialization;
    @NotBlank
    private String roomNumber;
    @NotEmpty
    private String roleId;
}
