package br.com.ferreiradev.fmu.core.domain.constants;

public final class ErrorConstants {
    public static final String NOT_FOUND_MESSAGE = "No resource found";
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid provided credentials";
    public static final String UNAUTHORIZED_ERROR_MESSAGE = "There's an error in the authentication process";
    public static final String EXPIRED_SESSION_MESSAGE = "Your session has been already expired, please login again.";
    public static final String ACCESS_DENIED_MESSAGE = "You don't have permission to access this resource";

    // Fields Validations
    public static final String INVALID_FIELD_GENERIC_MESSAGE = "Request body contains invalid field(s)";
    public static final String INVALID_EMAIL_MESSAGE = "Email provided is invalid";
    public static final String REQUIRED_FIELD_MESSAGE = "Field is required";

}
