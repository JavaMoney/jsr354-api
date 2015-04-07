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

import java.util.function.Supplier;

/**
 * The supplier of {@link ExchangeRateProvider} that defines an implementations.
 */
public interface ExchangeRateProviderSupplier extends Supplier<String> {

	/**
	 * @return description of the implementation of {@link ExchangeRateProvider}
	 */
	String getDescription();
}
