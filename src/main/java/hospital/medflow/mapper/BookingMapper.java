package hospital.medflow.mapper;

import hospital.medflow.dto.booking.BookingCreateDto;
import hospital.medflow.dto.booking.BookingDto;
import hospital.medflow.dto.booking.BookingUpdateDto;
import hospital.medflow.model.Booking;
import hospital.medflow.model.Doctor;
import hospital.medflow.model.enums.BookingStatus;
import hospital.medflow.validator.DoctorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final DoctorValidator validator;

    public List<BookingDto> toDto(List<Booking> bookings) {
        if (bookings == null) {
            return Collections.emptyList();
        }
        return bookings.stream().map(this::toDto).collect(Collectors.toList());
    }

    public BookingDto toDto(Booking booking) {
        return BookingDto.builder().
                id(booking.getId()).
                patient(booking.getPatient().getFirstName() + " " + booking.getPatient().getLastName()).
                doctor(booking.getDoctor().getFirstName() + " " + booking.getDoctor().getLastName()).
                dateTime(booking.getDateTime()).
                status(booking.getStatus()).
                queueNumber(booking.getQueueNumber()).
                phone(booking.getPatient() != null ? booking.getPatient().getPhone() : null).build();
    }

    public Booking fromDto(BookingCreateDto dto) {
        Doctor doctor = validator.existAndGet(dto.getDoctorId());
        Booking booking = new Booking();
        booking.setDoctor(doctor);
        booking.setDateTime(dto.getDateTime());
        booking.setStatus(BookingStatus.SCHEDULED);
        return booking;
    }

    public void fromDto(BookingUpdateDto dto, Booking booking) {
        Doctor doctor = validator.existAndGet(dto.getDoctorId());
        booking.setDoctor(doctor);
        booking.setDateTime(dto.getDateTime());
    }
}
