package hospital.medflow.service;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.booking.BookingDto;
import hospital.medflow.dto.doctor.DoctorCreateDto;
import hospital.medflow.dto.doctor.DoctorDto;
import hospital.medflow.dto.doctor.DoctorUpdateDto;
import hospital.medflow.mapper.BookingMapper;
import hospital.medflow.mapper.DoctorMapper;
import hospital.medflow.model.Booking;
import hospital.medflow.model.Doctor;
import hospital.medflow.repository.BookingRepository;
import hospital.medflow.repository.DoctorRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.service.base.CRUDService;
import hospital.medflow.validator.BookingValidator;
import hospital.medflow.validator.DoctorValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService extends AbstractService<DoctorRepository, DoctorMapper, DoctorValidator>
        implements CRUDService<DoctorDto, DoctorUpdateDto, DoctorCreateDto, BaseCriteria, String> {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public DoctorService(DoctorRepository repository, DoctorMapper mapper, DoctorValidator validator, BookingRepository bookingRepository, BookingMapper bookingMapper) {
        super(repository, mapper, validator);
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public DataList<List<DoctorDto>> getAll(BaseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<Doctor> doctors = repository.findAllDoctors(pageable, criteria.getSearch());
        List<DoctorDto> dto = mapper.toDto(doctors.getContent());
        return DataList.<List<DoctorDto>>builder()
                .data(dto)
                .totalPages(doctors.getTotalPages())
                .allElements(doctors.getTotalElements())
                .build();
    }

    @Override
    public DoctorDto get(String id) {
        Doctor doctor = validator.existAndGet(id);
        return mapper.toDto(doctor);
    }

    @Override
    public DoctorDto create(DoctorCreateDto dto) {
        validator.validateDoctor(dto);
        Doctor doctor = mapper.fromDto(dto);
        return mapper.toDto(repository.save(doctor));
    }

    @Override
    public DoctorDto update(String id, DoctorUpdateDto dto) {
        validator.validateDoctor(dto, id);
        Doctor doctor = validator.existAndGet(id);
        mapper.fromDto(dto, doctor);
        return mapper.toDto(repository.save(doctor));
    }

    @Override
    public void delete(String id) {
        Doctor doctor = validator.existAndGet(id);
        doctor.setDeleted(true);
        repository.save(doctor);
    }

    public List<BookingDto> getBookingsByDoctorId(String id) {
        List<Booking> bookings = bookingRepository.findByDoctorIdAndDeletedFalse(id);
        return bookingMapper.toDto(bookings);
    }
}
