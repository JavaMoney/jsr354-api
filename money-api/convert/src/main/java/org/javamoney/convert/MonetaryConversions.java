/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package org.javamoney.convert;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javamoney.convert.spi.MonetaryConversionsSingletonSpi;

/**
 * This singleton defines access to the exchange and currency conversion logic
 * of JavaMoney. It allows to evaluate the currently available exchange rate
 * type instances and provides access to the corresponding
 * {@link ConversionProvider} instances.
 * <p>
 * This class is thread safe.
 * <p>
 * This class is designed to support also contextual behaviour, e.g. in Java EE
 * containers each application may provide its own {@link ConversionProvider}
 * instances, e.g. by registering them as CDI beans. An EE container can
 * register an according {@link MonetaryConversionsSingletonSpi} that manages
 * the different application contexts transparently. In a SE environment this
 * class is expected to behave like an ordinary singleton, loading its SPIs from
 * the {@link ServiceLoader}.
 * <p>
 * This class is thread-safe. Hereby it is important to know that it delegates
 * to the registered {@link MonetaryConversionsSingletonSpi} SPI, which also is
 * required to be thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryConversions {

	/**
	 * The SPI currently active, use {@link ServiceLoader} to register an
	 * alternate implementation.
	 */
	private static final MonetaryConversionsSingletonSpi MONETARY_CONVERSION_SPI = loadMonetaryConversionSpi();

	/**
	 * Private singleton constructor.
	 */
	private MonetaryConversions() {
	}

	/**
	 * Method that loads the {@link MonetaryConversionsSpi} on class loading.
	 * 
	 * @return the instance ot be registered into the shared variable.
	 */
	private static MonetaryConversionsSingletonSpi loadMonetaryConversionSpi() {
		try {
			// try loading directly from ServiceLoader
			Iterator<MonetaryConversionsSingletonSpi> instances = ServiceLoader
					.load(
							MonetaryConversionsSingletonSpi.class).iterator();
			MonetaryConversionsSingletonSpi spiLoaded = null;
			if (instances.hasNext()) {
				spiLoaded = instances.next();
				if (instances.hasNext()) {
					throw new IllegalStateException(
							"Ambigous reference to spi (only "
									+ "one can be registered: "
									+ MonetaryConversionsSingletonSpi.class
											.getName());
				}
				return spiLoaded;
			}
		} catch (Throwable e) {
			Logger.getLogger(MonetaryConversions.class.getName()).log(
					Level.WARNING,
					"No MonetaryConversionSpi registered, using  default.", e);
		}
		return new DefaultMonetaryConversionsSpi();
	}

	/**
	 * Access an instance of {@link ConversionProvider} for the given
	 * {@link ExchangeRateType}. Use {@link #getSupportedExchangeRateTypes()} to
	 * check, which {@link ConversionProvider}s are available.
	 * 
	 * @param type
	 *            the {@link ExchangeRateType} that identifies the provider
	 *            instance to be accessed, not {@code null}.
	 * 
	 * @return the exchange rate type if this instance.
	 * @throws IllegalArgumentException
	 *             if no such {@link ConversionProvider} is available.
	 */
	public static ConversionProvider getConversionProvider(ExchangeRateType type) {
		return MONETARY_CONVERSION_SPI.getConversionProvider(type);
	}

	/**
	 * Return all supported {@link ExchangeRateType} instances for which
	 * {@link ConversionProvider} instances can be obtained.
	 * 
	 * @return all supported exchange rate type instances, never {@code null}.
	 */
	public static Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
		return MONETARY_CONVERSION_SPI.getSupportedExchangeRateTypes();
	}

	/**
	 * Checks if a {@link ConversionProvider} can be accessed for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param type
	 *            the required {@link ExchangeRateType}, not {@code null}.
	 * @return true, if a {@link ConversionProvider} for this exchange rate type
	 *         can be obtained from this {@link MonetaryConversions} instance.
	 */
	public static boolean isSupportedExchangeRateType(ExchangeRateType type) {
		return MONETARY_CONVERSION_SPI.isSupportedExchangeRateType(type);
	}

	/**
	 * This class represents the default implementation of
	 * {@link MonetaryConversionsSpi} used always when no alternative is
	 * registered within the {@link ServiceLoader}.
	 * 
	 * @author Anatole Tresch
	 * 
	 */
	private final static class DefaultMonetaryConversionsSpi implements
			MonetaryConversionsSingletonSpi {

		/**
		 * The default does not provide any {@link ConversionProvider} as of
		 * now.
		 */
		@Override
		public ConversionProvider getConversionProvider(ExchangeRateType type) {
			// TODO Check by TCK!
			// if (type == null) {
			// throw new
			// IllegalArgumentException("Unsupported Conversion Type: "
			// + type);
			// }
			throw new IllegalArgumentException(
					"Unsupported ExchangeRateType type: " + type);
		}

		/**
		 * Returns always an empty collection.
		 */
		@Override
		public Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
			// TODO Check by TCK!
			// Collection<ExchangeRateType> rates = MONETARY_CONVERSION_SPI
			// .getSupportedExchangeRateTypes();
			// if (rates == null) {
			// throw new IllegalStateException(
			// "Invalid MonetaryConversionSpi: must return non null collection for supported rate types.");
			// }
			// return rates;
			return Collections.emptySet();
		}

		/**
		 * Returns always false.
		 */
		@Override
		public boolean isSupportedExchangeRateType(ExchangeRateType type) {
			return false;
		}
	}
}
