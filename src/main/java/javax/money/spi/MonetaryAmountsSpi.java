/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryAmounts;
import javax.money.MonetaryContext;
import javax.money.MonetaryException;

/**
 * SPI for the backing implementation of the {@link javax.money.MonetaryAmounts} singleton. It
 * should load and manage (including contextual behavior), if needed) the different registered
 * {@link MonetaryAmountFactory} instances.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountsSpi {

	/**
	 * Access the {@link MonetaryAmountFactory} for the given {@code amountType} .
	 * 
	 * @param amountType
	 *            the {@link MonetaryAmount} implementation type, targeted by the factory.
	 * @return the {@link MonetaryAmountFactory}, or {@code null}, if no such
	 *         {@link MonetaryAmountFactory} is available in the current context.
	 */
	public <T extends MonetaryAmount> MonetaryAmountFactory<T> getAmountFactory(
			Class<T> amountType);

	/**
	 * Access the default {@link MonetaryAmount} type.
	 * 
	 * @see MonetaryAmounts#getDefaultAmountType()
	 * @return a the default {@link MonetaryAmount} type corresponding, never {@code null}.
	 * @throws MonetaryException
	 *             if no {@link MonetaryAmountFactoryProviderSpi} is available, or no
	 *             {@link MonetaryAmountFactoryProviderSpi} targeting the configured default
	 *             {@link MonetaryAmount} type.
	 */
	public Class<? extends MonetaryAmount> getDefaultAmountType();
	
	/**
	 * Get the currently registered {@link MonetaryAmount} implementation types.
	 * 
	 * @return the {@link Set} if registered {@link MonetaryAmount} implementation types, never
	 *         {@code null}.
	 */
	public Set<Class<? extends MonetaryAmount>> getAmountTypes();


	/**
	 * Get the {@link MonetaryAmount} implementation class, that best matches to cover the given
	 * {@link MonetaryContext}.
	 * <p>
	 * The evaluation order should consider the following aspects:
	 * <ul>
	 * <li>If {@link MonetaryContext#getAmountType()} is defined, it should be considered.
	 * Nevertheless if precision/scale cannot be met, a {@link javax.money.MonetaryException} should
	 * be thrown.
	 * <li>The remaining implementation class candidates must cover the required precision.
	 * <li>The remaining implementation class candidates must cover the required max scale.
	 * <li>If max scale is met, but {@code precision==0} (unlimited precision), the
	 * {@link MonetaryAmount} implementation candidate should be chosen with highest possible
	 * precision.
	 * <li>If still multiple implementation candidates qualify, the ones with
	 * {@code Flavor.PERFORMANCE} are preferred.
	 * <li>After this point the selection may be arbitrary.
	 * </ul>
	 * 
	 * @param requiredContext
	 *            the required {@link MonetaryContext}
	 * @return the {@link MonetaryAmount} implementation class, that best matches to cover the given
	 *         {@link MonetaryContext}, never {@code null}.
	 * @throws javax.money.MonetaryException
	 *             if no {@link MonetaryAmount} implementation class can cover the required
	 *             {@link MonetaryContext}.
	 */
	public Class<? extends MonetaryAmount> queryAmountType(
			MonetaryContext requiredContext);


}