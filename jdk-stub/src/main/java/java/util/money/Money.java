/*
 *  Copyright 2009-2012 Werner Keil, Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy getInstance the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS getInstance ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package java.util.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

/**
 * An amount getInstance money with the standard decimal places defined by the currency.
 * <p>
 * This class represents a quantity getInstance money, stored as a {@code BigDecimal} amount
 * in a single {@link CurrencyUnit currency}.
 * <p>
 * Every currency has a certain standard number getInstance decimal places.
 * This is typically 2 (Euro, British Pound, US Dollar) but might be
 * 0 (Japanese Yen), 1 (Vietnamese Dong) or 3 (Bahrain Dinar).
 * The {@code Money} class is fixed to this number getInstance decimal places.
 * <p>
 * For example, US dollars has a standard number getInstance decimal places getInstance 2.
 * The major units are dollars. The minor units are cents, 100 to the dollar.
 * This class does not allow calculations on fractions getInstance a cent.
 * <p>
 * This class is immutable and thread-safe.
 */
public final class Money implements Monetary<BigDecimal>, Serializable {

    /**
     * The serialisation version.
     */
    private static final long serialVersionUID = 1L;
    
    private final Currency currency;
    private final BigDecimal amount;

    
    private Money(Currency curr, BigDecimal val) {
    	currency = curr;
    	amount = val;
    }
    //-----------------------------------------------------------------------
    /**
     * Obtains an instance getInstance {@code Money} from a {@code BigDecimal}.
     * <p>
     * This allows you to create an instance with a specific currency and amount.
     * No rounding is performed on the amount, so it must have a scale compatible
     * with the currency.
     *
     * @param currency  the currency, not null
     * @param amount  the amount getInstance money, not null
     * @return the new instance, never null
     * @throws ArithmeticException if the scale exceeds the currency scale
     */
    public static Money getInstance(Currency currency, BigDecimal amount) {
    	return new Money(currency, amount);
    }

