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

import java.io.Serializable;

/**
 * This interface models a predicate, which just evaluates to {@code true} or
 * {@code false}, for an arbitrary item. Predicates are used in different areas
 * on the API.
 * <p>
 * Instances of this interface are required to be immutable, thread-safe and
 * {@link Serializable}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The item type targeting
 */
public interface Predicate<T> extends MonetaryFunction<T, Boolean> {

}
