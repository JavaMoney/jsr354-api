package javax.money.format.spi;

import javax.money.format.AmountParser;
import javax.money.format.LocalizationStyle;

public interface AmountParserFactory {

	AmountParser getParser(LocalizationStyle style);

}
