/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryException;
import java.util.Objects;

/**
 * Exception thrown when a monetary conversion operation fails.
 *
 * @author Werner Keil
 * @author Stephen Colebourne
 * @author Anatole Tresch
 */
public class CurrencyConversionException extends MonetaryException {

    /**
     * Serialization lock.
     */
    private static final long serialVersionUID = -7743240650686883450L;

    /**
     * Base currency.
     */
    private final CurrencyUnit base;
    /**
     * Terminating currency.
     */
    private final CurrencyUnit term;
    /**
     * The acquired {@link ConversionContext}, may be null.
     */
    private final ConversionContext conversionContext;

    /**
     * Constructs an <code>CurrencyConversionException</code> with the specified
     * detail message, timestamp, source and target currency.
     *
     * @param base              the source currency, may be null.
     * @param term              the target currency, may be null.
     * @param conversionContext the {@link javax.money.convert.ConversionContext} in place.
     * @param message           the detail message.
     */
    public CurrencyConversionException(CurrencyUnit base, CurrencyUnit term,
                                       ConversionContext conversionContext, String message) {
        super("Cannot convert " + String.valueOf(base) + " into "
                + String.valueOf(term) + ": " + message);
        this.base = base;
        this.term = term;
        this.conversionContext = conversionContext;
    }

    /**
     * Constructs an <code>CurrencyConversionException</code> with the specified
     * source and target currency.
     *
     * @param base the source currency, may be null.
     * @param term the target currency, may be null.
     * @param conversionContext the {@link javax.money.convert.ConversionContext} in place.
     */
    public CurrencyConversionException(CurrencyUnit base, CurrencyUnit term,
                                       ConversionContext conversionContext) {
        super("Cannot convert " + String.valueOf(base) + " into "
                + String.valueOf(term));
        this.base = base;
        this.term = term;
        this.conversionContext = conversionContext;
    }

    /**
     * Constructs a new exception with the specified source and target currency,
     * detail message and cause.
     * <p/>
     * <p/>
     * Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail message.
     *
     * @param base    the source currency, may be null.
     * @param term    the target currency, may be null.
     * @param message the detail message (which is saved for later retrieval by the
     *                {@link Throwable#getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link Throwable#getCause()} method). (A <tt>null</tt> value
     *                is permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @param conversionContext the {@link javax.money.convert.ConversionContext} in place.
     */
    public CurrencyConversionException(CurrencyUnit base, CurrencyUnit term,
                                       ConversionContext conversionContext, String message, Throwable cause) {
        super("Cannot convert " + String.valueOf(base) + " into "
                + String.valueOf(term)
                + (Objects.nonNull(message) ? ": " + message : ""), cause);
        this.base = base;
        this.term = term;
        this.conversionContext = conversionContext;
    }

    /**
     * Gets the first currency at fault.
     *
     * @return the currency at fault, may be null
     */
    public CurrencyUnit getBaseCurrency() {
        return base;
    }

    /**
     * Gets the second currency at fault.
     *
     * @return the currency at fault, may be null
     */
    public CurrencyUnit getTermCurrency() {
        return term;
    }

    /**
     * Gets the queried timestamp at fault.
     *
     * @return the queried timestamp, or {@code null}.
     */
    public ConversionContext getConversionContext() {
        return this.conversionContext;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CurrencyConversionException [base=" + base + ", term=" + term
                + ", conversionContext=" + conversionContext + "]: "
                + getMessage();
    }

}
