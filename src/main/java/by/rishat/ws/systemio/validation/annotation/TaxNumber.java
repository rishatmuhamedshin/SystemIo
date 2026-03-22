package by.rishat.ws.systemio.validation.annotation;

import by.rishat.ws.systemio.validation.validators.TaxNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = TaxNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TaxNumber {
    String message() default "Неверный формат налогового номера для выбранной страны";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}