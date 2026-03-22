package by.rishat.ws.systemio.service;

//Реализуем Адаптер, для "Учитывайте возможность добавления новых платежных процессоров."
public interface PaymentService {
    String getName();
    void process(double amount);
}
