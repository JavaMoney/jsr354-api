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
package javax.money.spi;

import java.util.Collection;
import java.util.ServiceLoader;

/**
 * Instances of this interface can be registered using the {@link ServiceLoader}
 * . This will define and enable the according currency namespaces, as returned
 * by {@link #getNamespaces()}.
 * <p>
 * Only currency namespaces that are defined by such a provider are possible to
 * be used.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyNamespaceProviderSpi {
	
	/**
	 * Provides the ids of the currency namespaces that are available.
	 * 
	 * @return ids of the currency namespaces.
	 */
	public Collection<String> getNamespaces();

}
