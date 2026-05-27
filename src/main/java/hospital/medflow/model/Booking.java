package hospital.medflow.model;

import hospital.medflow.model.entity.BaseEntity;
import hospital.medflow.model.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(nullable = false)
    private Integer queueNumber;
}