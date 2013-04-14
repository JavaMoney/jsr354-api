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

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This class allows instances of {@link MonetaryAmount} to be filtered by
 * arbitrary predicates.
 * 
 * @author Anatole Tresch
 */
public final class AmountVisitor implements
		MonetaryFunction<MonetaryFunction<MonetaryAmount, Boolean>, Integer> {
	/** The input {@link MonetaryAmount} instances to be filtered. */
	private Collection<MonetaryAmount> input = new ArrayList<MonetaryAmount>();

	/**
	 * Creates a new, empty {@link AmountVisitor}.
	 */
	public AmountVisitor() {

	}

	/**
	 * Creates a new {@link AmountVisitor}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be visited, not null.
	 */
	public AmountVisitor(MonetaryAmount... amounts) {
		this();
		add(Arrays.asList(amounts));
	}

	/**
	 * Creates a new {@link SeparateCurrencies}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be separated, not null.
	 * @return the new instance.
	 */
	public AmountVisitor(Iterable<MonetaryAmount> amounts) {
		this();
		add(amounts);
	}

	/**
	 * Adds the given amounts for separation.
	 * 
	 * @param amounts
	 *            The amounts.
	 * @return this instance for building.
	 */
	public AmountVisitor add(MonetaryAmount... amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount monetaryAmount : amounts) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	/**
	 * Method that allows to add amounts to the visitor, that can be visited
	 * later.
	 * 
	 * @param amounts
	 *            The amount to be added.
	 * @return This visitor instance, for chaining.
	 */
	public AmountVisitor add(Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount monetaryAmount : amounts) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	/**
	 * This method visits all items in the current {@link AmountVisitor}. The
	 * passed {@code predicate} signals by its return value, if it has handled
	 * the current {@link MonetaryAmount} by returning {@code true}.
	 * 
	 * @return the sum of calls to the given predicate that evaluated to
	 *         {@code true}.
	 */
	public Integer apply(MonetaryFunction<MonetaryAmount, Boolean> predicate) {
		int visited = 0;
		for (MonetaryAmount amt : this.input) {
			if (predicate.apply(amt)) {
				visited++;
			}
		}
		return visited;
	}

}
