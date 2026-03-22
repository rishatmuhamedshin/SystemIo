package by.rishat.ws.systemio.validation.annotation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TaxNumber {
    String message() default "";
}
