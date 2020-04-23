/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Instances of this class allow to externalize the numeric value of a {@link MonetaryAmount}. The class extends
 * {@link java.lang.Number} for maximal compatibility with the JDK but also adds additional functionality to
 * overcome some of the pitfalls of the JDK's Number class.
 * <h3>Implementation specification</h3>
 * <p>Implementations of this interface must be : </p>
 * <ul>
 * <li>Comparable, hereby comparing the numeric value.</li>
 * <li>immutable</li>
 * <li>serializable</li>
 * <li>thread-safe</li>
 * </ul>
 *
 * @author Anatole Tresch
 */
public abstract class NumberValue extends Number implements Comparable<NumberValue> {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6410309081241720626L;

    /**
     * Get the numeric implementation type, that is the base of this number.
     *
     * @return the numeric implementation type, not {@code null}.
     */
    public abstract Class<?> getNumberType();

    /**
     * Returns the <i>precision</i> of this {@code MonetaryAmount}. (The precision is the number of
     * digits in the unscaled value.)
     *
     * <p>
     * The precision of a zero value is 1.
     *
     * @return the precision of this {@code MonetaryAmount}.
     */
    public abstract int getPrecision();

    /**
     * Returns the <i>scale</i> of this {@code MonetaryAmount}. If zero or positive, the scale is
     * the number of digits to the right of the decimal point. If negative, the unscaled value of
     * the number is multiplied by ten to the power of the negation of the scale. For example, a
     * scale of {@code -3} means the unscaled value is multiplied by 1000.
     *
     * @return the scale of this {@code MonetaryAmount}.
     */
    public abstract int getScale();

    /**
     * Access the numeric value as {@code int}. Hereby no truncation will be performed to fit the
     * value into the target data type.
     *
     * @return the (possibly) truncated value of the {@link MonetaryAmount}.
     * @throws ArithmeticException If the value must be truncated to fit the target datatype.
     */
    public abstract int intValueExact();

    /**
     * Access the numeric value as {@code long}. Hereby no truncation will be performed to fit the
     * value into the target data type.
     *
     * @return the (possibly) truncated value of the {@link MonetaryAmount}.
     * @throws ArithmeticException If the value must be truncated to fit the target datatype.
     */
    public abstract long longValueExact();

    /**
     * Access the numeric value as {@code double}. Hereby no truncation will be performed to fit the
     * value into the target data type.
     *
     * @return the (possibly) truncated value of the {@link MonetaryAmount}.
     * @throws ArithmeticException If the value must be truncated to fit the target datatype.
     */
    public abstract double doubleValueExact();

    /**
     * Access the numeric value as {@code Number}. Hereby truncation may be performed as needed to
     * fit the value into the target data type.
     *
     * @param numberType The concrete number class to be returned. Basically the following Number types,
     *                   must be supported if available on the corresponding runtime platform:
     *                   <ul>
     *                   <li>{@code java.lang.Long}</li>
     *                   <li>{@code java.lang.Double}</li>
     *                   <li>{@code java.lang.Number}</li>
     *                   <li>{@code java.math.BigInteger}, currently not available on all platforms.</li>
     *                   <li>{@code java.math.BigDecimal}, currently not available on all platforms.</li>
     *                   </ul>
     * @return the (possibly) truncated value of the {@link MonetaryAmount}.
     */
    public abstract <T extends Number> T numberValue(Class<T> numberType);

    /**
     * Access the current NumberValue rounded using the given {@link java.math.MathContext}.
     *
     * @param mathContext the {@link java.math.MathContext} to be applied.
     * @return the new NumberValue, never null.
     * @see java.math.BigDecimal#round(java.math.MathContext)
     */
    public abstract NumberValue round(MathContext mathContext);

    /**
     * Access the numeric value as {@code Number}. Hereby no truncation will be performed to fit the
     * value into the target data type.
     *
     * @param numberType The concrete number class to be returned. Basically the following Number types,
     *                   must be supported if available on the corresponding runtime platform:
     *                   <ul>
     *                   <li>{@code java.lang.Long}</li>
     *                   <li>{@code java.lang.Double}</li>
     *                   <li>{@code java.lang.Number}</li>
     *                   <li>{@code java.math.BigInteger}, currently not available on all platforms.</li>
     *                   <li>{@code java.math.BigDecimal}, currently not available on all platforms.</li>
     *                   </ul>
     * @return the (possibly) truncated value of the {@link MonetaryAmount}.
     * @throws ArithmeticException If the value must be truncated to fit the target datatype.
     */
    public abstract <T extends Number> T numberValueExact(Class<T> numberType);

    /**
     * This method allows to extract the numerator part of the current fraction, hereby given
     * <pre><code>
     * w = longValue()
     * n = getFractionNominator()
     * d = getFractionDenominator()
     * </code></pre>
     *
     * the following must be always true:
     *
     * <pre><code>
     * !(w&lt;0 &amp;&amp; n&gt;0)  and
     * !(w&gt;0 &amp;&amp; n&lt;0)  and
     * d&gt;0            and
     * |n| &lt; d        // || = absolute value
     * </code></pre>.
     *
     * @return the amount's fraction numerator..
     */
    public abstract long getAmountFractionNumerator();

    /**
     * This method allows to extract the denominator part of the current fraction, hereby given
     * <pre><code>
     * w = longValue()
     * n = getFractionNominator()
     * d = getFractionDenominator()
     * </code></pre>
     *
     * the following must be always true:
     *
     * <pre><code>
     * !(w&lt;0 &amp;&amp; n&gt;0)  and
     * !(w&gt;0 &amp;&amp; n&lt;0)  and
     * d&gt;0            and
     * |n| &lt; d        // || = absolute value
     * </code></pre>.
     *
     * @return the amount's fraction denominator.
     */
    public abstract long getAmountFractionDenominator();

    @Override
    public int compareTo(NumberValue other) {
        return numberValue(BigDecimal.class).compareTo(other.numberValue(BigDecimal.class));
    }

}
