package by.rishat.ws.systemio.exception.couponException;

import by.rishat.ws.systemio.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidCouponException extends ApiException {

    public InvalidCouponException(String message) {
        super("INVALID_COUPON",
                message,
                HttpStatus.BAD_REQUEST
        );
    }
}