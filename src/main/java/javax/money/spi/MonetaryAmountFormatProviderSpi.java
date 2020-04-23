/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money.spi;

import javax.money.format.AmountFormatQuery;
import javax.money.format.MonetaryAmountFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

/**
 * SPI (formatting) providing {@link MonetaryAmountFormat} instances.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountFormatProviderSpi {

    /**
     * Access the provider's name.
     * @return this provider's name, not null.
     */
    default String getProviderName(){
        return getClass().getSimpleName();
    }

    /**
     * Create a new {@link MonetaryAmountFormat} for the given input.
     * 
     * @param formatQuery
     *            The {@link javax.money.format.AmountFormatContext} to be used.
     * @return An according {@link MonetaryAmountFormat} instance, or {@code null}, which delegates
     *         the request to subsequent {@link MonetaryAmountFormatProviderSpi} instances
     *         registered.
     */
    Collection<MonetaryAmountFormat> getAmountFormats(AmountFormatQuery formatQuery);

    /**
     * Gets a list with available locales for this format provider.
     * @return list of available locales, never null.
     */
    Set<Locale> getAvailableLocales();

    /**
     * Gets a list with available format names for this format provider.
     * @return list of available formats, never null.
     */
    Set<String> getAvailableFormatNames();
}
