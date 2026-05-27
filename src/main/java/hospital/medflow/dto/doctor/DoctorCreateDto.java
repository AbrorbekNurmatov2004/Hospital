package hospital.medflow.dto.doctor;

import hospital.medflow.model.enums.Specialization;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCreateDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,64}$",
            message = "Password must be 8-64 chars and include upper, lower, number, and special character")
    private String password;
    @NotNull
    private Specialization specialization;
    @NotBlank
    private String roomNumber;
    @NotEmpty
    private String roleId;
}
