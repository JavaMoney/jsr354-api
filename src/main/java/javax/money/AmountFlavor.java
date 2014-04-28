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
package javax.money;

/**
 * Defines the possible flavors of {@link javax.money.MonetaryAmount} implementations.
 * This information can be additionally used to determine which
 * implementation type should be used, additionally to the other properties
 * and attributes in {@link MonetaryContext}.
 *
 * @author Anatole Tresch
 * @see javax.money.MonetaryAmounts#queryAmountType(MonetaryContext)
 */
public enum AmountFlavor{
    /**
     * The implementation is optimized for precise results, not primarly for
     * performance.
     */
    PRECISION,
    /**
     * The implementation is optimized for fast results, but reduced
     * precision and scale, may be possible.
     */
    PERFORMANCE,
    /**
     * The implementation has no defined flavor.
     */
    UNDEFINED,
}