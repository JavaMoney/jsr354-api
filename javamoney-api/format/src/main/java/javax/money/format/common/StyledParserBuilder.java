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
 * Provides the ability to build a parser by programmatic defining the parsing
 * required. The implementing class should add additional configuration methods
 * returning itself as the method's result, for example:
 * 
 * <pre>
 * public final class MyParserBuilder implements ParserBuilder<MyItem>{
 * 
 *   public MyParserBuilder setPlaceBeforeX(boolean placeBefore){
 *     ...
 *   }
 * 
 *   ...
 *   
 * }
 * </pre>
 * 
 * Parser Builders are mutable and intended for use by a single thread. A new
 * instance should be created for each use. The parsers produced by the builder
 * are immutable and thread-safe.
 * 
 * @see StyledParser
 * @see StyleableItemParser
 */
public interface StyledParserBuilder<T> {
	
	/**
	 * Builds the {@link StyleableItemParser} from this builder.
	 * <p>
	 * Once the builder is in the correct state it must be converted to a
	 * {@code Formatter} of {@link StyleableFormatter} to be used. Calling
	 * this method does not change the state of this instance, so it can still
	 * be used.
	 * 
	 * @return the {@link StyleableItemParser} built from this builder, never null
	 */
	public StyleableItemParser<T> toStylableItemParser();

	/**
	 * Builds the parser from the builder setting the locale.
	 * <p>
	 * Calling this method does not change the state of this instance, so it can
	 * still be used.
	 * <p>
	 * Once the builder is in the correct state it must be converted to a
	 * {@code Formatter} of {@link StyleableFormatter} to be used. This method
	 * uses the specified locale. For extended style parameters use
	 * {@link StyledParserBuilder#toParser(LocalizationStyle)} instead of.
	 * 
	 * @param locale
	 *            the locale for the parser, not null
	 * @return the {@link Styled} {@link StyledParser} built from this builder, never
	 *         null
	 */
	public StyledParser<T> toItemParser(Locale locale);

	/**
	 * Builds the parser from the builder setting the {@link LocalizationStyle}.
	 * <p>
	 * Once the builder is in the correct state it must be converted to a
	 * {@code Formatter} of {@link StyleableFormatter} to be used. Calling
	 * this method does not change the state of this instance, so it can still
	 * be used.
	 * 
	 * @param style
	 *            the style for the parser, not null
	 * @return the {@link Styled} {@link StyledParser} built from this builder, never
	 *         null
	 */
	public StyledParser<T> toItemParser(LocalizationStyle style);

}
