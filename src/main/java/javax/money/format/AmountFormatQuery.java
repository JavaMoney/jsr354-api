/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import javax.money.AbstractContext;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryAmounts;

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
public final class AmountFormatQuery extends AbstractContext {

    static final String STYLE_ID = "styleId";

    /**
     * Constructor, used from the Builder.
     *
     * @param builder the corresponding {@link javax.money.format.AmountFormatQueryBuilder}, not null.
     */
    AmountFormatQuery(AmountFormatQueryBuilder builder){
        super(builder);
    }

    /**
     * Gets a style's id.
     *
     * @return the styleId, or null.
     */
    public String getStyleId(){
        return getText(STYLE_ID);
    }

    /**
     * Gets a Locale to be applied.
     *
     * @return the style's locale, or null.
     */
    public Locale getLocale(){
        return get(Locale.class);
    }

    /**
     * Gets the {@link javax.money.MonetaryAmountFactoryQuery} to be used for accessing {@link  javax.money
     * .MonetaryAmountFactory}, when amount's are parsed.
     *
     * @return the monetary context, or {@code null}.
     */
    public MonetaryAmountFactory getMonetaryAmopuntFactory(){
        return get(MonetaryAmountFactory.class, MonetaryAmounts.getDefaultAmountFactory());
    }

    /**
     * Returns the providers and ordering to be used.
     *
     * @return the ordered providers, never null.
     */
    public Collection<String> getProviders(){
        return getCollection("providers", Collections.emptySet());
    }

    /**
     * Creates a simple format query based on a singlet Locale, similar to {@link java.text.DecimalFormat#getInstance
     * (java.util.Locale)}.
     *
     * @param locale the target locale, not null.
     */
    public static AmountFormatQuery of(Locale locale){
        return AmountFormatQueryBuilder.create(locale).build();
    }

    /**
     * Get a {@link javax.money.format.AmountFormatQueryBuilder} preinitialized with this context instance.
     *
     * @return a new preinitialized builder, never null.
     */
    public AmountFormatQueryBuilder toBuilder(){
        return AmountFormatQueryBuilder.create(this);
    }
}
