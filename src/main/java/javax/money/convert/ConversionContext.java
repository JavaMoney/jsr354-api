/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import javax.money.AbstractContext;

/**
 * This class models a context for which a {@link ExchangeRate} is valid. It allows to define
 * different settings such as
 * <ul>
 * <li>the required {@link javax.money.convert.RateType}, </li>
 * <li>the required target timestamp</li>
 * <li>the required validity duration</li>
 * <li>additional non standard or extended attributes determined by the implementations participating in the
 * ExchangeRateProvider chain.</li>
 * </ul>
 * This class is immutable, thread-safe and serializable.
 *
 * @author Anatole Tresch
 */
public final class ConversionContext extends AbstractContext {

    private static final long serialVersionUID = 2386546659786888877L;

    /**
     * ConversionContext that queries any conversion available.
     */
    public static final ConversionContext ANY_CONVERSION =
            new ConversionContextBuilder().setRateType(RateType.ANY).build();
    /**
     * ConversionContext quering for any deferred rates.
     */
    public static final ConversionContext DEFERRED_CONVERSION =
            new ConversionContextBuilder().setRateType(RateType.DEFERRED).build();
    /**
     * ConversionContext quering for any historic rates.
     */
    public static final ConversionContext HISTORIC_CONVERSION =
            new ConversionContextBuilder().setRateType(RateType.HISTORIC).build();
    /**
     * ConversionContext quering for real-time rates.
     */
    public static final ConversionContext REALTIME_CONVERSION =
            new ConversionContextBuilder().setRateType(RateType.REALTIME).build();
    /**
     * ConversionContext quering for any other rates.
     */
    public static final ConversionContext OTHER_CONVERSION =
            new ConversionContextBuilder().setRateType(RateType.OTHER).build();

    /**
     * Private constructor, used by {@link javax.money.convert.ConversionContextBuilder}.
     *
     * @param builder the Builder.
     */
    ConversionContext(ConversionContextBuilder builder) {
        super(builder);
    }

    /**
     * Get the deferred flag. Exchange rates can be deferred or real.time.
     *
     * @return the deferred flag, or {code null}.
     */
    public RateType getRateType() {
        return get(RateType.class);
    }


    /**
     * Get the provider of this rate. The provider of a rate can have different
     * contexts in different usage scenarios, such as the service type or the
     * stock exchange.
     *
     * @return the provider, or {code null}.
     */
    public String getProviderName() {
        return getText("provider");
    }

    /**
     * Creates a conversion query builder with the context data from this context instance.
     *
     * @return a corresponding conversion query builder instance, never null.
     */
    public ConversionContextBuilder toBuilder() {
        return ConversionContextBuilder.of(this);
    }

    /**
     * Creates a query builder based on this onctex.
     *
     * @return a new instance of {@link ConversionQueryBuilder}, never null.
     */
    public ConversionQueryBuilder toQueryBuilder() {
        return ConversionQueryBuilder.of().importContext(this);
    }

    /**
     * Simple factory method for {@link ConversionContext}. For more
     * possibilities to initialize a {@link ConversionContext}, please use a
     * {@link javax.money.convert.ConversionContextBuilder},
     *
     * @param provider the provider name, not {@code null}
     * @param rateType the required rate type.
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(String provider, RateType rateType) {
        ConversionContextBuilder b = new ConversionContextBuilder();
        b.setRateType(rateType);
        b.setProviderName(provider);
        return b.build();
    }

    /**
     * Creates a new ConversionContext for the given  {@link ProviderContext} and the given {@link RateType}.
     * <p>
     * <i>Note:</i> for adding additional attributes use {@link javax.money.convert.ConversionContextBuilder
     * (ProviderContext, RateType)}.
     *
     * @param providerContext the provider context, not null.
     * @param rateType        the rate type, not null.
     * @return a corresponding instance of ConversionContext.
     */
    public static ConversionContext from(ProviderContext providerContext, RateType rateType) {
        return ConversionContextBuilder.create(providerContext, rateType).build();
    }

    /**
     * Creates a {@link ConversionContext} for accessing rates of the given
     * type, without specifying the rate's provider.
     *
     * @param rateType the required rate type.
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of(RateType rateType) {
        switch (rateType) {
            default:
            case ANY:
                return ANY_CONVERSION;
            case DEFERRED:
                return DEFERRED_CONVERSION;
            case HISTORIC:
                return HISTORIC_CONVERSION;
            case REALTIME:
                return REALTIME_CONVERSION;
            case OTHER:
                return OTHER_CONVERSION;
        }
    }

    /**
     * Simple factory method for {@link ConversionContext}. For more
     * possibilities to initialize a {@link ConversionContext}, please use a
     * {@link javax.money.convert.ConversionContextBuilder},
     *
     * @return a new instance of {@link ConversionContext}
     */
    public static ConversionContext of() {
        return ANY_CONVERSION;
    }


}
