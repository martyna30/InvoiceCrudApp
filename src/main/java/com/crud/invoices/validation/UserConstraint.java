package com.crud.invoices.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserConstraint {
    String message() default "User with such an e-mail already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String field();

}
