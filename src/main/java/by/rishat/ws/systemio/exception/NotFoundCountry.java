package by.rishat.ws.systemio.exception;

import org.springframework.http.HttpStatus;

public class NotFoundCountry extends ApiException {

    public NotFoundCountry(String message) {
        super("NO_COUNTRY_AVAILABLE",
                message,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
