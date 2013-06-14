/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.function;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This {@link MonetaryCalculation} allows to separate instances of
 * {@link MonetaryAmount} by their {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 */
public final class SeparateCurrencies implements
		MonetaryFunction<Iterable<MonetaryAmount>, Set<CurrencyUnit>> {

	/**
	 * The shared instance of this class.
	 */
	private static final SeparateCurrencies INSTANCE = new SeparateCurrencies();

	/**
	 * Private constructor, there is only one instance of this class, accessible
	 * calling {@link #of()}.
	 */
	private SeparateCurrencies() {
	}

	/**
	 * Access the shared instance of {@link SeparateCurrencies} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static SeparateCurrencies of() {
		return INSTANCE;
	}

	/**
	 * Evaluates the non equal {@link CurrencyUnit} instances of the given
	 * amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the non equal {@link CurrencyUnit} instances.
	 */
	public static Set<CurrencyUnit> from(Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		return SeparateCurrencies.of().apply(amounts);
	}

	/**
	 * Evaluates the non equal {@link CurrencyUnit} instances of the given
	 * amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the non equal {@link CurrencyUnit} instances.
	 */
	public static Set<CurrencyUnit> from(MonetaryAmount... amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		return SeparateCurrencies.of().apply(Arrays.asList(amounts));
	}

	/**
	 * Evaluates the non equal {@link CurrencyUnit} instances of the given
	 * amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the non equal {@link CurrencyUnit} instances.
	 */
	public Set<CurrencyUnit> apply(Iterable<MonetaryAmount> amounts) {
		Set<CurrencyUnit> result = new HashSet<CurrencyUnit>();
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount monetaryAmount : amounts) {
			result.add(monetaryAmount.getCurrency());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SeparateCurrencies [Iterable<MonetaryAmount> -> Set<CurrencyUnit>]";
	}

	/**
	 * Allows to check, if the currency of the two amounts are the same, meaning
	 * that corresponding currency name spaces and currency codes must be equal.
	 * 
	 * @see CurrencyUnit#getNamespace()
	 * @see CurrencyUnit#getCurrencyCode()
	 * @param amount
	 *            The amount to compare to, not {@code null}.
	 * @return {@code true}, if the {@link CurrencyUnit} of this instance has
	 *         the same name space and code.
	 */
	public static boolean hasSameCurrency(MonetaryAmount... amounts) {
		return hasSameCurrency(Arrays.asList(amounts));
	}

	/**
	 * Allows to check, if the currency of the two amounts are the same, meaning
	 * that corresponding currency name spaces and currency codes must be equal.
	 * 
	 * @see CurrencyUnit#getNamespace()
	 * @see CurrencyUnit#getCurrencyCode()
	 * @param amount
	 *            The amount to compare to, not {@code null}.
	 * @return {@code true}, if the {@link CurrencyUnit} of this instance has
	 *         the same name space and code.
	 */
	public static boolean hasSameCurrency(Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("Amounts required.");
		}
		CurrencyUnit unit = null;
		for (MonetaryAmount am : amounts) {
			if (unit == null) {
				unit = am.getCurrency();
				continue;
			}
			if (!unit.getNamespace().equals(am.getCurrency().getNamespace())) {
				return false;
			}
			if (!unit.getCurrencyCode().equals(
					am.getCurrency().getCurrencyCode())) {
				return false;
			}
		}
		return true;
	}
}
