/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
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
