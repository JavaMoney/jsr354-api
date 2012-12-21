package javax.money.format.spi;

import javax.money.format.AmountFormatter;
import javax.money.format.LocalizationStyle;


public interface AmountFormatterFactory {

	AmountFormatter getFormatter(LocalizationStyle style);
	
}
