package javax.money.function;

import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

public class CountPredicate extends AbstractValuePredicate<MonetaryFunction<MonetaryAmount,Boolean>>{

	private Integer min;
	private Integer max;
	
	private int currentNum;
	
	public CountPredicate withMinMatching(Integer min){
		this.min = min;
		return this;
	}
	
	public CountPredicate withMaxMatching(Integer max){
		this.max = max;
		return this;
	} 
	
	@Override
	protected boolean isPredicateTrue(MonetaryAmount value,
			Set<MonetaryFunction<MonetaryAmount, Boolean>> acceptedValues) {
		for (MonetaryFunction<MonetaryAmount, Boolean> subPredicate : acceptedValues) {
			if(subPredicate.apply(value)){
				currentNum++;
				if(checkMaxFailed()){
					return false;
				}
			}
		}
		return checkMinFailed();
	}

	protected boolean checkMaxFailed() {
		if(max !=null && currentNum > max.intValue()){
			return true;
		}
		return false;
	}
	
	protected boolean checkMinFailed() {
		if(min !=null && currentNum < min.intValue()){
			return true;
		}
		return false;
	}

	
}