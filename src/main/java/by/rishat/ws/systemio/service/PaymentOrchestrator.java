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

    public void execute(BigDecimal amount, String processorName) {
        log.info("Запрос на оплату через: {}. Сумма: {}", processorName, amount);

        PaymentService selectedService = paymentServices.stream()
                .filter(service -> service.getName().equalsIgnoreCase(processorName))
                .findFirst()
                .orElseThrow(() -> new NoProcessorAvailable("Платежный процессор '" + processorName + "' не поддерживается"));

        selectedService.process(amount);
    }
}
