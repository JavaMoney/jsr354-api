/**
 * 
 */
package javax.money;

/**
 * @author Werner Keil
 *
 */
public interface Monetary<T> extends Comparable<Monetary<T>> {
	
    /**
     * Gets the currency.
     * 
     * @return the currency, never null
     */
    public CurrencyUnit getCurrency();
    	
    /**
     * Gets the monetary amount.
     * <p>
     * This returns the monetary value as a {@code T}.
     * The scale will be the scale of this money.
     * 
     * @return the amount, never null
     */
    public T getAmount();
    
    /**
     * Gets the scale of the {@code T} amount.
     * <p>
     * The scale has the same meaning as in {@link BigDecimal}.
     * Positive values represent the number of decimal places in use.
     * For example, a scale of 2 means that the money will have two decimal places
     * such as 'USD 43.25'.
     * <p>
     * For {@code Money}, the scale is fixed and always matches that of the currency.
     * 
     * @return the scale in use, typically 2 but could be 0, 1 and 3
     */
    public int getScale();
}
