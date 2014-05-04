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
import javax.money.TestCurrency;
import javax.money.spi.MonetaryConversionsSingletonSpi;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Anatole
 * @author Werner
 * @version 0.2 on 04.05.14.
 */
public class TestMonetaryConversionsSingletonSpi implements MonetaryConversionsSingletonSpi{

    private ExchangeRateProvider provider = new DummyRateProvider();

    @Override
    public ExchangeRateProvider getExchangeRateProvider(String... providers){
        if(providers.length==1 && providers[0].equals("test")){
            return provider;
        }
        return null;
    }

    @Override
    public CurrencyConversion getConversion(CurrencyUnit termCurrency, ConversionContext conversionContext,
                                            String... providers){
        return provider.getCurrencyConversion(termCurrency);
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
    public ProviderContext getProviderContext(String provider){
        if("test".equals(provider)){
            return ProviderContext.of(provider);
        }
        return null;
    }

    @Override
    public List<String> getDefaultProviderChain(){
        return Collections.emptyList();
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
            return new ExchangeRate.Builder(getClass().getSimpleName(), RateType.OTHER)
                    .setBase(sourceAmount.getCurrency()).setTerm(termCurrency).setFactor(TestNumberValue.of(1))
                    .build();
        }

        @Override
        public CurrencyConversion with(ConversionContext conversionContext){
            return new DummyConversion(termCurrency);
        }

        @Override
        public <T extends MonetaryAmount> T apply(T value){
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
        public boolean isAvailable(CurrencyUnit base, CurrencyUnit term, ConversionContext conversionContext){
            return false;
        }

        @Override
        public boolean isAvailable(String baseCode, String termCode){
            return false;
        }

        @Override
        public boolean isAvailable(String baseCode, String termCode, ConversionContext conversionContext){
            return false;
        }

        @Override
        public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term){
            return new ExchangeRate.Builder(getClass().getSimpleName(), RateType.OTHER)
                    .setBase(base).setTerm(term).setFactor(TestNumberValue.of(1))
                    .build();
        }

        @Override
        public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term, ConversionContext conversionContext){
            return new ExchangeRate.Builder(getClass().getSimpleName(), RateType.OTHER)
                    .setBase(base).setTerm(term).setFactor(TestNumberValue.of(1))
                    .build();
        }

        @Override
        public ExchangeRate getExchangeRate(String baseCode, String termCode){
            return getExchangeRate(TestCurrency.of(baseCode),TestCurrency.of(termCode));
        }

        @Override
        public ExchangeRate getExchangeRate(String baseCode, String termCode, ConversionContext conversionContext){
            return getExchangeRate(TestCurrency.of(baseCode),TestCurrency.of(termCode));
        }

        @Override
        public ExchangeRate getReversed(ExchangeRate rate){
            return getExchangeRate(rate.getTerm(),rate.getBase());
        }

        @Override
        public CurrencyConversion getCurrencyConversion(CurrencyUnit term){
            return new DummyConversion(term);
        }

        @Override
        public CurrencyConversion getCurrencyConversion(CurrencyUnit term, ConversionContext conversionContext){
            return new DummyConversion(term);
        }

        @Override
        public CurrencyConversion getCurrencyConversion(String termCode){
            return new DummyConversion(TestCurrency.of(termCode));
        }

        @Override
        public CurrencyConversion getCurrencyConversion(String termCode, ConversionContext conversionContext){
            return new DummyConversion(TestCurrency.of(termCode));
        }
    }
}
