/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
package javax.money.function;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * This class allows to extract the minor part of a {@link MonetaryAmount}
 * instance.
 * 
 * @author Anatole Tresch
 */
public final class MinorPart implements MonetaryOperator {

	/**
	 * The shared instance of this class.
	 */
	private MinorPart INSTANCE = new MinorPart();

	/**
	 * Private constructor, there is only one instance of this class, accessible
	 * calling {@link #of()}.
	 */
	private MinorPart() {
	}

	/**
	 * Access the shared instance of {@link MinorPart} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public MinorPart of() {
		return INSTANCE;
	}

	/**
	 * Gets the amount in major units as a {@code MonetaryAmount} with scale 0.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 'EUR 2', and 'BHD -1.345' will return 'BHD -1'.
	 * <p>
	 * This is returned as a {@code MonetaryAmount} rather than a
	 * {@code BigInteger} . This is to allow further calculations to be
	 * performed on the result. Should you need a {@code BigInteger}, simply
	 * call {@code asType(BigInteger.class)}.
	 * 
	 * @return the major units part of the amount, never {@code null}
	 */
	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		return fromAmount(amount);
	}

	/**
	 * Gets the amount in minor units as a {@code long}.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for a {@code long}
	 */
	public static long fromAsLong(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.movePointRight(number.precision()).longValueExact();
	}

	/**
	 * Gets the amount in minor units as an {@code int}.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for an {@code int}
	 */
	public static int fromAsInt(MonetaryAmount amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.movePointRight(number.precision()).intValueExact();
	}

	/**
	 * Gets the amount in minor units as a {@code MonetaryAmount} with scale 0.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 'EUR 235', and 'BHD -1.345' will return 'BHD -1345'.
	 * <p>
	 * This is returned as a {@code MonetaryAmount} rather than a
	 * {@code BigInteger} . This is to allow further calculations to be
	 * performed on the result. Should you need a {@code BigInteger}, simply
	 * call {@link asType(BigInteger.class)}.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount, never null
	 */
	@SuppressWarnings("unchecked")
	public static <T extends MonetaryAmount> T fromAmount(T amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return (T) amount.from(number.movePointRight(number.precision())
				.longValueExact());
	}
}
