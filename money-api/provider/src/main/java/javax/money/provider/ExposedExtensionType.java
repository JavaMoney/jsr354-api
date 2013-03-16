/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.provider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This is a annotation that defines the API type exposed by
 * {@link MonetaryExtension} classes. Extensions are loaded automatically on
 * startup. This allows to keep all money related functionality bundled within
 * one central catalog within {@link Monetary}.
 * 
 * @author Anatole Tresch
 */
@Target({ ElementType.TYPE })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExposedExtensionType {

	/**
	 * This method allows an extension to define the type that is exposed as
	 * API. It is highly recommended that extensions provide an according usage
	 * interface instead of returning implementation classes.
	 * 
	 * @return The exposed type, never null.
	 */
	public Class<?> value();

}
