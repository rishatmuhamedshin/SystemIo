package by.rishat.ws.systemio.service;

import java.math.BigDecimal;

public interface PriceCalculatorService {
    BigDecimal calculate(Integer productId, String taxNumber, String couponCode);
}
