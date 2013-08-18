package javax.money.function;

import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

public class OrPredicate extends
		AbstractValuePredicate<MonetaryFunction<MonetaryAmount, Boolean>> {

	private boolean exclusive = false;

	@Override
	protected boolean isPredicateTrue(MonetaryAmount value,
			Set<MonetaryFunction<MonetaryAmount, Boolean>> acceptedValues) {
		boolean currentValue = false;
		for (MonetaryFunction<MonetaryAmount, Boolean> subPredicate : acceptedValues) {
			if (!subPredicate.apply(value)) {
				if (exclusive) {
					if (currentValue) {
						return false;
					}
				}
				else {
					return true;
				}
			}
		}
		return currentValue;
	}

	public OrPredicate withXOR(boolean exclusiveOR) {
		this.exclusive = exclusiveOR;
		return this;
	}

}