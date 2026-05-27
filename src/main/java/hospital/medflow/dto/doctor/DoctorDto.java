package hospital.medflow.dto.doctor;

import hospital.medflow.dto.IdNameDTO;
import hospital.medflow.model.enums.Specialization;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private Specialization specialization;
    private String roomNumber;
    private String profileImageUrl;
    private IdNameDTO role;
}
