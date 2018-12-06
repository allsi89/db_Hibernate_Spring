package gamestore.constants;

public class Constants {

    public static final String APP_START_STR = "App started. Enter input or \"STOP\" to end the application.";
    public static final String STOP = "STOP";
    public static final String REGISTER_USER = "RegisterUser";
    public static final String LOGIN_USER = "LoginUser";
    public static final String LOGOUT = "Logout";
    public static final String ADD_GAME = "AddGame";
    public static final String NO_LOGGED_USER = "No logged user.";
    public static final String INCORRECT_URL = "Incorrect URL";
    public static final String IMAGE_THUMBNAIL = "image_thumbnail";
    public static final String RELEASE_DATE = "release_date";
    public static final String TEXT = "text";


    public static final String USER_DOES_NOT_EXIST = "User does not exist.";
    public static final String INCORRECT_USERNAME_PASSWORD = "Incorrect username / password";
    public static final String LOGGED_IN = "Successfully logged in ";
    public static final String SESSION_IS_TAKEN = "Session is taken.";
    public static final String CANNOT_LOG_OUT_NO_USER_WAS_LOGGED_IN = "Cannot log out. No user was logged in.";
    public static final String USER_S_SUCCESSFULLY_LOGGED_OUT = "User %s successfully logged out";
    public static final String INCORRECT_EMAIL = "Incorrect email.";
    public static final String PASSWORD_CANNOT_BE_NULL = "Password cannot be null.";
    public static final String PASSWORD_MUST_CONTAIN_AT_LEAST_1_UPPERCASE_1_LOWERCASE_LETTER_AND_1_DIGIT = "Password must contain at least 1 uppercase, 1 lowercase letter and 1 digit.";
    public static final String PASSWORD_MUST_BE_AT_LEAST_6_SYMBOLS_LONG = "Password must be at least 6 symbols long.";
    public static final String EMAIL_CANNOT_BE_NULL = "Email cannot be null.";
    public static final String USER_ALREADY_EXISTS = "User already exists.";
    public static final String WAS_REGISTERED = " was registered";
    public static final String PASSWORDS_DON_T_MATCH = "Passwords don't match.";
    public static final String TITLE_CANNOT_BE_NULL = "Title cannot be null.";
    public static final String INVALID_INPUT = "Invalid input";


    //public static final String URL_REGEXP = "^((http|https):\\/\\/)?www\\.youtube\\.com\\/watch\\?v=(?=.*[a-z])(?=.*[A-Z]).{11}$";
    public static final String TITLE_REGEXP = "^[A-Z].+.{3,100}";
    public static final String PASS_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$";
    public static final String EMAIL_REGEXP = "[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-z]{2,4}";
    public static final String INVALID_TRAILER_LINK = "Invalid trailer link.";
    public static final String DESCRIPTION_SHOULD_BE_AT_LEAST_20_WSYMBOLS_LONG = "Description should be at least 20 wsymbols long.";
    public static final String GAME_ALREADY_EXISTS = "Game already exists.";
    public static final String ADDED_S = "Added %s";
    public static final String THERE_IS_NO_SUCH_GAME = "There is no such game.";
    public static final String GAME_DOESN_T_EXIST = "Game doesn't exist.";
    public static final String EDIT_GAME = "EditGame";
    public static final String DELETE_GAME = "DeleteGame";
    public static final String ALL_GAME = "AllGame";
    public static final String DETAIL_GAME = "DetailGame";
    public static final String NO_VALID_INPUT = "No valid input";
    public static final String YOU_MUST_BE_ADMIN_TO_DELETE_A_GAME = "You must be ADMIN to delete a game.";
    public static final String TITLE_S = "Title: %s";
    public static final String PRICE_2_F = "Price: %.2f";
    public static final String PRICE_S = "Price: %s";
    public static final String PRICE_IS = "Price: %s";
}
