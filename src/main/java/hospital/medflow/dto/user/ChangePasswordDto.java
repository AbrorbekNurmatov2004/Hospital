package hospital.medflow.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    @NotBlank(message = "Old password cannot be blank")
    private String oldPassword;
    @NotBlank(message = "New password cannot be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,64}$",
            message = "Password must be 8-64 chars and include upper, lower, number, and special character")
    private String newPassword;
    @NotBlank(message = "Confirm password cannot be blank")
    @Size(min = 8, max = 64, message = "Confirm password must be 8-64 characters")
    private String confirmPassword;
}
