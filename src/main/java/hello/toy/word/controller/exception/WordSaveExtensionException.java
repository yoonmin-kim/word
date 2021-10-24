package hello.toy.word.controller.exception;

public class WordSaveExtensionException extends Exception {
	public WordSaveExtensionException() {
		super();
	}

	public WordSaveExtensionException(String message) {
		super(message);
	}

	public WordSaveExtensionException(String message, Throwable cause) {
		super(message, cause);
	}

	public WordSaveExtensionException(Throwable cause) {
		super(cause);
	}

	protected WordSaveExtensionException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
