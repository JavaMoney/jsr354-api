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
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryConversionsSpi;

/**
 * This singleton defines access to the exchange and currency conversion logic of JavaMoney. It
 * allows to evaluate the currently available exchange rate type instances and provides access to
 * the corresponding {@link ExchangeRateProvider} instances.
 * <p>
 * This class is thread safe.
 * <p>
 * This class is designed to support also contextual behaviour, e.g. in Java EE containers each
 * application may provide its own {@link ExchangeRateProvider} instances, e.g. by registering them
 * as CDI beans. An EE container can register an according {@link MonetaryConversionsSpi}
 * that manages the different application contexts transparently. In a SE environment this class is
 * expected to behave like an ordinary singleton, loading its SPIs from the {@link ServiceLoader}.
 * <p>
 * This class is thread-safe. Hereby it is important to know that it delegates to the registered
 * {@link MonetaryConversionsSpi} SPI, which also is required to be thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryConversions {

	/**
	 * The SPI currently active, use {@link ServiceLoader} to register an alternate implementation.
	 */
	private static final MonetaryConversionsSpi MONETARY_CONVERSION_SPI = Bootstrap
			.getService(MonetaryConversionsSpi.class);

	/**
	 * Private singleton constructor.
	 */
	private MonetaryConversions() {
	}

	/**
	 * Access an instance of {@link CurrencyConversion} for the given {@link ExchangeRateType}. Use
	 * {@link #getSupportedExchangeRateTypes()} to check, which are available.
	 * 
	 * @param type
	 *            the {@link ExchangeRateType} that identifies the provider instance to be accessed,
	 *            not {@code null}.
	 * 
	 * @return the exchange rate type if this instance.
	 * @throws IllegalArgumentException
	 *             if no such {@link ExchangeRateProvider} is available.
	 */
	public static CurrencyConversion getConversion(CurrencyUnit targetConversion) {
		return getConversion(
				getDefaultConversionContext(), targetConversion);
	}

	private static ConversionContext getDefaultConversionContext() {
		return MONETARY_CONVERSION_SPI.getDefaultConversionContext();
	}

	/**
	 * Access an instance of {@link CurrencyConversion} for the given {@link ExchangeRateType}. Use
	 * {@link #getSupportedExchangeRateTypes()} to check, which are available.
	 * 
	 * @param conversionContext
	 *            the {@link ConversionContext} required, not {@code null}.
	 * @param termCurrency
	 *            the terminating or target currency, not {@code null}.
	 * @return the exchange rate type if this instance.
	 * @throws IllegalArgumentException
	 *             if no such {@link ExchangeRateProvider} is available.
	 */
	public static CurrencyConversion getConversion(
			ConversionContext conversionContext, CurrencyUnit termCurrency) {
		return MONETARY_CONVERSION_SPI.getConversion(conversionContext,
				termCurrency);
	}

	/**
	 * Access an instance of {@link CurrencyConversion} for the given {@link ExchangeRateType}. Use
	 * {@link #getSupportedExchangeRateTypes()} to check, which are available.
	 * 
	 * @param conversionContext
	 *            the {@link ConversionContext} required, not {@code null}.
	 * @return the exchange rate provider.
	 * @throws IllegalArgumentException
	 *             if no such {@link ExchangeRateProvider} is available.
	 */
	public static ExchangeRateProvider getRateProvider(
			ConversionContext conversionContext) {
		return MONETARY_CONVERSION_SPI
				.getExchangeRateProvider(conversionContext);
	}

	/**
	 * Return all supported {@link ConversionContext} instances for which
	 * {@link ExchangeRateProvider} or {@link CurrencyConversion} instances can be obtained.
	 * 
	 * @return all supported {@link ConversionContext} instances, never {@code null}.
	 */
	public static Collection<ConversionContext> getSupportedConversionContexts() {
		return MONETARY_CONVERSION_SPI.getSupportedConversionContexts();
	}

	/**
	 * Checks if a {@link ExchangeRateProvider} can be accessed for the given
	 * {@link ExchangeRateType}.
	 * 
	 * @param conversionContext
	 *            the required {@link ConversionContext}, not {@code null}
	 * @return true, if a {@link ExchangeRateProvider} or {@link CurrencyConversion} for this
	 *         {@link ConversionContext} can be obtained from this {@link MonetaryConversions}
	 *         instance.
	 */
	public static boolean isSupportedConversionContext(
			ConversionContext conversionContext) {
		return MONETARY_CONVERSION_SPI
				.isSupportedConversionContext(conversionContext);
	}

}
