package by.rishat.ws.systemio.dto;

import by.rishat.ws.systemio.validation.annotation.TaxNumber;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Запрос на проведение покупки")
public class PurchaseRequest {

    @NotNull(message = "ID продукта обязательно")
    @Schema(description = "Идентификатор продукта", example = "1")
    private Integer product;

    @NotBlank(message = "Налоговый номер обязателен")
    @TaxNumber
    @Schema(description = "Налоговый номер (формат зависит от страны)", example = "DE123456789")
    private String taxNumber;

    @Pattern(regexp = "^P\\d{1,3}$", message = "Купон должен быть в формате P15")
    @Schema(description = "Промокод (P + цифры)", example = "P10", nullable = true)
    private String couponCode;

    @NotBlank(message = "Payment processor is required")
    @Pattern(regexp = "^(paypal|stripe)$", message = "Поддерживаются: paypal, stripe")
    @Schema(
            description = "Тип платежной системы",
            allowableValues = {"paypal", "stripe"},
            example = "paypal"
    )
    private String paymentProcessor;
}