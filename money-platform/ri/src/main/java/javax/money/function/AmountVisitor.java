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
public final class AmountVisitor
		implements
		MonetaryFunction<MonetaryFunction<MonetaryAmount, Boolean>, 
		                 Integer> {
	/** The input {@link MonetaryAmount} instances to be filtered. */
	private Collection<MonetaryAmount> input = new ArrayList<MonetaryAmount>();

	/**
	 * Creates a new {@link SeparateCurrencies}, using the given amounts.
	 * 
	 * @return the new instance.
	 */
	public static AmountVisitor of() {
		return new AmountVisitor();
	}

	/**
	 * Creates a new {@link SeparateCurrencies}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be separated, not null.
	 * @return the new instance.
	 */
	public static AmountVisitor of(MonetaryAmount... amounts) {
		return new AmountVisitor().add(Arrays.asList(amounts));
	}

	/**
	 * Creates a new {@link SeparateCurrencies}, using the given amounts.
	 * 
	 * @param amounts
	 *            the amounts to be separated, not null.
	 * @return the new instance.
	 */
	public static AmountVisitor of(Iterable<MonetaryAmount> amounts) {
		return new AmountVisitor().add(amounts);
	}

	private AmountVisitor() {
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

	public AmountVisitor add(Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount monetaryAmount : amounts) {
			this.input.add(monetaryAmount);
		}
		return this;
	}

	public Integer apply(
			MonetaryFunction<MonetaryAmount, Boolean> predicate) {
		int visited = 0;
		for (MonetaryAmount amt : this.input) {
			if (predicate.apply(amt)) {
				visited++;
			}
		}
		return visited;
	}

}
