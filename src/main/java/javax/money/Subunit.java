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
 * <h4>Implementation specification</h4>
 * Implementation of this class
 * <ul>
 * <li>are required to be implement {@code equals/hashCode} considering the
 * concrete implementation type and currency code.
 * <li>are required to be thread-safe
 * <li>are required to be immutable
 * <li>are highly recommended to be serializable.
 * </ul>
 * 
 * @author Werner Keil
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Denomination_(currency)">Wikipedia: Denomination (currency)</a>
 */
public interface Subunit extends CurrencyUnit {

	/**
	 * Gets the unique currency code, the effective code depends on the
	 * currency.
	 * <p>
	 * Since each currency is identified by this code, the currency code is
	 * required to be defined for every {@link Subunit} and not
	 * {@code null} or empty.
	 * <p>
	 * For ISO codes the 3-letter ISO code should be returned. For non ISO
	 * currencies no constraints are defined.
	 * 
	 * @return the currency code, never {@code null}. For the ISO-4217
	 *         namespace, this this will be the three letter ISO-4217 code.
	 *         However, alternate currencies can have different codes. Also
	 *         there is no constraint about the formatting of alternate codes,
	 *         despite they fact that the currency codes must be unique.
	 */
	public String getCurrencyCode();
	
    /**
     * Gets the number of fraction digits used with this subunit.
     * For example, the default number of fraction digits for the Euro is 2,
     * while for the Japanese Yen it's 0.
     * In the case of pseudo-currencies, such as IMF Special Drawing Rights,
     * -1 is returned.
     *
     * @return the number of fraction digits used with this subunit
     */
    public int getFractionDigits();

}
