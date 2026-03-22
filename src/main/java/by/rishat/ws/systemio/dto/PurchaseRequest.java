package by.rishat.ws.systemio.dto;

import by.rishat.ws.systemio.validation.annotation.TaxNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {

    @NotNull(message = "ID продукта обязательно")
    private Integer product;

    @NotBlank(message = "Налоговый номер обязателен")
    @TaxNumber
    private String taxNumber;

    @Pattern(regexp = "^P\\d{1,3}$", message = "Купон должен быть в формате P15")
    private String couponCode;

    @NotBlank(message = "Payment processor is required")
    @Pattern(regexp = "^(paypal|stripe)$", message = "Поддерживаются: paypal, stripe")
    private String paymentProcessor;
}