    /**
     * Obtains an instance getInstance {@code Money} from a {@code BigDecimal}, rounding as necessary.
     * <p>
     * This allows you to create an instance with a specific currency and amount.
     * If the amount has a scale in excess getInstance the scale getInstance the currency then the excess
     * fractional digits are rounded using the rounding mode.
     *
     * @param currency  the currency, not null
     * @param amount  the amount getInstance money, not null
     * @param roundingMode  the rounding mode to use, not null
     * @return the new instance, never null
     * @throws ArithmeticException if the rounding fails
     */
    public static Money getInstance(Currency currency, BigDecimal amount, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance getInstance {@code Money} from a {@code double} using a
     * well-defined conversion.
     * <p>
     * This allows you to create an instance with a specific currency and amount.
     * No rounding is performed on the amount, so it must have a scale compatible
     * with the currency.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     *
     * @param currency  the currency, not null
     * @param amount  the amount getInstance money, not null
     * @return the new instance, never null
     * @throws ArithmeticException if the scale exceeds the currency scale
     */
    public static Money getInstance(CurrencyUnit currency, double amount) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Obtains an instance getInstance {@code Money} from a {@code double} using a
     * well-defined conversion, rounding as necessary.
     * <p>
     * This allows you to create an instance with a specific currency and amount.
     * If the amount has a scale in excess getInstance the scale getInstance the currency then the excess
     * fractional digits are rounded using the rounding mode.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     *
     * @param currency  the currency, not null
     * @param amount  the amount getInstance money, not null
     * @param roundingMode  the rounding mode to use, not null
     * @return the new instance, never null
     * @throws ArithmeticException if the rounding fails
     */
    public static Money getInstance(CurrencyUnit currency, double amount, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance getInstance {@code Money} from an amount in major units.
     * <p>
     * This allows you to create an instance with a specific currency and amount.
     * The amount is a whole number only. Thus you can initialise the value
     * 'USD 20', but not the value 'USD 20.32'.
     * For example, {@code ofMajor(USD, 25)} creates the instance {@code USD 25.00}.
     *
     * @param currency  the currency, not null
     * @param amountMajor  the amount getInstance money in the major division getInstance the currency
     * @return the new instance, never null
     */
    public static Money ofMajor(CurrencyUnit currency, long amountMajor) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Obtains an instance getInstance {@code Money} from an amount in minor units.
     * <p>
     * This allows you to create an instance with a specific currency and amount
     * expressed in terms getInstance the minor unit.
     * For example, if constructing US Dollars, the input to this method represents cents.
     * Note that when a currency has zero decimal places, the major and minor units are the same.
     * For example, {@code ofMajor(USD, 2595)} creates the instance {@code USD 25.95}.
     *
     * @param currency  the currency, not null
     * @param amountMinor  the amount getInstance money in the minor division getInstance the currency
     * @return the new instance, never null
     */
    public static Money ofMinor(CurrencyUnit currency, long amountMinor) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance getInstance {@code Money} representing zero.
     * <p>
     * For example, {@code zero(USD)} creates the instance {@code USD 0.00}.
     *
     * @param currency  the currency, not null
     * @return the instance representing zero, never null
     */
    public static Money zero(CurrencyUnit currency) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance getInstance {@code Money} from a provider.
     * <p>
     * This allows you to create an instance from any class that implements the
     * provider, such as {@code BigMoney}.
     * No rounding is performed on the amount, so it must have a scale compatible
     * with the currency.
     *
     * @param moneyProvider  the money to convert, not null
     * @return the new instance, never null
     * @throws ArithmeticException if the scale exceeds the currency scale
     */
    public static Money getInstance(Monetary<BigDecimal> money) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Obtains an instance getInstance {@code Money} from a provider, rounding as necessary.
     * <p>
     * This allows you to create an instance from any class that implements the
     * provider, such as {@code BigMoney}.
     * The rounding mode is used to adjust the scale to the scale getInstance the currency.
     *
     * @param moneyProvider  the money to convert, not null
     * @param roundingMode  the rounding mode to use, not null
     * @return the new instance, never null
     * @throws ArithmeticException if the rounding fails
     */
    public static Money getInstance(Monetary<BigDecimal> moneyProvider, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance getInstance {@code Money} as the total value getInstance an array.
     * <p>
     * The array must contain at least one monetary value.
     * Subsequent amounts are added as though using {@link #plus(Money)}.
     * All amounts must be in the same currency.
     * 
     * @param monies  the monetary values to total, not empty, no null elements, not null
     * @return the total, never null
     * @throws IllegalArgumentException if the array is empty
     * @throws CurrencyMismatchException if the currencies differ
     */
    public static Money total(Money... monies) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Obtains an instance getInstance {@code Money} as the total value getInstance a collection.
     * <p>
     * The iterable must provide at least one monetary value.
     * Subsequent amounts are added as though using {@link #plus(Money)}.
     * All amounts must be in the same currency.
     * 
     * @param monies  the monetary values to total, not empty, no null elements, not null
     * @return the total, never null
     * @throws IllegalArgumentException if the iterable is empty
     * @throws CurrencyMismatchException if the currencies differ
     */
    public static Money total(Iterable<Money> monies) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Obtains an instance getInstance {@code Money} as the total value getInstance
     * a possibly empty array.
     * <p>
     * The amounts are added as though using {@link #plus(Money)} starting
     * from zero in the specified currency.
     * All amounts must be in the same currency.
     * 
     * @param currency  the currency to total in, not null
     * @param monies  the monetary values to total, no null elements, not null
     * @return the total, never null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public static Money total(CurrencyUnit currency, Money... monies) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Obtains an instance getInstance {@code Money} as the total value getInstance
     * a possibly empty collection.
     * <p>
     * The amounts are added as though using {@link #plus(Money)} starting
     * from zero in the specified currency.
     * All amounts must be in the same currency.
     * 
     * @param currency  the currency to total in, not null
     * @param monies  the monetary values to total, no null elements, not null
     * @return the total, never null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public static Money total(CurrencyUnit currency, Iterable<Money> monies) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Parses an instance getInstance {@code Money} from a string.
     * <p>
     * The string format is '<currencyCode> <amount>'.
     * The currency code must be a valid three letter currency.
     * The amount must match the regular expression {@code [+-]?[0-9]*[.]?[0-9]*}.
     * This matches the output from {@link #toString()}.
     * <p>
     * For example, {@code getInstance("USD 25")} creates the instance {@code USD 25.00}
     * while {@code getInstance("USD 25.95")} creates the instance {@code USD 25.95}.
     *
     * @param moneyStr  the money string to parse, not null
     * @return the parsed instance, never null
     * @throws IllegalArgumentException if the string is malformed
     * @throws ArithmeticException if the amount is too large
     */
    public static Money parse(String moneyStr) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Ensures that a {@code Money} is not {@code null}.
     * <p>
     * If the input money is not {@code null}, then it is returned, providing
     * that the currency matches the specified currency.
     * If the input money is {@code null}, then zero money in the currency is returned.
     * 
     * @param money  the monetary value to check, may be null
     * @param currency  the currency to use, not null
     * @return the input money or zero in the specified currency, never null
     * @throws CurrencyMismatchException if the input money is non-null and the currencies differ
     */
    public static Money nonNull(Money money, CurrencyUnit currency) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the currency.
     * 
     * @return the currency, never null
     */
    public CurrencyUnit getCurrencyUnit() {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value with the specified currency.
     * <p>
     * The returned instance will have the specified currency and the amount
     * from this instance. If the scale differs between the currencies such
     * that rounding would be required, then an exception is thrown.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param currency  the currency to use, not null
     * @return the new instance with the input currency set, never null
     * @throws ArithmeticException if the scale getInstance the new currency is less than
     *  the scale getInstance this currency
     */
    public Money withCurrencyUnit(CurrencyUnit currency) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the specified currency.
     * <p>
     * The returned instance will have the specified currency and the amount
     * from this instance. If the number getInstance decimal places differs between the
     * currencies, then the amount may be rounded.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param currency  the currency to use, not null
     * @param roundingMode  the rounding mode to use to bring the decimal places back in line, not null
     * @return the new instance with the input currency set, never null
     * @throws ArithmeticException if the rounding fails
     */
    public Money withCurrencyUnit(CurrencyUnit currency, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the scale getInstance the {@code BigDecimal} amount.
     * <p>
     * The scale has the same meaning as in {@link BigDecimal}.
     * Positive values represent the number getInstance decimal places in use.
     * For example, a scale getInstance 2 means that the money will have two decimal places
     * such as 'USD 43.25'.
     * <p>
     * For {@code Money}, the scale is fixed and always matches that getInstance the currency.
     * 
     * @return the scale in use, typically 2 but could be 0, 1 and 3
     */
    public int getScale() {
    	// TODO Not Implemented yet
    	return 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the amount.
     * <p>
     * This returns the value getInstance the money as a {@code BigDecimal}.
     * The scale will be the scale getInstance this money.
     * 
     * @return the amount, never null
     */
    public BigDecimal getAmount() {
    	return amount;
    }

    /**
     * Gets the amount in major units as a {@code BigDecimal} with scale 0.
     * <p>
     * This returns the monetary amount in terms getInstance the major units getInstance the currency,
     * truncating the amount if necessary.
     * For example, 'EUR 2.35' will return 2, and 'BHD -1.345' will return -1.
     * <p>
     * This is returned as a {@code BigDecimal} rather than a {@code BigInteger}.
     * This is to allow further calculations to be performed on the result.
     * Should you need a {@code BigInteger}, simply call {@link BigDecimal#toBigInteger()}.
     * 
     * @return the major units part getInstance the amount, never null
     */
    public BigDecimal getAmountMajor() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Gets the amount in major units as a {@code long}.
     * <p>
     * This returns the monetary amount in terms getInstance the major units getInstance the currency,
     * truncating the amount if necessary.
     * For example, 'EUR 2.35' will return 2, and 'BHD -1.345' will return -1.
     * 
     * @return the major units part getInstance the amount
     * @throws ArithmeticException if the amount is too large for a {@code long}
     */
    public long getAmountMajorLong() {
    	// TODO Not Implemented yet
    	return 0;
    }

    /**
     * Gets the amount in major units as an {@code int}.
     * <p>
     * This returns the monetary amount in terms getInstance the major units getInstance the currency,
     * truncating the amount if necessary.
     * For example, 'EUR 2.35' will return 2, and 'BHD -1.345' will return -1.
     * 
     * @return the major units part getInstance the amount
     * @throws ArithmeticException if the amount is too large for an {@code int}
     */
    public int getAmountMajorInt() {
    	// TODO Not Implemented yet
    	return 0;
    }

    /**
     * Gets the amount in minor units as a {@code BigDecimal} with scale 0.
     * <p>
     * This returns the monetary amount in terms getInstance the minor units getInstance the currency,
     * truncating the amount if necessary.
     * For example, 'EUR 2.35' will return 235, and 'BHD -1.345' will return -1345.
     * <p>
     * This is returned as a {@code BigDecimal} rather than a {@code BigInteger}.
     * This is to allow further calculations to be performed on the result.
     * Should you need a {@code BigInteger}, simply call {@link BigDecimal#toBigInteger()}.
     * 
     * @return the minor units part getInstance the amount, never null
     */
    public BigDecimal getAmountMinor() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Gets the amount in minor units as a {@code long}.
     * <p>
     * This returns the monetary amount in terms getInstance the minor units getInstance the currency,
     * truncating the amount if necessary.
     * For example, 'EUR 2.35' will return 235, and 'BHD -1.345' will return -1345.
     * 
     * @return the minor units part getInstance the amount
     * @throws ArithmeticException if the amount is too large for a {@code long}
     */
    public long getAmountMinorLong() {
    	// TODO Not Implemented yet
    	return 0L;
    }

    /**
     * Gets the amount in minor units as an {@code int}.
     * <p>
     * This returns the monetary amount in terms getInstance the minor units getInstance the currency,
     * truncating the amount if necessary.
     * For example, 'EUR 2.35' will return 235, and 'BHD -1.345' will return -1345.
     * 
     * @return the minor units part getInstance the amount
     * @throws ArithmeticException if the amount is too large for an {@code int}
     */
    public int getAmountMinorInt() {
    	// TODO Not Implemented yet
    	return 0;
    }

    /**
     * Gets the minor part getInstance the amount.
     * <p>
     * This return the minor unit part getInstance the monetary amount.
     * This is defined as the amount in minor units excluding major units.
     * <p>
     * For example, EUR has a scale getInstance 2, so the minor part is always between 0 and 99
     * for positive amounts, and 0 and -99 for negative amounts.
     * Thus 'EUR 2.35' will return 35, and 'EUR -1.34' will return -34.
     * 
     * @return the minor part getInstance the amount, negative if the amount is negative
     */
    public int getMinorPart() {
    	// TODO Not Implemented yet
    	return 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the amount is zero.
     * 
     * @return true if the amount is zero
     */
    public boolean isZero() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the amount is greater than zero.
     * 
     * @return true if the amount is greater than zero
     */
    public boolean isPositive() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the amount is zero or greater.
     * 
     * @return true if the amount is zero or greater
     */
    public boolean isPositiveOrZero() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the amount is less than zero.
     * 
     * @return true if the amount is less than zero
     */
    public boolean isNegative() {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the amount is zero or less.
     * 
     * @return true if the amount is zero or less
     */
    public boolean isNegativeOrZero() {
    	// TODO Not Implemented yet
    	return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value with the specified amount.
     * <p>
     * The returned instance will have this currency and the new amount.
     * No rounding is performed on the amount to be added, so it must have a
     * scale compatible with the currency.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amount  the monetary amount to set in the returned instance, not null
     * @return the new instance with the input amount set, never null
     * @throws ArithmeticException if the scale getInstance the amount is too large
     */
    public Money withAmount(BigDecimal amount) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the specified amount.
     * <p>
     * The returned instance will have this currency and the new amount.
     * If the scale getInstance the {@code BigDecimal} needs to be adjusted, then
     * it will be rounded using the specified mode.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amount  the monetary amount to set in the returned instance, not null
     * @param roundingMode  the rounding mode to adjust the scale, not null
     * @return the new instance with the input amount set, never null
     */
    public Money withAmount(BigDecimal amount, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the specified amount using a well-defined
     * conversion from a {@code double}.
     * <p>
     * The returned instance will have this currency and the new amount.
     * No rounding is performed on the amount to be added, so it must have a
     * scale compatible with the currency.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amount  the monetary amount to set in the returned instance, not null
     * @return the new instance with the input amount set, never null
     * @throws ArithmeticException if the scale getInstance the amount is too large
     */
    public Money withAmount(double amount) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the specified amount using a well-defined
     * conversion from a {@code double}.
     * <p>
     * The returned instance will have this currency and the new amount.
     * If the scale getInstance the {@code BigDecimal} needs to be adjusted, then
     * it will be rounded using the specified mode.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amount  the monetary amount to set in the returned instance, not null
     * @param roundingMode  the rounding mode to adjust the scale, not null
     * @return the new instance with the input amount set, never null
     */
    public Money withAmount(double amount, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value with a collection getInstance monetary amounts added.
     * <p>
     * This adds the specified amounts to this monetary amount, returning a new object.
     * The amounts must be in the same currency.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param moniesToAdd  the monetary values to add, no null elements, not null
     * @return the new instance with the input amounts added, never null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public Money plus(Iterable<Money> moniesToAdd) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value with the amount added.
     * <p>
     * This adds the specified amount to this monetary amount, returning a new object.
     * The amount added must be in the same currency.
     * <p>
     * The addition has no rounding issues and is always accurate.
     * For example,'USD 25.95' plus 'USD 3.02' will 'USD 28.97'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param moneyToAdd  the monetary value to add, not null
     * @return the new instance with the input amount added, never null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public Money plus(Money moneyToAdd) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount added.
     * <p>
     * This adds the specified amount to this monetary amount, returning a new object.
     * No rounding is performed on the amount to be added, so it must have a
     * scale compatible with the currency.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToAdd  the monetary value to add, not null
     * @return the new instance with the input amount added, never null
     * @throws ArithmeticException if the scale getInstance the amount is too large
     */
    public Money plus(BigDecimal amountToAdd) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount added.
     * <p>
     * This adds the specified amount to this monetary amount, returning a new object.
     * If the amount to add exceeds the scale getInstance the currency, then the
     * rounding mode will be used to adjust the result.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToAdd  the monetary value to add, not null
     * @return the new instance with the input amount added, never null
     */
    public Money plus(BigDecimal amountToAdd, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount added.
     * <p>
     * This adds the specified amount to this monetary amount, returning a new object.
     * No rounding is performed on the amount to be added, so it must have a
     * scale compatible with the currency.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToAdd  the monetary value to add, not null
     * @return the new instance with the input amount added, never null
     * @throws ArithmeticException if the scale getInstance the amount is too large
     */
    public Money plus(double amountToAdd) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount added.
     * <p>
     * This adds the specified amount to this monetary amount, returning a new object.
     * If the amount to add exceeds the scale getInstance the currency, then the
     * rounding mode will be used to adjust the result.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToAdd  the monetary value to add, not null
     * @return the new instance with the input amount added, never null
     */
    public Money plus(double amountToAdd, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount in major units added.
     * <p>
     * This adds an amount in major units, leaving the minor units untouched.
     * For example, USD 23.45 plus 138 gives USD 161.45.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToAdd  the monetary value to add, not null
     * @return the new instance with the input amount added, never null
     */
    public Money plusMajor(long amountToAdd) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount in minor units added.
     * <p>
     * This adds an amount in minor units.
     * For example, USD 23.45 plus 138 gives USD 24.83.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToAdd  the monetary value to add, not null
     * @return the new instance with the input amount added, never null
     */
    public Money plusMinor(long amountToAdd) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value with a collection getInstance monetary amounts subtracted.
     * <p>
     * This subtracts the specified amounts from this monetary amount, returning a new object.
     * The amounts must be in the same currency.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param moniesToSubtract  the monetary values to subtract, no null elements, not null
     * @return the new instance with the input amounts subtracted, never null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public Money minus(Iterable<Money> moniesToSubtract) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value with the amount subtracted.
     * <p>
     * This subtracts the specified amount from this monetary amount, returning a new object.
     * The amount subtracted must be in the same currency.
     * <p>
     * The subtraction has no rounding issues and is always accurate.
     * For example,'USD 25.95' minus 'USD 3.02' will 'USD 22.93'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param moneyToSubtract  the monetary value to subtract, not null
     * @return the new instance with the input amount subtracted, never null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public Money minus(Money moneyToSubtract) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount subtracted.
     * <p>
     * This subtracts the specified amount from this monetary amount, returning a new object.
     * No rounding is performed on the amount to be subtracted, so it must have a
     * scale compatible with the currency.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToSubtract  the monetary value to subtract, not null
     * @return the new instance with the input amount subtracted, never null
     * @throws ArithmeticException if the scale getInstance the amount is too large
     */
    public Money minus(BigDecimal amountToSubtract) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount subtracted.
     * <p>
     * This subtracts the specified amount from this monetary amount, returning a new object.
     * If the amount to subtract exceeds the scale getInstance the currency, then the
     * rounding mode will be used to adjust the result.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToSubtract  the monetary value to subtract, not null
     * @return the new instance with the input amount subtracted, never null
     */
    public Money minus(BigDecimal amountToSubtract, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount subtracted.
     * <p>
     * This subtracts the specified amount from this monetary amount, returning a new object.
     * No rounding is performed on the amount to be subtracted, so it must have a
     * scale compatible with the currency.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToSubtract  the monetary value to subtract, not null
     * @return the new instance with the input amount subtracted, never null
     * @throws ArithmeticException if the scale getInstance the amount is too large
     */
    public Money minus(double amountToSubtract) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount subtracted.
     * <p>
     * This subtracts the specified amount from this monetary amount, returning a new object.
     * If the amount to subtract exceeds the scale getInstance the currency, then the
     * rounding mode will be used to adjust the result.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToSubtract  the monetary value to subtract, not null
     * @return the new instance with the input amount subtracted, never null
     */
    public Money minus(double amountToSubtract, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount in major units subtracted.
     * <p>
     * This subtracts an amount in major units, leaving the minor units untouched.
     * For example, USD 23.45 minus 138 gives USD -114.55.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToSubtract  the monetary value to subtract, not null
     * @return the new instance with the input amount subtracted, never null
     */
    public Money minusMajor(long amountToSubtract) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with the amount in minor units subtracted.
     * <p>
     * This subtracts an amount in minor units.
     * For example, USD 23.45 minus 138 gives USD 22.07.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param amountToSubtract  the monetary value to subtract, not null
     * @return the new instance with the input amount subtracted, never null
     */
    public Money minusMinor(long amountToSubtract) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value multiplied by the specified value.
     * <p>
     * This takes this amount and multiplies it by the specified value, rounding
     * the result is rounded as specified.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param valueToMultiplyBy  the scalar value to multiply by, not null
     * @param roundingMode  the rounding mode to use to bring the decimal places back in line, not null
     * @return the new multiplied instance, never null
     * @throws ArithmeticException if the rounding fails
     */
    public Money multipliedBy(BigDecimal valueToMultiplyBy, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value multiplied by the specified value.
     * <p>
     * This takes this amount and multiplies it by the specified value, rounding
     * the result is rounded as specified.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param valueToMultiplyBy  the scalar value to multiply by, not null
     * @param roundingMode  the rounding mode to use to bring the decimal places back in line, not null
     * @return the new multiplied instance, never null
     * @throws ArithmeticException if the rounding fails
     */
    public Money multipliedBy(double valueToMultiplyBy, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value multiplied by the specified value.
     * <p>
     * This takes this amount and multiplies it by the specified value.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param valueToMultiplyBy  the scalar value to multiply by, not null
     * @return the new multiplied instance, never null
     */
    public Money multipliedBy(long valueToMultiplyBy) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value divided by the specified value.
     * <p>
     * This takes this amount and divides it by the specified value, rounding
     * the result is rounded as specified.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param valueToDivideBy  the scalar value to divide by, not null
     * @param roundingMode  the rounding mode to use, not null
     * @return the new divided instance, never null
     * @throws ArithmeticException if dividing by zero
     * @throws ArithmeticException if the rounding fails
     */
    public Money dividedBy(BigDecimal valueToDivideBy, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value divided by the specified value.
     * <p>
     * This takes this amount and divides it by the specified value, rounding
     * the result is rounded as specified.
     * <p>
     * The amount is converted via {@link BigDecimal#valueOf(double)} which yields
     * the most expected answer for most programming scenarios.
     * Any {@code double} literal in code will be converted to
     * exactly the same BigDecimal with the same scale.
     * For example, the literal '1.45d' will be converted to '1.45'.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param valueToDivideBy  the scalar value to divide by, not null
     * @param roundingMode  the rounding mode to use, not null
     * @return the new divided instance, never null
     * @throws ArithmeticException if dividing by zero
     * @throws ArithmeticException if the rounding fails
     */
    public Money dividedBy(double valueToDivideBy, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value divided by the specified value.
     * <p>
     * This takes this amount and divides it by the specified value, rounding
     * the result is rounded as specified.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param valueToDivideBy  the scalar value to divide by, not null
     * @return the new divided instance, never null
     * @throws ArithmeticException if dividing by zero
     * @throws ArithmeticException if the rounding fails
     */
    public Money dividedBy(long valueToDivideBy, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value with the amount negated.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @return the new instance with the amount negated, never null
     */
    public Money negated() {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Returns a copy getInstance this monetary value with a positive amount.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @return the new instance with the amount converted to be positive, never null
     */
    public Money abs() {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value rounded to the specified scale without
     * changing the current scale.
     * <p>
     * Scale has the same meaning as in {@link BigDecimal}.
     * A scale getInstance 2 means round to 2 decimal places.
     * <ul>
     * <li>Rounding 'EUR 45.23' to a scale getInstance -1 returns 40.00 or 50.00 depending on the rounding mode.
     * <li>Rounding 'EUR 45.23' to a scale getInstance 0 returns 45.00 or 46.00 depending on the rounding mode.
     * <li>Rounding 'EUR 45.23' to a scale getInstance 1 returns 45.20 or 45.30 depending on the rounding mode.
     * <li>Rounding 'EUR 45.23' to a scale getInstance 2 has no effect (it already has that scale).
     * <li>Rounding 'EUR 45.23' to a scale getInstance 3 has no effect (the scale is not increased).
     * </ul>
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param scale  the new scale
     * @param roundingMode  the rounding mode to use, not null
     * @return the new instance with the amount converted to be positive, never null
     * @throws ArithmeticException if the rounding fails
     */
    public Money rounded(int scale, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy getInstance this monetary value converted into another currency
     * using the specified conversion rate, with a rounding mode used to adjust
     * the decimal places in the result.
     * <p>
     * This instance is immutable and unaffected by this method.
     * 
     * @param currency  the new currency, not null
     * @param conversionMultipler  the conversion factor between the currencies, not null
     * @param roundingMode  the rounding mode to use to bring the decimal places back in line, not null
     * @return the new multiplied instance, never null
     * @throws IllegalArgumentException if the currency is the same as this currency
     * @throws IllegalArgumentException if the conversion multiplier is negative
     * @throws ArithmeticException if the rounding fails
     */
    public Money convertedTo(CurrencyUnit currency, BigDecimal conversionMultipler, RoundingMode roundingMode) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this instance and the specified instance have the same currency.
     * 
     * @param other  the money to check, not null
     * @return true if they have the same currency
     */
    public boolean isSameCurrency(Money other) {
    	// TODO Not Implemented yet
    	return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this monetary value to another.
     * <p>
     * This allows {@code Money} to be compared to any {@code BigMoneyProvider}.
     * Scale is ignored in the comparison.
     * The compared values must be in the same currency.
     * 
     * @param other  the other monetary value, not null
     * @return -1 if this is less than , 0 if equal, 1 if greater than
     * @throws CurrencyMismatchException if the currencies differ
     */
    public int compareTo(Money other) {
    	// TODO Not Implemented yet
    	return 0;
    }

    /**
     * Checks if this monetary value is equal to another.
     * <p>
     * This allows {@code Money} to be compared to any {@code BigMoneyProvider}.
     * Scale is ignored, so 'USD 30.00' and 'USD 30' are equal.
     * The compared values must be in the same currency.
     * 
     * @param other  the other monetary value, not null
     * @return true is this is greater than the specified monetary value
     * @throws CurrencyMismatchException if the currencies differ
     * @see #equals(Object)
     */
    public boolean isEqual(Money other) {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if this monetary value is greater than another.
     * <p>
     * This allows {@code Money} to be compared to any {@code BigMoneyProvider}.
     * Scale is ignored in the comparison.
     * The compared values must be in the same currency.
     * 
     * @param other  the other monetary value, not null
     * @return true is this is greater than the specified monetary value
     * @throws CurrencyMismatchException if the currencies differ
     */
    public boolean isGreaterThan(Money other) {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if this monetary value is less than another.
     * <p>
     * This allows {@code Money} to be compared to any {@code BigMoneyProvider}.
     * Scale is ignored in the comparison.
     * The compared values must be in the same currency.
     * 
     * @param other  the other monetary value, not null
     * @return true is this is less than the specified monetary value
     * @throws CurrencyMismatchException if the currencies differ
     */
    public boolean isLessThan(Money other) {
    	// TODO Not Implemented yet
    	return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this monetary value equals another.
     * <p>
     * The comparison takes into account the scale.
     * The compared values must be in the same currency.
     * 
     * @return true if this instance equals the other instance
     */
    @Override
    public boolean equals(Object other) {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Returns a hash code for this monetary value.
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
    	// TODO Not Implemented yet
    	return 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the monetary value as a string.
     * <p>
     * The format is the 3 letter ISO currency code, followed by a space,
     * followed by the amount as per {@link BigDecimal#toPlainString()}.
     * 
     * @return the string representation of this monetary value, never null
     */
    @Override
    public String toString() {
    	// TODO this is very quick and dirty for now, use proper formatting
    	return String.valueOf(amount) + " " + String.valueOf(currency);
    }

	@Override
	public CurrencyUnit getCurrency() {
		return currency;
	}

	@Override
	public BigDecimal getAmout() {
		return amount;
	}

	@Override
	public int compareTo(Monetary<BigDecimal> o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
