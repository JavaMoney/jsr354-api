/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

/**
 * Factory for {@link MonetaryAmount} instances for a given type.
 * <p>
 * This singleton allows to get {@link MonetaryAmount} instances depending on
 * the precision and scale requirements. If not defined a default
 * {@link MonetaryContext} is used, which can also be configured by adding a
 * file {@code /javamoney.properties} to the classpath, with the following
 * content:
 * 
 * <pre>
 * # Default MathContext for Money
 * #-------------------------------
 * # Custom MonetaryContext, overrides default entries from 
 * # org.javamoney.moneta.Money.monetaryContext
 * # RoundingMode hereby is optional (default = HALF_EVEN)
 * Money.defaults.precision=256
 * Money.defaults.scale=-1
 * Money.attributes.java.math.RoundingMode=RoundingMode.HALF_EVEN
 * </pre>
 * 
 * whereas {@code Money} should be replaced with the simple class name of the
 * implementation class.
 * <p>
 * <h2>Implementation specification</h2> Due to the builder like style,
 * instances of this interface are <b>not</b> required to be thread-safe!
 * 
 * @version 0.6.1
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryAmountFactory {

	/**
	 * Access the {@link MonetaryAmount} implementation type.
	 * 
	 * @return the {@link MonetaryAmount} implementation type, never
	 *         {@code null}.
	 */
	public Class<? extends MonetaryAmount> getAmountType();

	/**
	 * Sets the {@link CurrencyUnit} to be used.
	 * 
	 * @param currencyCode
	 *            the currencyCode of the currency to be used, not {@code null}
	 * @throws UnknownCurrencyException
	 *             if the {@code currencyCode} is not resolvable.
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory withCurrency(String currencyCode);

	/**
	 * Sets the {@link CurrencyUnit} to be used.
	 * 
	 * @param currency
	 *            the {@link CurrencyUnit} to be used, not {@code null}
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory with(CurrencyUnit currency);

	/**
	 * Sets the number to be used.
	 * 
	 * @param number
	 *            the number to be used
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory with(double number);

	/**
	 * Sets the number to be used.
	 * 
	 * @param number
	 *            the number to be used
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory with(long number);

	/**
	 * Sets the number to be used.
	 * 
	 * @param number
	 *            the number to be used, not {@code null}.
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory with(Number number);

	/**
	 * Sets the {@link MonetaryContext} to be used.
	 * 
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, not {@code null}.
	 * @throws MonetaryException
	 *             when the {@link MonetaryContext} given exceeds the
	 *             capabilities supported by this factory type.
	 * @return This factory instance, for chaining.
	 * @see #getMaximalMonetaryContext()
	 */
	public MonetaryAmountFactory with(
			MonetaryContext monetaryContext);

	/**
	 * Uses an arbitrary {@link MonetaryAmount} to initialize this factory.
	 * Properties reused are:
	 * <ul>
	 * <li>CurrencyUnit,</li>
	 * <li>Number value,</li>
	 * <li>MonetaryContext.</li>
	 * </ul>
	 * 
	 * @param amount
	 *            the amount to be used, not {@code null}.
	 * @throws MonetaryException
	 *             when the {@link MonetaryContext} implied by
	 *             {@code amount.getMonetaryContext()} exceeds the capabilities
	 *             supported by this factory type.
	 * @return this factory instance, for chaining.
	 */
	public MonetaryAmountFactory with(MonetaryAmount amount);

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the current data
	 * set on this factory.
	 * 
	 * @return the corresponding {@link MonetaryAmount}.
	 * @see #getAmountType()
	 */
	public MonetaryAmount create();

	/**
	 * Returns the default {@link MonetaryContext} used, when no
	 * {@link MonetaryContext} is provided.
	 * <p>
	 * The default context is not allowed to exceed the capabilities of the
	 * maximal {@link MonetaryContext} supported.
	 * 
	 * @see #getMaximalMonetaryContext()
	 * @return the default {@link MonetaryContext}, never {@code null}.
	 */
	public MonetaryContext getDefaultMonetaryContext();

	/**
	 * Returns the maximal {@link MonetaryContext} supported, for requests that
	 * exceed these maximal capabilities, an {@link ArithmeticException} must be
	 * thrown.
	 * 
	 * @return the maximal {@link MonetaryContext} supported, never {@code null}
	 */
	public MonetaryContext getMaximalMonetaryContext();

}