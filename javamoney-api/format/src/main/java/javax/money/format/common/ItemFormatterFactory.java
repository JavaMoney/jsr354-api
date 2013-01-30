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
package javax.money.format.common;

import java.util.Locale;

/**
 * This class represent the accessor interface for creating different kind of
 * formatters.
 * 
 * @see ItemFormatter
 * @see StyleableItemFormatter
 * @author Anatole Tresch
 */
public interface ItemFormatterFactory<T> extends Targeted<T> {

	/**
	 * This method returns an instance of a fixed styled {@link ItemFormatter}.
	 * 
	 * @param locale
	 *            The target locale. The locale will be converted into an
	 *            according {@link LocalizationStyle} using
	 *            {@link LocalizationStyle#of(Locale)}.
	 * @return the formatter required, if available.
	 * @throws
	 */
	public ItemFormatter<T> getFormatter(Locale locale);

	/**
	 * This method returns an instance of a fixed styled {@link ItemFormatter}.
	 * 
	 * @param style
	 *            The target localization style.
	 * @return the formatter required, if available.
	 * @throws
	 */
	public ItemFormatter<T> getFormatter(LocalizationStyle style);

	/**
	 * This method returns an instance of a {@link StyleableItemFormatter}.
	 * 
	 * @return the formatter required, if available.
	 * @throws
	 */
	public StyleableItemFormatter<T> getLocalizableFormatter();

}
