package javax.money.function;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.money.MonetaryAmount;
import javax.money.MonetaryFunction;
import javax.money.MonetaryOperator;

public final class MoneyFunctions {

	private static final MathContext DEFAULT_MATH_CONTEXT = initDefaultMathContext();

	private static final Reciprocal RECIPROCAL = new Reciprocal();

	/**
	 * The shared instance of this class.
	 */
	private static final MinorPart MINORPART = new MinorPart();
	private static final MinorUnits MINORUNITS = new MinorUnits();
	private static final MajorPart MAJORPART = new MajorPart();
	private static final MajorUnits MAJORUNITS = new MajorUnits();
	/**
	 * The shared instance of this class.
	 */
	private static final Total TOTAL = new Total();
	private static final Average AVERAGE = new Average();
	
	/**
	 * The shared instance of this class.
	 */
	private static final Maximum MAXIMUM = new Maximum();
	private static final Minimum MINIMUM = new Minimum();

	private MoneyFunctions() {
		// Singleton constructor
	}

	/**
	 * Get {@link MathContext} for {@link Permil} instances.
	 * 
	 * @return the {@link MathContext} to be used, by default
	 *         {@link MathContext#DECIMAL64}.
	 */
	private static MathContext initDefaultMathContext() {
		// TODO Initialize default, e.g. by system properties, or better:
		// classpath properties!
		return MathContext.DECIMAL64;
	}

	public static MonetaryOperator reciprocal() {
		return RECIPROCAL;
	}

/**
	 * Factory method creating a new instance with the given {@code BigDecimal) permil value;
	 * @param decimal the decimal value of the permil operator being created.
	 * @return a new  {@code Permil} operator
	 */
	public static MonetaryOperator permil(BigDecimal decimal) {
		return new Permil(decimal);
	}

/**
	 * Factory method creating a new instance with the given {@code Number) permil value;
	 * @param decimal the decimal value of the permil operator being created.
	 * @return a new  {@code Permil} operator
	 */
	public static MonetaryOperator permil(Number number) {
		return permil(number, DEFAULT_MATH_CONTEXT);
	}

/**
	 * Factory method creating a new instance with the given {@code Number) permil value;
	 * @param decimal the decimal value of the permil operator being created.
	 * @return a new  {@code Permil} operator
	 */
	public static MonetaryOperator permil(Number number, MathContext mathContext) {
		return new Permil(getBigDecimal(number, mathContext));
	}

	/**
	 * Converts to {@link BigDecimal}, if necessary, or casts, if possible.
	 * 
	 * @param number
	 *            The {@link Number}
	 * @param mathContext
	 *            the {@link MathContext}
	 * @return the {@code number} as {@link BigDecimal}
	 */
	private static final BigDecimal getBigDecimal(Number number,
			MathContext mathContext) {
		if (number instanceof BigDecimal) {
			return (BigDecimal) number;
		} else {
			return new BigDecimal(number.doubleValue(), mathContext);
		}
	}

/**
	 * Factory method creating a new instance with the given {@code BigDecimal) percent value;
	 * @param decimal the decimal value of the percent operator being created.
	 * @return a new  {@code Percent} operator
	 */
	public static MonetaryOperator percent(BigDecimal decimal) {
		return new Percent(decimal); // TODO caching, e.g. array for 1-100 might
										// work.
	}

/**
	 * Factory method creating a new instance with the given {@code Number) percent value;
	 * @param decimal the decimal value of the percent operator being created.
	 * @return a new  {@code Percent} operator
	 */
	public static MonetaryOperator percent(Number number) {
		return percent(getBigDecimal(number, DEFAULT_MATH_CONTEXT));
	}

	/**
	 * Access the shared instance of {@link MinorPart} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public MonetaryOperator minorPart() {
		return MINORPART;
	}

	/**
	 * Access the shared instance of {@link MajorPart} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public MonetaryOperator majorPart() {
		return MAJORPART;
	}

	/**
	 * Access the shared instance of {@link MinorPart} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public MonetaryFunction<MonetaryAmount, Long> minorUnits() {
		return MINORUNITS;
	}

	/**
	 * Access the shared instance of {@link MinorPart} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public MonetaryFunction<MonetaryAmount, Long> majorUnits() {
		return MAJORUNITS;
	}

	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static MonetaryFunction<Iterable<? extends MonetaryAmount>, MonetaryAmount> total() {
		return TOTAL;
	}

	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static MonetaryFunction<Iterable<? extends MonetaryAmount>, MonetaryAmount> average() {
		return AVERAGE;
	}
	
	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static MonetaryFunction<Iterable<? extends MonetaryAmount>, MonetaryAmount> minimum() {
		return MINIMUM;
	}
	
	/**
	 * Access the shared instance of {@link Total} for use.
	 * 
	 * @return the shared instance, never {@code null}.
	 */
	public static MonetaryFunction<Iterable<? extends MonetaryAmount>, MonetaryAmount> maximum() {
		return MAXIMUM;
	}

}
