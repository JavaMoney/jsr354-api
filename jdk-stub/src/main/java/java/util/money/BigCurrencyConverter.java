package java.util.money;

/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

import static java.util.FormattableFlags.LEFT_JUSTIFY;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Formatter;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRate;

/**
 * <p>
 * This class represents a converter between two currencies using {@code BigDecimal}
 * </p>
 * 
 * <p>
 * CurrencyUnit converters convert values based upon the current exchange rate
 * {@link CurrencyUnit#getExchangeRate() exchange rate}. If the
 * {@link CurrencyUnit#getExchangeRate() exchange rate} from the target CurrencyUnit to
 * the source CurrencyUnit is not set, conversion fails. In others words, the
 * converter from a CurrencyUnit <code>A</code> to a CurrencyUnit <code>B</code> is
 * independant from the converter from <code>B</code> to <code>A</code>.
 * </p>
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.2.2
 */
public final class BigCurrencyConverter implements CurrencyConverter<BigDecimal> {

	/**
	 * Holds the exchange rate.
	 */
	private final ExchangeRate<BigDecimal> rate;

	private void checkFactor(Number factor) {
		if (factor == null)
			throw new UnsupportedOperationException("Exchange Rate not set " +
					rate.getSource() + "->" + rate.getTarget()); //$NON-NLS-1$
	}

	private static final CurrencyUnit fromJDK(java.util.Currency jdkCurrency) {
		return Currency.getInstance(jdkCurrency.getCurrencyCode());
	}

	/**
	 * Creates the CurrencyUnit converter from the source CurrencyUnit to the target
	 * CurrencyUnit.
	 * 
	 * @param source
	 *            the source CurrencyUnit.
	 * @param target
	 *            the target CurrencyUnit.
	 * @param factor
	 *            the multiplier factor from source to target.
	 * @return the corresponding converter.
	 */
	protected BigCurrencyConverter(CurrencyUnit source, CurrencyUnit target, BigDecimal factor) {
		rate = new ExchangeRate<BigDecimal>(source, target, factor);
	}

	/**
	 * Creates the CurrencyUnit converter from the source CurrencyUnit to the target
	 * CurrencyUnit.
	 * 
	 * @param source
	 *            the source CurrencyUnit.
	 * @param target
	 *            the target CurrencyUnit.
	 * @param factor
	 *            the multiplier factor from source to target.
	 * @return the corresponding converter.
	 */
	public BigCurrencyConverter(CurrencyUnit source, Money target,
			BigDecimal factor) {
		if (target instanceof Monetary) {
			rate = new ExchangeRate<BigDecimal>(source, target.getCurrencyUnit(),
					factor);
		} else {
			CurrencyUnit defCurrency = Currency.getInstance(Locale.getDefault());
			rate = new ExchangeRate<BigDecimal>(defCurrency, defCurrency, factor);
		}
	}

	/**
	 * Creates the CurrencyUnit converter from the source CurrencyUnit to the target
	 * CurrencyUnit using <strong>JDK</strong> types.
	 * 
	 * @param source
	 *            the source CurrencyUnit (<strong>JDK</strong>).
	 * @param target
	 *            the target CurrencyUnit (<strong>JDK</strong>).
	 * @param factor
	 *            the multiplier factor from source to target.
	 * @return the corresponding converter.
	 */
	public BigCurrencyConverter(java.util.Currency source,
			java.util.Currency target, BigDecimal factor) {
		this(fromJDK(source), fromJDK(target), factor);
	}

	/**
	 * Returns the source CurrencyUnit.
	 * 
	 * @return the source CurrencyUnit.
	 */
	public CurrencyUnit getSource() {
		return rate.getSource();
	}

	/**
	 * Returns the target CurrencyUnit.
	 * 
	 * @return the target CurrencyUnit.
	 */
	public CurrencyUnit getTarget() {
		return rate.getTarget();
	}

	public BigCurrencyConverter inverse() {
		return new BigCurrencyConverter(rate.getTarget(), rate.getSource(),
				rate.getFactor());
	}

	public BigCurrencyConverter negate() {
		return new BigCurrencyConverter(rate.getSource(), rate.getTarget(), rate
				.getFactor().negate());
	}

	public double convert(double value) {
		// Number factor = getExchangeRate(rate.getTarget());
		Number factor = rate.getFactor();
		checkFactor(factor);
		return factor.doubleValue() * value;
	}

	public Number convert(Number value, MathContext ctx)
			throws ArithmeticException {
		// Number factor = rate.getSource().getExchangeRate(rate.getTarget());
		Number factor = rate.getFactor();
		checkFactor(factor);
		if (factor instanceof BigDecimal)
			return ((BigDecimal)value).multiply((BigDecimal) factor, ctx);
		if (factor instanceof Number) {
			return ((BigDecimal)value).multiply(
					(BigDecimal.valueOf(((Number) factor).doubleValue())), ctx);
		} else { // Reverts to double convert.
			return ((BigDecimal)value)
					.multiply(BigDecimal.valueOf(factor.doubleValue()), ctx);
		}
	}

	public Number convert(Number value) {
		if (value instanceof BigDecimal) {
			return convert((BigDecimal) value, MathContext.DECIMAL128);
		} else {
			return convert(value.doubleValue());
		}
	}

	@Override
	public boolean equals(Object cvtr) {
		if (!(cvtr instanceof BigCurrencyConverter))
			return false;
		BigCurrencyConverter that = (BigCurrencyConverter) cvtr;
		return this.rate.getSource().equals(that.rate.getSource())
				&& this.rate.getTarget().equals(that.rate.getTarget());
	}

	@Override
	public int hashCode() {
		return rate.getSource().hashCode() + rate.getTarget().hashCode();
	}

	public boolean isLinear() {
		return true;
	}

	public boolean isIdentity() {
		return false;
	}

	public ExchangeRate<BigDecimal> getExchangeRate() {
		return rate;
	}

	public void formatTo(Formatter fmt, int f, int width, int precision) {
		StringBuilder sb = new StringBuilder();

		// decide form of name
		String name = getSource().toString();
		String symbol = getSource().getSymbol();
		// if (fmt.locale().equals(Locale.FRANCE))
		// name = frenchCompanyName;
		// boolean alternate = (f & ALTERNATE) == ALTERNATE;
		boolean usesymbol = true; // alternate || (precision != -1 && precision
									// < 10);
		String out = (usesymbol ? symbol : name);

		// apply precision
		if (precision == -1 || out.length() < precision) {
			// write it all
			sb.append(out);
		} else {
			sb.append(out.substring(0, precision - 1)).append('*');
		}

		// apply width and justification
		int len = sb.length();
		if (len < width)
			for (int i = 0; i < width - len; i++)
				if ((f & LEFT_JUSTIFY) == LEFT_JUSTIFY)
					sb.append(' ');
				else
					sb.insert(0, ' ');

		fmt.format(sb.toString());
	}

	public BigDecimal convert(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}
}
