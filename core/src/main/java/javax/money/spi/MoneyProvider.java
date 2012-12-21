package javax.money.spi;

import javax.money.CurrencyUnit;
import javax.money.Amount;

/**
 * The Money provider models the SPI interface that allows to exchange the Java {@link Amount}
 * implementation for different platforms or usage scenarios. 
 * 
 * @TODO Discussion: should caching features implemented by the SPI, or by the Spec Part
 * 
 * @author Anatole Tresch
 */
public interface MoneyProvider {

	Amount create(CurrencyUnit currency, Number number);

	Amount create(CurrencyUnit currency, byte value);

	Amount create(CurrencyUnit currency, short value);

	Amount create(CurrencyUnit currency, int value);

	Amount create(CurrencyUnit currency, float value);

	Amount create(CurrencyUnit currency, double value);

}
