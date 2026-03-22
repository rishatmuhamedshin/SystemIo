package by.rishat.ws.systemio.service.payment;

import by.rishat.ws.systemio.dto.customException.paymentExceptions.PaypalPaymentException;
import by.rishat.ws.systemio.service.PaymentService;
import by.rishat.ws.systemio.service.payment.mock.Paypal;
import org.springframework.stereotype.Service;

@Service
public class PaypalPaymentService implements PaymentService {
    private final Paypal paypal = new Paypal();

    @Override
    public String getName() {
        return "paypalProcessor";
    }

    @Override
    public void process(double amount) {
        int intAmount = (int) Math.round(amount);

        try {
            paypal.makePayment(intAmount);
        } catch (Exception e) {
            throw new PaypalPaymentException(e.getMessage());
        }
    }
}
