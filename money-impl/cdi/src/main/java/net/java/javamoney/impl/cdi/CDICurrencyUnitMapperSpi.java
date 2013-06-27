/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation Werner Keil -
 * extensions and adaptions.
 */
package net.java.javamoney.impl.cdi;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.ext.MonetaryCurrencies.CurrencyUnitMapperSpi;

import net.java.javamoney.ri.ext.spi.CurrencyUnitMappingSpi;

/**
 * This class models the singleton defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
@Singleton
public class CDICurrencyUnitMapperSpi implements CurrencyUnitMapperSpi {
    /** Loaded currency mappers. */
    private Instance<CurrencyUnitMappingSpi> mappers;

    /**
     * COnstructor, also loading the registered spi's.
     */
    public CDICurrencyUnitMapperSpi() {
	mappers = CDIContainer.getInstances(CurrencyUnitMappingSpi.class);
    }

    @Override
    public CurrencyUnit map(String targetNamespace, CurrencyUnit currencyUnit, Long timestamp) {
	for (CurrencyUnitMappingSpi prov : mappers) {
	    CurrencyUnit mappedUnit = prov.map(currencyUnit, targetNamespace, null);
	    if (mappedUnit != null) {
		return mappedUnit;
	    }
	}
	return null;
    }

}
