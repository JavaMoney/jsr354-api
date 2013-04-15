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
 * This singleton defines access to the exchange and currency conversion logic
 * of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryConversion {
	/**
	 * The spi currently active, use {@link ServiceLoader} to register an
	 * altetrnate implementaiont.
	 */
	private static final MonetaryConversionSpi MONETARY_CONVERSION_SPI = loadMonetaryConversionSpi();

	/**
	 * Private singleton constructor.
	 */
	private MonetaryConversion() {
	}

	/**
	 * Method that loads the {@link MonetaryConversionSpi} on class loading.
	 * 
	 * @return the instance ot be registered into the shared variable.
	 */
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

	/**
	 * This is the spi interface to be implemented that determines how the
	 * different components are loaded and managed.
	 * 
	 * @author Anatole Tresch
	 */
	public static interface MonetaryConversionSpi {
		/**
		 * Access an instance of {@link ConversionProvider}.
		 * 
		 * @param type
		 *            The rate type.
		 * @return the provider, if it is a registered rate type, never null.
		 * @see #isSupportedExchangeRateType(ExchangeRateType)
		 */
		ConversionProvider getConversionProvider(ExchangeRateType type);

		/**
		 * Get all currently registered rate types.
		 * 
		 * @return all currently registered rate types
		 */
		Collection<ExchangeRateType> getSupportedExchangeRateTypes();

		/**
		 * Allows to quickly check, if a rate type is supported.
		 * 
		 * @param type
		 *            the rate type
		 * @return true, if the rate is supported, meaning an according
		 *         {@link ConversionProvider} can be loaded.
		 * @see #getConversionProvider(ExchangeRateType)
		 */
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
		ConversionProvider provider = MONETARY_CONVERSION_SPI
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
		Collection<ExchangeRateType> rates = MONETARY_CONVERSION_SPI
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
		return MONETARY_CONVERSION_SPI.isSupportedExchangeRateType(type);
	}

	/**
	 * This class represents the default implementation of
	 * {@link MonetaryConversionSpi} used always when no alternative is
	 * registered within the {@link ServiceLoader}.
	 * 
	 * @author Anatole Tresch
	 * 
	 */
	private final static class DefaultMonetaryConversionSpi implements
			MonetaryConversionSpi {

		/**
		 * The default does not provide any {@link ConversionProvider} as of
		 * now.
		 */
		@Override
		public ConversionProvider getConversionProvider(ExchangeRateType type) {
			throw new IllegalArgumentException(
					"Unsupported ExchangeRateType type: " + type);
		}

		/**
		 * Returns always an empty collection.
		 */
		@Override
		public Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
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
