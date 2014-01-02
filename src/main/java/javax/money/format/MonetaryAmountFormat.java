/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import java.io.IOException;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryAmounts;
import javax.money.MonetaryContext;
import javax.money.MonetaryOperator;
import javax.money.MonetaryQuery;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

/**
 * <p>
 * Formats instances of {@code MonetaryAmount} to a {@link String} or an {@link Appendable}.
 * </p>
 * <p>
 * To obtain a <code>MonetaryAmountFormat</code> for a specific locale, including the default
 * locale, call {@link MonetaryFormats#getAmountFormat(java.util.Locale)}. In general, do prefer
 * accessing <code>MonetaryAmountFormat</code> instances from the {@link MonetaryFormats} singleton,
 * instead of instantiating implementations directly, since the <code>MonetaryFormats</code> factory
 * method may return different subclasses or may implement contextual behaviour (in a EE context).
 * If you need to customize the format object, do something like this:
 * 
 * <blockquote>
 * 
 * <pre>
 * MonetaryAmountFormat f = MonetaryFormats.getInstance(loc);
 * f.setStyle(f.getStyle().toBuilder()..setPattern(&quot;###.##;(###.##)&quot;).create());
 * </pre>
 * 
 * </blockquote>
 * 
 * <p>
 * A <code>MonetaryAmountFormat</code> comprises a <em>pattern</em> and a set of <em>symbols</em>.
 * Since instances of {@link MonetaryAmountFormat} are modelled as immutable instances they may be
 * shared among threads. As shown above, changes must be done by creating corresponding builder
 * instances. The builder acquired can be used to change the format's pattern (calling
 * <code>setPattern(String)</code>), and/or {@link AmountFormatSymbols} (calling
 * <code>setSymbols(AmountFormatSymbols)</code>). The pattern and symbols provided are defined by
 * the {@link MonetaryAmountFormatProviderSpi} implementations registered. In Java SE typically
 * implementations will delegate number formatting to {@link java.text.DecialFormat}, which reads
 * data from localized <code>ResourceBundle</code>s.
 * 
 * <h4>Patterns</h4>
 * 
 * <code>MonetaryAmountFormat</code> patterns have the same syntax as
 * {@link java.text.DecimalFormat}: <blockquote>
 * 
 * <pre>
 * <i>Pattern:</i>
 *         <i>PositivePattern</i>
 *         <i>PositivePattern</i> ; <i>NegativePattern</i>
 * <i>PositivePattern:</i>
 *         <i>Prefix<sub>opt</sub></i> <i>Number</i> <i>Suffix<sub>opt</sub></i>
 * <i>NegativePattern:</i>
 *         <i>Prefix<sub>opt</sub></i> <i>Number</i> <i>Suffix<sub>opt</sub></i>
 * <i>Prefix:</i>
 *         any Unicode characters except &#92;uFFFE, &#92;uFFFF, and special characters
 * <i>Suffix:</i>
 *         any Unicode characters except &#92;uFFFE, &#92;uFFFF, and special characters
 * <i>Number:</i>
 *         <i>Integer</i> <i>Exponent<sub>opt</sub></i>
 *         <i>Integer</i> . <i>Fraction</i> <i>Exponent<sub>opt</sub></i>
 * <i>Integer:</i>
 *         <i>MinimumInteger</i>
 *         #
 *         # <i>Integer</i>
 *         # , <i>Integer</i>
 * <i>MinimumInteger:</i>
 *         0
 *         0 <i>MinimumInteger</i>
 *         0 , <i>MinimumInteger</i>
 * <i>Fraction:</i>
 *         <i>MinimumFraction<sub>opt</sub></i> <i>OptionalFraction<sub>opt</sub></i>
 * <i>MinimumFraction:</i>
 *         0 <i>MinimumFraction<sub>opt</sub></i>
 * <i>OptionalFraction:</i>
 *         # <i>OptionalFraction<sub>opt</sub></i>
 * <i>Exponent:</i>
 *         E <i>MinimumExponent</i>
 * <i>MinimumExponent:</i>
 *         0 <i>MinimumExponent<sub>opt</sub></i>
 * </pre>
 * 
 * </blockquote>
 * 
 * <p>
 * A <code>MonetaryAmountFormat</code> pattern contains a positive and negative subpattern, for
 * example, <code>"#,##0.00;(#,##0.00)"</code>. Each subpattern has a prefix, numeric part, and
 * suffix. The negative subpattern is optional; if absent, then the positive subpattern prefixed
 * with the localized minus sign (<code>'-'</code> in most locales) is used as the negative
 * subpattern. That is, <code>"0.00"</code> alone is equivalent to <code>"0.00;-0.00"</code>. If
 * there is an explicit negative subpattern, it serves only to specify the negative prefix and
 * suffix; the number of digits, minimal digits, and other characteristics are all the same as the
 * positive pattern. That means that <code>"#,##0.0#;(#)"</code> produces precisely the same
 * behavior as <code>"#,##0.0#;(#,##0.0#)"</code>.
 * 
 * <p>
 * The prefixes, suffixes, and various symbols used for infinity, digits, thousands separators,
 * decimal separators, etc. may be set to arbitrary values, and they will appear properly during
 * formatting. However, care must be taken that the symbols and strings do not conflict, or parsing
 * will be unreliable. For example, either the positive and negative prefixes or the suffixes must
 * be distinct for <code>DecimalFormat.parse()</code> to be able to distinguish positive from
 * negative values. (If they are identical, then <code>MonetaryAmountFormat</code> will behave as if
 * no negative subpattern was specified.) Another example is that the decimal separator and
 * thousands separator should be distinct characters, or parsing will be impossible.
 * 
 * <p>
 * The grouping separator is commonly used for thousands, but in some countries it separates
 * ten-thousands. Where as {@link java.text.DecimalFormat} relies on a constant grouping size of
 * digits between the grouping characters, such as 3 for 100,000,000 or 4 for 1,0000,0000, instances
 * of this interface support also grouping with different grouping sizes, e.g. [2,3] will result in
 * 1,000,000,00, or [1,1,2,3] will result in 10,000,00,0,0. Hereby the first elements in the
 * grouping array will define the group sizes of the first group, starting from the decimal point.
 * The last element in the array will be used as <i>default</i> grouping size, used for all
 * subsequent groupings. Passing an empty array [] will disable grouping.
 * <p>
 * A similar mechanism also allows to define different grouping characters. E.g. ['\'', '.'] with
 * grouping sizes [3] will result in 100.000'000.
 * 
 * <h4>Special Pattern Characters</h4>
 * 
 * <p>
 * Many characters in a pattern are taken literally; they are matched during parsing and output
 * unchanged during formatting. Special characters, on the other hand, stand for other characters,
 * strings, or classes of characters. They must be quoted, unless noted otherwise, if they are to
 * appear in the prefix or suffix as literals.
 * 
 * <p>
 * The characters listed here are used in non-localized patterns. Localized patterns use the
 * corresponding characters taken from this formatter's <code>DecimalFormatSymbols</code> object
 * instead, and these characters lose their special status. Two exceptions are the currency sign and
 * quote, which are not localized.
 * 
 * <blockquote>
 * <table border=0 cellspacing=3 cellpadding=0 summary="Chart showing symbol, * location, localized, and meaning.">
 * <tr bgcolor="#ccccff">
 * <th align=left>Symbol
 * <th align=left>Location
 * <th align=left>Localized?
 * <th align=left>Meaning
 * <tr valign=top>
 * <td><code>0</code>
 * <td>Number
 * <td>Yes
 * <td>Digit
 * <tr valign=top bgcolor="#eeeeff">
 * <td><code>#</code>
 * <td>Number
 * <td>Yes
 * <td>Digit, zero shows as absent
 * <tr valign=top>
 * <td><code>.</code>
 * <td>Number
 * <td>Yes
 * <td>Decimal separator or monetary decimal separator
 * <tr valign=top bgcolor="#eeeeff">
 * <td><code>-</code>
 * <td>Number
 * <td>Yes
 * <td>Minus sign
 * <tr valign=top>
 * <td><code>,</code>
 * <td>Number
 * <td>Yes
 * <td>Grouping separator
 * <tr valign=top bgcolor="#eeeeff">
 * <td><code>E</code>
 * <td>Number
 * <td>Yes
 * <td>Separates mantissa and exponent in scientific notation.
 * <em>Need not be quoted in prefix or suffix.</em>
 * <tr valign=top>
 * <td><code>;</code>
 * <td>Subpattern boundary
 * <td>Yes
 * <td>Separates positive and negative subpatterns
 * <tr valign=top bgcolor="#eeeeff">
 * <td><code>&#164;</code> (<code>&#92;u00A4</code>)
 * <td>Prefix or suffix
 * <td>No
 * <td>Currency sign, replaced by currency symbol. Differemt to SE, doubling of the currency symbol,
 * will have no effect, since the currency formatting is controlled by the {@link CurrencyStyle} set
 * on the format's {@link AmountStyle}.
 * <tr valign=top>
 * <td><code>'</code>
 * <td>Prefix or suffix
 * <td>No
 * <td>Used to quote special characters in a prefix or suffix, for example, <code>"'#'#"</code>
 * formats 123 to <code>"#123"</code>. To create a single quote itself, use two in a row:
 * <code>"# o''clock"</code>.
 * </table>
 * </blockquote>
 * 
 * <h4>Scientific Notation</h4>
 * 
 * <p>
 * Numbers in scientific notation are expressed as the product of a mantissa and a power of ten, for
 * example, 1234 can be expressed as 1.234 x 10^3. The mantissa is often in the range 1.0 <= x <
 * 10.0, but it need not be. <code>DecimalFormat</code> can be instructed to format and parse
 * scientific notation <em>only via a pattern</em>; there is currently no factory method that
 * creates a scientific notation format. In a pattern, the exponent character immediately followed
 * by one or more digit characters indicates scientific notation. Example: <code>"0.###E0"</code>
 * formats the number 1234 as <code>"1.234E3"</code>.
 * 
 * <ul>
 * <li>The number of digit characters after the exponent character gives the minimum exponent digit
 * count. There is no maximum. Negative exponents are formatted using the localized minus sign,
 * <em>not</em> the prefix and suffix from the pattern. This allows patterns such as
 * <code>"0.###E0 m/s"</code>.
 * 
 * <li>The minimum and maximum number of integer digits are interpreted together:
 * 
 * <ul>
 * <li>If the maximum number of integer digits is greater than their minimum number and greater than
 * 1, it forces the exponent to be a multiple of the maximum number of integer digits, and the
 * minimum number of integer digits to be interpreted as 1. The most common use of this is to
 * generate <em>engineering notation</em>, in which the exponent is a multiple of three, e.g.,
 * <code>"##0.#####E0"</code>. Using this pattern, the number 12345 formats to
 * <code>"12.345E3"</code>, and 123456 formats to <code>"123.456E3"</code>.
 * 
 * <li>Otherwise, the minimum number of integer digits is achieved by adjusting the exponent.
 * Example: 0.00123 formatted with <code>"00.###E0"</code> yields <code>"12.3E-4"</code>.
 * </ul>
 * 
 * <li>The number of significant digits in the mantissa is the sum of the <em>minimum integer</em>
 * and <em>maximum fraction</em> digits, and is unaffected by the maximum integer digits. For
 * example, 12345 formatted with <code>"##0.##E0"</code> is <code>"12.3E3"</code>. To show all
 * digits, set the significant digits count to zero. The number of significant digits does not
 * affect parsing.
 * 
 * <li>Exponential patterns may not contain grouping separators.
 * </ul>
 * 
 * <h4>Rounding</h4>
 * 
 * <code>MonetaryAmountFormat</code> provides arbitrary rounding (modeled as
 * {@link MonetaryOperator}) to be applied on display (before printing the formatted amount) and on
 * parse (converting a parsed amount before returning the parsed result).
 * 
 * <h4>Digits</h4>
 * 
 * For formatting, <code>DecimalFormat</code> uses the ten consecutive characters starting with the
 * localized zero digit defined in the <code>AmountFormatSymbols</code> object as digits. For
 * parsing, these digits as well as all Unicode decimal digits, as defined by
 * {@link Character#digit Character.digit}, are recognized.
 * 
 * <h4>Special Values</h4>
 * 
 * <p>
 * <code>NaN</code> is formatted as a string, which typically has a single character
 * <code>&#92;uFFFD</code>. This is the only value for which the prefixes and suffixes are not used.
 * 
 * <p>
 * Infinity is formatted as a string, which typically has a single character <code>&#92;u221E</code>
 * , with the positive or negative prefixes and suffixes applied. The infinity string is determined
 * by the <code>AmountFormatSymbols</code> object.
 * 
 * <p>
 * Negative zero (<code>"-0"</code>) should always parse to
 * <ul>
 * <li><code>0</code></li>
 * </ul>
 * 
 * <h4><a name="synchronization">Synchronization</a></h4>
 * 
 * <p>
 * Instances of this class are not required to be thread-safe. It is recommended to create separate
 * format instances for each thread. If multiple threads access a format concurrently, it must be
 * synchronized externally.
 * 
 * <h4>Example</h4>
 * 
 * <blockquote>
 * 
 * <pre>
 * <strong>// Print out a number using the localized number, currency,
 * // for each locale</strong>
 * Locale[] locales = MonetaryFormats.getAvailableLocales();
 * MonetaryAmount amount = ...;
 * MonetaryAmountFormat form;
 *     System.out.println("FORMAT");
 *     for (int i = 0; i < locales.length; ++i) {
 *         if (locales[i].getCountry().length() == 0) {
 *            continue; // Skip language-only locales
 *         }
 *         System.out.print(locales[i].getDisplayName());
 *         form = MonetaryFormats.getInstance(locales[i]);
 *         System.out.print(": " + form.getStyle().getPattern());
 *         String myAmount = form.format(amount);
 *         System.out.print(" -> " + myAmount);
 *         try {
 *             System.out.println(" -> " + form.parse(form.format(myAmount)));
 *         } catch (ParseException e) {}
 *     }
 * }
 * </pre>
 * 
 * </blockquote>
 */
