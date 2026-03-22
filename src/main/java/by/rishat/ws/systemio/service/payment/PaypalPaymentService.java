package by.rishat.ws.systemio.service.payment;

import by.rishat.ws.systemio.exception.paymentExceptions.PaypalPaymentException;
import by.rishat.ws.systemio.service.PaymentService;
import by.rishat.ws.systemio.service.payment.mock.Paypal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PaypalPaymentService implements PaymentService {
    private final Paypal paypal = new Paypal();

    @Override
    public String getName() {
        return "paypalProcessor";
    }

    @Override
    public void process(BigDecimal amount) {
        int intAmount = amount.setScale(0, RoundingMode.HALF_UP).intValue();
        try {
            paypal.makePayment(intAmount);
        } catch (Exception e) {
            throw new PaypalPaymentException(e.getMessage());
        }
    }
}
