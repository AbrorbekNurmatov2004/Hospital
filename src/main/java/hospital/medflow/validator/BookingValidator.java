package hospital.medflow.validator;

import hospital.medflow.exception.ResourceNotFoundException;
import hospital.medflow.model.Booking;
import hospital.medflow.repository.BookingRepository;
import hospital.medflow.utils.ErrorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingValidator {

    private final BookingRepository repository;

    public Booking existAndGet(String id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorConstants.s_NOT_FOUND.formatted("Booking"))
        );
    }

}
