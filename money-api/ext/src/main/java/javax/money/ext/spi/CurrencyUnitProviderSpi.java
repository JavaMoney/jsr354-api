/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.money.ext.spi;

import java.util.Collection;
import javax.money.CurrencyUnit;

/**
 * This class models the component defined by JSR 354 that provides accessors
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
