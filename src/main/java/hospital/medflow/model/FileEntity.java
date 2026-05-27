package hospital.medflow.model;

import hospital.medflow.model.entity.IdEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "file_entity")
public class FileEntity extends IdEntity {

    private String originalName;

    private String fileName;

    private Long size;

    private String contentType;

    private String path;
}
