package javax.money.function;

import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

public class CurrencyPredicate extends
		AbstractValuePredicate<CurrencyUnit> {

	protected boolean isPredicateTrue(MonetaryAmount value,
			Set<CurrencyUnit> acceptedValues) {
		return acceptedValues.contains(value.getCurrency());
	}

}