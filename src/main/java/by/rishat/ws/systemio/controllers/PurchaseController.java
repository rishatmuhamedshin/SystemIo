package by.rishat.ws.systemio.controllers;

import by.rishat.ws.systemio.dto.PriceRequest;
import by.rishat.ws.systemio.dto.PurchaseRequest;
import by.rishat.ws.systemio.service.PaymentOrchestrator;
import by.rishat.ws.systemio.service.PriceCalculatorService;
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
public class PurchaseController {

    private final PriceCalculatorService priceService;
    private final PaymentOrchestrator paymentOrchestrator;

    @PostMapping("/calculate-price")
    public ResponseEntity<Map<String, BigDecimal>> calculate(@Valid @RequestBody PriceRequest request) {
        BigDecimal finalPrice = priceService.calculate(
                request.getProduct(),
                request.getTaxNumber(),
                request.getCouponCode()
        );
        return ResponseEntity.ok(Map.of("finalPrice", finalPrice));
    }

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