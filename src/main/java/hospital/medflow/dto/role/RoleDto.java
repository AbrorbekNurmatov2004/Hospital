package hospital.medflow.dto.role;

import hospital.medflow.dto.permission.PermissionDto;
import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private String id;
    private String name;
    private String code;
    private List<PermissionDto> permissions;
}
