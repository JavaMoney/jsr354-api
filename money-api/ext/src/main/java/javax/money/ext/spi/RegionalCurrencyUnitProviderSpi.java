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
import javax.money.ext.Region;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit} using {@link Region}s. It is provided by the
 * Monetary singleton.
 *
 * @author Anatole Tresch
 */
public interface RegionalCurrencyUnitProviderSpi {

    /**
     * Access all currencies matching a {@link Region}, valid at the given
     * timestamp.
     *
     * @param locale the target locale, not null.
     * @param timestamp The target UTC timestamp, or {@code null} for the
     * current UTC timestamp.
     * @return the currencies found, or null.
     */
    public Collection<CurrencyUnit> getAll(Region region, Long timestamp);
}
