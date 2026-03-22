package by.rishat.ws.systemio.service.price;

import by.rishat.ws.systemio.entity.Country;
import by.rishat.ws.systemio.exception.couponException.InvalidCouponException;
import by.rishat.ws.systemio.exception.productException.ProductException;
import by.rishat.ws.systemio.repository.CouponRepository;
import by.rishat.ws.systemio.repository.ProductRepository;
import by.rishat.ws.systemio.service.PriceCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    @Override
    public BigDecimal calculate(Integer productId, String taxNumber, String couponCode) {

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Такого продукта не существует"));

        BigDecimal price = product.getPrice();

        log.debug("Начальная цена продукта '{}': {}", product.getName(), price);
        if (couponCode != null && !couponCode.isEmpty()) {
            var coupon = couponRepository.findByCode(couponCode)
                    .orElseThrow(() -> new InvalidCouponException( "Купона " + couponCode + " не существует"));

            BigDecimal discountPercent = coupon.getDiscountPercent();
            BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                    discountPercent.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)
            );
            price = price.multiply(discountMultiplier);
            log.info("Купон применен: {}. Скидка: {}%. Цена после скидки: {}",
                    couponCode, discountPercent, price);
        }

        Country country = Country.getByNumber(taxNumber);
        BigDecimal taxRate = BigDecimal.valueOf(country.getTaxRate());
        log.debug("Страна: {} со ставкой налога: {}", country.name(), taxRate);

        BigDecimal taxMultiplier = BigDecimal.ONE.add(taxRate);
        BigDecimal finalPrice = price.multiply(taxMultiplier);

        log.info("Окончательная цена {}", finalPrice);

        return finalPrice.setScale(2, RoundingMode.HALF_UP);
    }
}