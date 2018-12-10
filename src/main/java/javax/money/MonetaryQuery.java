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
 * Strategy for querying a monetary amount.
 * <p>
 * Queries are a key tool for extracting information from monetary amounts. They
 * match the strategy design pattern, allowing different types of query to be
 * easily captured. Examples might be a query that checks if the amount is
 * positive, or one that extracts the currency as a symbol.
 * <p>
 * There are two equivalent ways of using a {@code MonetaryQuery}. The first is
 * to invoke the method on this interface. The second is to use
 * {@link MonetaryAmount#query(MonetaryQuery)}:
 * 
 * <pre><code>
 * // these two lines are equivalent, but the second approach is recommended
 * monetary = thisQuery.queryFrom(monetary);
 * // Recommended approach
 * monetary = monetary.query(thisQuery);
 * </code></pre>
 * 
 * It is recommended to use the second approach, {@code query(MonetaryQuery)},
 * as it is a lot clearer to read in code.
 * <h4>Implementation specification</h4>
 * This interface places no restrictions on the mutability of implementations,
 * however immutability is strongly recommended.
 * 
 * @param <R>
 *            the return type.
 * @author Anatole Tresch
 * @author Stephen Colebourne
 * @author Werner Keil
 * @version 0.8
 */
@FunctionalInterface
public interface MonetaryQuery<R>{

	/**
	 * Queries the specified monetary amount.
	 * <p>
	 * This queries the specified monetary amount to return an object using the
	 * logic encapsulated in the implementing class. Examples might be a query
	 * that checks if the amount is positive, or one that extracts the currency
	 * as a symbol.
	 * <p>
	 * There are two equivalent ways of using a {@code MonetaryQuery}. The first
	 * is to invoke the method on this interface. The second is to use
	 * {@link MonetaryAmount#query(MonetaryQuery)}:
	 * 
	 * <pre>
	 * // these two lines are equivalent, but the second approach is recommended
	 * monetary = thisQuery.queryFrom(monetary);
	 * monetary = monetary.query(thisQuery);
	 * </pre>
	 * 
	 * It is recommended to use the second approach,
	 * {@code query(MonetaryQuery)}, as it is a lot clearer to read in code.
	 * 
	 * <h4>Implementation specification</h4>
	 * The implementation must take the input object and query it. The
	 * implementation defines the logic of the query and is responsible for
	 * documenting that logic. It may use any method on {@code MonetaryAmount}
	 * to determine the result. The input object must not be altered.
	 * <p>
	 * This method may be called from multiple threads in parallel. It must be
	 * thread-safe when invoked.
	 * 
	 * @param amount
	 *            the monetary amount to query, not null
	 * @return the queried value, may return null to indicate not found
	 * @throws MonetaryException
	 *             if unable to query
	 * @throws ArithmeticException
	 *             if numeric overflow occurs
	 */
    R queryFrom(MonetaryAmount amount);

}