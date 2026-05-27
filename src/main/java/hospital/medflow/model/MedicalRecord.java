package hospital.medflow.model;

import hospital.medflow.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medical_records")
public class MedicalRecord extends BaseEntity {

    @OneToOne
    private Booking booking;

    @ManyToOne
    private Patient patient;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private String diagnosis;

    private String treatmentPlan;
}
