package net.java.javamoney.ri.ext.calc;

//import java.util.Collection;
//
//import javax.money.MonetaryAdjuster;
//import javax.money.MonetaryAmount;
//import javax.money.MonetaryFunction;
//
// TODO think on this how this should be best implemented...
//
//public final class DivideAndSeparate implements MonetaryFunction<Collection<MonetaryAmount>>{
//
//	private boolean addDifferenceToLastValue;
//	private MonetaryAdjuster rounding;
//	private MonetaryAmount total;
//	private Number divisor;
//	
//	public static DivideAndSeparate of(MonetaryAmount total,
//			Number divisor){
//		return new DivideAndSeparate(total, divisor);
//	}
//	
//	private DivideAndSeparate(MonetaryAmount total,
//			Number divisor) {
//		if(total==null){
//			throw new IllegalArgumentException("total required.");
//		}
//		if(divisor==null){
//			throw new IllegalArgumentException("divisor required.");
//		}
//		this.total = total;
//		this.divisor = divisor;
//	}
//	
//	
//	/**
//	 * @return the addDifferenceToLastValue
//	 */
//	public final boolean isAddDifferenceToLastValue() {
//		return addDifferenceToLastValue;
//	}
//
//	/**
//	 * @param addDifferenceToLastValue the addDifferenceToLastValue to set
//	 */
//	public final DivideAndSeparate setAddDifferenceToLastValue(boolean addDifferenceToLastValue) {
//		this.addDifferenceToLastValue = addDifferenceToLastValue;
//		return this;
//	}
//
//	/**
//	 * @return the rounding
//	 */
//	public final MonetaryAdjuster getRounding() {
//		return rounding;
//	}
//
//	/**
//	 * @param rounding the rounding to set
//	 */
//	public final DivideAndSeparate setRounding(MonetaryAdjuster rounding) {
//		this.rounding = rounding;
//		return this;
//	}
//
//	/**
//	 * @return the total
//	 */
//	public final MonetaryAmount getTotal() {
//		return total;
//	}
//
//	/**
//	 * @param total the total to set
//	 */
//	public final DivideAndSeparate setTotal(MonetaryAmount total) {
//		this.total = total;
//		return this;
//	}
//
//	/**
//	 * @return the divisor
//	 */
//	public final Number getDivisor() {
//		return divisor;
//	}
//
//	/**
//	 * @param divisor the divisor to set
//	 */
//	public final DivideAndSeparate setDivisor(Number divisor) {
//		this.divisor = divisor;
//		return this;
//	}
//
//	/**
//	 * This method divides this amount into a number of sub-amounts determined
//	 * by the divisor passed.
//	 * 
//	 * @param divisor
//	 *            Determines how many amounts should be divided based on this
//	 *            amount (which represents the total amount).
//	 * @param addDifferenceToLastValue
//	 *            if true, the rounding difference between the sum of the
//	 *            divided amounts and this total amount value, is simply added
//	 *            to the last amount, otherwise the last element of the array
//	 *            returned contains the rounding difference (note: this element
//	 *            may be 0!).<br/>
//	 *            For example dividing 100 by 3, when set to true, a three
//	 *            element array is returned, containing 33.33, 33.33 and 33.34.<br/>
//	 *            If set to false, a 4 elements array would be returned,
//	 *            containing 3.33, 3.33, 3.33, 0.01.
//	 * @return the divided and separated amounts, and, if
//	 *         addDifferenceToLastValue is false, an additional amount instance
//	 *         containing the rounding difference.
//	 */
//	public Collection<MonetaryAmount> call() {
//		throw new UnsupportedOperationException("Not implemented");
//	}
//
//}
