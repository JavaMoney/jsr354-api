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
package javax.money.format;

import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.MonetaryQuery;
import java.io.IOException;

/**
 * <p>
 * Formats instances of {@code MonetaryAmount} to a {@link String} or an {@link Appendable}.
 * </p>
 * <p>
 * To obtain a <code>MonetaryAmountFormat</code> for a specific locale, including the default
 * locale, call {@link MonetaryFormats#getAmountFormat(java.util.Locale, String...)}.
 *
 * More complex formatting scenarios can be implemented by registering instances of {@link javax.money.spi
 * .MonetaryAmountFormatProviderSpi}.
 * The spi implementation creates new instances of {@link javax.money.format.MonetaryAmountFormat} based on the
 * <i>styleId</i> and <i> (arbitrary) attributes</i> passed within the {@link javax.money.format.AmountFormatContext}.
 * </p>
 * <p>In general, do prefer
 * accessing <code>MonetaryAmountFormat</code> instances from the {@link MonetaryFormats} singleton,
 * instead of instantiating implementations directly, since the <code>MonetaryFormats</code> factory
 * method may return different subclasses or may implement contextual behaviour (in a EE context).
 * If you need to customize the format object, do something like this:
 * </p>
 * <blockquote>
 * <pre><code>
 * MonetaryAmountFormat f = MonetaryFormats.getInstance(loc);
 * f.setStyle(f.getStyle().toBuilder().setPattern(&quot;###.##;(###.##)&quot;).build());
 * </code></pre>
 * </blockquote>
 * <h3>Special Values</h3>
 * 
 * <p>
 * Negative zero (<code>"-0"</code>) should always parse to
 * <ul>
 * <li><code>0</code></li>
 * </ul>
 *
 * <h3><a id="synchronization">Synchronization</a></h3>
 *
 * <p>
 * Instances of this class are not required to be thread-safe. It is recommended to of separate
 * format instances for each thread. If multiple threads access a format concurrently, it must be
 * synchronized externally.
 * 
 * <h3>Example</h3>
 * <blockquote>
 * <pre><code>
 * <strong>// Print out a number using the localized number, currency,
 * // for each locale</strong>
 * Locale[] locales = MonetaryFormats.getAvailableLocales();
 * MonetaryAmount amount = ...;
 * MonetaryAmountFormat form;
 *     System.out.println("FORMAT");
 *     for (int i = 0; i &lt; locales.length; ++i) {
 *         if (locales[i].getCountry().length() == 0) {
 *            continue; // Skip language-only locales
 *         }
 *         System.out.print(locales[i].getDisplayName());
 *         form = MonetaryFormats.getInstance(locales[i]);
 *         System.out.print(": " + form.getStyle().getPattern());
 *         String myAmount = form.format(amount);
 *         System.out.print(" -&gt; " + myAmount);
 *         try {
 *             System.out.println(" -&gt; " + form.parse(form.format(myAmount)));
 *         } catch (ParseException e) {}
 *     }
 * }
 * </code></pre>
 * </blockquote>
 */
public interface MonetaryAmountFormat extends MonetaryQuery<String>{


    /**
     * The {@link AmountFormatContext} to be applied when a {@link MonetaryAmount} is formatted.
     *
     * @return the {@link AmountFormatContext} used, never {@code null}.
     */
    AmountFormatContext getContext();


    /**
     * Formats the given {@link MonetaryAmount} to a String.
     *
     * @param amount the amount to format, not {@code null}
     * @return the string printed using the settings of this formatter
     * @throws UnsupportedOperationException if the formatter is unable to print
	 * @throws IllegalStateException if an IO error occurs.
     */
    default String format(MonetaryAmount amount){
        StringBuilder b = new StringBuilder();
        try{
            print(b, amount);
        }
        catch(IOException e){
            throw new IllegalStateException("Formatting error.", e);
        }
        return b.toString();
    }

    /**
     * Formats the given {@link MonetaryAmount} to a {@code Appendable}.
     * <p>
     * Example implementations of {@code Appendable} are {@code StringBuilder}, {@code StringBuffer}
     * or {@code Writer}. Note that {@code StringBuilder} and {@code StringBuffer} never throw an
     * {@code IOException}.
     *
     * @param appendable the appendable to add to, not null
     * @param amount     the amount to print, not null
     * @throws UnsupportedOperationException if the formatter is unable to print
     * @throws IOException                   if an IO error occurs, thrown by the {@code appendable}
     * @throws MonetaryParseException        if there is a problem while parsing
     */
    void print(Appendable appendable, MonetaryAmount amount) throws IOException;

    /**
     * Fully parses the text into an instance of {@link MonetaryAmount}.
     * <p>
     * The parse must complete normally and parse the entire text. If the parse completes without
     * reading the entire length of the text, an exception is thrown. If any other problem occurs
     * during parsing, an exception is thrown.
     * <p>
     * Additionally the effective implementation type returned can be determined by the
     * {@link MonetaryContext} applied to the {@link MonetaryAmountFormat}. This formatter will call
     * {@link javax.money.Monetary#getDefaultAmountType()} and will use the result returned
     * to access a corresponding {@link javax.money.MonetaryAmountFactory} to of the instance
     * returned.
     *
     * @param text the text to parse, not null
     * @return the parsed value, never {@code null}
     * @throws UnsupportedOperationException if the formatter is unable to parse
     * @throws MonetaryParseException        if there is a problem while parsing
     */
    MonetaryAmount parse(CharSequence text) throws MonetaryParseException;

}
