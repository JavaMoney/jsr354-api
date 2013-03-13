/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
