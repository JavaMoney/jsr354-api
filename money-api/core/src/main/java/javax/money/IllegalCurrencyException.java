/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 */
package javax.money;

/**
 * Exception thrown when the requested currency is illegal. This is the base
 * exception for all illegal arguments in JSR 354, extending
 * {@link IllegalArgumentException}
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 * 
 * @author Werner Keil
 * @author Stephen Colebourne
 */
public class IllegalCurrencyException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2295538564436783042L;

	private String currencyCode;
	private String namespace;

	/**
	 * Constructs an <code>IllegalCurrencyException</code> with the specified
	 * detail message.
	 * 
	 * @param s
	 *            the detail message.
	 */
	public IllegalCurrencyException(String message) {
		super(message);
	}

	
	/**
	 * Constructs an <code>IllegalCurrencyException</code> with the specified
	 * detail message.
	 * 
	 * @param s
	 *            the detail message.
	 */
	public IllegalCurrencyException(String namespace, String code) {
		super(namespace + ':' + code);
		this.namespace = namespace;
		this.currencyCode = code;
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * <p>
	 * Note that the detail message associated with <code>cause</code> is
	 * <i>not</i> automatically incorporated in this exception's detail message.
	 * 
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link Throwable#getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link Throwable#getCause()} method). (A <tt>null</tt> value
	 *            is permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public IllegalCurrencyException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getNamespace() {
		return namespace;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IllegalCurrencyException [namespace=" + namespace
				+ ", currencyCode=" + currencyCode + "]";
	}

}
