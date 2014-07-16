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
import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import javax.money.spi.MonetaryConversionsSingletonSpi;
import java.util.*;

/**
 * @author Anatole
 * @author Werner
 * @version 0.3 on 11.05.14.
 */
public class TestMonetaryConversionsSingletonSpi implements MonetaryConversionsSingletonSpi{

    private ExchangeRateProvider provider = new DummyRateProvider();
    private List<ExchangeRateProvider> providers = new ArrayList<>();

    public TestMonetaryConversionsSingletonSpi(){
        providers.add(provider);
    }

    @Override
    public ExchangeRateProvider getExchangeRateProvider(ConversionQuery query){
        if(query.getProviders().isEmpty()){
            // when the default should be used, our provider will be part of.
            return provider;
        }
        if(query.getProviders().contains("test")){
            return provider;
        }
        throw new MonetaryException(
                "Only 'test' provider supported by 'test' provider (TestMonetaryConversionsSingletonSpi).");
    }

    @Override
    public Collection<ExchangeRateProvider> getExchangeRateProviders(ConversionQuery query){
        if(query.getProviders().contains("test")){
            return providers;
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<String> getProviderNames(){
        return Arrays.asList(new String[]{"test"});
    }

    @Override
    public boolean isProviderAvailable(String provider){
        return "test".equals(provider);
    }

    @Override
    public List<String> getDefaultProviderChain(){
        return new ArrayList(getProviderNames());
    }

    private static final class DummyConversion implements CurrencyConversion{

        private CurrencyUnit termCurrency;
        private ConversionContext ctx = ConversionContext.of();

        public DummyConversion(CurrencyUnit termCurrency){
            this.termCurrency = termCurrency;
        }

        @Override
        public CurrencyUnit getTermCurrency(){
            return termCurrency;
        }

        @Override
        public ConversionContext getConversionContext(){
            return ctx;
        }

        @Override
        public ExchangeRate getExchangeRate(MonetaryAmount sourceAmount){
            return new DefaultExchangeRate.Builder(getClass().getSimpleName(), RateType.OTHER)
                    .setBase(sourceAmount.getCurrency()).setTerm(termCurrency).setFactor(TestNumberValue.of(1)).build();
        }

        @Override
        public CurrencyConversion with(ConversionContext conversionContext){
            return new DummyConversion(termCurrency);
        }

        @Override
        public MonetaryAmount apply(MonetaryAmount value){
            return value;
        }
    }

    private static final class DummyRateProvider implements ExchangeRateProvider{

        private ProviderContext ctx = ProviderContext.of("test");

        @Override
        public ProviderContext getProviderContext(){
            return ctx;
        }

        @Override
        public boolean isAvailable(CurrencyUnit base, CurrencyUnit term){
            return false;
        }

        @Override
        public boolean isAvailable(ConversionQuery conversionContext){
            return false;
        }

        @Override
        public ExchangeRate getExchangeRate(ConversionQuery query){
            return new DefaultExchangeRate.Builder(getClass().getSimpleName(), RateType.OTHER)
                    .setBase(query.getBaseCurrency()).setTerm(query.getTermCurrency()).setFactor(TestNumberValue.of(1))
                    .build();
        }

        @Override
        public ExchangeRate getReversed(ExchangeRate rate){
            return getExchangeRate(rate.getTerm(), rate.getBase());
        }

        @Override
        public CurrencyConversion getCurrencyConversion(ConversionQuery query){
            return new DummyConversion(query.getTermCurrency());
        }

    }
}
