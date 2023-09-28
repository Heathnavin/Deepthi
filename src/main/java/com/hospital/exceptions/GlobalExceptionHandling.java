package com.hospital.exceptions;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

	@ExceptionHandler(UserNameNotFoundException.class)
	public ResponseEntity<String> handleUserNameNotFoundException(UserNameNotFoundException e){
		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(e.getMessage());
		
	}

	@ExceptionHandler(HospitalDetailsNotFoundException.class)
	public ResponseEntity<String> handleHospitalDetailsNotFoundException(HospitalDetailsNotFoundException e){
		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(e.getMessage());
	}

	@ExceptionHandler(HospitalIdMismatchException.class)
	public ResponseEntity<String> handleHospitalIdMismatchException(HospitalIdMismatchException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
		
	}

	@ExceptionHandler(HospitalNameAlreadyExistsException.class)
	public ResponseEntity<String> handleHospitalNameAlreadyExistsException(HospitalNameAlreadyExistsException e){
		 return ResponseEntity.status(HttpStatus.CONFLICT)
                 .body(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            var fieldError = (FieldError) error;
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(errors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
	}
}