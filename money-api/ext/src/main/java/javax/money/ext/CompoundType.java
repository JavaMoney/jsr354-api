/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.util.Map;

/**
 * Defines a {@link CompoundType} containing several results. Hereby the
 * different results are identified by arbitrary keys. Additionally each
 * {@link CompoundType} has a <i>leading</i> item that identifies the type of
 * result.<br/>
 * A {@link CompoundType} instance is defined to be implemented as immutable
 * object and therefore is very useful for modeling multidimensional results
 * objects or input parameters as they are common in financial applications.
 * 
 * @author Anatole Tresch
 */
public interface CompoundType extends Map<String, Class<?>> {

	/**
	 * A {@link CompoundType}may have a type identifier that helps to identify,
	 * what type of items object is returned.
	 * 
	 * @return the {@link CompoundType}'s type, never null.
	 */
	public String getId();

	/**
	 * This method allows to check if a key within the {@code CompoundType} is a
	 * required value, so a corresponding {@link CompoundValue} is valid.
	 * 
	 * @param key
	 *            the key
	 * @return true, if the corresponding value is required, false otherwise.
	 */
	public boolean isRequired(String key);

	/**
	 * Validates if the given {@link CompoundValue} defines all the attributes
	 * as required by this {@link CompoundType} instance.
	 * 
	 * @param compundValueMap
	 *            the {@link Map} to be validated before a {@link CompoundValue}
	 *            is created.
	 * @see #isValid(CompoundValue)
	 * @throws IllegalArgumentException
	 *             if validation fails.
	 */
	public void validate(Map<String, Object> compundValueMap)
			throws ValidationException;
}
