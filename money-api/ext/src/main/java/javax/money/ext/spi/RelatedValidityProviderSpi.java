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

import javax.money.ext.RelatedValidityInfo;
import javax.money.ext.RelatedValidityQuery;
import javax.money.ext.ValidityType;

/**
 * This interface must be implemented by components that provide related
 * validity data (history of relationships between data). It is the
 * responsibility of the registered {@link ValiditiesSingletonSpi} to load the
 * and manage the instances of {@link RelatedValidityProviderSpi}. Depending on
 * the runtime environment, implementations may be loaded using the
 * {@link ServiceLoader}. But also alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface RelatedValidityProviderSpi {

	/**
	 * Access the (unique) provider id.
	 * 
	 * @see {@link RelatedValidityQuery#getValiditySource()}
	 * @see {@link RelatedValidityQuery#withValiditySource(String)}
	 * @return the validity provider id, never {@code null}, not empty. The
	 *         identifier must be unique, since it may also be used to
	 *         explicitly select validity information from a certain provider.
	 */
	public String getProviderId();

	/**
	 * Return the {@link ValidityType} instances that this instance is
	 * supporting, this is used for determining which providers have to be
	 * called for evaluating a given {@link RelatedValidityQuery} query.
	 * 
	 * @see {@link RelatedValidityQuery#getValidityType()}
	 * @see {@link RelatedValidityQuery#withValidityType(ValidityType)}
	 * @param itemType
	 *            the item type, not {@code null}.
	 * @param relatedType
	 *            the type of the related item, not {@code null}.
	 * @return the set of supported {@link ValidityType}s, never {@code null}.
	 */
	public Set<ValidityType> getValidityTypes(Class itemType, Class relatedType);

	/**
	 * Return the item types that this provider instance is supporting, this is
	 * used for determining, which providers must be called for a given
	 * {@link RelatedValidityQuery} query.
	 * 
	 * @see {@link RelatedValidityQuery#getItemType()}
	 * @return the set of supported item types, never {@code null}.
	 */
	public Set<Class> getItemTypes();

	/**
	 * Return the related item types that this instance is supporting, given the
	 * base item type. This information is used for determining which providers
	 * must be called for a given {@link RelatedValidityQuery} query.
	 * 
	 * @see {@link RelatedValidityQuery#getRelatedToType()}
	 * 
	 * @param itemType
	 *            the item's type, which can be related to the types returned.
	 * @return the set of supported related item types, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if the itemType is not one of the types returned by
	 *             {@link #getItemTypes()}
	 */
	public Set<Class> getRelatedItemTypes(Class<?> itemType);

	/**
	 * Access all {@link RelatedValidityInfo} for the given query.
	 * 
	 * @param query
	 *            the related validity query.
	 * @return the {@link RelatedValidityInfo} instances found, never
	 *         {@code null}.
	 */
	public <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfo(
			RelatedValidityQuery<T, R> query);

}
