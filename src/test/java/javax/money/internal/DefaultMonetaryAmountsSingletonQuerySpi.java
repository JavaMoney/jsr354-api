/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money.internal;

import javax.money.*;
import javax.money.spi.MonetaryAmountsSingletonQuerySpi;
import java.util.*;

/**
 * Test implementation of MonetaryAmountsSingletonQuerySpi.
 */
public class DefaultMonetaryAmountsSingletonQuerySpi implements MonetaryAmountsSingletonQuerySpi {

    private List<MonetaryAmountFactory<?>> factories = new ArrayList<>();

    public DefaultMonetaryAmountsSingletonQuerySpi() {
        factories.add(new DummyAmountBuilder());
        factories = Collections.unmodifiableList(factories);
    }

    @Override
    public Collection<MonetaryAmountFactory<?>> getAmountFactories(MonetaryAmountFactoryQuery query) {
        if (query.getProviderNames().contains("gigigig2")) {
            return Collections.emptyList();
        }
        return factories;
    }

}
