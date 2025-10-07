package com.hms.profile.exception;

public class PatientAlreadyExist extends RuntimeException {
    public PatientAlreadyExist(String message) {
        super(message);
    }
}
