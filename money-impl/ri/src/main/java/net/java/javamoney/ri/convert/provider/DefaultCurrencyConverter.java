package net.java.javamoney.ri.convert.provider;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.convert.ConversionProvider;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;

import net.java.javamoney.ri.convert.FixedCurrencyConversion;
import net.java.javamoney.ri.convert.LazyBoundCurrencyConversion;

/**
 * @author Anatole Tresch
 * @author Werner Keil
 *
 */
public class DefaultCurrencyConverter implements CurrencyConverter {

	private final ConversionProvider rateProvider;

	public DefaultCurrencyConverter(ConversionProvider rateProvider) {
		if (rateProvider == null) {
			throw new IllegalArgumentException("rateProvider required.");
		}
		this.rateProvider = rateProvider;
	}

	@Override
	public ExchangeRateType getExchangeRateType() {
		return this.rateProvider.getExchangeRateType();
	}

	@Override
	public <T extends MonetaryAmount> T  convert(T amount, CurrencyUnit target) {
		ExchangeRate rate = this.rateProvider.getExchangeRate(
				amount.getCurrency(), target);
		if (rate == null) {
			throw new CurrencyConversionException(amount.getCurrency(), target,
					null, "No rate available.");
		}
		return (T)new FixedCurrencyConversion(rate).apply(amount);
	}

	@Override
	public <T extends MonetaryAmount> T convert(T amount, CurrencyUnit target,
			Long timestamp) {
		ExchangeRate rate = this.rateProvider.getExchangeRate(
				amount.getCurrency(), target, timestamp);
		if (rate == null) {
			throw new CurrencyConversionException(amount.getCurrency(), target,
					null, "No rate available.");
		}
		return (T)new FixedCurrencyConversion(rate).apply(amount);
	}

	@Override
	public CurrencyConversion getConversion(CurrencyUnit base, CurrencyUnit term) {
		ExchangeRate rate = this.rateProvider.getExchangeRate(base, term);
		if (rate == null) {
			throw new CurrencyConversionException(base, term, null,
					"No rate available.");
		}
		return new FixedCurrencyConversion(rate);
	}

	@Override
	public CurrencyConversion getConversion(CurrencyUnit base,
			CurrencyUnit term, long targetTimestamp) {
		ExchangeRate rate = this.rateProvider.getExchangeRate(base, term,
				targetTimestamp);
		if (rate == null) {
			throw new CurrencyConversionException(base, term, targetTimestamp,
					"No rate available.");
		}
		return new FixedCurrencyConversion(rate);
	}

	@Override
	public CurrencyConversion getConversion(CurrencyUnit term) {
		return new LazyBoundCurrencyConversion(term, this.rateProvider);
	}

	@Override
	public CurrencyConversion getConversion(CurrencyUnit term,
			long targetTimestamp) {
		return new LazyBoundCurrencyConversion(term, this.rateProvider, targetTimestamp);
	}

}
