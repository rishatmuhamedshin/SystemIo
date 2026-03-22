package by.rishat.ws.systemio.dto.customException.paymentExceptions;

import by.rishat.ws.systemio.dto.customException.ApiException;
import org.springframework.http.HttpStatus;

public class NoProcessorAvailable extends ApiException {

    public NoProcessorAvailable(String message) {
        super("NO_PROCESSORS_AVAILABLE",
                message,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
