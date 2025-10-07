package com.hms.profile.exception;



import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validationEx(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation failed");

        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                sb.append(fieldError.getField()).append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ")
        );
        problemDetail.setDetail(sb.toString());
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail badCred(BadCredentialsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setTitle("Authentication failed");
        return problemDetail;
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ProblemDetail doctorNotFound(DoctorNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Doctor not found");
        problemDetail.setDetail("Doctor not found for the given ID");
        return problemDetail;
    }

    // ✅ Handle both PatientAlreadyExist and PatientAlreadyExistsException
    @ExceptionHandler({PatientAlreadyExist.class})
    public ResponseEntity<Map<String, Object>> handlePatientAlreadyExists(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Patient Already Exists");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.CONFLICT.value());

        log.warn("Patient already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ProblemDetail patientNotFound(PatientNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Patient not found");
        problemDetail.setDetail("Patient not found for the given ID");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail wrongParam(MethodArgumentTypeMismatchException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid parameter type");
        problemDetail.setDetail("The parameter '" + ex.getName() + "' has an invalid type. Expected: " +
                ex.getRequiredType().getSimpleName());
        return problemDetail;
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ProblemDetail missingParam(MissingServletRequestPartException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Missing parameter");
        problemDetail.setDetail("Required parameter '" + ex.getRequestPartName() + "' is missing");
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> errorResponse = new HashMap<>();

        String message = ex.getMessage();
        if (message != null && message.contains("adhaar_no")) {
            errorResponse.put("error", "Duplicate Aadhaar Number");
            errorResponse.put("message", "A patient with this Aadhaar number already exists");
        } else if (message != null && message.contains("phone_no")) {
            errorResponse.put("error", "Duplicate Phone Number");
            errorResponse.put("message", "A patient with this phone number already exists");
        } else if (message != null && message.contains("email")) {
            errorResponse.put("error", "Duplicate Email");
            errorResponse.put("message", "A patient with this email already exists");
        } else {
            errorResponse.put("error", "Data Constraint Violation");
            errorResponse.put("message", "The data you're trying to save violates database constraints");
        }

        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.CONFLICT.value());

        log.warn("Data integrity violation: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", "An unexpected error occurred");
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        log.error("Unexpected error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}



