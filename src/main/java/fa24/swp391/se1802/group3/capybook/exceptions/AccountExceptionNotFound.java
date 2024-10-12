package fa24.swp391.se1802.group3.capybook.exceptions;


public class AccountExceptionNotFound extends RuntimeException {
    public AccountExceptionNotFound() {
    }

    public AccountExceptionNotFound(String message) {
        super(message);
    }

    public AccountExceptionNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountExceptionNotFound(Throwable cause) {
        super(cause);
    }

    public AccountExceptionNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
