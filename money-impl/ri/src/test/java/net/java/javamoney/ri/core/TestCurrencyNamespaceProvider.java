package net.java.javamoney.ri.core;

import java.util.Arrays;
import java.util.Collection;

public class TestCurrencyNamespaceProvider implements
		javax.money.spi.CurrencyNamespaceProviderSpi {

	@Override
	public Collection<String> getNamespaces() {
		return Arrays.asList(new String[] { "Test" ,"ns", "ns1", "testBuildBoolean"});
	}

}
