package hospital.medflow.dto.user;

import hospital.medflow.dto.IdNameDTO;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private IdNameDTO role;
    private Boolean superAdmin;
    private String profileImageUrl;
}