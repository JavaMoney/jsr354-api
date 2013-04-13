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
 * This class allows to calculate the average of some {@link MonetaryAmount}
 * instances.
 * 
 * @author Anatole Tresch
 */
public final class Average implements
		MonetaryFunction<Iterable<MonetaryAmount>, MonetaryAmount> {

	private static final Average INSTANCE = new Average();

	private Average() {
	}
	
	public static final Average of() {
		return INSTANCE;
	}

	public static MonetaryAmount from(Iterable<MonetaryAmount> amounts) {
		return Average.of().apply(amounts);
	}

	public static MonetaryAmount from(MonetaryAmount... amounts) {
		return Average.of().apply(Arrays.asList(amounts));
	}

	public MonetaryAmount apply(Iterable<MonetaryAmount> amounts) {
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		MonetaryAmount total = null;
		int itemNumber = 0;

		for (MonetaryAmount amount : amounts) {
			itemNumber++;
			if (total == null) {
				total = amount;
			} else {
				total = total.add(amount);
			}
		}
		return total.divide(itemNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Average [Iterable<MonetaryAmount> -> MonetaryAmount]";
	}

}
