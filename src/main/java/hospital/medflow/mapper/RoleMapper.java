package hospital.medflow.mapper;

import hospital.medflow.dto.permission.PermissionDto;
import hospital.medflow.dto.role.RoleCreateDto;
import hospital.medflow.dto.role.RoleDto;
import hospital.medflow.dto.role.RoleUpdateDto;
import hospital.medflow.model.Permission;
import hospital.medflow.model.Role;
import hospital.medflow.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;

    public List<RoleDto> toDto(List<Role> roles) {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream().map(this::toDto).collect(Collectors.toList());
    }

    public RoleDto toDto(Role role) {
        List<PermissionDto> permissions = permissionMapper.toDto(role.getPermissions());
        return RoleDto.builder().
                id(role.getId()).
                name(role.getName()).
                code(role.getCode()).
                permissions(permissions).
                build();
    }

    public Role fromDto(RoleCreateDto dto) {
        List<Permission> permissions = permissionRepository.findAllByIdIn(dto.getPermissionIds());
        Role role = new Role();
        role.setName(dto.getName());
        role.setCode(dto.getCode());
        role.setPermissions(permissions);
        return role;
    }

    public Role fromDto(Role role, RoleUpdateDto dto) {
        List<Permission> permissions = permissionRepository.findAllByIdIn(dto.getPermissionIds());
        role.setName(dto.getName());
        role.setCode(dto.getCode());
        role.setPermissions(permissions);
        return role;
    }

}
