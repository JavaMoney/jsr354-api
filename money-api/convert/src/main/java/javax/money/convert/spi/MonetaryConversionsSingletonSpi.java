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
package javax.money.convert.spi;

import java.util.Collection;
import java.util.ServiceLoader;

import javax.money.convert.ConversionProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.MonetaryConversions;

/**
 * This is the SPI to be implemented, and that is registered into the
 * {@code MonetaryConversions} singleton accessor. It should be registered as a
 * service using the JDK {@code ServiceLoader}. Hereby only one instance can be
 * registered at a time.<br/>
 * This interface is designed to support also contextual behaviour, e.g. in Java
 * EE containers each application may provide its own {@link ConversionProvider}
 * instances, e.g. by registering them as CDI beans. An EE container can
 * register an according {@link MonetaryConversionsSingletonSpi} that manages
 * the different application contexts transparently. In a SE environment this
 * class is expected to behave like an ordinary singleton, loading its SPIs from
 * the {@link ServiceLoader}.
 * <p>
 * Instances of this class must be thread safe.
 * <p>
 * Only one instance can be registered using the {@link ServiceLoader}. When
 * registering multiple instances the {@link MonetaryConversions} accessor will
 * not work.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryConversionsSingletonSpi {

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
	 *            the {@link ExchangeRateType}
	 * @return {@code true}, if the rate is supported, meaning an according
	 *         {@link ConversionProvider} can be loaded.
	 * @see #getConversionProvider(ExchangeRateType)
	 */
	boolean isSupportedExchangeRateType(ExchangeRateType type);
}
