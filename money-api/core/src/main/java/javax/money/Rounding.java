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
package javax.money;

/**
 * This interface defines a {@link Rounding}. It is hereby important to
 * distinguish between internal rounding such as implied by the maximal
 * precision/scale of an amount, contrary to rounding applied to a
 * {@link MonetaryAmount} or a calculation algorithm. Since different use cases
 * may require {@link Rounding} done at very different stages within a complex
 * financial calculation, {@link Rounding}is not directly attached with its
 * type.
 * <p>
 * Nevertheless the JSR's extensions provide a RoundingMonetaryAmount, which
 * wraps a {@link MonetaryAmount} and adds implicit rounding.
 * 
 * @author Anatole Tresch
 */
public interface Rounding {

	/**
	 * This method is called for rounding an amount.
	 * 
	 * @param amount
	 *            the amount to be rounded
	 * @return the rounded amount.
	 * @throws ArithmeticException
	 *             if rounding fails.
	 */
	public MonetaryAmount round(MonetaryAmount amount);
	

}