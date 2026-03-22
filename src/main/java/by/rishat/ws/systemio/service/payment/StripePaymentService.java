package by.rishat.ws.systemio.service.payment;

import by.rishat.ws.systemio.exception.paymentExceptions.StripePaymentException;
import by.rishat.ws.systemio.service.PaymentService;
import by.rishat.ws.systemio.service.payment.mock.StripePaymentProcessor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentService implements PaymentService {
    private final StripePaymentProcessor stripePaymentProcessor = new StripePaymentProcessor();

    @Override
    public String getName() {
        return "stripe";
    }

    @Override
    public void process(BigDecimal amount) {
        boolean success = stripePaymentProcessor.pay((amount.floatValue()));
        if (!success) {
            throw new StripePaymentException("Цена меньше 100");
        }
    }

}
