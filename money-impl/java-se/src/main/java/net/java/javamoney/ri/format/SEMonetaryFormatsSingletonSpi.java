/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 */
package net.java.javamoney.ri.format;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.format.ItemFormat;
import javax.money.format.ItemFormatException;
import javax.money.format.LocalizationStyle;
import javax.money.format.spi.MonetaryFormatsSingletonSpi;

import javax.money.format.spi.ItemFormatFactorySpi;
import net.java.javamoney.ri.format.AbstractMonetaryFormatsSingletonSpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SEMonetaryFormatsSingletonSpi extends AbstractMonetaryFormatsSingletonSpi {

    @Override
    protected Iterable<ItemFormatFactorySpi> getItemFormatFactorySpis() {
        return ServiceLoader.load(ItemFormatFactorySpi.class);
    }
   
}
