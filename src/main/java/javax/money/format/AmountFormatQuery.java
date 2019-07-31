/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
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

import javax.money.AbstractQuery;
import javax.money.Monetary;
import javax.money.MonetaryAmountFactory;

/**
 * Query for accessing instances of {@link javax.money.format.MonetaryAmountFormat}. In general it is determined by the
 * implementation, what formats are provided. Nevertheless the following queries must be supported:
 * <ul>
 * <li>Accessing a format based on a Locale, that is also supported by {@link java.text
 * .DecimalFormat#getAvailableLocales()}.</li>
 * </ul>
 * All other formats are optional. For further details about supported formats refer to the implementation's
 * documentation.
 * <p>
 * This class is immutable, thread-safe and serializable.
 */
public final class AmountFormatQuery extends AbstractQuery {


	private static final long serialVersionUID = 5848832058616502383L;
	/**
     * Key used for the format name attribute.
     */
    static final String KEY_QUERY_FORMAT_NAME = "Query.formatName";

    /**
     * Constructor, used from the Builder.
     *
     * @param builder the corresponding {@link javax.money.format.AmountFormatQueryBuilder}, not null.
     */
    AmountFormatQuery(AmountFormatQueryBuilder builder) {
        super(builder);
    }

    /**
     * Gets a style's id.
     *
     * @return the styleId, or null.
     */
    public String getFormatName() {
        return getText(KEY_QUERY_FORMAT_NAME);
    }

    /**
     * Gets a Locale to be applied.
     *
     * @return the style's locale, or null.
     */
    public Locale getLocale() {
        return get(Locale.class);
    }

    /**
     * Gets the {@link javax.money.MonetaryAmountFactoryQuery} to be used for accessing {@link  javax.money
     * .MonetaryAmountFactory}, when amount's are parsed.
     *
     * @return the monetary context, or {@code null}.
     */
    @SuppressWarnings("rawtypes")
	public MonetaryAmountFactory getMonetaryAmountFactory() {
        MonetaryAmountFactory factory = get(MonetaryAmountFactory.class);
        if (factory == null) {
            return Monetary.getDefaultAmountFactory();
        }
        return factory;
    }

    /**
     * Creates a simple format query based on a single Locale, similar to {@link java.text.DecimalFormat#getInstance
     * (java.util.Locale)}.
     *
     * @param locale    the target locale, not null.
     * @param providers the providers to be used, not null.
     * @return a new query instance
     */
    public static AmountFormatQuery of(Locale locale, String... providers) {
        return AmountFormatQueryBuilder.of(locale).setProviderNames(providers).build();
    }

    /**
     * Get a {@link javax.money.format.AmountFormatQueryBuilder} preinitialized with this context instance.
     *
     * @return a new preinitialized builder, never null.
     */
    public AmountFormatQueryBuilder toBuilder() {
        return AmountFormatQueryBuilder.of(this);
    }
}
