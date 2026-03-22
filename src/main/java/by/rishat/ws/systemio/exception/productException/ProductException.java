package by.rishat.ws.systemio.exception.productException;

import by.rishat.ws.systemio.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ProductException extends ApiException {

    public ProductException(String message) {
        super("PRODUCT_NOT_FOUND",
                message,
                HttpStatus.NOT_FOUND
        );
    }
}