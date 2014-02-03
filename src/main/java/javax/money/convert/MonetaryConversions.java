/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money.convert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryConversionsSpi;

/**
 * This singleton defines access to the exchange and currency conversion logic
 * of JavaMoney. It allows to evaluate the currently available exchange rate
 * type instances and provides access to the corresponding
 * {@link ExchangeRateProvider} instances.
 * <p>
 * This class is thread safe.
 * <p>
 * This class is designed to support also contextual behaviour, e.g. in Java EE
 * containers each application may provide its own {@link ExchangeRateProvider}
 * instances, e.g. by registering them as CDI beans. An EE container can
 * register an according {@link MonetaryConversionsSpi} that manages the
 * different application contexts transparently. In a SE environment this class
 * is expected to behave like an ordinary singleton, loading its SPIs from the
 * {@link ServiceLoader}.
 * <p>
 * This class is thread-safe. Hereby it is important to know that it delegates
 * to the registered {@link MonetaryConversionsSpi} SPI, which also is required
 * to be thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryConversions {

	/**
	 * The SPI currently active, use {@link ServiceLoader} to register an
	 * alternate implementation.
	 */
	private static final MonetaryConversionsSpi MONETARY_CONVERSION_SPI = Bootstrap
			.getService(MonetaryConversionsSpi.class);

	/**
	 * Private singleton constructor.
	 */
	private MonetaryConversions() {
	}

	/**
	 * Access an instance of {@link CurrencyConversion} for the given providers.
	 * Use {@link #getSupportedExchangeRateTypes()} to check, which are
	 * available.
	 * 
	 * @param termCurrency
	 *            the terminating or target currency, not {@code null}
	 * @param conversionContext
	 *            the {@link ConversionContext} required, not {@code null}
	 * @param providers
	 *            Additional providers, for building a provider chain
	 * @return the exchange rate type if this instance.
	 * @throws IllegalArgumentException
	 *             if no such {@link ExchangeRateProvider} is available.
	 */
	public static CurrencyConversion getConversion(CurrencyUnit termCurrency,
			String... providers) {
		if (providers.length == 0) {
			return MONETARY_CONVERSION_SPI.getConversion(termCurrency,
					ConversionContext.of(),
					getDefaultProviderChain().toArray(new String[0]));
		}
		return MONETARY_CONVERSION_SPI.getConversion(termCurrency,
				ConversionContext.of(), providers);
	}

	/**
	 * Access an instance of {@link CurrencyConversion} for the given providers.
	 * Use {@link #getSupportedExchangeRateTypes()} to check, which are
	 * available.
	 * 
	 * @param termCurrency
	 *            the terminating or target currency, not {@code null}
	 * @param conversionContext
	 *            the {@link ConversionContext} required, not {@code null}
	 * @param providers
	 *            Additional providers, for building a provider chain
	 * @return the exchange rate type if this instance.
	 * @throws IllegalArgumentException
	 *             if no such {@link ExchangeRateProvider} is available.
	 */
	public static CurrencyConversion getConversion(CurrencyUnit termCurrency,
			ConversionContext conversionContext, String... providers) {
		if (providers.length == 0) {
			return MONETARY_CONVERSION_SPI.getConversion(termCurrency,
					conversionContext,
					getDefaultProviderChain().toArray(new String[0]));
		}
		return MONETARY_CONVERSION_SPI.getConversion(termCurrency,
				conversionContext, providers);
	}

	/**
	 * Access an instance of {@link CurrencyConversion} for the given
	 * {@link ExchangeRateType}. Use {@link #getSupportedExchangeRateTypes()} to
	 * check, which are available.
	 * 
	 * @param conversionContext
	 *            the {@link ConversionContext} required, not {@code null}.
	 * @return the exchange rate provider.
	 * @throws IllegalArgumentException
	 *             if no such {@link ExchangeRateProvider} is available.
	 */
	public static ExchangeRateProvider getExchangeRateProvider(
			String... providers) {
		if (providers.length == 0) {
			return MONETARY_CONVERSION_SPI
					.getExchangeRateProvider(getDefaultProviderChain().toArray(
							new String[0]));
		}
		return MONETARY_CONVERSION_SPI.getExchangeRateProvider(providers);
	}

	/**
	 * Return the (non localized) names of all providers available in the
	 * current context. Each id can be used to obtain
	 * {@link ExchangeRateProvider} or {@link CurrencyConversion} instances.
	 * 
	 * @return all supported provider ids, never {@code null}.
	 */
	public static Collection<String> getProvidersNames() {
		Collection<String> providers = MONETARY_CONVERSION_SPI
				.getProviderNames();
		if (providers == null) {
			Logger.getLogger(MonetaryConversions.class.getName()).warning(
					"No supported rate/conversion providers returned by SPI: "
							+ MONETARY_CONVERSION_SPI.getClass().getName());
			return Collections.emptySet();
		}
		return providers;
	}

	/**
	 * Get the {@link ProviderContext} for a provider.
	 * 
	 * @param provider
	 *            the provider name, not {@code null}.
	 * @return the corresponding {@link ProviderContext}, not {@code null}.
	 * @throws IllegalArgumentException
	 *             if no such provider is registered.
	 */
	public static ProviderContext getProviderContext(String provider) {
		return MONETARY_CONVERSION_SPI.getProviderContext(provider);
	}

	/**
	 * Checks if a provider is available in the current context.
	 * 
	 * @param provider
	 *            the provider name, not {@code null}
	 * @return true, if the rate/conversion provider with the given name is
	 *         available, so {@link CurrencyConversion} or
	 *         {@link ExchangeRateProvider} with this provider name can be
	 *         obtained from the {@link MonetaryConversions} instance.
	 */
	public static boolean isProviderAvailable(String provider) {
		return MONETARY_CONVERSION_SPI.isProviderAvailable(provider);
	}

	/**
	 * Get the default provider used.
	 * 
	 * @return the default provider, never {@code null}.
	 */
	public static List<String> getDefaultProviderChain() {
		List<String> defaultChain = MONETARY_CONVERSION_SPI
				.getDefaultProviderChain();
		Objects.requireNonNull(defaultChain,
				"No default provider chain provided by SPI: "
						+ MONETARY_CONVERSION_SPI.getClass().getName());
		return defaultChain;
	}

}
