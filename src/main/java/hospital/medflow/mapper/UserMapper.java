package hospital.medflow.mapper;

import hospital.medflow.dto.IdNameDTO;
import hospital.medflow.dto.user.UserCreateDto;
import hospital.medflow.dto.user.UserDto;
import hospital.medflow.dto.user.UserUpdateDto;
import hospital.medflow.model.Role;
import hospital.medflow.model.User;
import hospital.medflow.validator.RoleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleValidator authRoleValidator;

    public List<UserDto> toDto(List<User> users) {
        if (users == null) return Collections.emptyList();
        return users.stream().map(this::toDto).toList();
    }

    public UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder().
                id(user.getId()).
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                username(user.getUsername()).
                superAdmin(user.getSuperAdmin()).
                role(toIdNameDto(user.getRole())).
                profileImageUrl(user.getProfileImage() != null ? user.getProfileImage().getPath() : null).
                build();
    }

    private IdNameDTO toIdNameDto(Role role) {
        if (role == null) return null;
        return IdNameDTO.builder().
                id(role.getId()).
                name(role.getName()).
                build();
    }

    public User fromDto(UserCreateDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setSuperAdmin(false);
        if (dto.getRoleId() != null) {
            user.setRole(authRoleValidator.existsAndGet(dto.getRoleId()));
        }
        return user;
    }

    public void fromDto(UserUpdateDto dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        if (dto.getRoleId() != null) {
            user.setRole(authRoleValidator.existsAndGet(dto.getRoleId()));
        }
    }
}