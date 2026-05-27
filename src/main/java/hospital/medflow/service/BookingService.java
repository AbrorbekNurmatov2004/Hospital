package hospital.medflow.service;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.booking.BookingCreateDto;
import hospital.medflow.dto.booking.BookingDto;
import hospital.medflow.dto.booking.BookingUpdateDto;
import hospital.medflow.mapper.BookingMapper;
import hospital.medflow.model.Booking;
import hospital.medflow.model.Patient;
import hospital.medflow.model.enums.BookingStatus;
import hospital.medflow.repository.BookingRepository;
import hospital.medflow.repository.PatientRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.service.base.CRUDService;
import hospital.medflow.validator.BookingValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService extends AbstractService<BookingRepository, BookingMapper, BookingValidator>
        implements CRUDService<BookingDto, BookingUpdateDto, BookingCreateDto, BaseCriteria, String> {

    private final PatientRepository patientRepository;

    public BookingService(BookingRepository repository, BookingMapper mapper, BookingValidator validator, PatientRepository patientRepository) {
        super(repository, mapper, validator);
        this.patientRepository = patientRepository;
    }

    @Override
    public DataList<List<BookingDto>> getAll(BaseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<Booking> bookings = repository.findAllBookings(pageable, criteria.getSearch());
        List<BookingDto> dto = mapper.toDto(bookings.getContent());
        return DataList.<List<BookingDto>>builder()
                .data(dto)
                .totalPages(bookings.getTotalPages())
                .allElements(bookings.getTotalElements())
                .build();
    }

    @Override
    public BookingDto get(String id) {
        Booking booking = validator.existAndGet(id);
        return mapper.toDto(booking);
    }

    @Override
    public BookingDto create(BookingCreateDto dto) {
        LocalDateTime start = dto.getDateTime().toLocalDate().atStartOfDay();
        LocalDateTime end = dto.getDateTime().toLocalDate().atTime(23, 59, 59);
        int queue = repository.countByDoctorIdAndDateTimeBetween(dto.getDoctorId(), start, end);

        Patient patient = patientRepository.findByPhone(dto.getPhone())
                .orElseGet(() -> {
                    Patient newPatient = new Patient();
                    newPatient.setFirstName(dto.getFirstName());
                    newPatient.setLastName(dto.getLastName());
                    newPatient.setPhone(dto.getPhone());
                    return newPatient;
                });

        Booking booking = mapper.fromDto(dto);
        booking.setPatient(patient);
        booking.setQueueNumber(queue + 1);
        return mapper.toDto(repository.save(booking));
    }

    @Override
    public BookingDto update(String id, BookingUpdateDto dto) {
        Booking booking = validator.existAndGet(id);
        mapper.fromDto(dto, booking);
        Patient patient = booking.getPatient();
        patient.setPhone(dto.getPhone());
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patientRepository.save(patient);
        booking.setPatient(patient);
        return mapper.toDto(repository.save(booking));
    }

    @Override
    public void delete(String id) {
        Booking booking = validator.existAndGet(id);
        booking.setDeleted(true);
        repository.save(booking);
    }

    public void updateStatus(String id, BookingStatus status) {
        Booking booking = validator.existAndGet(id);
        booking.setStatus(status);
        repository.save(booking);
    }
}