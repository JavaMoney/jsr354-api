/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money.ext.spi;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.Set;

import javax.money.ext.ValidityInfo;
import javax.money.ext.ValidityQuery;
import javax.money.ext.ValidityType;

/**
 * This interface must be implemented by components that provide validity data
 * (history of data). It is the responsibility of the registered
 * {@link ValiditiesSingletonSpi} to load the and manage the instances of
 * {@link ValidityProviderSpi}. Depending on the runtime environment,
 * implementations may be loaded using the {@link ServiceLoader}. But also
 * alternate mechanisms are possible, e.g. CDI.
 * 
 * @author Anatole Tresch
 */
public interface ValidityProviderSpi {

	/**
	 * Access the (unique) provider id.
	 * 
	 * @see {@link ValidityQuery#getValiditySource()}
	 * @see {@link ValidityQuery#withValiditySource(String)}
	 * @return the validity provider id, not {@code null}, not empty. The
	 *         identifier must be unique, since it may also be used to
	 *         explicitly select validity information from a certain provider.
	 */
	public String getProviderId();

	/**
	 * Return the {@link ValidityType} instances that this instance is
	 * supporting, this can be used for determining which providers may create
	 * result for a given query.
	 * 
	 * @return the set of supported {@link ValidityType}s, never {@code null}.
	 */
	public Set<ValidityType> getValidityTypes();

	/**
	 * Return the item types that this provider instance is supporting, this is
	 * used for determining, which providers must be called for a given
	 * {@link ValidityQuery} query.
	 * 
	 * @see {@link ValidityQuery#getItemType()}
	 * @return the set of supported item types, never {@code null}.
	 */
	public Set<Class<?>> getItemTypes();

	/**
	 * Access all {@link ValidityInfo} for the given query.
	 * 
	 * @param query
	 *            the related validity query.
	 * @return the {@link ValidityInfo} instances found, never {@code null}.
	 */
	public <T> Collection<ValidityInfo<T>> getValidityInfo(
			ValidityQuery<T> query);
}
