package by.rishat.ws.systemio.service;

import java.math.BigDecimal;

//Реализуем Адаптер, для "Учитывайте возможность добавления новых платежных процессоров."
public interface PaymentService {
    String getName();
    void process(BigDecimal amount);
}
