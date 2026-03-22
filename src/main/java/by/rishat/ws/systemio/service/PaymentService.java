package by.rishat.ws.systemio.service;

//Реализуем Адаптер, для "Учитывайте возможность добавления новых платежных процессоров."
public interface PaymentService {
    String getName(String name);
    void process(double amount);
}
