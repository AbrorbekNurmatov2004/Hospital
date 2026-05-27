package hospital.medflow.service;

import hospital.medflow.config.YmlData;
import hospital.medflow.mapper.FileMapper;
import hospital.medflow.model.FileEntity;
import hospital.medflow.repository.FileRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.validator.FileValidator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@Service
public class FileService extends AbstractService<FileRepository, FileMapper, FileValidator> {

    private final YmlData ymlData;

    public FileService(FileRepository repository, FileMapper mapper, FileValidator validator, YmlData ymlData) {
        super(repository, mapper, validator);
        this.ymlData = ymlData;
    }

    public ResponseEntity<String> upload(MultipartFile file) {
        validator.validate(file);
        Map<String, String> upload = uploadDb(file, ymlData.getFileRoot());
        String filePath = upload.get("path");
        String generatedName = upload.get("generatedName");
        FileEntity entity = mapper.fromDto(file);
        entity.setPath(filePath);
        entity.setFileName(generatedName);
        return new ResponseEntity<>(repository.save(entity).getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<FileSystemResource> download(String id) {
        FileEntity entity = validator.existAndGet(id);
        FileSystemResource resource = new FileSystemResource(entity.getPath());
        String originalName = resource.getFilename();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; attachment; filename=\"" + extractOriginalFileName(originalName) + "\"")
                .contentType(MediaType.parseMediaType(entity.getContentType()))
                .body(resource);
    }

    private Map<String, String> uploadDb(MultipartFile file, String fileRoot) {
        String generatedName = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename();
        Path path = Path.of(fileRoot, generatedName);
        try (InputStream inputStream = file.getInputStream()) {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            return Map.of("path", path.toString(), "generatedName", generatedName);
        } catch (IOException e) {
            throw new RuntimeException("Failed while uploading file", e);
        }
    }

    private String extractOriginalFileName(String fileName) {
        return fileName.substring(fileName.indexOf("_") + 1);
    }
}
