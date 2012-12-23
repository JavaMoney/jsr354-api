/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
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
 */
package javax.money.format.spi;

import java.util.ServiceLoader;

import javax.money.format.AmountFormatter;
import javax.money.format.Formatter;
import javax.money.format.LocalizationStyle;
import javax.money.format.MoneyFormat;

/**
 * Instances of this class can be registered using the {@link ServiceLoader}
 * API. The formatter runtime will ask each registered instance for a formatter
 * given the style provided, until an instance will return a non-null instance
 * of {@link AmountFormatter}. This instance finally will be returned to the
 * client.
 * <p>
 * Note that the formatter runtime does not perform any caching of instances
 * returned. It is the responsibility of the implementations of this interface,
 * to implement reuse of resource, where useful. Nevertheless keep in mind that
 * synchronization of shared resources can lead to severe performance issues.
 * Therefore in most of the cases it is reasonable to create a new formatter
 * instance on each access and to delegate caching aspects to the client using
 * this API. Similarly it is not required that the instances returned by the SPI
 * must be thread safe. If a thread safe instance of a formatter is required,
 * one of {@link MoneyFormat#synchronizedFormatter(Formatter)},
 * {@link MoneyFormat#synchronizedAmountFormatter(AmountFormatter)} can be used
 * to obtain a synchronized instance of a {@link Formatter} or
 * {@link AmountFormatter}.
 * 
 * @author Anatole Tresch
 */
public interface AmountFormatterFactory {

	/**
	 * Creates a new instance of the formatter defined by the passed localization
	 * style instance, if the style (style id, one of the style locales or
	 * additional attributes) required are not supported by this factory, null
	 * should be returned.
	 * 
	 * @param style
	 *            the localization settings.
	 * @return a formatter instance representing the given style, or null.
	 */
	AmountFormatter getFormatter(LocalizationStyle style);

}
