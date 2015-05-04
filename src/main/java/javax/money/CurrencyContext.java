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

/**
 * This class models the attributable context of {@link javax.money.CurrencyUnit} instances. It
 * provides information about
 * <ul>
 * <li>the provider that provided the instance (required)
 * <li>the target timestamp / temporal unit
 * <li>any other attributes, identified by the attribute type, e.g. regions, tenants etc.
 * </ul>
 * <p>Instances of this class must be created using the {@link javax.money.CurrencyContextBuilder}. Typically the
 * contexts are created and assigned by the classes that implement the {@link javax.money.spi.CurrencyProviderSpi}.
 * The according implementation classes should document, which attributes are available.</p>
 * <p>
 * This class is immutable, serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class CurrencyContext extends AbstractContext implements Serializable{

	private static final long serialVersionUID = 8450310852172607016L;


	/**
     * Constructor, used from the {@link javax.money.CurrencyContextBuilder}.
     *
     * @param builder the corresponding builder, not null.
     */
    CurrencyContext(CurrencyContextBuilder builder){
        super(builder);
    }


    /**
     * Allows to convert a instance into the corresponding {@link javax.money.CurrencyContextBuilder}, which allows
     * to change the values and of another {@link javax.money.CurrencyContext} instance.
     *
     * @return a new Builder instance, preinitialized with the values from this instance.
     */
    public CurrencyContextBuilder toBuilder(){
        return CurrencyContextBuilder.of(this);
    }


}
