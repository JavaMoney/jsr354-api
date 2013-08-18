package javax.money.function;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

public abstract class AbstractValuePredicate<T> implements MonetaryFunction<MonetaryAmount,Boolean>{

	private Set<T> acceptedValues = new HashSet<T>();
	
	public AbstractValuePredicate<T> withValues(T... values) {
		withValues(Arrays.asList(values));
		return this;
	}

	public AbstractValuePredicate<T> withValues(Collection<T> values) {
		this.acceptedValues.addAll(values);
		return this;
	}

	public AbstractValuePredicate<T> withoutValues(T... values) {
		withoutValues(Arrays.asList(values));
		return this;
	}

	public AbstractValuePredicate<T> withoutValues(
			Collection<T> values) {
		this.acceptedValues.removeAll(values);
		return this;
	}

	public AbstractValuePredicate<T> clearValues() {
		this.acceptedValues.clear();
		return this;
	}
	
	@Override
	public Boolean apply(MonetaryAmount value) {
		if(isPredicateTrue(value, this.acceptedValues)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	protected abstract boolean isPredicateTrue(MonetaryAmount value, Set<T> acceptedValues);

}
