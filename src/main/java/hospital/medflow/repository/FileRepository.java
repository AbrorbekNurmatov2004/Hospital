package hospital.medflow.repository;

import hospital.medflow.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {
}
