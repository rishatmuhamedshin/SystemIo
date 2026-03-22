package by.rishat.ws.systemio.exception;

public record ValidationErrorResponse(
        String errorCode,
        String message,
        java.time.LocalDateTime timestamp,
        java.util.List<FieldErrorDetail> errors
) {}
