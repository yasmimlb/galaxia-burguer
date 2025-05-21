package com.galaxiaburguer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValorDoLancheValidator implements ConstraintValidator<ValorDoLancheValido, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        // Valores absurdamente altos não são permitidos
        return value > 0 && value <= 1000;
    }
}