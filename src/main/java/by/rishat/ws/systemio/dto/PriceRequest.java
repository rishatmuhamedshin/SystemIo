package by.rishat.ws.systemio.dto;

import by.rishat.ws.systemio.validation.annotation.TaxNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PriceRequest {
    @NotNull
    private Integer product;

    @TaxNumber
    private String taxNumber;

    @Pattern(regexp = "^P\\d{1,3}$", message = "Купон должен быть в формате P15")
    private String couponCode;
}