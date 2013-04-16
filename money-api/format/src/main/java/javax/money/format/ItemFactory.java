/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.format;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;

/**
 * This class models the component that interprets/assembles the result passed
 * by several {@link FormatToken} and build the final item T to be returned as
 * final parse result.<br/>
 * As an example parsing a monetary amount includes parsing of a {@link Number}
 * as well as a {@link CurrencyUnit}. An instance of {@link ItemFactory} finally
 * than creates an instance of {@link MonetaryAmount} to be returned by the
 * {@code ItemFormat<MonetaryAmount>}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 */
public interface ItemFactory<T> extends MonetaryFunction<ParseContext<T>, T> {
	/**
	 * Returns true, if the required item is available from the parsed results.
	 * 
	 * @param context
	 *            The current {@link ParseContext}.
	 * @return true, if the required item can be found in the
	 *         {@link ParseContext}'s results.
	 */
	boolean isComplete(ParseContext<T> context);
}