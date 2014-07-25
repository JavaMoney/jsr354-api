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
 * @see MonetaryAmountFormat#getAmountFormatContext()
 * @author Anatole Tresch
 */
@SuppressWarnings("serial")
public final class AmountFormatContext extends AbstractContext{

    static final String STYLE_ID = "styleId";

    /**
     * Creates a new instance of {@link javax.money.format.AmountFormatContext}.
     *
     * @param builder the corresponding builder.
     */
    AmountFormatContext(AmountFormatContextBuilder builder){
        super(builder);
    }

    /**
     * Access the style's {@link Locale}.
     *
     * @return the {@link Locale}, never {@code null}.
     */
    public String getStyleId(){
        return getText(STYLE_ID);
    }

    /**
     * Access the context's Locale.
     *
     * @return the Locale, or null.
     */
    public Locale getLocale(){
        return get(Locale.class);
    }

    /**
     * Access the format's {@link javax.money.MonetaryAmountFactory} that is used to create new amounts during
     * parsing. If not set explicitly, the default {@link javax.money.MonetaryAmountFactory} is used.
     *
     * @return the {@link javax.money.MonetaryAmountFactory}, never {@code null}.
     */
    public MonetaryAmountFactory<?> getParseFactory(){
        return get(MonetaryAmountFactory.class);
    }
   
}