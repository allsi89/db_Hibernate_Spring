package gamestore.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static gamestore.constants.Constants.*;

public class UserLoginDto {
    private String email;
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @NotNull(message = EMAIL_CANNOT_BE_NULL)
    @Pattern(regexp = EMAIL_REGEXP,
            message = INCORRECT_EMAIL)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = PASSWORD_CANNOT_BE_NULL)
    @Pattern(regexp = PASS_REGEXP,
            message = PASSWORD_MUST_CONTAIN_AT_LEAST_1_UPPERCASE_1_LOWERCASE_LETTER_AND_1_DIGIT)
    @Size(min = 6,
            message = PASSWORD_MUST_BE_AT_LEAST_6_SYMBOLS_LONG)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
