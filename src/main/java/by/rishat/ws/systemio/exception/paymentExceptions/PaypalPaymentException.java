package by.rishat.ws.systemio.exception.paymentExceptions;

import by.rishat.ws.systemio.exception.ApiException;
import org.springframework.http.HttpStatus;

public class PaypalPaymentException extends ApiException {

    public PaypalPaymentException(String message) {
        super("BAD_REQUEST",
                message,
                HttpStatus.BAD_REQUEST
        );
    }
}
