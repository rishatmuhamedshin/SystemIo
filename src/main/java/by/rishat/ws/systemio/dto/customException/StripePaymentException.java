package by.rishat.ws.systemio.dto.customException;

import org.springframework.http.HttpStatus;

public class StripePaymentException extends ApiException {

    public StripePaymentException(String message) {
        super("BAD_REQUEST",
                message,
                HttpStatus.BAD_REQUEST
        );
    }
}