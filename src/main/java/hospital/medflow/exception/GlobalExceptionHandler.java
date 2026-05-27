package hospital.medflow.exception;

import hospital.medflow.dto.AppErrorDto;
import hospital.medflow.utils.ErrorConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<AppErrorDto> handleNotFound(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler({BadRequestException.class, BadCredentialsException.class, RoomAlreadyExistsException.class, WardCapacityExceededException.class})
    public ResponseEntity<AppErrorDto> handleBadRequest(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorDto> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<AppErrorDto> handleConflict(AlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppErrorDto> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at {}: ", request.getRequestURI(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorConstants.SERVER_ERROR, request);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<AppErrorDto> handleAuthNotFound(AuthenticationCredentialsNotFoundException e, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AppErrorDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    private ResponseEntity<AppErrorDto> buildErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        AppErrorDto error = AppErrorDto.builder()
                .path(request.getRequestURI())
                .error(status.value())
                .message(message)
                .timestamp(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.status(status).body(error);
    }

}
