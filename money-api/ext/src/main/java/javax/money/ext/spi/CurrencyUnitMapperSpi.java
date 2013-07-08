/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.money.ext.spi;

import javax.money.CurrencyUnit;

/**
 * This class models the component defined by JSR 354 that provides
 * accessors for {@link CurrencyUnit}. It is provided by the Monetary
 * singleton.
 *
 * @author Anatole Tresch
 */
public interface CurrencyUnitMapperSpi {

    /**
     * This method maps the given {@link CurrencyUnit} to another
     * {@link CurrencyUnit} with the given target namespace.
     *
     * @param unit
     *            The source unit, never {@code null}.
     * @param targetNamespace
     *            the target namespace, never {@code null}.
     * @param timestamp
     *            the target timestamp, may be null.
     * @return The mapped {@link CurrencyUnit}, or null.
     */
    public CurrencyUnit map(String targetNamespace, CurrencyUnit currencyUnit, Long timestamp);
    
}
