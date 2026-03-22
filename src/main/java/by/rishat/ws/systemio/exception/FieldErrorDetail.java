package by.rishat.ws.systemio.exception;

public record FieldErrorDetail(String field, Object rejectedValue, String message) {}