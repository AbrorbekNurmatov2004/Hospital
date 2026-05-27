package hospital.medflow.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import hospital.medflow.model.enums.BookingStatus;
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
public class BookingCreateDto {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    @NotNull
    private BookingStatus status;
    @NotBlank
    @Pattern(regexp = "^\\+998\\d{9}$",
            message = "Phone number must be 12 numbers")
    private String phone;
}
