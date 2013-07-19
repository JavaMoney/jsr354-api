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

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This class allows to calculate the minimum of some {@link MonetaryAmount}
 * instances.
 * 
 * @author Anatole Tresch
 */
public final class Minimum implements
		MonetaryFunction<Iterable<? extends MonetaryAmount>, MonetaryAmount> {

	/**
	 * The shared instance of this class.
	 */
	private static final Minimum INSTANCE = new Minimum();

	/**
	 * Private constructor, there is only one instance of this class, accessible
	 * calling {@link #of()}.
	 */
	private Minimum() {
	}

	/**
	 * Access the shared instance of {@link Minimum} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static final Minimum of() {
		return INSTANCE;
	}

	/**
	 * Evaluates the minimum of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the minimum.
	 */
	public static MonetaryAmount from(Iterable<? extends MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		return Minimum.of().apply(amounts);
	}

	/**
	 * Evaluates the minimum of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the minimum.
	 */
	public static MonetaryAmount from(MonetaryAmount... amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		return Minimum.of().apply(Arrays.asList(amounts));
	}

	/**
	 * Evaluates the minimum of the given amounts.
	 * 
	 * @param amounts
	 *            The amounts, at least one instance, not null, all of the same
	 *            currency.
	 * @return the minimum.
	 */
	public MonetaryAmount apply(Iterable<? extends MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		MonetaryAmount result = null;
		for (MonetaryAmount amount : amounts) {
			if (result == null) {
				result = amount;
			} else if (result.isGreaterThan(amount)) {
				result = amount;
			}
		}
		if (result == null) {
			throw new IllegalArgumentException("amounts is empty.");
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
		return "Minimum [Iterable<MonetaryAmount> -> MonetaryAmount]";
	}

}
