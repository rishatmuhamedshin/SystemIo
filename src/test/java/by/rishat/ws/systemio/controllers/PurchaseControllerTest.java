package by.rishat.ws.systemio.controllers;

import by.rishat.ws.systemio.dto.PriceRequest;
import by.rishat.ws.systemio.dto.PurchaseRequest;
import by.rishat.ws.systemio.service.PaymentOrchestrator;
import by.rishat.ws.systemio.service.PriceCalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PriceCalculatorService priceService;

    @MockitoBean
    private PaymentOrchestrator paymentOrchestrator;

    @Test
    @DisplayName("Должен успешно рассчитать цену (200 OK)")
    void calculatePriceSuccess() throws Exception {

        PriceRequest request = new PriceRequest(1, "DE123456789", "P10");
        BigDecimal expectedPrice = new BigDecimal("107.10");

        when(priceService.calculate(eq(1), eq("DE123456789"), eq("P10")))
                .thenReturn(expectedPrice);

        mockMvc.perform(post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPrice").value(107.10));
    }

    @Test
    @DisplayName("Должен вернуть 400 Bad Request при невалидном налоговом номере")
    void calculatePriceInvalidTaxNumber() throws Exception {
        PriceRequest request = new PriceRequest(1, "INVALID_TAX", "P10");

        mockMvc.perform(post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_FAILED"));
    }

    @Test
    @DisplayName("Должен успешно выполнить покупку (200 OK)")
    void purchaseSuccess() throws Exception {
        PurchaseRequest request = new PurchaseRequest(1, "IT12345678901", "P15", "paypal");
        BigDecimal calculatedPrice = new BigDecimal("85.00");

        when(priceService.calculate(anyInt(), anyString(), anyString()))
                .thenReturn(calculatedPrice);

        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(paymentOrchestrator).executeRandom(calculatedPrice);
    }

    @Test
    @DisplayName("Должен вернуть 400, если процессор оплаты не поддерживается")
    void purchase_InvalidProcessor() throws Exception {
        PurchaseRequest request = new PurchaseRequest(1, "IT12345678901", "P15", "crypto");

        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}