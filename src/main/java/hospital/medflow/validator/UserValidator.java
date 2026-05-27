package hospital.medflow.validator;

import hospital.medflow.config.CustomUserDetails;
import hospital.medflow.dto.user.ChangePasswordDto;
import hospital.medflow.exception.ResourceNotFoundException;
import hospital.medflow.exception.UsernameAlreadyExistsException;
import hospital.medflow.model.User;
import hospital.medflow.repository.UserRepository;
import hospital.medflow.utils.ErrorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User existAndGet(String id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("user") + id)
        );
    }

    public void existUsername(String username) {
        if (repository.findProjectedByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(ErrorConstants.THIS_USERNAME_EXISTED);
        }
    }

    public void existUsername(String id, String newUsername) {
        repository.findProjectedByUsername(newUsername).ifPresent(found -> {
            if (!found.getId().equals(id)) {
                throw new UsernameAlreadyExistsException(ErrorConstants.THIS_USERNAME_EXISTED);
            }
        });
    }

    public User existsUsernameAndGet(String username) {
        return repository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("user")));
    }

    public void validateChangePassword(ChangePasswordDto dto, CustomUserDetails sessionUser) {
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BadCredentialsException(ErrorConstants.PASSWORD_DO_NOT_MATCH);
        }
        if (!passwordEncoder.matches(dto.getOldPassword(), sessionUser.getPassword())) {
            throw new BadCredentialsException(ErrorConstants.OLD_PASSWORD_DO_NOT_MATCH);
        }
        if (dto.getNewPassword().length() < 8) {
            throw new BadCredentialsException(ErrorConstants.PASSWORD_TOO_SHORT);
        }
    }
}
