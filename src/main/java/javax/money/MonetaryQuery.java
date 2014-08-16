/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
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
 * <pre>
 * // these two lines are equivalent, but the second approach is recommended
 * monetary = thisQuery.queryFrom(monetary);
 * // Recommended approach
 * monetary = monetary.query(thisQuery);
 * </pre>
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