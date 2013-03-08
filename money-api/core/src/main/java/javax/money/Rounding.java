package javax.money;


/**
 * This interface defines a {@link Rounding}, which basically is special case of
 * amount adjustements. It is hereby important to distinguish between internal
 * rounding such as implied by the maxaimal precision/scale of an amount,
 * contrary to rounding applied to a {@link MonetaryAmount} or a calculation
 * algorithm. Since different use cases may requirey {@link Rounding} done at
 * very different stages within a complex financial caclulation,
 * {@link Rounding}is not directly attached with its type.
 * <p>
 * Nevertheless the JSR's extensions provide a RoundingMonetaryAmount, which
 * wraps a {@link MonetaryAmount} and adds implicit rounding.
 * 
 * @author Anatole
 * 
 */
public interface Rounding extends AmountAdjuster {

}