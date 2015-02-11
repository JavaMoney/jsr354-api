/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
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