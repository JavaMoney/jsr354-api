/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This class allows instances of {@link MonetaryAmount} to be filtered by
 * arbitrary predicates.
 * 
 * @author Anatole Tresch
 */
public final class AmountFilter
		implements
		MonetaryFunction<MonetaryFunction<MonetaryAmount, Boolean>, Collection<MonetaryAmount>> {

	/** The input {@link MonetaryAmount} instances to be filtered. */
	private Collection<MonetaryAmount> input = new ArrayList<MonetaryAmount>();

	/**
	 * Creates a new empty {@link AmountFilter}.
	 */
	public AmountFilter() {
	}

	/**
	 * Creates a new {@link AmountFilter}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be added as input, not null.
	 */
	public AmountFilter(MonetaryAmount... amounts) {
		this();
		add(Arrays.asList(amounts));
	}

	/**
	 * Creates a new instance, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be added as input, not null.
	 */
	public AmountFilter(Iterable<MonetaryAmount> amounts) {
		this();
		add(amounts);
	}

	/**
	 * Allows to add instances of {@link MonetaryAmount} to the input list.
	 * 
	 * @param amounts
	 *            the amounts to be added.
	 * @return this filter instance, for chaining.
	 */
	public AmountFilter add(MonetaryAmount... amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount monetaryAmount : amounts) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	/**
	 * Allows to add instances of {@link MonetaryAmount} to the input list.
	 * 
	 * @param amounts
	 *            the amounts to be added.
	 * @return this filter instance, for chaining.
	 */
	public AmountFilter add(Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount monetaryAmount : amounts) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	/**
	 * Visits all amount in the input collection and selects the ones matching
	 * the predicate passed.
	 * 
	 * @param The
	 *            predicate determining what amounts are filtered into the
	 *            result.
	 * @return a collection with all the amounts, for which the predicate
	 *         returned {@code true}.
	 */
	public Collection<MonetaryAmount> apply(
			final MonetaryFunction<MonetaryAmount, Boolean> predicate) {
		final List<MonetaryAmount> result = new ArrayList<MonetaryAmount>();
		AmountVisitor visitor = new AmountVisitor(this.input);
		visitor.apply(new MonetaryFunction<MonetaryAmount, Boolean>() {
			@Override
			public Boolean apply(MonetaryAmount value) {
				if (predicate.apply(value)) {
					result.add(value);
					return true;
				}
				return false;
			}
		});
		return result;
	}

}
