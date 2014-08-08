/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A query type allows to distinguish different types of queries, e.g. a query for
 * mapping currencies, queries for accessing historic currencies and queries on the currencies available
 * for a concrete tenant.<p/>
 * The query type enables SPI implementations to easily distinguish different use cases and to provide results only
 * for the query types they are responsible for. Without query types different SPI providers would have to guess,
 * which type of query is required in a non standard way.
 *
 * @see javax.money.AbstractQuery#getQueryType()
 */
public interface QueryType{

    /**
     * Constant for the general default QueryType.
     */
    public static final QueryType DEFAULT = new QueryType(){
        @Override
        public String toString(){
            return "QueryType(default)";
        }
    };

    /**
     * Constant unmodifiable set containing only  QueryType.DEFAULT.
     */
    public static final Set<QueryType> DEFAULT_SET = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(DEFAULT)));

}
