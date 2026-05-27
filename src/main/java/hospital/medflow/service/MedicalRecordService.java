package hospital.medflow.service;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.medicalRecord.MedicalRecordCreateDto;
import hospital.medflow.dto.medicalRecord.MedicalRecordDto;
import hospital.medflow.mapper.MedicalRecordMapper;
import hospital.medflow.model.Booking;
import hospital.medflow.model.MedicalRecord;
import hospital.medflow.model.Patient;
import hospital.medflow.model.enums.BookingStatus;
import hospital.medflow.repository.BookingRepository;
import hospital.medflow.repository.MedicalRecordRepository;
import hospital.medflow.service.base.AbstractService;
import hospital.medflow.validator.BookingValidator;
import hospital.medflow.validator.MedicalValidator;
import hospital.medflow.validator.PatientValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService extends AbstractService<MedicalRecordRepository, MedicalRecordMapper, MedicalValidator>
        implements hospital.medflow.service.base.MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final MedicalRecordMapper mapper;
    private final MedicalValidator validator;
    private final BookingRepository bookingRepository;
    private final BookingValidator bookingValidator;
    private final PatientValidator patientValidator;

    public MedicalRecordService(MedicalRecordRepository repository, MedicalRecordMapper mapper, MedicalValidator validator, BookingRepository bookingRepository, BookingValidator bookingValidator, PatientValidator patientValidator) {
        super(repository, mapper, validator);
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
        this.bookingRepository = bookingRepository;
        this.bookingValidator = bookingValidator;
        this.patientValidator = patientValidator;
    }


    public DataList<List<MedicalRecordDto>> getAll(BaseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Page<MedicalRecord> result = repository.findAllPatients(criteria.getSearch(), pageable);
        List<MedicalRecordDto> dto = mapper.toDto(result.getContent());
        return DataList.<List<MedicalRecordDto>>builder().
                data(dto).
                allElements(result.getTotalElements()).
                totalPages(result.getTotalPages()).
                build();
    }

    public MedicalRecordDto get(String id) {
        MedicalRecord record = validator.existAndGet(id);
        return mapper.toDto(record);
    }

    public MedicalRecordDto create(MedicalRecordCreateDto dto) {
        MedicalRecord record = mapper.fromDto(dto);
        Patient patient = patientValidator.existAndGet(dto.getPatientId());
        Booking booking = bookingValidator.existAndGet(dto.getBookingId());
        record.setBooking(booking);
        record.setPatient(patient);
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);
        return mapper.toDto(repository.save(record));
    }

    public void delete(String id) {
        MedicalRecord record = validator.existAndGet(id);
        record.setDeleted(true);
        Booking booking = bookingValidator.existAndGet(record.getBooking().getId());
        booking.setDeleted(true);
        bookingRepository.save(booking);
        repository.save(record);
    }

    public List<MedicalRecordDto> getPatientHistory(String patientId) {
        return mapper.toDto(repository.findPatient(patientId));
    }
}
