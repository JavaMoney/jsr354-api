/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2014, Credit Suisse All rights
 * reserved.
 */
package javax.money.convert;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;
import javax.money.MonetaryException;
import javax.money.MonetaryOperator;

/**
 * This interface defines access to the exchange rates provided by a provider.
 * The provider and its capabilities can be defined in arbitrary detail by the
 * corresponding {@link javax.money.convert.ProviderContext}.
 * Instances of this class must only provide conversion data for exact one provider, identified by
 * {@link ProviderContext#getProvider()}.
 * <br/>
 * When accessing ExchangeRateProvider instances or {@link javax.money.convert.CurrencyConversion} instances from the
 * {@link MonetaryConversions}
 * in many cases a chain of providers will be returned. It is the reponsibility of the implementation code assembling
 * the chain to
 * establish a well defined coordination mechanism for evaluating the correct result. By default the first provider
 * in the chain that returns a non null result determines the final result of a call. Nevertheless adapting the
 * {@link javax.money.spi.MonetaryConversionsSingletonSpi} allows
 * to implement also alternate strategies, e.g. honoring different priorities of providers as well.
 * <p>
 * Implementations of this interface are required to be thread save.
 * <p>
 * Implementations of this class must neither be immutable nor serializable.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface ExchangeRateProvider{

    /**
     * Access the {@link ConversionContext} for this
     * {@link ExchangeRateProvider}. Each instance of
     * {@link ExchangeRateProvider} provides conversion data for exact one
     * {@link ConversionContext} .
     *
     * @return the exchange rate type, never {@code null}.
     */
    ProviderContext getProviderContext();

    /**
     * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
     * available from this provider. This method should check, if a given rate
     * is <i>currently</i> defined. It should be the same as
     * {@code isAvailable(base, term, System.currentTimeMillis())}.
     *
     * @param base              the base {@link CurrencyUnit}
     * @param term              the term {@link CurrencyUnit}
     * @param conversionContext the required {@link ConversionContext}, not {@code null}
     * @return {@code true}, if such an {@link ExchangeRate} is currently
     * defined.
     */
    boolean isAvailable(CurrencyUnit base, CurrencyUnit term, ConversionContext conversionContext);

    /**
     * Access a {@link ExchangeRate} using the given currencies. The
     * {@link ExchangeRate} may be, depending on the data provider, eal-time or
     * deferred. This method should return the rate that is <i>currently</i>
     * valid. It should be the same as
     * {@code getExchangeRate(base, term, System.currentTimeMillis())}.
     *
     * @param base              base {@link CurrencyUnit}, not {@code null}
     * @param term              term {@link CurrencyUnit}, not {@code null}
     * @param conversionContext the required {@link ConversionContext}, not {@code null}
     * @return the matching {@link ExchangeRate}.
     * @throws CurrencyConversionException If no such rate is available.
     */
    ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term, ConversionContext conversionContext);

    /**
     * The method reverses the {@link ExchangeRate} to a rate mapping from term
     * to base {@link CurrencyUnit}. Hereby the factor must <b>not</b> be
     * recalculated as {@code 1/oldFactor}, since typically reverse rates are
     * not symmetric in most cases.
     *
     * @return the matching reversed {@link ExchangeRate}, or {@code null}, if
     * the rate cannot be reversed.
     */
    ExchangeRate getReversed(ExchangeRate rate);

    /**
     * Access a {@link CurrencyConversion} that can be applied as a
     * {@link MonetaryOperator} to an amount.
     *
     * @param term              term {@link CurrencyUnit}, not {@code null}
     * @param conversionContext the required {@link ConversionContext}, not {@code null}
     * @return a new instance of a corresponding {@link CurrencyConversion},
     * never {@code null}.
     */
    CurrencyConversion getCurrencyConversion(CurrencyUnit term, ConversionContext conversionContext);

    /**
     * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
     * available from this provider. This method should check, if a given rate
     * is <i>currently</i> defined. It should be the same as
     * {@code isAvailable(base, term, System.currentTimeMillis())}.
     *
     * @param base the base {@link CurrencyUnit}
     * @param term the term {@link CurrencyUnit}
     * @return {@code true}, if such an {@link ExchangeRate} is currently
     * defined.
     */
    default boolean isAvailable(CurrencyUnit base, CurrencyUnit term){
        return isAvailable(base, term, ConversionContext.ANY_CONVERSION);
    }

    /**
     * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
     * available from this provider. This method should check, if a given rate
     * is <i>currently</i> defined. It should be the same as
     * {@code isAvailable(base, term, System.currentTimeMillis())}.
     *
     * @param baseCode the base currency code
     * @param termCode the terminal/target currency code
     * @return {@code true}, if such an {@link ExchangeRate} is currently
     * defined.
     * @throws MonetaryException if one of the currency codes passed is not valid.
     */
    default boolean isAvailable(String baseCode, String termCode){
        return isAvailable(MonetaryCurrencies.getCurrency(baseCode), MonetaryCurrencies.getCurrency(termCode));
    }

    /**
     * Checks if an {@link ExchangeRate} between two {@link CurrencyUnit} is
     * available from this provider. This method should check, if a given rate
     * is <i>currently</i> defined. It should be the same as
     * {@code isAvailable(base, term, System.currentTimeMillis())}.
     *
     * @param baseCode          the base currency code
     * @param termCode          the terminal/target currency code
     * @param conversionContext the required {@link ConversionContext}, not {@code null}
     * @return {@code true}, if such an {@link ExchangeRate} is currently
     * defined.
     * @throws MonetaryException if one of the currency codes passed is not valid.
     */
    default boolean isAvailable(String baseCode, String termCode, ConversionContext conversionContext){
        return isAvailable(MonetaryCurrencies.getCurrency(baseCode), MonetaryCurrencies.getCurrency(termCode),
                           conversionContext);
    }

    /**
     * Access a {@link ExchangeRate} using the given currencies. The
     * {@link ExchangeRate} may be, depending on the data provider, eal-time or
     * deferred. This method should return the rate that is <i>currently</i>
     * valid. It should be the same as
     * {@code getExchangeRate(base, term, System.currentTimeMillis())}.
     *
     * @param base base {@link CurrencyUnit}, not {@code null}
     * @param term term {@link CurrencyUnit}, not {@code null}
     * @return the matching {@link ExchangeRate}.
     * @throws CurrencyConversionException If no such rate is available.
     */
    default ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term){
        return getExchangeRate(base, term, ConversionContext.ANY_CONVERSION);
    }


    /**
     * Access a {@link ExchangeRate} using the given currencies. The
     * {@link ExchangeRate} may be, depending on the data provider, eal-time or
     * deferred. This method should return the rate that is <i>currently</i>
     * valid. It should be the same as
     * {@code getExchangeRate(base, term, System.currentTimeMillis())}.
     *
     * @param baseCode base currency code, not {@code null}
     * @param termCode term/target currency code, not {@code null}
     * @return the matching {@link ExchangeRate}.
     * @throws CurrencyConversionException If no such rate is available.
     * @throws MonetaryException           if one of the currency codes passed is not valid.
     */
    default ExchangeRate getExchangeRate(String baseCode, String termCode){
        return getExchangeRate(MonetaryCurrencies.getCurrency(baseCode), MonetaryCurrencies.getCurrency(termCode));
    }

    /**
     * Access a {@link ExchangeRate} using the given currencies. The
     * {@link ExchangeRate} may be, depending on the data provider, eal-time or
     * deferred. This method should return the rate that is <i>currently</i>
     * valid. It should be the same as
     * {@code getExchangeRate(base, term, System.currentTimeMillis())}.
     *
     * @param baseCode          base currency code, not {@code null}
     * @param termCode          term/target currency code, not {@code null}
     * @param conversionContext the required {@link ConversionContext}, not {@code null}
     * @return the matching {@link ExchangeRate}.
     * @throws CurrencyConversionException If no such rate is available.
     * @throws MonetaryException           if one of the currency codes passed is not valid.
     */
    default ExchangeRate getExchangeRate(String baseCode, String termCode, ConversionContext conversionContext){
        return getExchangeRate(MonetaryCurrencies.getCurrency(baseCode), MonetaryCurrencies.getCurrency(termCode),
                               conversionContext);
    }


    /**
     * Access a {@link CurrencyConversion} that can be applied as a
     * {@link MonetaryOperator} to an amount.
     *
     * @param term term {@link CurrencyUnit}, not {@code null}
     * @return a new instance of a corresponding {@link CurrencyConversion},
     * never {@code null}.
     */
    default CurrencyConversion getCurrencyConversion(CurrencyUnit term){
        return getCurrencyConversion(term, ConversionContext.ANY_CONVERSION);
    }


    /**
     * Access a {@link CurrencyConversion} that can be applied as a
     * {@link MonetaryOperator} to an amount.
     *
     * @param termCode terminal/target currency code, not {@code null}
     * @return a new instance of a corresponding {@link CurrencyConversion},
     * never {@code null}.
     * @throws MonetaryException if one of the currency codes passed is not valid.
     */
    default CurrencyConversion getCurrencyConversion(String termCode){
        return getCurrencyConversion(MonetaryCurrencies.getCurrency(termCode));
    }

    /**
     * Access a {@link CurrencyConversion} that can be applied as a
     * {@link MonetaryOperator} to an amount.
     *
     * @param termCode          terminal/target currency code, not {@code null}
     * @param conversionContext the required {@link ConversionContext}, not {@code null}
     * @return a new instance of a corresponding {@link CurrencyConversion},
     * never {@code null}.
     * @throws MonetaryException if one of the currency codes passed is not valid.
     */
    default CurrencyConversion getCurrencyConversion(String termCode, ConversionContext conversionContext){
        return getCurrencyConversion(MonetaryCurrencies.getCurrency(termCode), conversionContext);
    }
}
