package net.java.javamoney.ri.convert;

import javax.money.CurrencyUnit;
import javax.money.convert.ConversionType;
import javax.money.convert.ExchangeRate;

import net.java.javamoney.ri.convert.common.AbstractConversionRate;

public final class CurrencyExchangeRate extends
		AbstractConversionRate<CurrencyUnit, CurrencyUnit> implements
		ExchangeRate, Comparable<ExchangeRate> {

	private Long timestamp;
	private Long validUntil;
	private String location;
	private String dataProvider;
	private ExchangeRate[] chain = new ExchangeRate[] { this };

	public CurrencyExchangeRate(
			ConversionType<CurrencyUnit, CurrencyUnit> conversionType,
			CurrencyUnit source, CurrencyUnit target, Number factor) {
		super(conversionType, source, target, factor);
	}

	public CurrencyExchangeRate(
			ConversionType<CurrencyUnit, CurrencyUnit> conversionType,
			CurrencyUnit source, CurrencyUnit target, Number factor,
			ExchangeRate[] chain) {
		super(conversionType, source, target, factor);
		setExchangeRateChain(chain);
	}

	public CurrencyExchangeRate(
			ConversionType<CurrencyUnit, CurrencyUnit> conversionType,
			CurrencyUnit source, CurrencyUnit target, Number factor,
			ExchangeRate[] chain, Long timestamp,
			Long validUntil) {
		super(conversionType, source, target, factor);
		setExchangeRateChain(chain);
		setTimestamp(timestamp);
		setValidUntil(validUntil);
	}

	public CurrencyExchangeRate(
			ConversionType<CurrencyUnit, CurrencyUnit> conversionType,
			CurrencyUnit source, CurrencyUnit target, Number factor,
			Long timestamp, Long validUntil) {
		super(conversionType, source, target, factor);
		setTimestamp(timestamp);
		setValidUntil(validUntil);
	}

	@Override
	public final Long getTimestamp() {
		return timestamp;
	}

	public final void setTimestamp(Long timestamp) {
		ensureWritable();
		this.timestamp = timestamp;
	}

	@Override
	public Long getValidUntil() {
		return validUntil;
	}

	public final void setValidUntil(Long validUntil) {
		ensureWritable();
		this.validUntil = validUntil;
	}

	@Override
	public boolean isValid() {
		return validUntil == null
				|| validUntil.longValue() <= System.currentTimeMillis();
	}

	@Override
	public final String getLocation() {
		return location;
	}

	public final void setLocation(String location) {
		ensureWritable();
		this.location = location;
	}

	@Override
	public final String getDataProvider() {
		return dataProvider;
	}

	@Override
	public boolean isDerived() {
		return this.chain.length > 1;
	}

	public final void setDataProvider(String dataProvider) {
		ensureWritable();
		this.dataProvider = dataProvider;
	}

	public void setExchangeRateChain(ExchangeRate[] chain) {
		if (chain == null) {
			throw new IllegalArgumentException("Chain may not be null.");
		}
		// TODO check chain validity
		this.chain = chain.clone();
	}

	@Override
	public ExchangeRate[] getExchangeRateChain() {
		return this.chain.clone();
	}

	@Override
	public int compareTo(ExchangeRate o) {
		if(o==null){
			return -1;
		}
		int compare = this.getConversionType().compareTo(o.getConversionType());
		if (compare == 0) {
			if (location != null) {
				compare = this.location.compareTo(o.getLocation());
			} else if (o.getLocation() != null) {
				compare = o.getLocation().compareTo(this.location);
			}
		}
		return compare;
	}

}
