package by.rishat.ws.systemio.service;

import by.rishat.ws.systemio.exception.paymentExceptions.NoProcessorAvailable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentOrchestrator {

    private final List<PaymentService> paymentServices;

    public void executeRandom(BigDecimal amount) {
        if (paymentServices.isEmpty()) {
            throw new NoProcessorAvailable("Не найден платежный процессор");
        }

        //Берем рандомный сервис
        int randomIndex = ThreadLocalRandom.current().nextInt(paymentServices.size());
        PaymentService service = paymentServices.get(randomIndex);

        log.info("Выбрали рандомный сервис: {} с ценой: {}", service.getName(), amount);

        service.process(amount);
    }
}
