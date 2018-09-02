/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
module java.money {
    exports javax.money;
    exports javax.money.convert;
    exports javax.money.format;
    exports javax.money.spi;
    requires java.base;
    requires java.logging;
    uses javax.money.spi.CurrencyProviderSpi;
    uses javax.money.spi.MonetaryAmountFactoryProviderSpi;
    uses javax.money.spi.MonetaryAmountFormatProviderSpi;
    uses javax.money.spi.MonetaryAmountsSingletonQuerySpi;
    uses javax.money.spi.MonetaryAmountsSingletonSpi;
    uses javax.money.spi.MonetaryConversionsSingletonSpi;
    uses javax.money.spi.MonetaryCurrenciesSingletonSpi;
    uses javax.money.spi.MonetaryFormatsSingletonSpi;
    uses javax.money.spi.MonetaryRoundingsSingletonSpi;
    uses javax.money.spi.RoundingProviderSpi;
    uses javax.money.spi.ServiceProvider;
    uses javax.money.convert.ExchangeRateProvider;
    uses javax.money.convert.ExchangeRateProviderSupplier;
    uses javax.money.format.MonetaryAmountFormat;
}