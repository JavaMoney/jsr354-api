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
 * Defines a {@link CompoundValue} containing several results. Hereby the
 * different results are identified by arbitrary keys. Additionally each
 * {@link CompoundValue} has a <i>leading</i> item that identifies the type of
 * result.<br/>
 * A {@link CompoundValue} instance is defined to be implemented as immutable
 * object and therefore is very useful for modeling multidimensional results
 * objects or input parameters as they are common in financial applications.
 * 
 * @author Anatole Tresch
 */
public interface CompoundValue extends Map<String,Object>{

	/**
	 * A {@link CompoundValue} may have an identifier that helps to identify,
	 * what type of items object is returned.
	 * 
	 * @return the {@link CompoundValue}'s type, never null.
	 */
	public String getId();

	/**
	 * Get the compound type of this instance.
	 * 
	 * @return the compound type, never {@code null}.
	 */
	public CompoundType getCompoundType();
	
}
