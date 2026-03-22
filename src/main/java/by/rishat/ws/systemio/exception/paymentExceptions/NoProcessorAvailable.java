package by.rishat.ws.systemio.exception.paymentExceptions;

import by.rishat.ws.systemio.exception.ApiException;
import org.springframework.http.HttpStatus;

public class NoProcessorAvailable extends ApiException {

    public NoProcessorAvailable(String message) {
        super("NO_PROCESSORS_AVAILABLE",
                message,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
