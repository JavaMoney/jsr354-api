package javax.money.tck;

public final class TCKValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TCKValidationException(String message) {
		super(message);
	}

	public TCKValidationException(String message, Throwable e) {
		super(message, e);
	}

}
