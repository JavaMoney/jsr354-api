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
/**
 * Main package of the Money and Currency API. In more detail:
 * <ul>
 * <li>JSR 354 defines a minimal set of interfaces for interoperability, since
 * concrete usage scenarios do not allow to define an implementation that is
 * capable of covering all aspects identified. Consequently it must be possible
 * that implementations can provide several implementations for monetary
 * amounts.
 *
 * Users should not reference the interfaces, instead the value types should be
 * used.</li>
 * <li>Implementations must provide value types for currencies and amounts,
 * implementing {@link javax.money.CurrencyUnit} and
 * {@link javax.money.MonetaryAmount}.</li>
 * <li>Implementations must also provide a minimal set of roundings, modeled as
 * {@link javax.money.MonetaryRounding}. This should include basic roundings for
 * ISO currencies, roundings defined by {@link java.math.MathContext} or
 * {@link java.math.RoundingMode}.</li>
 * <li>This API must avoid restrictions that prevents its use in different
 * runtime environments, such as EE or ME.</li>
 * <li>Method naming and style for currency modeling should be in alignment
 * with parts of the Java Collection API or {@code java.time} / [JodaMoney].</li>
 * </ul>
 */
package javax.money;

