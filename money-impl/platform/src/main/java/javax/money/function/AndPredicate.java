package javax.money.function;

import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

public class AndPredicate extends AbstractValuePredicate<MonetaryFunction<MonetaryAmount,Boolean>>{

	@Override
	protected boolean isPredicateTrue(MonetaryAmount value,
			Set<MonetaryFunction<MonetaryAmount, Boolean>> acceptedValues) {
		for (MonetaryFunction<MonetaryAmount, Boolean> subPredicate : acceptedValues) {
			if(!subPredicate.apply(value)){
				return false;
			}
		}
		return true;
	}

	
}