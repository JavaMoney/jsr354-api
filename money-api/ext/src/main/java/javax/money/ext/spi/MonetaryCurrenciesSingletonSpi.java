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
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.money.CurrencyUnit;
import javax.money.UnknownCurrencyException;
import javax.money.ext.CurrencyValidity;

/**
 * This interface must be implemented and registered using the
 * {@code ServiceLoader}. It backs up the {@code MonetaryCurrencies} singleton.
 *
 * @author Anatole Tresch
 */
public interface MonetaryCurrenciesSingletonSpi {

    /**
     * Access the default namespace that this {@link CurrencyUnitProviderSpi}
     * instance is using. The default namespace can be changed by adding a file
     * META-INF/java-money.properties with the following entry
     * {@code defaultCurrencyNamespace=myNamespace}. When not set explicitly
     * {@code ISO-4217} is assummed.
     *
     * @return the default namespace used.
     */
    public String getDefaultNamespace();

    /**
     * This method allows to evaluate, if the given currency name space is
     * defined. "ISO-4217" should be defined in all environments (default).
     *
     * @param namespace the required name space
     * @return true, if the name space exists.
     */
    public boolean isNamespaceAvailable(String namespace);

    /**
     * This method allows to access all name spaces currently defined.
     * "ISO-4217" should be defined in all environments (default).
     *
     * @return the array of currently defined name space.
     */
    public Collection<String> getNamespaces();

    /*-- Access of current currencies --*/
    /**
     * Checks if a currency is defined using its name space and code. This is a
     * convenience method for {@link #getCurrency(String, String)}, where as
     * namespace the default namespace is assumed.
     *
     * @see #getDefaultNamespace()
     * @param code The code that, together with the namespace identifies the
     * currency.
     * @return true, if the currency is defined.
     */
    public boolean isAvailable(String code);

    /**
     * Checks if a currency is defined using its name space and code.
     *
     * @param namespace The name space, e.g. 'ISO-4217'.
     * @param code The code that, together with the namespace identifies the
     * currency.
     * @return true, if the currency is defined.
     */
    public boolean isAvailable(String namespace, String code);

    /**
     * Access a currency using its name space and code. This is a convenience
     * method for {@link #getCurrency(String, String, Date)}, where {@code null}
     * is passed for the target date (meaning current date).
     *
     * @param namespace The name space, e.g. 'ISO-4217'.
     * @param code The code that, together with the namespace identifies the
     * currency.
     * @return The currency found, never null.
     * @throws UnknownCurrencyException if the required currency is not defined.
     */
    public CurrencyUnit get(String namespace, String code);

    /**
     * Access a currency using its code. This is a convenience method for
     * {@link #getCurrency(String, String)}, where as namespace the default
     * namespace is assumed.
     *
     * @see #getDefaultNamespace()
     * @param code The code that, together with the namespace identifies the
     * currency.
     * @return The currency found, never null.
     * @throws UnknownCurrencyException if the required currency is not defined.
     */
    public CurrencyUnit get(String code);

    /**
     * This method maps the given {@link CurrencyUnit} to another
     * {@link CurrencyUnit} with the given target namespace.
     *
     * @param unit The source unit, never {@code null}.
     * @param targetNamespace the target namespace, never {@code null}.
     * @return The mapped {@link CurrencyUnit}, or null.
     */
    public CurrencyUnit map(String targetNamespace, CurrencyUnit currencyUnit);

    /**
     * This method maps the given {@link CurrencyUnit} to another
     * {@link CurrencyUnit} with the given target namespace.
     *
     * @param unit The source unit, never {@code null}.
     * @param targetNamespace the target namespace, never {@code null}.
     * @return The mapped {@link CurrencyUnit}, or null.
     */
    public CurrencyUnit map(String targetNamespace, long timestamp, CurrencyUnit currencyUnit);

    /**
     * Access a list of provider ids for the {@link ValidityProvider}
     *
     * @return the {@link ValidityProvider} ids available, never null.
     * @see #getCurrencyValidity(java.lang.String)
     */
    public Set<String> getValidityProviders();

    /**
     * Access an instance of the CurrencyValidity for the required validity
     * source.
     *
     * @param provider the validity provider.
     * @return
     */
    public CurrencyValidity getCurrencyValidity(String provider);

    /**
     * This method maps the given {@link CurrencyUnit} instances to another
     * {@link CurrencyUnit} instances with the given target namespace.
     *
     * @param units The source units, never {@code null}.
     * @param targetNamespace the target namespace, never {@code null}.
     * @return The mapped {@link CurrencyUnit} instances (same array length). If
     * a unit could not be mapped, the according array element will be
     * {@code null}.
     */
    public List<CurrencyUnit> mapAll(String targetNamespace, CurrencyUnit... units);

    /**
     * This method maps the given {@link CurrencyUnit} instances to another
     * {@link CurrencyUnit} instances with the given target namespace.
     *
     * @param targetNamespace the target namespace, never {@code null}.
     * @param timestamp The target UTC timestamp.
     * @param units The source units, never {@code null}.
     *
     * @return The mapped {@link CurrencyUnit} instances (same array length). If
     * a unit could not be mapped, the according array element will be
     * {@code null}.
     */
    public List<CurrencyUnit> mapAll(String targetNamespace, long timestamp, CurrencyUnit... units);

	/**
     * Access all currencies for a given namespace. 
     * @see #getNamespaces()
     * @see #getDefaultNamespace()
     * @param namespace The target namespace, not null.
     * @return The currencies found, never null.
     * @throws UnknownCurrencyException if the required namespace is not defined.
     */
    public Collection<CurrencyUnit> getAll(String namespace);
    
}
