/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 */
package javax.money;

/**
 * General Monetary exception.
 * A base exception for JSR 354, 
 * extending {@link RuntimeException}
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 *
 * @author Werner Keil
 */
public class MonetaryException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9039026008242959369L;

	/**
     * Constructs a <code>MonetaryException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public MonetaryException(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public MonetaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
