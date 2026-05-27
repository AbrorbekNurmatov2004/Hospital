package hospital.medflow.mapper;

import hospital.medflow.dto.doctor.DoctorCreateDto;
import hospital.medflow.dto.doctor.DoctorDto;
import hospital.medflow.dto.doctor.DoctorUpdateDto;
import hospital.medflow.dto.IdNameDTO;
import hospital.medflow.model.Doctor;
import hospital.medflow.model.Role;
import hospital.medflow.validator.RoleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DoctorMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleValidator authRoleValidator;

    public List<DoctorDto> toDto(List<Doctor> doctors) {
        if (doctors == null) {
            return Collections.emptyList();
        }
        return doctors.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DoctorDto toDto(Doctor doctor) {
        return DoctorDto.builder().
                id(doctor.getId()).
                firstName(doctor.getFirstName()).
                lastName(doctor.getLastName()).
                username(doctor.getUsername()).
                specialization(doctor.getSpecialization()).
                roomNumber(doctor.getRoomNumber()).
                profileImageUrl(doctor.getProfileImage() != null ? doctor.getProfileImage().getPath() : null).
                role(toIdNameDto(doctor.getRole())).
                build();
    }

    private IdNameDTO toIdNameDto(Role role) {
        if (role == null) return null;
        return IdNameDTO.builder().
                id(role.getId()).
                name(role.getName()).
                build();
    }

    public Doctor fromDto(DoctorCreateDto dto) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setUsername(dto.getUsername());
        doctor.setPassword(passwordEncoder.encode(dto.getPassword()));
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setRoomNumber(dto.getRoomNumber());
        if (dto.getRoleId() != null) {
            doctor.setRole(authRoleValidator.existsAndGet(dto.getRoleId()));
        }
        return doctor;
    }

    public void fromDto(DoctorUpdateDto dto, Doctor doctor) {
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setUsername(dto.getUsername());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setRoomNumber(dto.getRoomNumber());
        if (dto.getRoleId() != null) {
            doctor.setRole(authRoleValidator.existsAndGet(dto.getRoleId()));
        }
    }

}
