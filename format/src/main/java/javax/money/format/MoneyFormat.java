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

/**
 * This class represent the singleton for money related formatting and parsing
 * functionality.
 * 
 * @author Anatole Tresch
 */
public final class MoneyFormat {

	/**
	 * Private singleton constructor.
	 */
	private MoneyFormat() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method allows to create a new {@link AmountFormatterBuilder} that
	 * can be used to create a {@link AmountFormatter} using programmatic
	 * configuration.
	 * 
	 * @return a new instance of a {@link AmountFormatterBuilder}, never null.
	 */
	public AmountFormatterBuilder createFormatterBuilder() {
		return null;
	}

	/**
	 * This method returns a parser instance for {@link Amount} instances
	 * formatted in the given {@link Locale}. The instance returned must be
	 * provided by the registered AmountParserFactory SPI implementation.
	 * 
	 * @param locale
	 *            The target locale. The locale will be converted into an
	 *            according {@link LocalizationStyle} using
	 *            {@link LocalizationStyle#of(Locale)}.
	 * @return the according parser, if available.
	 * @throws
	 */
	public AmountParser getAmountParser(Locale locale) {
		return null;
	}

	/**
	 * This method returns a parser instance for {@link Amount} instances
	 * formatted in the given {@link Locale}. The instance returned must be
	 * provided by the registered AmountParserFactory SPI implementation.
	 * 
	 * @param style
	 *            The target localization style.
	 * @return the according parser, if available.
	 * @throws
	 */
	public AmountParser getAmountParser(LocalizationStyle style) {
		return null;
	}

	/**
	 * This method returns an instance of a {@link AmountFormatter} that is
	 * provided the registered AmountFormatterFactory SPI implementation.
	 * 
	 * @param locale
	 *            The target locale. The locale will be converted into an
	 *            according {@link LocalizationStyle} using
	 *            {@link LocalizationStyle#of(Locale)}.
	 * @return the formatter required, if available.
	 * @throws
	 */
	public AmountFormatter getAmountFormatter(Locale locale) {
		return null;
	}

	/**
	 * This method returns an instance of a {@link AmountFormatter} that is
	 * provided the registered AmountFormatterFactory SPI implementation.
	 * 
	 * @param style
	 *            The target localization style.
	 * @return the formatter required, if available.
	 * @throws
	 */
	public AmountFormatter getAmountFormatter(LocalizationStyle style) {
		return null;
	}

	/**
	 * This method returns an instance of a {@link CurrencyFormatter}.
	 * 
	 * @param style
	 *            The target localization style.
	 * @return a currency formatter.
	 */
	public CurrencyFormatter getCurrencyFormatter(LocalizationStyle style) {
		return null;
	}

	/**
	 * This method returns an instance of a {@link CurrencyFormatter}.
	 * 
	 * @param locale
	 *            The target locale.
	 * @return a currency formatter.
	 */
	public CurrencyFormatter getCurrencyFormatter(Locale locale) {
		return null;
	}

	/**
	 * This method returns an instance of a {@link CurrencyParser}.
	 * 
	 * @param namespace
	 *            the target name space of currencies.
	 * @param style
	 *            The target localization style.
	 * @return a currency formatter.
	 */
	public CurrencyParser getCurrencyParser(String namespace,
			LocalizationStyle style) {
		return null;
	}

	/**
	 * This method returns an instance of a {@link CurrencyParser}.
	 * 
	 * @param namespace
	 *            the target name space of currencies.
	 * @param locale
	 *            The target locale.
	 * @return a currency formatter.
	 */
	public CurrencyParser getCurrencyParser(String namespace, Locale locale) {
		return null;
	}

}
