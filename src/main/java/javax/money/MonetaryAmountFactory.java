/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

/**
 * Factory for {@link MonetaryAmount} instances for a given type. It can be accessed, by
 * <ul>
 * <li>calling {@link MonetaryAmount#getFactory()}, returning a {@link MonetaryAmountFactory}
 * creating amounts of the same implementation type, which also provided the factory instance.</li>
 * <li>calling {@link MonetaryAmounts#getAmountFactory(Class)} accessing a
 * {@link MonetaryAmountFactory} for a concrete type.</li>
 * <li>calling {@link MonetaryAmounts#getDefaultAmountFactory()} accessing a default
 * {@link MonetaryAmountFactory}.
 * </ul>
 * <p>
 * Implementations of this interface allow to get {@link MonetaryAmount} instances providing
 * different data as required:
 * <ul>
 * <li>the {@link CurrencyUnit}, or the corresponding currency code</li>
 * <li>the number part</li>
 * <li>the required {@link MonetaryContext}</li>
 * <li>by passing a {@link MonetaryAmount} instance, it is possible to convert an arbitrary amount
 * implementation to the implementation provided by this factory. If the current factory cannot
 * support the precision/scale as required by the current {@link NumberValue} a
 * {@link MonetaryException} must be thrown.</li>
 * </ul>
 * If not defined a default {@link MonetaryContext} is used, which can also be configured by adding
 * configuration to a file {@code /javamoney.properties} to the classpath.
 * 
 * Hereby the entries should start with {@code a.b.Money.ctx}. The entries valid must be documented
 * on the according implementation class, where the following entries should be defined as follows
 * (example below given for a class {@code a.b.Money}:
 * <ul>
 * <li>{@code a.b.Money.ctx.precision} to define the maximal supported precision.</li>
 * <li>{@code a.b.Money.ctx.scale} to define the maximal supported scale.</li>
 * <li>{@code a.b.Money.ctx.fixedScale} to define the scale to be fixed (constant).</li>
 * </ul>
 * <p>
 * <h2>Implementation specification</h2> Instances of this interface are <b>not</b> required to be
 * thread-safe!
 * 
 * @version 0.6.1
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryAmountFactory<T extends MonetaryAmount> {

	/**
	 * Access the {@link MonetaryAmount} implementation type.
	 * 
	 * @return the {@link MonetaryAmount} implementation type, never {@code null}.
	 */
	public Class<? extends MonetaryAmount> getAmountType();

	/**
	 * Sets the {@link CurrencyUnit} to be used.
	 * 
	 * @param currencyCode
	 *            the currencyCode of the currency to be used, not {@code null}. The currency code
	 *            will be resolved using {@link MonetaryCurrencies#getCurrency(String)}.
	 * @throws UnknownCurrencyException
	 *             if the {@code currencyCode} is not resolvable.
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory<T> withCurrency(String currencyCode);

	/**
	 * Sets the {@link CurrencyUnit} to be used.
	 * 
	 * @param currency
	 *            the {@link CurrencyUnit} to be used, not {@code null}
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory<T> with(CurrencyUnit currency);

	/**
	 * Sets the number to be used.
	 * 
	 * @param number
	 *            the number to be used
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory<T> with(double number);

	/**
	 * Sets the number to be used.
	 * 
	 * @param number
	 *            the number to be used
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory<T> with(long number);

	/**
	 * Sets the number to be used.
	 * 
	 * @param number
	 *            the number to be used, not {@code null}.
	 * @return This factory instance, for chaining.
	 */
	public MonetaryAmountFactory<T> with(Number number);

	/**
	 * Sets the {@link MonetaryContext} to be used.
	 * 
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, not {@code null}.
	 * @throws MonetaryException
	 *             when the {@link MonetaryContext} given exceeds the capabilities supported by this
	 *             factory type.
	 * @return This factory instance, for chaining.
	 * @see #getMaximalMonetaryContext()
	 */
	public MonetaryAmountFactory<T> with(
			MonetaryContext monetaryContext);

	/**
	 * Uses an arbitrary {@link MonetaryAmount} to initialize this factory. Properties reused are:
	 * <ul>
	 * <li>CurrencyUnit,</li>
	 * <li>Number value,</li>
	 * <li>MonetaryContext.</li>
	 * </ul>
	 * 
	 * @param amount
	 *            the amount to be used, not {@code null}.
	 * @throws MonetaryException
	 *             when the {@link MonetaryContext} implied by {@code amount.getMonetaryContext()}
	 *             exceeds the capabilities supported by this factory type.
	 * @return this factory instance, for chaining.
	 */
	public MonetaryAmountFactory<T> with(MonetaryAmount amount);

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the current data set on this factory.
	 * 
	 * @return the corresponding {@link MonetaryAmount}.
	 * @see #getAmountType()
	 */
	public T create();

	/**
	 * Returns the default {@link MonetaryContext} used, when no {@link MonetaryContext} is
	 * provided.
	 * <p>
	 * The default context is not allowed to exceed the capabilities of the maximal
	 * {@link MonetaryContext} supported.
	 * 
	 * @see #getMaximalMonetaryContext()
	 * @return the default {@link MonetaryContext}, never {@code null}.
	 */
	public MonetaryContext getDefaultMonetaryContext();

	/**
	 * Returns the maximal {@link MonetaryContext} supported, for requests that exceed these maximal
	 * capabilities, an {@link ArithmeticException} must be thrown.
	 * 
	 * @return the maximal {@link MonetaryContext} supported, never {@code null}
	 */
	public MonetaryContext getMaximalMonetaryContext();

}