public interface MonetaryAmountFormat extends MonetaryQuery<String> {

	/**
	 * The {@link CurrencyUnit} to be applied when a {@link MonetaryAmount} is parsed and no
	 * {@link CurrencyUnit} is provided within the pattern.
	 * 
	 * @return the {@link CurrencyUnit} used, or {@code null}, if no such default
	 *         {@link CurrencyUnit} is set.
	 */
	public CurrencyUnit getDefaultCurrency();

	/**
	 * Set the {@link CurrencyUnit} to be used.
	 * 
	 * @param currency
	 *            the currency. When set to null, parsing of an amount without any parseable
	 *            currency information will fail, creating a {@link MonetaryParseException}.
	 */
	public void setDefaultCurrency(CurrencyUnit currency);

	/**
	 * The {@link AmountStyle} to be applied when a {@link MonetaryAmount} is formatted.
	 * 
	 * @return the {@link AmountStyle} used, never {@code null}.
	 */
	public AmountStyle getAmountStyle();

	/**
	 * Set the {@link AmountStyle} to be used.
	 * 
	 * @param style
	 *            the style, not null.
	 */
	public void setAmountStyle(AmountStyle style);

	/**
	 * The {@link MonetaryContext} to be applied when a {@link MonetaryAmount} is parsed.
	 * 
	 * @return the {@link MonetaryContext} used, or {@code null}, when the defaults should be used.
	 * @see MonetaryAmount#getMonetaryContext
	 */
	public MonetaryContext getMonetaryContext();

