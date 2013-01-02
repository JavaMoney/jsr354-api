/*
 * Copyright (c) 2012-2013,  Credit Suisse, Werner Keil
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
 * Exception thrown when a monetary operation fails due to mismatched currencies.
 * <p>
 * For example, this exception would be thrown when trying to add a monetary
 * value in one currency to a monetary value in a different currency.
 * <p>
 * This exception makes no guarantees about immutability or thread-safety.
 *
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Stephen Colebourne
 */
public class CurrencyMismatchException extends IllegalArgumentException {

    /** Serialization lock. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param firstCurrency  the first currency, may be null
     */
    public CurrencyMismatchException(CurrencyUnit source, CurrencyUnit target) {
    	// TODO Not Implemented yet
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the first currency at fault.
     * 
     * @return the currency at fault, may be null
     */
    public CurrencyUnit getSource() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Gets the second currency at fault.
     * 
     * @return the currency at fault, may be null
     */
    public CurrencyUnit getTarget() {
    	// TODO Not Implemented yet
    	return null;
    }

}
