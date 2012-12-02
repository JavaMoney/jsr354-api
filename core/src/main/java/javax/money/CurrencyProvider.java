/*
 * JSR 354: Money and Currency API
 */
package javax.money;

/**
 * @author Werner Keil
 *
 */
public interface CurrencyProvider {
	CurrencyUnit getCurrency();
}
