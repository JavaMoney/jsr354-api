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
package javax.money.ext;

import javax.money.AmountAdjuster;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversionException;
import javax.money.spi.MonetaryExtension;

/**
 * Component for accessing {@link MonetaryAmount} instances. It is provided by
 * the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface AmountUtils extends MonetaryExtension {

	/**
	 * Adjust all the passed instances of {@code MonetaryAmount} and returns an
	 * adjusted array.
	 * <p>
	 * The array must contain at least one monetary value. Subsequent amounts
	 * are adjusted and added as though using {@link #plus(MonetaryAmount)}. All amounts
	 * must be in the same currency.
	 * 
	 * @param adjuster
	 *            The {@link AmountAdjuster} to be used, not null.
	 * @param amounts
	 *            the monetary values to adjust and total, not empty, no null
	 *            elements, not null
	 * @return the adjusted {@link MonetaryAmount} instances, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount[] adjustAll(AmountAdjuster adjuster,
			MonetaryAmount... amounts);

	/**
	 * Adjust all the passed instances of {@code MonetaryAmount} and returns an
	 * adjusted array.
	 * <p>
	 * The array must contain at least one monetary value. Subsequent amounts
	 * are adjusted and added as though using {@link #plus(MonetaryAmount)}. All amounts
	 * must be in the same currency.
	 * 
	 * @param adjuster
	 *            The {@link AmountAdjuster} to be used, not null.
	 * @param amounts
	 *            the monetary values to adjust and total, not empty, no null
	 *            elements, not null
	 * @return the adjusted {@link MonetaryAmount} instances, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount[] adjustAll(AmountAdjuster adjuster,
			Iterable<MonetaryAmount> amounts);
	
	/**
	 * Obtains the minimal {@code MonetaryAmount} from the items passed.
	 * <p>
	 * The array must contain at least one monetary value. All amounts must be
	 * in the same currency.
	 * 
	 * @param amounts
	 *            the monetary amounts to extract the minimum from, not empty,
	 *            no null elements, not null
	 * @return the minimum amount, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty or null.
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount min(MonetaryAmount... amounts);

	/**
	 * Obtains the minimal {@code MonetaryAmount} from the items passed.
	 * <p>
	 * The array must contain at least one monetary value. All amounts must be
	 * in the same currency.
	 * 
	 * @param amounts
	 *            the monetary amounts to extract the minimum from, not empty,
	 *            no null elements, not null
	 * @return the minimum amount, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty or null.
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount min(Iterable<MonetaryAmount> amounts);

	/**
	 * Obtains the maximal {@code MonetaryAmount} from the items passed.
	 * <p>
	 * The array must contain at least one monetary value. All amounts must be
	 * in the same currency.
	 * 
	 * @param amounts
	 *            the monetary amounts to extract the minimum from, not empty,
	 *            no null elements, not null
	 * @return the maximal amount, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty or null.
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount max(MonetaryAmount... amounts);

	/**
	 * Obtains the maximal {@code MonetaryAmount} from the items passed.
	 * <p>
	 * The array must contain at least one monetary value. All amounts must be
	 * in the same currency.
	 * 
	 * @param amounts
	 *            the monetary amounts to extract the minimum from, not empty,
	 *            no null elements, not null
	 * @return the maximal amount, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty or null.
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount max(Iterable<MonetaryAmount> amounts);

	/**
	 * Obtains an instance of {@code MonetaryAmount} as the total value an
	 * adjusted array.
	 * <p>
	 * The array must contain at least one monetary value. Subsequent amounts
	 * are adjusted and added as though using {@link #plus(Money)}. All amounts
	 * must be in the same currency.
	 * 
	 * @param adjuster
	 *            The {@link AmountAdjuster} to be used, not null.
	 * @param amounts
	 *            the monetary values to adjust and total, not empty, no null
	 *            elements, not null
	 * @return the adjusted total, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount adjustedTotal(AmountAdjuster adjuster,
			MonetaryAmount... amounts);

	/**
	 * Obtains an instance of {@code MonetaryAmount} as the total value an
	 * adjusted array.
	 * <p>
	 * The array must contain at least one monetary value. Subsequent amounts
	 * are adjusted and added as though using {@link #plus(Money)}. All amounts
	 * must be in the same currency.
	 * 
	 *  @param adjuster
	 *            The {@link AmountAdjuster} to be used, not null.
	 * @param amounts
	 *            the monetary values to total, not empty, no null elements, not
	 *            null
	 * @return the total, never null
	 * @throws IllegalArgumentException
	 *             if the iterable is empty
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount adjustedTotal(AmountAdjuster adjuster,
			Iterable<MonetaryAmount> amounts);

	/**
	 * Obtains an instance of {@code Money} as the total value an array.
	 * <p>
	 * The array must contain at least one monetary value. Subsequent amounts
	 * are added as though using {@link #plus(Money)}. All amounts must be in
	 * the same currency.
	 * 
	 * @param amounts
	 *            the monetary values to total, not empty, no null elements, not
	 *            null
	 * @return the total, never null
	 * @throws IllegalArgumentException
	 *             if the array is empty
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount total(MonetaryAmount... amounts);

	/**
	 * Obtains an instance of {@code Money} as the total value a collection.
	 * <p>
	 * The iterable must provide at least one monetary value. Subsequent amounts
	 * are added as though using {@link #plus(Money)}. All amounts must be in
	 * the same currency.
	 * 
	 * @param amounts
	 *            the monetary values to total, not empty, no null elements, not
	 *            null
	 * @return the total, never null
	 * @throws IllegalArgumentException
	 *             if the iterable is empty
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount total(Iterable<MonetaryAmount> amounts);

	/**
	 * Obtains an instance of {@code Money} as the total value a possibly empty
	 * array.
	 * <p>
	 * The amounts are added as though using {@link #plus(Money)} starting from
	 * zero in the specified currency. All amounts must be in the same currency.
	 * 
	 * @param currency
	 *            the currency to total in, not null
	 * @param amounts
	 *            the monetary values to total, no null elements, not null
	 * @return the total, never null
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount total(CurrencyUnit currency, MonetaryAmount... amounts);

	/**
	 * Obtains an instance of {@code Money} as the total value a possibly empty
	 * collection.
	 * <p>
	 * The amounts are added as though using {@link #plus(Money)} starting from
	 * zero in the specified currency. All amounts must be in the same currency.
	 * 
	 * @param currency
	 *            the currency to total in, not null
	 * @param amounts
	 *            the monetary values to total, no null elements, not null
	 * @return the total, never null
	 * @throws CurrencyConversionException
	 *             if the currencies differ
	 */
	public MonetaryAmount total(CurrencyUnit currency,
			Iterable<MonetaryAmount> amounts);

	/**
	 * Ensures that an {@code Amount} is not {@code null}.
	 * <p>
	 * If the input money is not {@code null}, then it is returned, providing
	 * that the currency matches the specified currency. If the input amount is
	 * {@code null}, then a zero amount in the currency is returned.
	 * 
	 * @param amonut
	 *            the amount to check, may be null
	 * @param currency
	 *            the currency to use, not null
	 * @return the input amount or zero in the specified currency, never null
	 * @throws CurrencyConversionException
	 *             if the input money is non-null and the currencies differ
	 */
	public MonetaryAmount nonNull(MonetaryAmount amount, CurrencyUnit currency);

}
