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

import java.util.Set;

import javax.money.bootstrap.Bootstrap;
import javax.money.spi.MonetaryAmountsSpi;

/**
 * Factory singleton for {@link MonetaryAmount} instances as provided by the
 * different registered {@link MonetaryAmountProviderSpi} instances.
 * <p>
 * This singleton allows to get {@link MonetaryAmountFactory} instances for the
 * registered {@link MonetaryAmount} implementation classes or depending on the
 * precision and scale requirements.
 * 
 * @version 0.6.1
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryAmounts {
	/**
	 * The used {@link MonetaryAmountsSpi} instance.
	 */
	private static MonetaryAmountsSpi monetaryAmountsSpi = Bootstrap
			.getService(MonetaryAmountsSpi.class);

	/**
	 * Private singleton constructor.
	 */
	private MonetaryAmounts() {
	}

	/**
	 * Access an {@link MonetaryAmountFactory} for the given
	 * {@link MonetaryAmount} implementation type.
	 * 
	 * @param amountType
	 *            {@link MonetaryAmount} implementation type, nor {@code null}.
	 * @return the corresponding {@link MonetaryAmountFactory}, never
	 *         {@code null}.
	 * @throws MonetaryException
	 *             if no {@link MonetaryAmountFactory} targeting the given
	 *             {@link MonetaryAmount} implementation class is registered.
	 */
	public static <T extends MonetaryAmount<T>> MonetaryAmountFactory<T> getAmountFactory(
			Class<T> amountType) {
		MonetaryAmountFactory<T> factory = monetaryAmountsSpi
				.getAmountFactory(amountType);
		if (factory == null) {
			throw new MonetaryException("No AmountFactory registered for "
					+ amountType);
		}
		return factory;
	}

	/**
	 * Access the default {@link MonetaryAmountFactory} as defined by
	 * {@link #getDefaultAmountType()}.
	 * 
	 * @return the {@link MonetaryAmountFactory} corresponding to
	 *         {@link #getDefaultAmountType()}, never {@code null}.
	 * @throws MonetaryException
	 *             if no {@link MonetaryAmountFactory} targeting the
	 *             {@link #getDefaultAmountType()} implementation class is
	 *             registered.
	 */
	@SuppressWarnings("unchecked")
	public static MonetaryAmountFactory<?> getDefaultAmountFactory() {
		return getAmountFactory(getDefaultAmountType());
	}

	/**
	 * Access all currently available {@link MonetaryAmount} implementation
	 * classes that are accessible from this {@link MonetaryAmount} singleton.
	 * 
	 * @return all currently available {@link MonetaryAmount} implementation
	 *         classes that have corresponding {@link MonetaryAmountFactory}
	 *         instances provided, never {@code null}
	 */
	public static Set<Class<? extends MonetaryAmount<?>>> getAmountTypes() {
		return monetaryAmountsSpi.getAmountTypes();
	}

	/**
	 * Get the default {@link MonetaryAmount} implementation class, if no
	 * explicit implementation type is passed.
	 * 
	 * @return the default {@link MonetaryAmount} implementation class, never
	 *         {@code null}.
	 * @throws MonetaryException
	 *             if no {@link MonetaryAmountFactory} is registered.
	 */
	public static Class<? extends MonetaryAmount> getDefaultAmountType() {
		return monetaryAmountsSpi.getDefaultAmountType();
	}

	/**
	 * Get the {@link MonetaryAmount} implementation class, that best matches to
	 * cover the given {@link MonetaryContext}.
	 * 
	 * @param requiredContext
	 *            the {@link MonetaryContext} to be queried for a matching
	 *            {@link MonetaryAmount} implementation, not{@code null}.
	 * @return the {@link MonetaryAmount} implementation class, that best
	 *         matches to cover the given {@link MonetaryContext}, never
	 *         {@code null}.
	 * @throws MonetaryException
	 *             if no {@link MonetaryAmount} implementation class can cover
	 *             the required {@link MonetaryContext}.
	 */
	public static Class<? extends MonetaryAmount> queryAmountType(
			MonetaryContext<?> requiredContext) {
		return monetaryAmountsSpi.queryAmountType(requiredContext);
	}

}