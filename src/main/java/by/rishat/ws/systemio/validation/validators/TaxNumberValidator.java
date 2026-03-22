package by.rishat.ws.systemio.validation.validators;

import by.rishat.ws.systemio.entity.Country;
import by.rishat.ws.systemio.validation.annotation.TaxNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import static java.util.Arrays.stream;


public class TaxNumberValidator implements ConstraintValidator<TaxNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return stream(Country.values())
                .anyMatch(country -> value.matches(country.getPattern()));
    }
}