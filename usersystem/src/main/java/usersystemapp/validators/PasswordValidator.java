package usersystemapp.validators;

import org.springframework.stereotype.Component;
import usersystemapp.constants.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class PasswordValidator  implements ConstraintValidator<Password, CharSequence> {
    private static final Pattern PATTERN_LOWER = Pattern.compile("[a-z]");
    private static final Pattern PATTERN_UPPER = Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_DIGIT = Pattern.compile("[0-9]");
    private static final Pattern PATTERN_SYMBOL = Pattern.compile("[!@#$%^&*()_+<>?]");

    private int minLength;
    private int maxLength;
    private boolean hasUpper;
    private boolean hasLower;
    private boolean hasDigit;
    private boolean hasSpecialSymbol;



    @Override
    public void initialize(Password password) {
        this.minLength = password.minLength();
        this.maxLength = password.maxLength();
        this.hasUpper = password.containsUpperCase();
        this.hasLower = password.containsLowerCase();
        this.hasDigit = password.containsDigit();
        this.hasSpecialSymbol = password.containsSpecialSymbols();

    }

    @Override
    public boolean isValid(CharSequence chars, ConstraintValidatorContext context) {
        if (chars == null) {
            AnnoUtil.setErrorMessage(context, Constants.PASSWORD_CANNOT_BE_NULL);
            return false;
        }

        if (chars.length() < this.minLength) {
            AnnoUtil.setErrorMessage(context, Constants.PASSWORD_TOO_SHORT);
            return false;
        }

        if (chars.length() > this.maxLength) {
            AnnoUtil.setErrorMessage(context, Constants.PASSWORD_TOO_LONG);
            return false;
        }

        String password = chars.toString();

        if (!PATTERN_LOWER.matcher(password).find() && this.hasLower) {
            AnnoUtil.setErrorMessage(context, Constants.PASSWORD_SHOULD_CONTAIN_LOWERCASE_LETTER);
            return false;
        }

        if (!PATTERN_UPPER.matcher(password).find() && this.hasUpper) {
            AnnoUtil.setErrorMessage(context, Constants.PASSWORD_SHOULD_CONTAIN_UPPERCASE_LETTER);
            return false;
        }

        if (!PATTERN_DIGIT.matcher(password).find() && this.hasDigit) {
            AnnoUtil.setErrorMessage(context, Constants.PASSWORD_SHOULD_CONTAIN_DIGIT);
            return false;
        }

        if (!PATTERN_SYMBOL.matcher(password).find() && this.hasSpecialSymbol) {
            AnnoUtil.setErrorMessage(context, Constants.PASSWORD_SHOULD_CONTAIN_SPECIAL_SYMBOL);
            return false;
        }

        return true;
    }
}
