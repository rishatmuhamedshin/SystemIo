package by.rishat.ws.systemio.dto.customException;

import org.springframework.http.HttpStatus;

public class PaypalPaymentException extends ApiException {

    public PaypalPaymentException(String message) {
        super("BAD_REQUEST",
                message,
                HttpStatus.BAD_REQUEST
        );
    }
}
