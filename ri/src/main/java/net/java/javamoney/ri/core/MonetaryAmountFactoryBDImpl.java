package net.java.javamoney.ri.core;

import java.math.BigDecimal;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;

public class MonetaryAmountFactoryBDImpl implements MonetaryAmountFactory {

	public MonetaryAmount get(CurrencyUnit currency, Number number) {
		return new BigDecimalAmount(currency, number);
	}

	public MonetaryAmount get(CurrencyUnit currency, byte value) {
		return new BigDecimalAmount(currency, new Byte(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, short value) {
		return new BigDecimalAmount(currency, new Short(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, int value) {
		return new BigDecimalAmount(currency, new Integer(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, long value) {
		return new BigDecimalAmount(currency, new Long(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, long major, long minor) {
		if(minor < 0){
			throw new IllegalArgumentException("Minor value must not be < 0.");
		}
		return new BigDecimalAmount(currency, new BigDecimal(
				String.valueOf(major) + '.' + minor));
	}

	public MonetaryAmount get(CurrencyUnit currency, float value) {
		return new BigDecimalAmount(currency, new Float(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, double value) {
		return new BigDecimalAmount(currency, new Double(value));
	}

	public MonetaryAmount zero(CurrencyUnit currency) {
		return new BigDecimalAmount(currency, new Double(0.0d));
	}

	public Class<?> getNumberClass() {
		return BigDecimal.class;
	}

}
