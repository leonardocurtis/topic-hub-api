package io.github.leo.topichub.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    private static final Pattern LOWERCASE = Pattern.compile("(?=.*[a-z])");
    private static final Pattern UPPERCASE = Pattern.compile("(?=.*[A-Z])");
    private static final Pattern DIGIT = Pattern.compile("(?=.*\\d)");
    private static final Pattern SPECIAL = Pattern.compile("(?=.*[@$!%*?&#])");
    private static final Pattern MIN_LENGTH = Pattern.compile(".{8,}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null || value.isBlank()) return true;

        ctx.disableDefaultConstraintViolation();

        boolean valid = true;

        if (!LOWERCASE.matcher(value).find()) {
            addMessage(ctx, "Password must contain at least one lowercase letter");
            valid = false;
        }
        if (!UPPERCASE.matcher(value).find()) {
            addMessage(ctx, "Password must contain at least one uppercase letter");
            valid = false;
        }
        if (!DIGIT.matcher(value).find()) {
            addMessage(ctx, "Password must contain at least one digit");
            valid = false;
        }
        if (!SPECIAL.matcher(value).find()) {
            addMessage(ctx, "Password must contain at least one special character (@$!%*?&#)");
            valid = false;
        }
        if (!MIN_LENGTH.matcher(value).find()) {
            addMessage(ctx, "Password must be at least 8 characters long");
            valid = false;
        }

        return valid;
    }

    private void addMessage(ConstraintValidatorContext ctx, String message) {
        ctx.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
