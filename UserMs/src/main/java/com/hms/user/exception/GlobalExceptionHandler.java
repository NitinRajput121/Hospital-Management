package com.hms.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ProblemDetail validationEx(MethodArgumentNotValidException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation failed");

        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                        sb.append(fieldError.getField()).append(":")
                                .append(fieldError.getDefaultMessage())
                                .append(";")
                );
        problemDetail.setDetail(sb.toString());
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail badCred(BadCredentialsException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setTitle("Password or email is wrong");
        return problemDetail;
    }

    @ExceptionHandler(EmailAlreadyExist.class)
    public ProblemDetail emailExists(EmailAlreadyExist ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setTitle("email already exist");
        return problemDetail;
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail userNotFound(UserNotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("User not found");
        problemDetail.setDetail("for this email user is not found");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail wrongparm(MethodArgumentTypeMismatchException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("wrong parameter");
        problemDetail.setDetail("you have given wrong parameter");
        return problemDetail;
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ProblemDetail wrongparm(MissingServletRequestPartException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("missing param");
        problemDetail.setDetail("you have missed parameter");
        return problemDetail;
    }


}
