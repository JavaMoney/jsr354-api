/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
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
 */
package javax.money.format;

import java.io.IOException;
import java.util.Locale;

/**
 * Formats instances of number to and from a String.
 * <p>
 * Instances of {@code NumberPrinterParser} can be created by
 * {@code NumberPrinterParserFactory}.
 * <p>
 * This class is immutable and thread-safe.
 */
public interface Parser<T> {

	/**
	 * Fully parses the text into a {@code Money} requiring that the parsed
	 * amount has the correct number of decimal places.
	 * <p>
	 * The parse must complete normally and parse the entire text (currency and
	 * amount). If the parse completes without reading the entire length of the
	 * text, an exception is thrown. If any other problem occurs during parsing,
	 * an exception is thrown.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @return the parsed monetary value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws FormatException
	 *             if there is a problem while parsing
	 * @throws ArithmeticException
	 *             if the scale of the parsed money exceeds the scale of the
	 *             currency
	 */
	public T parse(CharSequence text, LocalizationStyle locale)throws ParseException;
	
	/**
	 * Fully parses the text into a {@code Money} requiring that the parsed
	 * amount has the correct number of decimal places.
	 * <p>
	 * The parse must complete normally and parse the entire text (currency and
	 * amount). If the parse completes without reading the entire length of the
	 * text, an exception is thrown. If any other problem occurs during parsing,
	 * an exception is thrown.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @return the parsed monetary value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws FormatException
	 *             if there is a problem while parsing
	 * @throws ArithmeticException
	 *             if the scale of the parsed money exceeds the scale of the
	 *             currency
	 */
	public T parse(CharSequence text, Locale locale)throws ParseException;
	
}
