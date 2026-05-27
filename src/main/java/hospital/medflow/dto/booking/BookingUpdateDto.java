package hospital.medflow.dto.booking;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String doctorId;
    @NotNull
    @Future
    private LocalDateTime dateTime;
    @NotBlank
    @Pattern(regexp = "^\\+998\\d{9}$",
            message = "Phone number must be 12 numbers")
    private String phone;
}

