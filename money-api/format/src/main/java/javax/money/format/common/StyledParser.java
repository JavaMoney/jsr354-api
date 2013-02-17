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

/**
 * Instance that implement this interface parse Strings into instances of of
 * type T.
 * TODO check if this class can be moved to {@code java.util}.
 */
public interface StyledParser<T>  extends Targeted<T>, Styled{

	/**
	 * Fully parses the text into an instance of T.
	 * <p>
	 * The parse must complete normally and parse the entire text. If the parse
	 * completes without reading the entire length of the text, an exception is
	 * thrown. If any other problem occurs during parsing, an exception is
	 * thrown.
	 * <p>
	 * This method uses a {@link LocalizationStyle} as an input parameter.
	 * Styles allows to define detailed and customized formatting input
	 * parameters. This allows to implement also complex formatting requirements
	 * using this interface.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @return the parsed value, never null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws ParseException
	 *             if there is a problem while parsing
	 */
	public T parse(CharSequence text)
			throws ParseException;


}
