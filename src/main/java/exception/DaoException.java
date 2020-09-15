package exception;

public class DaoException extends RuntimeException {
    public DaoException(String message, String message1) {
        super("DaoException");
    }
}
