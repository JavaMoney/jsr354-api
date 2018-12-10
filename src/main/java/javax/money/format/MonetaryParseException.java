/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money.format;

import java.util.Objects;

import javax.money.MonetaryException;

/**
 * Signals that an error has been reached unexpectedly while parsing.
 * 
 * @author Werner Keil
 */
public class MonetaryParseException extends MonetaryException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -2855079387296896628L;

	/**
	 * The zero-based character offset into the string being parsed at which the
	 * error was found during parsing.
	 * 
	 * @serial
	 */
	private int errorIndex;

	/** The original input data. */
	private CharSequence data;

	/**
	 * Constructs a MonetaryParseException with the specified detail message,
	 * parsed text and index. A detail message is a String that describes this
	 * particular exception.
	 * 
	 * @param message
	 *            the detail message
	 * @param parsedData
	 *            the parsed text, should not be null
	 * @param errorIndex
	 *            the position where the error is found while parsing.
	 */
	public MonetaryParseException(String message, CharSequence parsedData,
			int errorIndex) {
		super(message);
        if(errorIndex > parsedData.length()){
            throw new IllegalArgumentException("Invalid error index > input.length");
        }
		this.data = parsedData;
		this.errorIndex = errorIndex;
	}

	/**
	 * Constructs a MonetaryParseException with the parsed text and offset. A
	 * detail message is a String that describes this particular exception.
	 * 
	 * @param parsedData
	 *            the parsed text, should not be null
	 * @param errorIndex
	 *            the position where the error is found while parsing.
	 */
	public MonetaryParseException(CharSequence parsedData,
			int errorIndex) {
		super("Parse Error");
        if(errorIndex > parsedData.length()){
            throw new IllegalArgumentException("Invalid error index > input.length");
        }
		this.data = parsedData;
		this.errorIndex = errorIndex;
	}

	/**
	 * Returns the index where the error was found.
	 * 
	 * @return the index where the error was found
	 */
	public int getErrorIndex() {
		return errorIndex;
	}

	/**
	 * Returns the string that was being parsed.
	 * 
	 * @return the parsed input string, or {@code null}, if {@code null} was passed as
	 *         input.
	 */
	public String getInput() {
		
		if (Objects.isNull(data)) {
			return null;
		}
		return data.toString();
	}

}
