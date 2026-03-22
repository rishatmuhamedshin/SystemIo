package by.rishat.ws.systemio.entity;

import by.rishat.ws.systemio.exception.NotFoundCountry;
import lombok.Getter;

@Getter
public enum Country {
    GERMANY("DE", 0.19, "^DE\\d{9}$"),
    ITALY("IT", 0.22, "^IT\\d{11}$"),
    GREECE("GR", 0.24, "^GR\\d{9}$"),
    FRANCE("FR", 0.20, "^FR[a-zA-Z]{2}\\d{9}$");

    private final String prefix;
    private final double taxRate;
    private final String pattern;

    Country(String prefix, double taxRate, String pattern) {
        this.prefix = prefix;
        this.taxRate = taxRate;
        this.pattern = pattern;
    }

    public static Country getByNumber(String taxNumber) {
        return java.util.Arrays.stream(values())
                .filter(c -> taxNumber.startsWith(c.prefix))
                .findFirst()
                .orElseThrow(() -> new NotFoundCountry("Не известный prefix страны"));
    }
}
