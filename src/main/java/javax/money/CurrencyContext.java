/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * This class models the attributable context of {@link javax.money.CurrencyUnit} instances. It
 * provides information about
 * <ul>
 * <li>the provider that provided the instance (required)
 * <li>the target timestamp / temporal unit
 * <li>any other attributes, identified by the attribute type, e.g. regions, tenants etc.
 * </ul>
 * <p>Instances of this class must be created using the {@link javax.money.CurrencyContext.Builder}. Typically the
 * contexts are created and assigned by the classes that implement the {@link javax.money.spi.CurrencyProviderSpi}.
 * The according implementation classes should document, which attributes are available.</p>
 * <p>
 * This class is immutable, serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class CurrencyContext extends AbstractContext
        implements Serializable{

    /**
     * Constructor, used from the {@link javax.money.CurrencyContext.Builder}.
     *
     * @param builder the corresponding builder, not null.
     */
    private CurrencyContext(Builder builder){
        super(builder);
    }


    /**
     * Allows to convert a instance into the corresponding {@link javax.money.CurrencyContext.Builder}, which allows
     * to change the values and create another {@link javax.money.CurrencyContext} instance.
     *
     * @return a new Builder instance, preinitialized with the values from this instance.
     */
    public CurrencyContext.Builder toBuilder(){
        return new Builder(getProvider()).importContext(this);
    }


    /**
     * Builder class for creating new instances of {@link javax.money.CurrencyContext} adding detailed information
     * about a {@link javax.money.CurrencyUnit} instance. Typically the
     * contexts are created and assigned by the classes that implement the {@link javax.money.spi.CurrencyProviderSpi}.
     * The according implementation classes should document, which attributes are available.
     * <p>
     * Note this class is NOT thread-safe.
     *
     * @see CurrencyUnit#getCurrencyContext()
     */
    public static final class Builder extends AbstractContextBuilder<Builder,CurrencyContext>{

        /**
         * Creates a new builder.
         *
         * @param provider the provider name, creating the corresponding {@link javax.money.CurrencyUnit} containing
         *                 the final {@link javax.money.CurrencyContext} created by this builder, not null.
         */
        public Builder(String provider){
            Objects.requireNonNull(provider);
            setProvider(provider);
        }

        /**
         * Creates a new instance of {@link CurrencyContext}.
         *
         * @return a new {@link CurrencyContext} instance.
         */
        @Override
        public CurrencyContext build(){
            return new CurrencyContext(this);
        }

    }

}
