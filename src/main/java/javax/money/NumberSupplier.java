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
package javax.money;


/**
 * Represents a supplier of {@link NumberValue}-valued results. This is the
 * {@link NumberValue}-producing specialization of {@code Supplier} (as in Java 8).
 * 
 * <p>
 * There is no requirement that a distinct result be returned each time the
 * supplier is invoked.
 * 
 * <p>
 * This is a <b>functional interface</b> whose
 * functional method is {@link #getNumber()}.
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 * @version 0.6
 * @since 0.8
 * @see java.util.function.Supplier
 */
@FunctionalInterface
public interface NumberSupplier {

	/**
	 * Gets the corresponding {@link javax.money.NumberValue}.
	 * 
	 * @return the corresponding {@link javax.money.NumberValue}, not null.
	 */
	NumberValue getNumber();
}