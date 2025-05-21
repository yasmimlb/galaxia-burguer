package com.galaxiaburguer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValorDoLancheValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValorDoLancheValido {
    String message() default "Valor do lanche inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}