/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors: Anatole Tresch - initial implementation Werner Keil -
 * extensions and adaptions.
 */
package net.java.javamoney.ri.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.ext.RelatedValidityInfo;
import javax.money.ext.RelatedValidityQuery;
import javax.money.ext.ValidityInfo;
import javax.money.ext.ValidityQuery;
import javax.money.ext.ValidityType;
import javax.money.ext.spi.RelatedValidityProviderSpi;
import javax.money.ext.spi.ValiditiesSingletonSpi;
import javax.money.ext.spi.ValidityProviderSpi;

/**
 * This class models the an internal service class, that provides the base
 * functionality required for the {@link ValiditiesSingletonSpi} implementation.
 * It is extended for different runtime scenarios, hereby allowing the spi
 * implementation loaded using different mechanisms.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public abstract class AbstractValiditiesService {
	/** Loaded validity providers. */
	private final Map<String, ValidityProviderSpi> validityProviders = new ConcurrentHashMap<String, ValidityProviderSpi>();
	/** Loaded validity providers. */
	private final Map<Class, List<ValidityProviderSpi>> validityProvidersPerType = new ConcurrentHashMap<Class, List<ValidityProviderSpi>>();

	/** Loaded related validity providers. */
	private final Map<String, RelatedValidityProviderSpi> relatedValidityProviders = new ConcurrentHashMap<String, RelatedValidityProviderSpi>();
	/** Loaded related validity providers. */
	private final Map<Class, List<RelatedValidityProviderSpi>> relatedValidityProvidersPerType = new ConcurrentHashMap<Class, List<RelatedValidityProviderSpi>>();

	/**
	 * Constructor, also loading the registered spi's.
	 */
	public AbstractValiditiesService() {
		reload();
	}

	public <T> Set<String> getValiditySources(Class<T> type) {
		List<ValidityProviderSpi> list = validityProvidersPerType.get(type);
		if (list == null) {
			return Collections.emptySet();
		}
		Set<String> result = new HashSet<String>();
		for (ValidityProviderSpi spi : list) {
			result.add(spi.getProviderId());
		}
		return result;
	}

	public <T, R> Set<String> getRelatedValiditySources(Class<T> type,
			Class<R> relatedType) {
		List<RelatedValidityProviderSpi> list = relatedValidityProvidersPerType
				.get(type);
		if (list == null) {
			return Collections.emptySet();
		}
		Set<String> result = new HashSet<String>();
		for (RelatedValidityProviderSpi spi : list) {
			if (spi.getRelatedItemTypes(type).contains(relatedType)) {
				result.add(spi.getProviderId());
			}
		}
		return result;
	}

	public <T> Collection<ValidityInfo<T>> getValidityInfo(
			ValidityQuery<T> query) {
		List<ValidityInfo<T>> result = new ArrayList<ValidityInfo<T>>();
		List<ValidityProviderSpi> list = validityProvidersPerType.get(query
				.getItemType());
		if (list == null) {
			return Collections.emptySet();
		}
		for (ValidityProviderSpi validityProviderSpi : list) {
			if (validityProviderSpi.getValidityTypes().contains(
					query.getValidityType())) {
				result.addAll(validityProviderSpi.getValidityInfo(query));
			}
		}
		return result;
	}

	public <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfo(
			RelatedValidityQuery<T, R> query) {
		List<RelatedValidityInfo<T, R>> result = new ArrayList<RelatedValidityInfo<T, R>>();
		List<RelatedValidityProviderSpi> list = relatedValidityProvidersPerType
				.get(query
						.getItemType());
		if (list == null) {
			return Collections.emptySet();
		}
		for (RelatedValidityProviderSpi validityProviderSpi : list) {
			if (validityProviderSpi.getRelatedItemTypes(
					query.getItemType()).contains(
					query.getRelatedToType())) {
				result.addAll(validityProviderSpi.getRelatedValidityInfo(query));
			}
		}
		return result;
	}

	public <T> Set<Class> getRelatedValidityRelationTypes(Class<T> type) {
		Set<Class> result = new HashSet<Class>();
		for (RelatedValidityProviderSpi validityProviderSpi : this.relatedValidityProviders
				.values()) {
			if (validityProviderSpi.getItemTypes().contains(type)) {
				result.addAll(validityProviderSpi.getRelatedItemTypes(type));
			}
		}
		return result;
	}

	public Set<Class> getRelatedValidityItemTypes() {
		Set<Class> result = new HashSet<Class>();
		for (RelatedValidityProviderSpi validityProviderSpi : this.relatedValidityProviders
				.values()) {
			result.addAll(validityProviderSpi.getItemTypes());
		}
		return result;
	}

	public Set<Class> getValidityItemTypes() {
		Set<Class> result = new HashSet<Class>();
		for (ValidityProviderSpi validityProviderSpi : this.validityProviders
				.values()) {
			result.addAll(validityProviderSpi.getItemTypes());
		}
		return result;
	}

	public <T,R> Set<ValidityType> getRelatedValidityTypes(Class<T> type, Class<R> relatedType) {
		Set<ValidityType> result = new HashSet<ValidityType>();
		for (RelatedValidityProviderSpi validityProviderSpi : this.relatedValidityProvidersPerType
				.get(type)) {
			result.addAll(validityProviderSpi.getValidityTypes(type, relatedType));
		}
		return result;
	}

	public <T> Set<ValidityType> getValidityTypes(Class<T> type) {
		Set<ValidityType> result = new HashSet<ValidityType>();
		for (ValidityProviderSpi validityProviderSpi : this.validityProvidersPerType
				.get(type)) {
			result.addAll(validityProviderSpi.getValidityTypes());
		}
		return result;
	}

	/**
	 * This method reloads the providers available from the
	 * {@link ServiceLoader}. This adds providers that were not yet visible
	 * before.
	 */
	public void reload() {
		for (ValidityProviderSpi validitySpi : getValidityProviderSpis()) {
			ValidityProviderSpi prov = this.validityProviders
					.get(validitySpi.getProviderId());
			if (prov != null) {
				throw new IllegalStateException(
						"Duplicate ValidityProviderSpi: "
								+ validitySpi.getProviderId());
			}
			this.validityProviders
					.put(validitySpi.getProviderId(), validitySpi);
			for (Class type : validitySpi.getItemTypes()) {
				List<ValidityProviderSpi> provList = this.validityProvidersPerType
						.get(type);
				if (provList == null) {
					provList = new ArrayList<ValidityProviderSpi>();
					this.validityProvidersPerType.put(
							type,
							provList);
				}
				provList.add(validitySpi);
			}
		}
		for (RelatedValidityProviderSpi relatedValiditySpi : getRelatedValidityProviderSpis()) {
			RelatedValidityProviderSpi prov = this.relatedValidityProviders
					.get(relatedValiditySpi.getProviderId());
			if (prov != null) {
				throw new IllegalStateException(
						"Duplicate RelatedValidityProviderSpi: "
								+ relatedValiditySpi.getProviderId());
			}
			this.relatedValidityProviders.put(
					relatedValiditySpi.getProviderId(),
					relatedValiditySpi);
			for (Class type : relatedValiditySpi.getItemTypes()) {
				List<RelatedValidityProviderSpi> provList = this.relatedValidityProvidersPerType
						.get(type);
				if (provList == null) {
					provList = new ArrayList<RelatedValidityProviderSpi>();
					this.relatedValidityProvidersPerType.put(
							type,
							provList);
				}
				provList.add(relatedValiditySpi);
			}
		}
	}

	protected abstract Collection<ValidityProviderSpi> getValidityProviderSpis();

	protected abstract Collection<RelatedValidityProviderSpi> getRelatedValidityProviderSpis();
}
