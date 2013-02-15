package net.java.javamoney.ri.convert.common;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.money.convert.ExchangeRateType;



public abstract class AbstractConversionRate<S, T> {

	private final S source;
	private final T target;
	private final Number factor;
	private final ExchangeRateType exchangeRateType;
	private Map<String, Object> attributes;
	private boolean readOnly;


	public AbstractConversionRate(ExchangeRateType conversionType,
			S source, T target, Number factor) {
		if (source == null) {
			throw new IllegalArgumentException("source may not be null.");
		}
		if (target == null) {
			throw new IllegalArgumentException("target may not be null.");
		}
		if (factor == null) {
			throw new IllegalArgumentException("factor may not be null.");
		}
		if (conversionType == null) {
			throw new IllegalArgumentException(
					"exchangeRateType may not be null.");
		}
		this.source = source;
		this.target = target;
		this.factor = factor;
		this.exchangeRateType = conversionType;
	}

	public boolean isReadOnloy() {
		return this.readOnly;
	}

	protected void ensureWritable() {
		if (readOnly) {
			throw new IllegalStateException(
					"Item is readOnly and can not be changed.");
		}
	}

	@SuppressWarnings("unchecked")
	public final <A> A getAttribute(String key, Class<A> type) {
		if (attributes != null) {
			return (A) attributes.get(key);
		}
		return null;
	}

	public final Object setAttribute(String key, Object value) {
		ensureWritable();
		if (attributes == null) {
			this.attributes = new HashMap<String, Object>();
		}
		return this.attributes.put(key, value);
	}

	public final Enumeration<String> getAttributeKeys() {
		if (attributes != null) {
			return Collections.enumeration(attributes.keySet());
		}
		return Collections.emptyEnumeration();
	}

	public final Class<?> getAttributeType(String key) {
		if (attributes != null) {
			Object o = attributes.get(key);
			if (o != null) {
				return o.getClass();
			}
		}
		return null;
	}

	public int compareTo(AbstractConversionRate<?,?> other) {
		if(other==null){
			return -1;
		}
		return this.exchangeRateType.compareTo(other.getExchangeRateType());
	}

	public final S getSource() {
		return source;
	}

	public final T getTarget() {
		return target;
	}

	public final Number getFactor() {
		return factor;
	}

	public final ExchangeRateType getExchangeRateType() {
		return exchangeRateType;
	}

}
