/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.function;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This class allows to calculate the minimum of some {@link MonetaryAmount}
 * instances.
 * 
 * @author Anatole Tresch
 */
public final class Minimum implements
		MonetaryFunction<Iterable<MonetaryAmount>, MonetaryAmount> {

	private static final Minimum INSTANCE = new Minimum();

	private Minimum() {
	}

	public static final Minimum of() {
		return INSTANCE;
	}

	public MonetaryAmount apply(Iterable<MonetaryAmount> amounts) {
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
