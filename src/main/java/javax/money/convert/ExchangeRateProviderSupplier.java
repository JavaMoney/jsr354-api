package javax.money.convert;

import java.util.function.Supplier;

/**
 * The supplier of {@link ExchangeRateProvider} that defines an implementations.
 * @author otaviojava
 */
public interface ExchangeRateProviderSupplier extends Supplier<String> {

	/**
	 * @return description of the implementation of {@link ExchangeRateProvider}
	 */
	String getDescription();
}
