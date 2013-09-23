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
import java.util.Currency;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.spi.CurrencyNamespaceProviderSpi;

/**
 * This class models the type of a {@link CurrencyUnit} namespace as immutable
 * value type. Basically the types possible are determined by the loaded
 * CurrencyNamespaceProviderSpi instances. Typical use cases is that exchange
 * rates for different credit card systems or debit/credit may differ. This
 * class allows to distinguish these rates and also provides access to the rate
 * types used.
 * 
 * @author Anatole Tresch
 */
public final class CurrencyNamespace implements Serializable,
		Comparable<CurrencyNamespace> {

	/**
	 * The predefined name space for ISO 4217 currencies, similar to
	 * {@link Currency}.
	 */
	public static final String ISO_NAMESPACE_ID = "ISO-4217";

	/**
	 * The default ISO namespace.
	 */
	public static final CurrencyNamespace ISO_NAMESPACE = new CurrencyNamespace(
			ISO_NAMESPACE_ID);
	// TODO see https://en.wikipedia.org/wiki/ISO_4217#Without_currency_code

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7505497771490888058L;
	/** The id of this type. */
	private final String id;
	/** The cache of types. */
	private static final Map<String, CurrencyNamespace> CONFIGURED_INSTANCES = new ConcurrentHashMap<String, CurrencyNamespace>();

	private static ServiceLoader<CurrencyNamespaceProviderSpi> namespaceProviders;

	/**
	 * Loads the registered CurrencyNamespaceProviderSpi instances and
	 * initializes the according namespaces.
	 */
	static {
		try {
			CONFIGURED_INSTANCES.put(ISO_NAMESPACE.getId(), ISO_NAMESPACE);
			namespaceProviders = ServiceLoader
					.load(CurrencyNamespaceProviderSpi.class);
			for (CurrencyNamespaceProviderSpi prov : namespaceProviders) {
				for (String ns : prov.getNamespaces()) {
					CONFIGURED_INSTANCES.put(ns, new CurrencyNamespace(ns));
				}
			}
			Logger.getLogger(CurrencyNamespace.class.getName())
					.info("Defined Currency namespaces: "
							+ CONFIGURED_INSTANCES.keySet());
		} catch (Exception e) {
			Logger.getLogger(CurrencyNamespace.class.getName())
					.log(Level.SEVERE,
							"Failed to initialize CurrencyNamespaceProviderSpis, possibly only ISO namespace will be available.",
							e);
		}
	}

	/**
	 * Access a namespace by its name. A namespace must be defined by
	 * registering a corresponding {@link CurrencyNamespaceProviderSpi} spi
	 * using the JDK {@link ServiceLoader}.
	 * 
	 * @see #getAvailableNamespaces()
	 * @param id
	 *            The namespace identifier.
	 * @return The namespace instance.
	 * @throws IllegalArgumentException
	 *             If the required nymespace is not available.
	 */
	public static CurrencyNamespace of(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id required.");
		}
		CurrencyNamespace instance = CONFIGURED_INSTANCES.get(id);
		if (instance == null) {
			throw new IllegalArgumentException("Invalid currency namespace '"
					+ id + "', supported namespaces are "
					+ CONFIGURED_INSTANCES.keySet());
		}
		return instance;
	}

	/**
	 * Access all currently available Currency namespaces.
	 * 
	 * @return the available currency namespaces.
	 */
	public static Set<String> getAvailableNamespaces() {
		return CONFIGURED_INSTANCES.keySet();
	}

	/**
	 * Constructs a new instance of an ExchangeRateType..
	 * 
	 * @param id
	 *            The id of this type instance, never null.
	 */
	public CurrencyNamespace(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}
		this.id = id;
	}

	/**
	 * Get the identifier of this instance.
	 * 
	 * @return The identifier, never null.
	 */
	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyNamespace other = (CurrencyNamespace) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CurrencyNamespace [id=" + id + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(CurrencyNamespace o) {
		if (o == null) {
			return -1;
		}
		int compare = id.compareTo(o.id);
		return compare;
	}

}
