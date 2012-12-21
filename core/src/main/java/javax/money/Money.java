package javax.money;

/**
 * Access point for money related core functionality.
 * 
 * @author Anatole Tresch
 */
public final class Money {

	/**
	 * Singleton accessor.
	 */
	private Money() {
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param number
	 *            The required numeric value, not null.
	 * @return The amount instance.
	 */
	public static Amount get(CurrencyUnit currency, Number number) {
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public static Amount get(CurrencyUnit currency, byte value) {
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public static Amount get(CurrencyUnit currency, short value) {
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public static Amount get(CurrencyUnit currency, int value) {
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public static Amount get(CurrencyUnit currency, float value) {
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public static Amount get(CurrencyUnit currency, double value) {
		return null;
	}

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param currency
	 *            The required currency, not null.
	 * @param value
	 *            The required numeric value.
	 * @return The amount instance.
	 */
	public MoneyMath getMoneyMath() {
		return null;
	}
}
