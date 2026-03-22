package by.rishat.ws.systemio.dto;

import by.rishat.ws.systemio.validation.annotation.TaxNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceRequest {
    @NotNull
    private Integer product;

    @NotBlank(message = "Налоговый номер обязателен")
    @TaxNumber
    private String taxNumber;

    @Pattern(regexp = "^P\\d{1,3}$", message = "Купон должен быть в формате P15")
    private String couponCode;
}