	/**
	 * Set the {@link MonetaryContext} to be used.
	 * 
	 * @param context
	 *            the context, or {@code null} for using the default {@link MonetaryContext} as
	 *            returned by {@link MonetaryAmountFactory#getDefaultMonetaryContext()} returned
	 *            from the {@link MonetaryAmountFactory} as returned by
	 *            {@link MonetaryAmounts#getDefaultAmountFactory()}.
	 */
	public void setMonetaryContext(MonetaryContext context);

	/**
	 * Formats the given {@link MonetaryAmount} to a String.
	 * 
	 * @param amount
	 *            the amount to format, not {@code null}
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 */
	public String format(MonetaryAmount amount);

	/**
	 * Formats the given {@link MonetaryAmount} to a {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder}, {@code StringBuffer}
	 * or {@code Writer}. Note that {@code StringBuilder} and {@code StringBuffer} never throw an
	 * {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param amount
	 *            the amount to print, not null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws IOException
	 *             if an IO error occurs, thrown by the {@code appendable}
	 * @throws MonetaryParseException
	 *             if there is a problem while parsing
	 */
	public void print(Appendable appendable, MonetaryAmount amount)
			throws IOException;

	/**
	 * Fully parses the text into an instance of {@link MonetaryAmount}.
	 * <p>
	 * The parse must complete normally and parse the entire text. If the parse completes without
	 * reading the entire length of the text, an exception is thrown. If any other problem occurs
	 * during parsing, an exception is thrown.
	 * <p>
	 * This method uses a mainly delegates to contained {@link AmountStyle}. Additionally when no
	 * currency is on the input stream, the value of {@link #getDefaultCurrency()} is used instead
	 * of.
	 * <p>
	 * Additionally the effective implementation type returned can be determined by the
	 * {@link MonetaryContext} applied to the {@link MonetaryAmountFormat}. This formatter will call
	 * {@link javax.money.MonetaryAmounts#getDefaultAmountType()} and will use the result returned
	 * to access a corresponding {@link javax.money.MonetaryAmountFactory} to create the instance
	 * returned.
	 * 
	 * @param text
	 *            the text to parse, not null
	 * @return the parsed value, never {@code null}
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to parse
	 * @throws MonetaryParseException
	 *             if there is a problem while parsing
	 */
	public MonetaryAmount parse(CharSequence text)
			throws MonetaryParseException;

}
