package leeleyok.privilage.common.exception;

/**
 * 存储层异常封装
 * 
 * @author leeley
 *
 */
public class StorageException extends RuntimeException {
	
	private static final long serialVersionUID = -5864781386545774657L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
