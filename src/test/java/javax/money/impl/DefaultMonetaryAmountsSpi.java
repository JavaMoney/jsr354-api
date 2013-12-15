package javax.money.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.AmountFactory;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.DummyAmountFactory.DummyAmount;
import javax.money.bootstrap.Bootstrap;
import javax.money.spi.MonetaryAmountsSpi;

public class DefaultMonetaryAmountsSpi implements MonetaryAmountsSpi {

	private Map<Class<? extends MonetaryAmount<?>>, AmountFactory<?>> factories = new ConcurrentHashMap<>();

	public DefaultMonetaryAmountsSpi() {
		for (AmountFactory f : Bootstrap.getServices(AmountFactory.class)) {
			factories.put(f.getImplementationType(), f);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends MonetaryAmount<T>> AmountFactory<T> getFactory(
			Class<T> amountType) {
		return (AmountFactory<T>) factories.get(amountType);
	}

	@Override
	public Set<Class<? extends MonetaryAmount<?>>> getTypes() {
		return factories.keySet();
	}

	@Override
	public Class<? extends MonetaryAmount<?>> getDefaultAmountType() {
		return DummyAmount.class;
	}

	@Override
	public Class<? extends MonetaryAmount<?>> getAmountType(
			MonetaryContext<?> requiredContext) {
		return DummyAmount.class;
	}

}
