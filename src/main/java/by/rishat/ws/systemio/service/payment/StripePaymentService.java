package by.rishat.ws.systemio.service.payment;

import by.rishat.ws.systemio.dto.customException.StripePaymentException;
import by.rishat.ws.systemio.service.PaymentService;
import by.rishat.ws.systemio.service.payment.mock.StripePaymentProcessor;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService {
    private final StripePaymentProcessor stripePaymentProcessor = new StripePaymentProcessor();

    @Override
    public String getName(String name) {
        return "stripe";
    }

    @Override
    public void process(double amount) {
        boolean success = stripePaymentProcessor.pay((float) amount);
        if (!success) {
            throw new StripePaymentException("Цена меньше 100");
        }
    }

}
