/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.util.Enumeration;
import java.util.Locale;

import javax.money.provider.MonetaryExtension;

/**
 * Provider that serves calculations for financial algorithmic.
 * 
 * @author Anatole Tresch Note: still experimental!
 */
public interface CalculationProvider extends MonetaryExtension{

	public Enumeration<Calculation<?>> getAvailableCalculations();

	public boolean isCalculationDefined(String calculationId);

	public <T> Calculation<T> getCalculation(String calculationId,
			Class<T> resultType);

	public Enumeration<ComplexCalculation> getComplexCalculations();

	public boolean isComplexCalculationDefined(String calculationId);

	public ComplexCalculation getComplexCalculation(String calculationId);

	public String getCalculationName(Calculation<?> calculation, Locale locale);

	public String getCalculationDescription(Calculation<?> calculation,
			Locale locale);

	public String getCalculationName(ComplexCalculation calculation,
			Locale locale);

	public String getCalculationDescription(ComplexCalculation calculation,
			Locale locale);

}
