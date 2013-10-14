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
 * A subunit of currency.
 * <p>
 * In a currency, there is usually a main unit (base), and a subunit that is a fraction of the main unit.
 * In some countries, there are multiple levels of subunits.
 * <p>
 * Currency subunits can be distinguished by separate {@link #getId()} Ids. Each Id is unique <b>per currency</b>.
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
 * @see <a href="http://publications.europa.eu/code/en/en-5000500.htm#fn-4">EU: Currency subunit</a>
 */
public interface SubUnit extends CurrencyUnit {
	
	/**
	 * Get the identifier of this subunit.
	 * This identifier is unique <b>per currency</b>.
	 * 
	 * @return The identifier, never null.
	 */
	public String getId();
		
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
