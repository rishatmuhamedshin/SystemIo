package by.rishat.ws.systemio.service.payment.mock;

public class Paypal {
    public void makePayment(Integer price) throws Exception {
        if (price > 100000) {
            throw new Exception("Превышен лимит в 100000");
        }
    }
}
