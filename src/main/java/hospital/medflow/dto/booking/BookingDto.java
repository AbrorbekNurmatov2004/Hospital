package hospital.medflow.dto.booking;

import hospital.medflow.model.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private String id;
    private String patient;
    private String doctor;
    private LocalDateTime dateTime;
    private BookingStatus status;
    private Integer queueNumber;
    private String phone;
}
