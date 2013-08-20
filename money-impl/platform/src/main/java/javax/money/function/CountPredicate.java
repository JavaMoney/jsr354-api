/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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