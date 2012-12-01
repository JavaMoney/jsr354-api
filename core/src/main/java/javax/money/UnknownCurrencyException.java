/*
 *  Copyright 2009-2012 Werner Keil
 *
 */
package javax.money;

/**
 * Exception thrown when the requested currency is unknown to the currency system in use.
 * <p>
 * For example, this exception would be thrown when trying to obtain a
 * currency using an unrecognized currency code or locale.
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 *
 * @author Werner Keil
 */
public class UnknownCurrencyException extends IllegalArgumentException {

    /** Serialization lock. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param message  the message, may be null
     */
    public UnknownCurrencyException(String message) {
    	// TODO Not Implemented yet
    }

}
