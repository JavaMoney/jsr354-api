package javax.money.function;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.MonetaryOperator;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.RoundingProviderSpi;

public final class TestRoundingProvider implements RoundingProviderSpi{

	@Override
	public MonetaryOperator getRounding(CurrencyUnit currency) {
		return new MonetaryOperator(){
			@Override
			public <T extends MonetaryAmount<T>> T apply(T value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getRounding(CurrencyUnit currency, long timestamp) {
		return new MonetaryOperator(){
			@Override
			public <T extends MonetaryAmount<T>> T apply(T value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getCashRounding(CurrencyUnit currency) {
		return new MonetaryOperator(){
			@Override
			public <T extends MonetaryAmount<T>> T apply(T value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getCashRounding(CurrencyUnit currency,
			long timestamp) {
		return new MonetaryOperator(){
			@Override
			public <T extends MonetaryAmount<T>> T apply(T value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getCustomRounding(String customRoundingId) {
		return new MonetaryOperator(){
			@Override
			public <T extends MonetaryAmount<T>> T apply(T value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getRounding(MonetaryContext monetaryContext) {
		return new MonetaryOperator(){
			@Override
			public <T extends MonetaryAmount<T>> T apply(T value) {
				return value;
			}
		};
	}

	@Override
	public Set<String> getCustomRoundingIds() {
		return Collections.emptySet();
	}


}
