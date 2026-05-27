package hospital.medflow.model;

import hospital.medflow.model.entity.BaseEntity;
import hospital.medflow.model.enums.WardType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wards")
public class Ward extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private WardType type;

    private Integer capacity = 0;

    private Integer occupied = 0;
}
