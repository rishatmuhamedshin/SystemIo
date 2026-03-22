package by.rishat.ws.systemio.controllers;

import by.rishat.ws.systemio.dto.PriceRequest;
import by.rishat.ws.systemio.dto.PurchaseRequest;
import by.rishat.ws.systemio.exception.ValidationErrorResponse;
import by.rishat.ws.systemio.service.PaymentOrchestrator;
import by.rishat.ws.systemio.service.PriceCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
@Tag(name = "Payments", description = "Управление расчетами и платежами")
public class PurchaseController {

    private final PriceCalculatorService priceService;
    private final PaymentOrchestrator paymentOrchestrator;

    @Operation(summary = "Расчет итоговой цены", description = "Вычисляет цену товара с учетом налоговой ставки страны и скидочного купона.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный расчет",
                    content = @Content(schema = @Schema(example = "{\"finalPrice\": 107.10}"))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Продукт или купон не найден")
    })
    @PostMapping("/calculate-price")
    public ResponseEntity<Map<String, BigDecimal>> calculate(@Valid @RequestBody PriceRequest request) {
        BigDecimal finalPrice = priceService.calculate(
                request.getProduct(),
                request.getTaxNumber(),
                request.getCouponCode()
        );
        return ResponseEntity.ok(Map.of("finalPrice", finalPrice));
    }

    @Operation(
            summary = "Проведение покупки",
            description = "Рассчитывает финальную цену и инициирует оплату. " +
                    "Поддерживаемые процессоры: **paypal** (ожидает оплату в целых числах) и **stripe** (работает с дробными)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оплата прошла успешно"),
            @ApiResponse(responseCode = "422", description = "Ошибка стороннего платежного шлюза")
    })
    @PostMapping("/purchase")
    public ResponseEntity<Void> purchase(@Valid @RequestBody PurchaseRequest request) {
        BigDecimal finalPrice = priceService.calculate(
                request.getProduct(),
                request.getTaxNumber(),
                request.getCouponCode()
        );

        paymentOrchestrator.executeRandom(finalPrice);

        return ResponseEntity.ok().build();
    }
}