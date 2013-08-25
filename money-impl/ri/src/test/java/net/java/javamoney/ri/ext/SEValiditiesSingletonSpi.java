/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors: Anatole Tresch - initial version. Wernner Keil - extensions and
 * adaptions.
 */
package net.java.javamoney.ri.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.money.ext.RelatedValidityInfo;
import javax.money.ext.RelatedValidityQuery;
import javax.money.ext.ValidityInfo;
import javax.money.ext.ValidityQuery;
import javax.money.ext.spi.RelatedValidityProviderSpi;
import javax.money.ext.spi.ValiditiesSingletonSpi;
import javax.money.ext.spi.ValidityProviderSpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link RegionImpl}.
 * 
 * @author Anatole Tresch
 */
public class SEValiditiesSingletonSpi implements ValiditiesSingletonSpi {

	private static final Logger LOG = LoggerFactory
			.getLogger(SEValiditiesSingletonSpi.class);

	/**
	 * Loaded region providers.
	 */
	private AbstractValiditiesService validityProviderService = new SEValidityService();

	@Override
	public <T, R> Set<String> getRelatedValidityProviderIds(Class<T> type,
			Class<R> relatedToType) {
		return validityProviderService.getRelatedValiditySources(type, relatedToType);
	}

	@Override
	public <T> Set<String> getValidityProviderIds(Class<T> type) {
		return validityProviderService.getValiditySources(type);
	}

	@Override
	public <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfo(
			RelatedValidityQuery<T, R> query) {
		return validityProviderService.getRelatedValidityInfo(query);
	}

	@Override
	public <T> Collection<ValidityInfo<T>> getValidityInfo(
			ValidityQuery<T> query) {
		return validityProviderService.getValidityInfo(query);
	}


}
