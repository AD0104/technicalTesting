package com.alten.mx.scheduling.persistance.dto;

public class GenericResponse {
    int status;
    String message;

    public GenericResponse() {}
    public GenericResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public void setStatus(int status) { this.status = status; }
    public int getStatus() { return this.status; }
    public void setMessage(String message) { this.message = message; }
    public String getMessage() { return this.message; }
}
