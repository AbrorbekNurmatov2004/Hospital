package hospital.medflow.validator;

import hospital.medflow.config.YmlData;
import hospital.medflow.exception.BadRequestException;
import hospital.medflow.exception.ResourceNotFoundException;
import hospital.medflow.model.FileEntity;
import hospital.medflow.repository.FileRepository;
import hospital.medflow.utils.ErrorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileValidator {

    private final YmlData ymlData;
    private final FileRepository repository;

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File not upload");
        }

        if (file.getSize() > ymlData.getMaxFileSize()) {
            throw new BadRequestException("File size must be under" + ymlData.getMaxFileSize() + " MB");
        }
    }

    public FileEntity existAndGet(String id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("file"))
        );
    }
}
