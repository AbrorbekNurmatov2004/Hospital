package hospital.medflow.validator;

import hospital.medflow.exception.ResourceNotFoundException;
import hospital.medflow.model.Permission;
import hospital.medflow.repository.PermissionRepository;
import hospital.medflow.utils.ErrorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionValidator {

    private final PermissionRepository permissionRepository;

    public Permission existsAndGet(String id) {
        return permissionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("Permission"))
        );
    }

}
