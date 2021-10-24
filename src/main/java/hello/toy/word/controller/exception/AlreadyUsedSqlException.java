package hello.toy.word.controller.exception;

public class AlreadyUsedSqlException extends Exception {
	public AlreadyUsedSqlException() {
		super();
	}

	public AlreadyUsedSqlException(String message) {
		super(message);
	}

	public AlreadyUsedSqlException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyUsedSqlException(Throwable cause) {
		super(cause);
	}

	protected AlreadyUsedSqlException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
