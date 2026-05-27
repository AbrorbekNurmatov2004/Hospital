package hospital.medflow.exception;

public class WardCapacityExceededException extends RuntimeException {
    public WardCapacityExceededException(String message) {
        super(message);
    }
}
