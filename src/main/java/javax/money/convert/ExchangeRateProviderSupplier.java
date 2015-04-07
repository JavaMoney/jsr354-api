/*
* CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
* CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
* PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
* DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
* AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
* BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
* API ("Specification") Copyright (c) 2012-2015, Credit SUISSE All rights
* reserved.
*/
package javax.money.convert;


/**
 * A supplier of {@link ExchangeRateProvider} name that references an implementation. This can be used to let
 * an enum type implement this interface, so enums values can be passed to {@link javax.money.convert.MonetaryConversions}
 * for determining the rate providers to be used.
 */
@FunctionalInterface
public interface ExchangeRateProviderSupplier{

    /**
     * Get the provider name. This signatire equals to the signrature of java.util.function.Supplier in Java 8.
     *
     * @return the provider name, not null.
     */
    String get();

}
