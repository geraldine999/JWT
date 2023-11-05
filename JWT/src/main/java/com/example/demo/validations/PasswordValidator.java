package com.example.demo.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        if (!lengthIsValid(value)) {
            return false;
        }

        if (!hasOneUpperCaseLetter(value)) {
            return false;
        }

        if (!hasTwoNumbers(value)) {
            return false;
        }

        return !hasAnySpecialCharacters(value);
    }

    private boolean lengthIsValid(CharSequence value) {
        return value.length() >= 8 && value.length() <= 12;
    }

    private boolean hasOneUpperCaseLetter(CharSequence value) {
        return value.chars().filter(Character::isUpperCase).count() == 1;
    }

    private boolean hasTwoNumbers(CharSequence value) {
        return value.chars().filter(Character::isDigit).count() == 2;
    }

    private boolean hasAnySpecialCharacters(CharSequence value) {
        return value.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
    }

}
