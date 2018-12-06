package usersystemapp.validators;

import javax.validation.ConstraintValidatorContext;

public final class AnnoUtil {

    public AnnoUtil() {
    }

    public static void setErrorMessage(final ConstraintValidatorContext context, final String errorMessage) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
    }
}
