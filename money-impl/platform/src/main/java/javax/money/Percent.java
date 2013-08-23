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
package javax.money;

import static java.text.NumberFormat.getPercentInstance;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;

/**
 * This class allows to extract the percentage of a {@link MonetaryAmount}
 * instance.
 * 
 * @version 0.5
 * @author Werner Keil
 */
public final class Percent implements MonetaryOperator {
	
	private static final MathContext DEFAULT_MATH_CONTEXT = initDefaultMathContext();
	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100, DEFAULT_MATH_CONTEXT);
	
	private final BigDecimal percentValue;

	/**
	 * Access the shared instance of {@link Percent} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	private Percent(final BigDecimal decimal) {
		percentValue = calcPercent(decimal);
	}

	private static MathContext initDefaultMathContext() {
		// TODO Initialize default, e.g. by system properties, or better:
		// classpath properties!
		return MathContext.DECIMAL64;
	}
	
    /**
	 * Factory method creating a new instance with the given {@code BigDecimal) percent value;
	 * @param decimal the decimal value of the percent operator being created.
	 * @return a new  {@code Percent} operator
	 */
	public static Percent of(BigDecimal decimal) {
		return new Percent(decimal); // TODO caching, e.g. array for 1-100 might work.
	}
	
    /**
	 * Factory method creating a new instance with the given {@code Number) percent value;
	 * @param decimal the decimal value of the percent operator being created.
	 * @return a new  {@code Percent} operator
	 */
	public static Percent of(Number number) {
		return of(getBigDecimal(number, DEFAULT_MATH_CONTEXT));
	}
	
	/**
	 * Gets the percentage of the amount.
	 * <p>
	 * This returns the monetary amount in percent. 
	 * For example, for 10% 'EUR 2.35'
	 * will return 0.235.
	 * <p>
	 * This is returned as a {@code MonetaryAmount}.
	 * 
	 * @return the percent result of the amount, never {@code null}
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		return amount.multiply(percentValue);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPercentInstance().format(percentValue);
	}
	
	private static final BigDecimal getBigDecimal(Number number, MathContext mathContext) {
		if (number instanceof BigDecimal) {
			return (BigDecimal) number;
		} else {
			return new BigDecimal(number.doubleValue(), mathContext);
		}
	}
	
	/**
	 * Calculate a BigDecimal value for a Percent e.g. "3" (3 percent) will
	 * generate .03
	 * 
	 * @return java.math.BigDecimal
	 * @param decimal
	 *            java.math.BigDecimal
	 */
	private static final BigDecimal calcPercent(BigDecimal decimal) {
		return decimal.divide(ONE_HUNDRED, DEFAULT_MATH_CONTEXT); // we now have .03
	}
}
