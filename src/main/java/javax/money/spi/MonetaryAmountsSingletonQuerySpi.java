/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;

/**
 * SPI (core) for the backing implementation of the {@link javax.money.MonetaryAmounts} singleton, implementing
 * the query functionality for amounts.
 *
 * @author Anatole Tresch
 */
public interface MonetaryAmountsSingletonQuerySpi{

    /**
     * Get the {@link javax.money.MonetaryAmount} implementation class, that best matches to cover the given
     * {@link javax.money.MonetaryContext}.
     * <p/>
     * The evaluation order should consider the following aspects:
     * <ul>
     * <li>If {@link javax.money.MonetaryContext#getAmountType()} is defined, it should be considered.
     * Nevertheless if precision/scale cannot be met, a {@link javax.money.MonetaryException} should
     * be thrown.
     * <li>The remaining implementation class candidates must cover the required precision.
     * <li>The remaining implementation class candidates must cover the required max scale.
     * <li>If max scale is met, but {@code precision==0} (unlimited precision), the
     * {@link javax.money.MonetaryAmount} implementation candidate should be chosen with highest possible
     * precision.
     * <li>If still multiple implementation candidates qualify, the ones with
     * {@code Flavor.PERFORMANCE} are preferred.
     * <li>After this point the selection may be arbitrary.
     * </ul>
     *
     * @param amountsSpi      The currently active instance of {@link javax.money.spi.MonetaryAmountsSingletonSpi},
     *                        providing the
     *                        available amount types, not null.
     * @param requiredContext the required {@link javax.money.MonetaryContext}
     * @return the {@link javax.money.MonetaryAmount} implementation class, that best matches to cover the given
     * {@link javax.money.MonetaryContext}, never {@code null}.
     * @throws javax.money.MonetaryException if no {@link javax.money.MonetaryAmount} implementation class can cover
     * the required
     *                                       {@link javax.money.MonetaryContext}.
     */
    public Class<? extends MonetaryAmount> queryAmountType(MonetaryAmountsSingletonSpi amountsSpi,
                                                           MonetaryContext requiredContext);


}