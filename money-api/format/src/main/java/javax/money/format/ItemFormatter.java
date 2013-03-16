/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 */
package javax.money.format;

import java.io.IOException;
import java.util.Locale;

/**
 * Formats instances of T to a {@link String} or an {@link Appendable}.
 */
public interface ItemFormatter<T> {

	/**
	 * Return the target type this {@link ItemFormatter} is expecting.
	 * 
	 * @return the target type, never {@code null}.
	 */
	public Class<T> getTargetClass();

	/**
	 * Access the {@link LocalizationStyle} attached to this
	 * {@link ItemFormatter}, which also contains the target {@link Locale}
	 * instances to be used, as well as other attributes configuring this
	 * formatter instance.
	 * 
	 * @return Returns the {@link LocalizationStyle} attached to this
	 *         {@link ItemFormatter}, never {@code null}.
	 */
	public LocalizationStyle getStyle();

	/**
	 * Formats a value of T to a {@code String}. This method uses a
	 * {@link LocalizationStyle} as an input parameter. Styles allows to define
	 * detailed and customized formatting input parameters. This allows to
	 * implement also complex formatting requirements using this interface.
	 * 
	 * @param item
	 *            the item to print, not null
	 * @return the string printed using the settings of this formatter
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 */
	public String format(T item);

	/**
	 * Prints a item value to an {@code Appendable}.
	 * <p>
	 * Example implementations of {@code Appendable} are {@code StringBuilder},
	 * {@code StringBuffer} or {@code Writer}. Note that {@code StringBuilder}
	 * and {@code StringBuffer} never throw an {@code IOException}.
	 * 
	 * @param appendable
	 *            the appendable to add to, not null
	 * @param item
	 *            the item to print, not null
	 * @throws UnsupportedOperationException
	 *             if the formatter is unable to print
	 * @throws ItemFormatException
	 *             if there is a problem while printing
	 * @throws IOException
	 *             if an IO error occurs
	 */
	public void print(Appendable appendable, T item) throws IOException;

}
