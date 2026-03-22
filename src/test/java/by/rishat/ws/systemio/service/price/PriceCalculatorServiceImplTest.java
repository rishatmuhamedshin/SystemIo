package by.rishat.ws.systemio.service.price;

import by.rishat.ws.systemio.entity.Coupon;
import by.rishat.ws.systemio.entity.Product;
import by.rishat.ws.systemio.exception.productException.ProductException;
import by.rishat.ws.systemio.repository.CouponRepository;
import by.rishat.ws.systemio.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceCalculatorServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private PriceCalculatorServiceImpl priceCalculatorService;

    @Test
    @DisplayName("Успешный расчет цены для Германии (19%) со скидкой 10%")
    void calculate_Success() {
        Integer productId = 1;
        String taxNumber = "DE123456789";
        String couponCode = "P10";

        Product product = new Product();
        product.setId(productId);
        product.setName("iPhone");
        product.setPrice(new BigDecimal("100.00"));

        Coupon coupon = new Coupon();
        coupon.setCode(couponCode);
        coupon.setDiscountPercent(new BigDecimal("10.00"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(couponRepository.findByCode(couponCode)).thenReturn(Optional.of(coupon));

        BigDecimal result = priceCalculatorService.calculate(productId, taxNumber, couponCode);


        assertEquals(new BigDecimal("107.10"), result);
        verify(productRepository).findById(productId);
        verify(couponRepository).findByCode(couponCode);
    }

    @Test
    @DisplayName("Расчет цены без купона для Италии (22%)")
    void calculate_Success_NoCoupon() {
        // Given
        Integer productId = 2;
        String taxNumber = "IT12345678901";

        Product product = new Product();
        product.setPrice(new BigDecimal("100.00"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        BigDecimal result = priceCalculatorService.calculate(productId, taxNumber, null);

        assertEquals(new BigDecimal("122.00"), result);
        verify(couponRepository, never()).findByCode(anyString());
    }

    @Test
    @DisplayName("Должен выбросить исключение, если продукт не найден")
    void calculate_ProductNotFound_ThrowsException() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductException.class, () ->
                priceCalculatorService.calculate(999, "DE123456789", null)
        );
    }
}