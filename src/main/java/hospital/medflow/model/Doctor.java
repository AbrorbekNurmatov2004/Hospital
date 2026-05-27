package hospital.medflow.model;

import hospital.medflow.model.enums.Specialization;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "doctors")
public class Doctor extends User {

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @Column(unique = true, nullable = false)
    private String roomNumber;
}
