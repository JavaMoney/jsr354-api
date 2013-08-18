/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money.ext.spi;

import java.util.Collection;
import javax.money.CurrencyUnit;

/**
 * This class models the component defined by JSR 354 that provides accessory
 * for {@link CurrencyUnit}. It is provided by the Monetary singleton.
 *
 * @author Anatole Tresch
 */
public interface CurrencyUnitProviderSpi {

    /**
     * Access the namespace this provider defines.
     *
     * @return the namespace of this provider, never null.
     */
    public String getNamespace();

    /**
     * Access all regions for a given namespace.
     *
     * @return the regions that belong to the given namespace.
     */
    public Collection<CurrencyUnit> getAll();

    /**
     * Access a {@link CurrencyUnit} by namespace and code.
     *
     * @param code the code, not null.
     * @return the {@link CurrencyUnit} found.
     */
    public CurrencyUnit get(String code);

    /**
     * Checks if a currency is defined using its name space and code.
     *
     * @param code The code that, together with the namespace identifies the
     * currency.
     * @return true, if the currency is defined.
     */
    public boolean isAvailable(String code);
}
