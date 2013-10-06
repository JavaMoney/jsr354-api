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
package javax.money;

/**
 * A unit of currency.
 * <p>
 * This interface represents a unit of currency such as the British Pound, Euro,
 * US Dollar, Bitcoin or other. It is mainly defined to provide interoperability
 * between different implementations.
 * <p>
 * Currencies can be distinguished by separate {@link #getCurrencyCode()} codes,
 * similar to {@link java.util.Currency}.
 * <p>
 * Implementation of this class are required to be
 * <ul>
 * <li>thread-safe
 * <li>immutable
 * <li>serializable.
 * 
 * @version 0.4
 * @author Werner Keil
 * @author Stephen Colebourne
 * @author Anatole Tresch
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Currency">Wikipedia: Currency</a>
 */
public interface CurrencyUnit {

	/**
	 * Gets the unique currency code, the effective code depends on the
	 * currency.
	 * <p>
	 * Since each currency is identified by this code, the currency code is
	 * required to be defined for every {@link CurrencyUnit} and not
	 * {@code null} or empty.
	 * 
	 * @return the currency code, never {@code null}. For the ISO-4217
	 *         namespace, this this will be the three letter ISO-4217 code.
	 *         However, alternate currencies can have different codes. Also
	 *         there is no constraint about the formatting of alternate codes,
	 *         despite they fact that the currency codes must be unique.
	 */
	public String getCurrencyCode();

}
