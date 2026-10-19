package dao;

/** DB操作の失敗。SQLException をそのままサーブレットに見せないために包む。 */
public class DataAccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
