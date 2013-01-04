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
package javax.money.format;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;

/**
 * Parses instances of {@link CurrencyUnit} to and from a {@link CharSequence}.
 * <p>
 * Instances of {@code CurrencyParser} can be obtained by calling
 * {@code MoneyFormat#getCurrencyParser(Locale)} or
 * {@code MoneyFormat#getCurrencyParser(LocalizationStyle)}.
 */
public interface CurrencyParser extends Parser<CurrencyUnit> {

	/**
	 * Get the name space this parser is working on.
	 * 
	 * @return the name space, never null.
	 */
	public String getNamespace();

	/**
	 * Parses a {@link CurrencyUnit} based on the localized symbol.
	 * 
	 * @param symbol
	 *            the input data.
	 * @param locale
	 *            the target locale
	 * @return parsed {@link CurrencyUnit}, never null.
	 * @throws UnknownCurrencyException
	 *             if the required symbol can be mapped to a
	 *             {@link CurrencyUnit} on the given namespace.
	 * @throws ParseException
	 *             if there is a problem while parsing
	 */
	public CurrencyUnit parseSymbol(CharSequence symbol, Locale locale);

	/**
	 * Parses a {@link CurrencyUnit} based on the localized symbol.
	 * 
	 * @param symbol
	 *            the input data.
	 * @param style
	 *            the target style.
	 * @return parsed {@link CurrencyUnit}, never null.
	 * @throws UnknownCurrencyException
	 *             if the required symbol can be mapped to a
	 *             {@link CurrencyUnit} on the given namespace.
	 * @throws ParseException
	 *             if there is a problem while parsing
	 */
	public CurrencyUnit parseSymbol(CharSequence symbol, LocalizationStyle style);

}
