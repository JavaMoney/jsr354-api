/*
 *  Copyright 2009-2012 Werner Keil, Stephen Colebourne
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
package java.util.money;


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
