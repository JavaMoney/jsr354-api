/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.util.Map;

import javax.money.provider.MonetaryExtension;

/**
 * Provider that serves calculations for financial algorithmic.
 * 
 * @author Anatole Tresch Note: still experimental!
 */
public interface CalculationProvider extends MonetaryExtension {

	@SuppressWarnings("rawtypes")
	public Map<String, Class<? extends Calculation>> getAvailableCalculations();

	@SuppressWarnings("rawtypes")
	public Class<? extends Calculation> getCalculationType(String id);

	public boolean isCalculationDefined(String id);

	public Calculation<?, ?> getCalculation(String id);

}
