package net.java.javamoney.ri;


/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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
 * Provider for available currencies using a file.
 * <p>
 * This reads the first resource named {@code //MoneyData.csv} on the classpath.
 */
class DefaultCurrencyUnitDataProvider extends CurrencyUnitDataProvider {

    /**
     * Registers all the currencies known by this provider.
     * <p>
     * This reads the first resource named '/MoneyData.csv' on the classpath.
     * 
     * @throws Exception if an error occurs
     */
    @Override
    protected void registerCurrencies() throws Exception {
    	// TODO Not Implemented yet
    }

}
