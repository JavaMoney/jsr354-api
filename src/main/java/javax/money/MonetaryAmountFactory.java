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
 * 
 * @version 0.6.1
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryAmountFactory<T extends MonetaryAmount<T>> {

	/**
	 * Access the {@link MonetaryAmount} implementation type.
	 * 
	 * @return the {@link MonetaryAmount} implementation type, never
	 *         {@code null}.
	 */
	public Class<T> getAmountType();

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @return a {@code MonetaryAmount} combining the numeric value and currency
	 *         unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public T getAmount(CurrencyUnit currency, long number);

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @return a {@code MonetaryAmount} combining the numeric value and currency
	 *         unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public T getAmount(CurrencyUnit currency, double number);

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the default
	 * {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @return a {@code MonetaryAmount} combining the numeric value and currency
	 *         unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 */
	public T getAmount(CurrencyUnit currency, Number number);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using the
	 * default {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency code, not {@code null}.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 * @throws UnknownCurrencyException
	 *             if the currency code can not be resolved to
	 *             {@link CurrencyUnit}.
	 */
	public T getAmount(String currencyCode, long number);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using the
	 * default {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency code, not {@code null}.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 * @throws UnknownCurrencyException
	 *             if the currency code can not be resolved to
	 *             {@link CurrencyUnit}.
	 */
	public T getAmount(String currencyCode, double number);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using the
	 * default {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency code, not {@code null}.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 * @throws UnknownCurrencyException
	 *             if the currency code can not be resolved to
	 *             {@link CurrencyUnit}.
	 */
	public T getAmount(String currencyCode, Number number);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using the
	 * default {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            the currency code, not {@code null}.
	 * @param context
	 *            the {@link MonetaryContext} required.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 * @throws UnknownCurrencyException
	 *             if the currency code can not be resolved to
	 *             {@link CurrencyUnit}.
	 */
	public T getAmount(String currencyCode, long number,
			MonetaryContext<?> context);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using the
	 * default {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency unit, not {@code null}.
	 * @param context
	 *            the {@link MonetaryContext} required.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 * @throws UnknownCurrencyException
	 *             if the currency code can not be resolved to
	 *             {@link CurrencyUnit}.
	 */
	public T getAmount(String currencyCode,
			double number,
			MonetaryContext<?> context);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using the
	 * default {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currencyCode
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} required.
	 * @return a {@code Money} combining the numeric value and currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the default
	 *             {@link MonetaryContext} used.
	 * @throws UnknownCurrencyException
	 *             if the currency code can not be resolved to
	 *             {@link CurrencyUnit}.
	 */
	public T getAmount(String currencyCode,
			Number number,
			MonetaryContext<?> monetaryContext);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using an
	 * explicit {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code Money} instance based on the monetary context with the
	 *         given numeric value, currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the
	 *             {@link MonetaryContext} used.
	 */
	public T getAmount(CurrencyUnit currency,
			long number,
			MonetaryContext<?> monetaryContext);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using an
	 * explicit {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code Money} instance based on the monetary context with the
	 *         given numeric value, currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the
	 *             {@link MonetaryContext} used.
	 */
	public MonetaryAmount<?> getAmount(CurrencyUnit currency,
			double number,
			MonetaryContext<?> monetaryContext);

	/**
	 * Creates a new instance of {@link MonetaryAmountFactory}, using an
	 * explicit {@link MonetaryContext}.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, not{@code null}.
	 * @return a {@code Money} instance based on the monetary context with the
	 *         given numeric value, currency unit.
	 * @throws ArithmeticException
	 *             If the number exceeds the capabilities of the
	 *             {@link MonetaryContext} used.
	 */
	public T getAmount(CurrencyUnit currency,
			Number number,
			MonetaryContext<?> monetaryContext);

	/**
	 * Factory method creating a zero instance with the given {@code currency}..
	 * 
	 * @param currency
	 *            the target {@link CurrencyUnit} of the {@link MonetaryAmount}
	 *            being created, not {@code null}.
	 * @return a new Money instance of zero, with a default
	 *         {@link MonetaryContext}.
	 */
	public T getAmountZero(CurrencyUnit currency);

	/**
	 * Factory method creating a zero instance with the given
	 * {@code currencyCode}.
	 * 
	 * @param currencyCode
	 *            the currency code to determine the {@link CurrencyUnit} of the
	 *            {@link MonetaryAmount} being created.
	 * @return a new Money instance of zero, with a default
	 *         {@link MonetaryContext}.
	 * @throws UnknownCurrencyException
	 *             if the currency code can not be resolved to
	 *             {@link CurrencyUnit}.
	 */
	public T getAmountZero(String currencyCode);

	/**
	 * Factory method creating a zero instance with the given {@code currency}.
	 * 
	 * @param currency
	 *            the target currency of the amount being created, not
	 *            {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, not{@code null}.
	 * @return a new Money instance of zero, with a default
	 *         {@link MonetaryContext}.
	 */
	public T getAmountZero(CurrencyUnit currency,
			MonetaryContext<?> monetaryContext);

	/**
	 * Factory method creating a zero instance with the given {@code currency}.
	 * 
	 * @param currencyCode
	 *            the target currency code to determine the {@link CurrencyUnit}
	 *            of the {@link MonetaryAmount} being created.
	 * @param monetaryContext
	 *            The {@link MonetaryContext} to be used, not {@code null}.
	 * @return a new {@link MonetaryAmount} instance of zero, with a default
	 *         {@link MonetaryContext}.
	 * @throws UnknownCurrencyException
	 *             if the currency code can not be resolved to
	 *             {@link CurrencyUnit}.
	 */
	public T getAmountZero(String currencyCode,
			MonetaryContext<?> monetaryContext);

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
	public MonetaryContext<T> getDefaultMonetaryContext();

	/**
	 * Returns the maximal {@link MonetaryContext} supported, for requests that
	 * exceed these maximal capabilities, an {@link ArithmeticException} must be
	 * thrown.
	 * 
	 * @return the maximal {@link MonetaryContext} supported, never {@code null}
	 */
	public MonetaryContext<T> getMaximalMonetaryContext();

	/**
	 * Converts (if necessary) the given {@link MonetaryAmount} to a new
	 * {@link MonetaryAmount} instance, hereby supporting the
	 * {@link MonetaryContext} given.
	 * 
	 * @param amt
	 *            the amount to be converted, if necessary.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return an according Money instance.
	 */
	public T getAmountFrom(MonetaryAmount<?> amt,
			MonetaryContext<?> monetaryContext);

}