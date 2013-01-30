/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 *    Anatole Tresch - extensions and adaptions.
 */
package net.java.javamoney.ri.convert;

//import static java.util.FormattableFlags.LEFT_JUSTIFY;
//
//import java.math.BigDecimal;
//import java.math.MathContext;
//import java.util.Formatter;
//import java.util.Locale;
//
//import java.util.Currency;
//
//import javax.money.CurrencyUnit;
//import javax.money.MonetaryAmount;
//import javax.money.convert.ExchangeRate;
//
///**
// * <p>
// * This class represents a converter between two currencies using
// * {@code BigDecimal}
// * </p>
// * 
// * <p>
// * CurrencyUnit converters convert values based upon the current exchange rate
// * {@link CurrencyUnit#getExchangeRate() exchange rate}. If the
// * {@link CurrencyUnit#getExchangeRate() exchange rate} from the target
// * CurrencyUnit to the source CurrencyUnit is not set, conversion fails. In
// * others words, the converter from a CurrencyUnit <code>A</code> to a
// * CurrencyUnit <code>B</code> is independant from the converter from
// * <code>B</code> to <code>A</code>.
// * </p>
// * 
// * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
// * @version 0.2.3
// */
//public final class BigCurrencyConverter implements CurrencyConverter {
//
//	/**
//	 * Holds the exchange rate.
//	 */
//	private final ExchangeRate rate;
//
//	private void checkFactor(Number factor) {
//		if (factor == null)
//			throw new UnsupportedOperationException("Exchange Rate not set "
//					+ rate.getSourceCurrency() + "->" + rate.getTargetCurrency()); //$NON-NLS-1$
//	}
//
//	private static final CurrencyUnit fromJDK(java.util.Currency jdkCurrency) {
//		return Currency.getInstance(jdkCurrency.getCurrencyCode());
//	}
//
//	/**
//	 * Creates the CurrencyUnit converter from the source CurrencyUnit to the
//	 * target CurrencyUnit.
//	 * 
//	 * @param source
//	 *            the source CurrencyUnit.
//	 * @param target
//	 *            the target CurrencyUnit.
//	 * @param factor
//	 *            the multiplier factor from source to target.
//	 * @return the corresponding converter.
//	 */
//	protected BigCurrencyConverter(CurrencyUnit source, CurrencyUnit target,
//			BigDecimal factor) {
//		rate = new ExchangeRateImpl(source, target, factor, null);
//	}
//
//	/**
//	 * Creates the CurrencyUnit converter from the source CurrencyUnit to the
//	 * target CurrencyUnit.
//	 * 
//	 * @param source
//	 *            the source Money.
//	 * @param target
//	 *            the target CurrencyUnit.
//	 * @param factor
//	 *            the multiplier factor from source to target.
//	 * @return the corresponding converter.
//	 */
//	public BigCurrencyConverter(Amount source, CurrencyUnit target,
//			BigDecimal factor) {
//		rate = new ExchangeRateImpl(source.getCurrency(), target, factor, null);
//	}
//
//	/**
//	 * Creates the CurrencyUnit converter from the source CurrencyUnit to the
//	 * target CurrencyUnit using <strong>JDK</strong> types.
//	 * 
//	 * @param source
//	 *            the source CurrencyUnit (<strong>JDK</strong>).
//	 * @param target
//	 *            the target CurrencyUnit (<strong>JDK</strong>).
//	 * @param factor
//	 *            the multiplier factor from source to target.
//	 * @return the corresponding converter.
//	 */
//	public BigCurrencyConverter(java.util.Currency source,
//			java.util.Currency target, BigDecimal factor) {
//		this(fromJDK(source), fromJDK(target), factor);
//	}
//
//	/**
//	 * Returns the source CurrencyUnit.
//	 * 
//	 * @return the source CurrencyUnit.
//	 */
//	public CurrencyUnit getSource() {
//		return rate.getSource();
//	}
//
//	/**
//	 * Returns the target CurrencyUnit.
//	 * 
//	 * @return the target CurrencyUnit.
//	 */
//	public CurrencyUnit getTarget() {
//		return rate.getTarget();
//	}
//
//	public BigCurrencyConverter inverse() {
//		return new BigCurrencyConverter(rate.getTarget(), rate.getSource(),
//				rate.getFactor());
//	}
//
//	public BigCurrencyConverter negate() {
//		return new BigCurrencyConverter(rate.getSource(), rate.getTarget(),
//				rate.getFactor().negate());
//	}
//
//	public double convert(double value) {
//		// Number factor = getExchangeRate(rate.getTarget());
//		Number factor = rate.getFactor();
//		checkFactor(factor);
//		return factor.doubleValue() * value;
//	}
//
//	public Amount convert(Amount value) {
//		return convert(value, MathContext.DECIMAL128);
//	}
//
//	public Amount convert(Amount value, MathContext ctx)
//			throws ArithmeticException {
//		// Number factor = rate.getSource().getExchangeRate(rate.getTarget());
//		Number factor = rate.getFactor();
//		checkFactor(factor);
//		return value.multiply(factor, ctx);
//	}
//	
//	public Amount convert(Number value) {
//		return convert(value, MathContext.DECIMAL128);
//	}
//
//	public Amount convert(Number value, MathContext ctx)
//			throws ArithmeticException {
//		// Number factor = rate.getSource().getExchangeRate(rate.getTarget());
//		Number factor = rate.getFactor();
//		checkFactor(factor);
//		return value.multiply(factor, ctx);
//	}
//
//	@Override
//	public boolean equals(Object cvtr) {
//		if (!(cvtr instanceof BigCurrencyConverter))
//			return false;
//		BigCurrencyConverter that = (BigCurrencyConverter) cvtr;
//		return this.rate.getSource().equals(that.rate.getSource())
//				&& this.rate.getTarget().equals(that.rate.getTarget());
//	}
//
//	@Override
//	public int hashCode() {
//		return rate.getSource().hashCode() + rate.getTarget().hashCode();
//	}
//
//	public boolean isLinear() {
//		return true;
//	}
//
//	public boolean isIdentity() {
//		return false;
//	}
//
//	public ExchangeRate getExchangeRate() {
//		return rate;
//	}
//
//}
