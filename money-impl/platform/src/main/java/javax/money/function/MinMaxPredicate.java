package javax.money.function;

import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

public class MinMaxPredicate implements MonetaryFunction<MonetaryAmount,Boolean>{

	private MonetaryAmount minAmount;
	
	private MonetaryAmount maxAmount;

	public MinMaxPredicate withMinValue(MonetaryAmount amount){
		return withMinValue(amount, true);
	}

	public MinMaxPredicate withMinValue(MonetaryAmount amount, boolean includeValue){
		// TODO Auto-generated method stub
		return this;
	}
	
	public MinMaxPredicate withMaxValue(MonetaryAmount amount){
		return withMaxValue(amount, true);
	}
	
	public MinMaxPredicate withMaxValue(MonetaryAmount amount, boolean includeValue){
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public Boolean apply(MonetaryAmount value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}