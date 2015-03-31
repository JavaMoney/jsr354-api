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
 * <li>calling {@link Monetary#getAmountFactory(Class)} accessing a
 * {@link MonetaryAmountFactory} for a concrete type <code>Class<T></code>.</li>
 * <li>calling {@link Monetary#getDefaultAmountFactory()} accessing a default
 * {@link MonetaryAmountFactory}.
 * </ul>
 * <p>
 * Implementations of this interface allow to get {@link MonetaryAmount} instances providing
 * different data as required:
 * <ul>
 * <li>the {@link CurrencyUnit}, or the corresponding currency code (must be solvable by
 * {@link Monetary}).</li>
 * <li>the number part</li>
 * <li>the {@link MonetaryContext}</li>
 * <li>by passing any {@link MonetaryAmount} instance, it is possible to convert an arbitrary amount
 * implementation to the implementation provided by this factory. If the current factory cannot
 * support the precision/scale as required by the current {@link NumberValue} a
 * {@link MonetaryException} must be thrown.</li>
 * </ul>
 * If not defined a default {@link MonetaryContext} is used, which can also be configured by adding
 * configuration to a file {@code /javamoney.properties} to the classpath.
 * <p>
 * Hereby the entries. e.g. for a class {@code MyMoney} should start with {@code a.b.MyMoney.ctx}. The entries valid
 * must be documented
 * on the according implementation class, where the following entries are defined for all implementation types
 * (example below given for a class {@code a.b.MyMoney}:
 * <ul>
 * <li>{@code a.b.MyMoney.ctx.precision} to define the maximal supported precision.</li>
 * <li>{@code a.b.MyMoney.ctx.maxScale} to define the maximal supported scale.</li>
 * <li>{@code a.b.MyMoney.ctx.fixedScale} to define the scale to be fixed (constant).</li>
 * </ul>
 * <p>
 * <h2>Implementation specification</h2> Instances of this interface are <b>not</b> required to be
 * thread-safe!
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.6.1
 */
public interface MonetaryAmountFactory<T extends MonetaryAmount> {

    /**
     * Access the {@link MonetaryAmount} implementation type.
     *
     * @return the {@link MonetaryAmount} implementation type, never {@code null}.
     */
    Class<? extends MonetaryAmount> getAmountType();

    /**
     * Sets the {@link CurrencyUnit} to be used.
     *
     * @param currencyCode the currencyCode of the currency to be used, not {@code null}. The currency code
     *                     will be resolved using {@link Monetary#getCurrency(String, String...)}.
     * @return This factory instance, for chaining.
     * @throws UnknownCurrencyException if the {@code currencyCode} is not resolvable.
     */
    default MonetaryAmountFactory<T> setCurrency(String currencyCode) {
        return setCurrency(Monetary.getCurrency(currencyCode));
    }

    /**
     * Sets the {@link CurrencyUnit} to be used.
     *
     * @param currency the {@link CurrencyUnit} to be used, not {@code null}
     * @return This factory instance, for chaining.
     */
    MonetaryAmountFactory<T> setCurrency(CurrencyUnit currency);

    /**
     * Sets the number to be used.
     *
     * @param number the number to be used
     * @return This factory instance, for chaining.
     */
    MonetaryAmountFactory<T> setNumber(double number);

    /**
     * Sets the number to be used.
     *
     * @param number the number to be used
     * @return This factory instance, for chaining.
     */
    MonetaryAmountFactory<T> setNumber(long number);

    /**
     * Sets the number to be used.
     *
     * @param number the number to be used, not {@code null}.
     * @return This factory instance, for chaining.
     */
    MonetaryAmountFactory<T> setNumber(Number number);

    /**
     * Get the maximum possible number that this type can represent. If the numeric model has no limitations on the
     * numeric range, null should be returned. If {@link MonetaryContext#getPrecision()} returns a value > 0 this
     * method is required to provide a maximal amount.
     *
     * @return the maximum possible number, or null.
     */
    NumberValue getMaxNumber();

    /**
     * Get the minimum possible number that this type can represent. If the numeric model has no limitations on the
     * numeric range, null should be returned.  If {@link MonetaryContext#getPrecision()} returns a value > 0 this
     * method is required to provide a maximal amount.
     *
     * @return the minimum possible number, or null.
     */
    NumberValue getMinNumber();

    /**
     * Sets the {@link MonetaryContext} to be used.
     *
     * @param monetaryContext the {@link MonetaryContext} to be used, not {@code null}.
     * @return This factory instance, for chaining.
     * @throws MonetaryException when the {@link MonetaryContext} given exceeds the capabilities supported by this
     *                           factory type.
     * @see #getMaximalMonetaryContext()
     */
    MonetaryAmountFactory<T> setContext(MonetaryContext monetaryContext);

    /**
     * Uses an arbitrary {@link MonetaryAmount} to initialize this factory. Properties reused are:
     * <ul>
     * <li>CurrencyUnit,</li>
     * <li>Number value,</li>
     * <li>MonetaryContext.</li>
     * </ul>
     *
     * @param amount the amount to be used, not {@code null}.
     * @return this factory instance, for chaining.
     * @throws MonetaryException when the {@link MonetaryContext} implied by {@code amount.getContext()}
     *                           exceeds the capabilities supported by this factory type.
     */
    default MonetaryAmountFactory<T> setAmount(MonetaryAmount amount) {
        setCurrency(amount.getCurrency());
        setNumber(amount.getNumber());
        setContext(amount.getContext());
        return this;
    }

    /**
     * Creates a new instance of {@link MonetaryAmount}, using the current data set on this factory.
     *
     * @return the corresponding {@link MonetaryAmount}.
     * @see #getAmountType()
     */
    T create();

    /**
     * Returns the default {@link MonetaryContext} used, when no {@link MonetaryContext} is
     * provided.
     * <p>
     * The default context is not allowed to exceed the capabilities of the maximal
     * {@link MonetaryContext} supported.
     *
     * @return the default {@link MonetaryContext}, never {@code null}.
     * @see #getMaximalMonetaryContext()
     */
    MonetaryContext getDefaultMonetaryContext();

    /**
     * Returns the maximal {@link MonetaryContext} supported, for requests that exceed these maximal
     * capabilities, an {@link ArithmeticException} must be thrown.
     *
     * @return the maximal {@link MonetaryContext} supported, never {@code null}
     */
    default MonetaryContext getMaximalMonetaryContext() {
        return getDefaultMonetaryContext();
    }

}