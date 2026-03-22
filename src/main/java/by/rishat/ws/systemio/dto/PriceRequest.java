package by.rishat.ws.systemio.dto;

import by.rishat.ws.systemio.validation.annotation.TaxNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на расчет стоимости товара")
public class PriceRequest {

    @NotNull(message = "ID продукта обязателен")
    @Schema(description = "Идентификатор продукта из базы данных", example = "1")
    private Integer product;

    @NotBlank(message = "Налоговый номер обязателен")
    @TaxNumber
    @Schema(description = "Налоговый номер страны (формат: CountryCode + цифры)",
            example = "DE123456789",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String taxNumber;

    @Pattern(regexp = "^P\\d{1,3}$", message = "Купон должен быть в формате P15")
    @Schema(description = "Код скидочного купона", example = "P10", nullable = true)
    private String couponCode;
}