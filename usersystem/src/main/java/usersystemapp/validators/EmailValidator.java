package usersystemapp.validators;

import org.springframework.stereotype.Component;
import usersystemapp.constants.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements ConstraintValidator<Email, CharSequence> {
    private int minUserNameLength;
    private int maxUserNameLength;
    private int maxHostNameLength;
    private Pattern pattern;

    @Override
    public void initialize(Email email) {
        this.minUserNameLength = email.minUserNameLength();
        this.maxUserNameLength = email.maxUserNameLength();
        this.maxHostNameLength = email.maxHostNameLength();
        this.pattern = Pattern.compile(email.regex());
    }

    @Override
    public boolean isValid(CharSequence chars, ConstraintValidatorContext context) {
        if (chars == null) {
            return false;
        }

        String email = chars.toString();
        int userNameLength = email.indexOf("@");
        int hostNameLength = email.length() - email.lastIndexOf("@") - 1;

        if (userNameLength < this.minUserNameLength) {
            AnnoUtil.setErrorMessage(context, Constants.EMAIL_LENGTH_TOO_SHORT);
            return false;
        }

        if (userNameLength > this.maxUserNameLength) {
            AnnoUtil.setErrorMessage(context, Constants.EMAIL_LENGTH_TOO_LONG);
            return false;
        }

        if (hostNameLength > this.maxHostNameLength) {
            AnnoUtil.setErrorMessage(context, Constants.EMAIL_HOST_LENGTH_TOO_LONG);
            return false;
        }

        return this.pattern.matcher(email).matches();
    }
}
