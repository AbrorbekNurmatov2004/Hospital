package hospital.medflow.mapper;

import hospital.medflow.model.FileEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileMapper {

    public FileEntity fromDto(MultipartFile file) {
        FileEntity entity = new FileEntity();
        entity.setSize(file.getSize());
        entity.setContentType(file.getContentType());
        entity.setOriginalName(file.getOriginalFilename());
        return entity;
    }

}
