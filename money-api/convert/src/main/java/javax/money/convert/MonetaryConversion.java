/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.convert;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This interface defines access to the exchange and currency conversion logic
 * of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryConversion {

	private static MonetaryConversionSpi monetaryConversionSpi = loadMonetaryConversionSpi();

	private MonetaryConversion() {
	}

	private static MonetaryConversionSpi loadMonetaryConversionSpi() {
		try {
			// try loading directly from ServiceLoader
			Iterator<MonetaryConversionSpi> instances = ServiceLoader.load(
					MonetaryConversionSpi.class).iterator();
			if (instances.hasNext()) {
				return instances.next();
			}
		} catch (Throwable e) {
			Logger.getLogger(MonetaryConversion.class.getName()).log(
					Level.INFO,
					"No MonetaryConversionSpi registered, using  default.", e);
		}
		return new DefaultMonetaryConversionSpi();
	}

	public static interface MonetaryConversionSpi {
		ConversionProvider getConversionProvider(ExchangeRateType type);

		Collection<ExchangeRateType> getSupportedExchangeRateTypes();

		boolean isSupportedExchangeRateType(ExchangeRateType type);
	}

	/**
	 * Access an instance of {@link ConversionProvider} for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the exchange rate type that this provider instance is
	 *            providing data for, not {@code null}.
	 * 
	 * @return the {@link ExchangeRateType} if this instance.
	 */
	public static ConversionProvider getConversionProvider(ExchangeRateType type) {
		ConversionProvider provider = monetaryConversionSpi
				.getConversionProvider(type);
		if (provider == null) {
			throw new IllegalArgumentException("Unsupported Conversion Type: "
					+ type);
		}
		return provider;
	}

	/**
	 * Return all supported {@link ExchangeRateType} instances for which
	 * {@link ConversionProvider} instances can be obtained.
	 * 
	 * @return all supported {@link ExchangeRateType} instances, never
	 *         {@code null}.
	 */
	public static Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
		Collection<ExchangeRateType> rates = monetaryConversionSpi
				.getSupportedExchangeRateTypes();
		if (rates == null) {
			throw new IllegalStateException(
					"Invalid MonetaryConversionSpi: must return non null collection for supported rate types.");
		}
		return rates;
	}

	/**
	 * CHecks if a {@link ConversionProvider} can be accessed for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the required {@link ExchangeRateType}, not {@code null}.
	 * @return true, if a {@link ConversionProvider} for this
	 *         {@link ExchangeRateType} can be obtained from this
	 *         {@link MonetaryConversion} instance.
	 */
	public static boolean isSupportedExchangeRateType(ExchangeRateType type) {
		return monetaryConversionSpi.isSupportedExchangeRateType(type);
	}

	private final static class DefaultMonetaryConversionSpi implements
			MonetaryConversionSpi {

		@Override
		public ConversionProvider getConversionProvider(ExchangeRateType type) {
			throw new IllegalArgumentException(
					"Unsupported ExchangeRateType type: " + type);
		}

		@Override
		public Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
			return Collections.emptySet();
		}

		@Override
		public boolean isSupportedExchangeRateType(ExchangeRateType type) {
			return false;
		}

	}

}
