
package javamoney.util.money;

import javax.money.Amount;
import javax.money.Money;
import javax.money.convert.CurrencyMismatchException;

/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/**
 * Utilities for working with monetary values that handle null.
 * <p>
 * This utility class contains thread-safe static methods.
 *
 * @author Stephen Colebourne, Werner Keil
 */
public final class MoneyUtils {

    //-----------------------------------------------------------------------
    /**
     * Private constructor.
     */
    private MoneyUtils() {
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the monetary value is zero, treating null as zero.
     * <p>
     * This method accepts any implementation of {@code BigMoneyProvider}.
     * 
     * @return true if the money is null or zero
     */
    public static boolean isZero(Amount moneyProvider) {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the monetary value is positive and non-zero, treating null as zero.
     * <p>
     * This method accepts any implementation of {@code BigMoneyProvider}.
     * 
     * @return true if the money is non-null and positive
     */
    public static boolean isPositive(Amount moneyProvider) {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the monetary value is positive or zero, treating null as zero.
     * <p>
     * This method accepts any implementation of {@code BigMoneyProvider}.
     * 
     * @return true if the money is null, zero or positive
     */
    public static boolean isPositiveOrZero(Amount moneyProvider) {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the monetary value is negative and non-zero, treating null as zero.
     * <p>
     * This method accepts any implementation of {@code BigMoneyProvider}.
     * 
     * @return true if the money is non-null and negative
     */
    public static boolean isNegative(Amount moneyProvider) {
    	// TODO Not Implemented yet
    	return false;
    }

    /**
     * Checks if the monetary value is negative or zero, treating null as zero.
     * <p>
     * This method accepts any implementation of {@code BigMoneyProvider}.
     * 
     * @return true if the money is null, zero or negative
     */
    public static boolean isNegativeOrZero(Amount moneyProvider) {
    	// TODO Not Implemented yet
    	return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Finds the maximum {@code Money} value, handing null.
     * <p>
     * This returns the greater of money1 or money2 where null is ignored.
     * If both input values are null, then null is returned.
     * 
     * @param money1  the first money instance, null returns money2
     * @param money2  the first money instance, null returns money1
     * @return the maximum value, null if both inputs are null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public static Money max(Amount money1, Amount money2) {
    	// TODO Not Implemented yet
    	return null;
    }

    /**
     * Finds the minimum {@code Money} value, handing null.
     * <p>
     * This returns the greater of money1 or money2 where null is ignored.
     * If both input values are null, then null is returned.
     * 
     * @param money1  the first money instance, null returns money2
     * @param money2  the first money instance, null returns money1
     * @return the minimum value, null if both inputs are null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public static Amount min(Amount money1, Amount money2) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Adds two {@code Money} objects, handling null.
     * <p>
     * This returns {@code money1 + money2} where null is ignored.
     * If both input values are null, then null is returned.
     * 
     * @param money1  the first money instance, null returns money2
     * @param money2  the first money instance, null returns money1
     * @return the total, where null is ignored, null if both inputs are null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public static Amount add(Amount money1, Amount money2) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
    /**
     * Subtracts the second {@code Money} from the first, handling null.
     * <p>
     * This returns {@code money1 - money2} where null is ignored.
     * If both input values are null, then null is returned.
     * 
     * @param money1  the first money instance, null treated as zero
     * @param money2  the first money instance, null returns money1
     * @return the total, where null is ignored, null if both inputs are null
     * @throws CurrencyMismatchException if the currencies differ
     */
    public static Amount subtract(Amount money1, Amount money2) {
    	// TODO Not Implemented yet
    	return null;
    }

    //-----------------------------------------------------------------------
}
