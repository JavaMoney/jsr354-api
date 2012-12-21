package javax.money;

import java.util.Locale;

/**
 * This instance defines a rounding mode that can be applied to amounts.
 * 
 * @author Anatole Tresch
 */
public interface Rounding {
	/**
	 * The ID uniquely identifies a rounding type.
	 * 
	 * @return The rounding ID, not null.
	 */
	String getID();

	/**
	 * The name of the rounding, using the default {@link Locale}.
	 * 
	 * @return The name of the rounding, not null.
	 */
	String getName();

	/**
	 * The name of the rounding, using the {@link Locale} provided.
	 * 
	 * @param locale
	 *            the target locale
	 * @return The name of the rounding, not null.
	 */
	String getName(Locale locale);

	/**
	 * This method is called by the Amount maths when rounding is required.
	 * 
	 * @param money
	 *            the amount
	 * @return the rounded amount
	 * @throws ArithmeticException
	 *             if rounding of the given amount fails.
	 */
	Amount round(Amount money);

}
