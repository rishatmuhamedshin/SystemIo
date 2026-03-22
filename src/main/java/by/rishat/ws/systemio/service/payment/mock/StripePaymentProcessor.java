package by.rishat.ws.systemio.service.payment.mock;

public class StripePaymentProcessor {
    public boolean pay(Float price) {
        return price >= 100;
    }
}
