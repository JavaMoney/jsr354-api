/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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



/**
 * Represents an operation on a single {@link MonetaryAmount} that produces a
 * result of type {@link MonetaryAmount}.
 * <p>
 * Examples might be an operator that rounds the amount to the nearest 1000, or
 * one that performs currency conversion.
 * <p>
 * There are two equivalent ways of using a {@code MonetaryOperator}. The first
 * is to invoke the method on this interface. The second is to use
 * {@link MonetaryAmount#with(MonetaryOperator)}:
 *
 * <pre><code>
 * // these two lines are equivalent, but the second approach is recommended
 * monetary = thisOperator.apply(monetary);
 * monetary = monetary.with(thisOperator);
 * </code></pre>
 *
 * It is recommended to use the second approach, {@code with(MonetaryOperator)},
 * as it is a lot clearer to read in code.
 *
 * <h4>Implementation specification</h4>
 * The implementation must take the input object and apply it. The
 * implementation defines the logic of the operator and is responsible for
 * documenting that logic. It may use any method on {@code MonetaryAmount} to
 * determine the result.
 * <p>
 * The input object must not be altered. Instead, an altered copy of the
 * original must be returned. This provides equivalent, safe behavior for
 * immutable and mutable monetary amounts.
 * <p>
 * This method may be called from multiple threads in parallel. It must be
 * thread-safe when invoked.
 *
 * <p>
 * This interface extends {@code java.util.function.UnaryOperator} introduced by Java 8.
 *
 * @author Werner Keil
 * @author Anatole Tresch
 *
 * @version 0.9
 */
@FunctionalInterface
public interface MonetaryOperator{

    /**
     * Applies the operator on the given amount.
     * @param amount the amount to be operated on.
     * @return the applied amount.
     */
    MonetaryAmount apply(MonetaryAmount amount);

}
