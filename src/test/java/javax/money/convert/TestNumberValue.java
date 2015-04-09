/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2014, Credit Suisse All rights
 * reserved.
 */
package javax.money.convert;

import javax.money.NumberValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Default implementation of {@link javax.money.NumberValue} based on {@link java.math.BigDecimal}.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public class TestNumberValue extends NumberValue {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/** The numeric value. */
	private final Number number;

    /**
     * The value 1, with a scale of 0.<br>
     * Backed by {@link java.math.BigDecimal#ONE}
     *
     * @since  0.8
     */
	public static final NumberValue ONE = new TestNumberValue(BigDecimal.ONE);

	public TestNumberValue(Number number) {
		Objects.requireNonNull(number, "Number required");
		this.number = number;
	}

	/**
	 * Creates a new instance of {@link javax.money.NumberValue}, using the given number.
	 *
	 * @param number
	 *            The numeric part, not null.
	 * @return A new instance of {@link javax.money.NumberValue}.
	 */
	public static NumberValue of(Number number) {
		return new TestNumberValue(number);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberValue#getNumberType()
	 */
	@Override
	public Class<?> getNumberType() {
		return this.number.getClass();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberValue#getPrecision()
	 */
	@Override
	public int getPrecision() {
		return numberValue(BigDecimal.class).precision();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberValue#getScale()
	 */
	@Override
	public int getScale() {
		return numberValue(BigDecimal.class).scale();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberBinding#getIntValue()
	 */
	@Override
	public int intValue() {
		return this.number.intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberBinding#getIntValueExact()
	 */
	@Override
	public int intValueExact() {
		return getBigDecimal(number).intValueExact();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberBinding#getLongValue()
	 */
	@Override
	public long longValue() {
		return this.number.longValue();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberBinding#getLongValueExact()
	 */
	@Override
	public long longValueExact() {
		return getBigDecimal(number).longValueExact();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberBinding#getFloatValue()
	 */
	@Override
	public float floatValue() {
		return this.number.floatValue();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberBinding#getDoubleValue()
	 */
	@Override
	public double doubleValue() {
		return this.number.doubleValue();
	}

    @Override
    public long getAmountFractionNumerator(){
        return 0;
    }

    @Override
    public long getAmountFractionDenominator(){
        return 0;
    }

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberBinding#getDoubleValueExact()
	 */
	@Override
	public double doubleValueExact() {
		double d = this.number.doubleValue();
		if (d == Double.NEGATIVE_INFINITY || d == Double.POSITIVE_INFINITY) {
			throw new ArithmeticException("Unable to convert to double: "
					+ this.number);
		}
		return d;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.NumberBinding#getNumberValue(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Number> T numberValue(Class<T> numberType) {
		if (BigDecimal.class == numberType) {
			return (T) getBigDecimal(number);
		}
		else if (BigInteger.class == numberType) {
			return (T) getBigDecimal(number).toBigInteger();
		}
		else if (Double.class == numberType) {
			return (T) Double.valueOf(this.number.doubleValue());
		}
		else if (Float.class == numberType) {
			return (T) Float.valueOf(this.number.floatValue());
		}
		else if (Long.class == numberType) {
			return (T) Long.valueOf(this.number.longValue());
		}
		else if (Integer.class == numberType) {
			return (T) Integer.valueOf(this.number.intValue());
		}
		else if (Short.class == numberType) {
			return (T) Short.valueOf(this.number.shortValue());
		}
		else if (Byte.class == numberType) {
			return (T) Byte.valueOf(this.number.byteValue());
		}
		throw new IllegalArgumentException("Unsupported numeric type: "
				+ numberType);
	}

    @Override
    public NumberValue round(MathContext mathContext) {
        return new TestNumberValue(new BigDecimal(this.number.toString()).round(mathContext));
    }

    /*
     * (non-Javadoc)
     * @see javax.money.NumberValue#numberValueExact(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
	@Override
	public <T extends Number> T numberValueExact(Class<T> numberType) {
		if (BigDecimal.class == numberType) {
			return (T) getBigDecimal(number);
		}
		else if (BigInteger.class == numberType) {
			return (T) getBigDecimal(number).toBigIntegerExact();
		}
		else if (Double.class == numberType) {
			double d = this.number.doubleValue();
			if (d == Double.NEGATIVE_INFINITY || d == Double.POSITIVE_INFINITY) {
				throw new ArithmeticException(
						"Value not exact mappable to double: " + this.number);
			}
			return (T) Double.valueOf(d);
		}
		else if (Float.class == numberType) {
			float f = this.number.floatValue();
			if (f == Float.NEGATIVE_INFINITY || f == Float.POSITIVE_INFINITY) {
				throw new ArithmeticException(
						"Value not exact mappable to float: " + this.number);
			}
			return (T) Float.valueOf(f);
		}
		else if (Long.class == numberType) {
			return (T) Long.valueOf(getBigDecimal(number).longValueExact());
		}
		else if (Integer.class == numberType) {
			return (T) Integer.valueOf(getBigDecimal(number).intValueExact());
		}
		else if (Short.class == numberType) {
			return (T) Short.valueOf(getBigDecimal(number).shortValueExact());
		}
		else if (Byte.class == numberType) {
			return (T) Short.valueOf(getBigDecimal(number).byteValueExact());
		}
		throw new IllegalArgumentException("Unsupported numeric type: "
				+ numberType);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
            return true;
        }
		if (obj instanceof TestNumberValue) {
			TestNumberValue other = (TestNumberValue) obj;
			return Objects.equals(number, other.number);
		}
		return false;
	}

    @Override
    public int hashCode(){
        return Objects.hashCode(number);
    }

    /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return String.valueOf(number);
	}

	/**
	 * Creates a {@link java.math.BigDecimal} from the given {@link Number} doing the valid conversion
	 * depending the type given.
	 *
	 * @param num
	 *            the number type
	 * @return the corresponding {@link java.math.BigDecimal}
	 */
	private static BigDecimal getBigDecimal(Number num) {
		// try fast equality check first (delegates to identity!)
		if (BigDecimal.class.equals(num.getClass())) {
			return (BigDecimal) num;
		}
		if (Long.class.equals(num.getClass())
				|| Integer.class.equals(num.getClass())
				|| Short.class.equals(num.getClass())
				|| Byte.class.equals(num.getClass())
				|| AtomicLong.class.equals(num.getClass())) {
			return BigDecimal.valueOf(num.longValue());
		}
		if (Float.class.equals(num.getClass())
				|| Double.class.equals(num.getClass())) {
			return new BigDecimal(num.toString());
		}
		// try instance of (slower)
		if (num instanceof BigDecimal) {
			return (BigDecimal) num;
		}
		if (num instanceof BigInteger) {
			return new BigDecimal((BigInteger) num);
		}
		try {
			// Avoid imprecise conversion to double value if at all possible
			return new BigDecimal(num.toString());
		} catch (NumberFormatException e) {
            // ignore
		}
		return BigDecimal.valueOf(num.doubleValue());
	}

}
