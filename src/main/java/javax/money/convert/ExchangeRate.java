/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money.convert;

import javax.money.CurrencySupplier;
import javax.money.CurrencyUnit;
import javax.money.NumberValue;
import java.io.Serializable;
import java.util.List;

/**
 * This class models an exchange rate, which defines the factor the numeric value of a base amount in some currency
 * 'A' must be multiplied
 * to get the corresponding amount in the terminating currency 'B'. Hereby
 * <ul>
 * <li>an exchange rate always models one rate from a base (source) to a term
 * (target) {@link CurrencyUnit}.</li>
 * <li>an exchange rate is always bound to a rate type, which typically matches
 * the data source of the conversion data, e.g. different credit card providers
 * may use different rates for the same conversion.</li>
 * <li>an exchange rate may restrict its validity. In most of the use cases a
 * rates' validity will be well defined, but it is also possible that the data
 * provider is not able to support the rate's validity, leaving it undefined-</li>
 * <li>an exchange rate has a provider, which is responsible for defining the
 * rate. A provider hereby may be, but must not be the same as the rate's data
 * source.</li>
 * <li>an exchange rate can be a <i>direct</i> rate, where its factor is
 * represented by a single conversion step. Or it can model a <i>derived</i>
 * rate, where multiple conversion steps are required to define the overall
 * base/term conversion. In case of derived rates the chained rates define the
 * overall factor, by multiplying the individual chain rate factors. Of course,
 * this also requires that each subsequent rate's base currency in the chain
 * does match the previous term currency (and vice versa):</li>
 * <li>Whereas the factor should be directly implied by the format rate chain
 * for derived rates, this is obviously not the case for the validity range,
 * since rates can have a undefined validity range. Nevertheless in many cases
 * also the validity range can (but must not) be derived from the rate chain.</li>
 * <li>Finally a conversion rate is always unidirectional. There might be cases
 * where the reciprocal value of {@link #getFactor()}} matches the correct reverse
 * rate. But in most use cases the reverse rate either has a different rate (not
 * equal to the reciprocal value), or might not be defined at all. Therefore for
 * reversing a ExchangeRate one must access an {@link ExchangeRateProvider} and
 * query for the reverse rate.</li>
 * </ul>
 * <p>
 * The class also implements {@link Comparable} to allow sorting of multiple
 * exchange rates using the following sorting order;
 * </p>
 * <ul>
 * <li>Exchange rate type</li>
 * <li>Exchange rate provider</li>
 * <li>base currency</li>
 * <li>term currency</li>
 * </ul>
 * <p>
 * Finally ExchangeRate is modeled as an immutable and thread safe type. Also
 * exchange rates are {@link Serializable}, hereby serializing in the following
 * form and order:
 * </p>
 * <ul>
 * <li>The base {@link CurrencyUnit}</li>
 * <li>The target {@link CurrencyUnit}</li>
 * <li>The factor (NumberValue)</li>
 * <li>The {@link ConversionContext}</li>
 * <li>The rate chain</li>
 * </ul>
 * <h3>Implementation Specification</h3>
 * <p>Implementations of this interface</p>
 * <ul>
 * <li>must be Comparable(with {@code ExchangeRate})</li>
 * <li>must implement equals/hashCode considering #getBaseCurrency, #getCurrency,
 * #getFactor and #getContext.</li>
 * <li>should be thread-safe</li>
 * <li>should be serializable</li>
 * <li>should provide a fluent builder API for constructing new rate instances easily.</li>
 * </ul>
 *
 * @author Werner Keil
 * @author Anatole Tresch
 * @see <a
 * href="https://en.wikipedia.org/wiki/Exchange_rate#Quotations">Wikipedia:
 * Exchange Rate (Quotations)</a>
 */
public interface ExchangeRate extends CurrencySupplier {

    /**
     * Access the {@link ConversionContext} of {@link ExchangeRate}.
     *
     * @return the conversion context, never null.
     */
    ConversionContext getContext();

    /**
     * Get the base (source) {@link CurrencyUnit}.
     *
     * @return the base {@link CurrencyUnit}.
     */
    CurrencyUnit getBaseCurrency();

    /**
     * Get the term (target) {@link CurrencyUnit}.
     *
     * @return the term {@link CurrencyUnit}.
     */
    @Override
    CurrencyUnit getCurrency();

    /**
     * Access the rate's bid factor.
     *
     * @return the bid factor for this exchange rate, or {@code null}.
     */
    NumberValue getFactor();

    /**
     * Access the chain of exchange rates.
     *
     * @return the chain of rates, in case of a derived rate, this may be
     * several instances. For a direct exchange rate, this equals to
     * <code>new ExchangeRate[]{this}</code>.
     */
    List<ExchangeRate> getExchangeRateChain();

    /**
     * Allows to evaluate if this exchange rate is a derived exchange rate.
     * Derived exchange rates are defined by an ordered list of subconversions
     * with intermediate steps, whereas a direct conversion is possible in one
     * steps.
     * <p>
     * This method always returns {@code true}, if the chain contains more than
     * one rate. Direct rates, have also a chain, but with exact one rate.
     *
     * @return true, if the exchange rate is derived.
     */
    default boolean isDerived() {
        return getExchangeRateChain().size() > 1;
    }

}
