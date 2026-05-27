package hospital.medflow.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoomAlreadyExistsException extends RuntimeException {
    private final HttpStatus status;
    public RoomAlreadyExistsException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
