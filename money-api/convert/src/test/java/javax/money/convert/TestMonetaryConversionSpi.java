package javax.money.convert;

import java.util.Arrays;
import java.util.Collection;

import javax.money.CurrencyUnit;
import javax.money.convert.MonetaryConversions.MonetaryConversionSpi;

public class TestMonetaryConversionSpi implements MonetaryConversionSpi {

	private ConversionProvider dummyProvider = new DummyConversionProvider();
	@Override
	public ConversionProvider getConversionProvider(ExchangeRateType type) {
		if(ExchangeRateType.of("test").equals(type)){
			return dummyProvider;
		}
		return null;
	}

	@Override
	public Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
		return Arrays.asList(new ExchangeRateType[] { ExchangeRateType
				.of("test") });
	}

	@Override
	public boolean isSupportedExchangeRateType(ExchangeRateType type) {
		return ExchangeRateType.of("test").equals(type);
	}

	public static final class DummyConversionProvider implements ConversionProvider{

		@Override
		public ExchangeRateType getExchangeRateType() {
			return ExchangeRateType.of("test");
		}

		@Override
		public boolean isAvailable(CurrencyUnit src, CurrencyUnit target) {
			return false;
		}

		@Override
		public boolean isAvailable(CurrencyUnit CurrencyUnit,
				CurrencyUnit target, Long timestamp) {
			return false;
		}

		@Override
		public ExchangeRate getExchangeRate(CurrencyUnit sourceCurrency,
				CurrencyUnit targetCurrency, Long timestamp) {
			return null;
		}

		@Override
		public ExchangeRate getExchangeRate(CurrencyUnit source,
				CurrencyUnit target) {
			return null;
		}

		@Override
		public ExchangeRate getReversed(ExchangeRate rate) {
			return null;
		}

		@Override
		public CurrencyConverter getConverter() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
