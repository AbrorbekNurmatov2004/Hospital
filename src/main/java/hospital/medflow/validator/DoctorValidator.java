package hospital.medflow.validator;

import hospital.medflow.dto.doctor.DoctorCreateDto;
import hospital.medflow.dto.doctor.DoctorUpdateDto;
import hospital.medflow.exception.ResourceNotFoundException;
import hospital.medflow.exception.RoomAlreadyExistsException;
import hospital.medflow.exception.UsernameAlreadyExistsException;
import hospital.medflow.model.Doctor;
import hospital.medflow.repository.DoctorRepository;
import hospital.medflow.utils.ErrorConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoctorValidator {

    private final DoctorRepository repository;

    public Doctor existAndGet(String id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("Doctor"))
        );
    }

    public void validateDoctor(DoctorCreateDto dto) {
        if (repository.findProjectedByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(ErrorConstants.THIS_USERNAME_EXISTED);
        }
        if (repository.existsByRoomNumber(dto.getRoomNumber())) {
            throw new RoomAlreadyExistsException(HttpStatus.BAD_REQUEST, "This room already exist");
        }
    }

    public void validateDoctor(DoctorUpdateDto dto, String id) {
        repository.findProjectedByUsername(dto.getUsername()).ifPresent(doctor -> {
            if (!doctor.getId().equals(id)) {
                throw new UsernameAlreadyExistsException(ErrorConstants.THIS_USERNAME_EXISTED);
            }
        });
        repository.findProjectedByRoomNumber(dto.getRoomNumber()).ifPresent(doctor -> {
            if (!doctor.getId().equals(id)) {
                throw new RoomAlreadyExistsException(HttpStatus.BAD_REQUEST, "This room already exist");
            }
        });
    }
}
