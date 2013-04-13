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
 * This class allows to calculate the total of some {@link MonetaryAmount}
 * instances.
 * 
 * @author Anatole Tresch
 */
public final class Total implements
		MonetaryFunction<Iterable<MonetaryAmount>, MonetaryAmount> {

	public MonetaryAmount apply(Iterable<MonetaryAmount> amounts) {
		MonetaryAmount result = null;
		if (amounts == null) {
			throw new IllegalArgumentException("amounts required.");
		}
		for (MonetaryAmount amount : amounts) {
			if(result==null){
				result = amount;
			}
			else{
				result = result.add(amount);
			}
		}
		if(result==null){
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
		return "Total [Iterable<MonetaryAmount> -> MonetaryAmount]";
	}

}
