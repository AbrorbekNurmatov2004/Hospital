package hospital.medflow.model;

import hospital.medflow.model.entity.BaseEntity;
import hospital.medflow.model.enums.BloodGroup;
import hospital.medflow.model.enums.PatientStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    private PatientStatus patientStatus = PatientStatus.ACTIVE;

    @Column(unique = true)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;
}