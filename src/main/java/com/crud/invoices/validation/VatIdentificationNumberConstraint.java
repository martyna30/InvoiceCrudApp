package com.crud.invoices.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VatIdentificationNumberValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VatIdentificationNumberConstraint {
    String message() default "Contractor with the same VATIN number already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String field();

}
