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

import java.util.Locale;

import javax.money.AbstractContext;
import javax.money.MonetaryAmountFactory;


/**
 * The {@link AmountFormatContext} provides details about a {@link javax.money.format.MonetaryAmountFormat}.
 *
 * @author Anatole Tresch
 * @see MonetaryAmountFormat#getContext()
 */
@SuppressWarnings("serial")
public final class AmountFormatContext extends AbstractContext {
    /**
     * Key used for the format name attribute.
     */
    static final String KEY_FORMAT_NAME = "formatName";

    /**
     * Creates a new instance of {@link javax.money.format.AmountFormatContext}.
     *
     * @param builder the corresponding builder.
     */
    AmountFormatContext(AmountFormatContextBuilder builder) {
        super(builder);
    }

    /**
     * Access the style's {@link Locale}.
     *
     * @return the {@link Locale}, never {@code null}.
     */
    public String getFormatName() {
        return getText(KEY_FORMAT_NAME);
    }

    /**
     * Access the context's Locale.
     *
     * @return the Locale, or null.
     */
    public Locale getLocale() {
        return get(Locale.class);
    }

    /**
     * Access the format's {@link javax.money.MonetaryAmountFactory} that is used to of new amounts during
     * parsing. If not set explicitly, the default {@link javax.money.MonetaryAmountFactory} is used.
     *
     * @return the {@link javax.money.MonetaryAmountFactory}, never {@code null}.
     */
    public MonetaryAmountFactory<?> getParseFactory() {
        return get(MonetaryAmountFactory.class);
    }

    /**
     * Creates a new builder instances, initialized with the data from this one.
     *
     * @return a new {@link javax.money.format.AmountFormatContextBuilder} instance, never null.
     */
    public AmountFormatContextBuilder toBuilder() {
        return AmountFormatContextBuilder.of(this);
    }

}