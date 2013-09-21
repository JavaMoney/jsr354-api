package net.java.javamoney.ri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.RoundingMode;
import java.util.Locale;

import javax.money.CurrencyNamespace;
import javax.money.CurrencyUnit;
import javax.money.Displayable;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import javax.money.Money;
import javax.money.MoneyCurrency;
import javax.money.convert.ConversionProvider;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.MonetaryConversions;
import javax.money.ext.MonetaryCurrencies;
import javax.money.ext.Region;
import javax.money.ext.RegionType;
import javax.money.ext.Regions;
import javax.money.ext.RelatedValidityQuery;
import javax.money.ext.Validities;
import javax.money.format.ItemFormat;
import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;
import javax.money.format.MonetaryFormats;
import javax.money.function.MoneyRoundings;
import javax.money.function.predicates.MonetaryPredicates;

import net.java.javamoney.ri.ext.RegionPrinter;
import net.java.javamoney.ri.ext.provider.icu4j.IcuRegion;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmokeTests {
	private static final Logger logger = LoggerFactory
			.getLogger(SmokeTests.class);

	private static final ExchangeRateType EZB_RATE_TYPE = ExchangeRateType
			.of("EZB");
	private static final ExchangeRateType IMF_RATE_TYPE = ExchangeRateType
			.of("IMF");

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = MonetaryCurrencies.get(
				CurrencyNamespace.ISO_NAMESPACE, "CHF");
		MonetaryAmount amount1 = Money.of(currency, 1.0d);
		MonetaryAmount amount2 = Money.of(currency, 1.0d);
		MonetaryAmount amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals("ISO-4217", currency.getNamespace().getId());
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}

	@Test
	public void testCurrencyAccess() {
		// Creating one
		CurrencyUnit currency = MonetaryCurrencies.get(
				CurrencyNamespace.ISO_NAMESPACE, "INR");
		assertNotNull(currency);
		assertEquals("INR", currency.getCurrencyCode());
		assertEquals(CurrencyNamespace.ISO_NAMESPACE, currency.getNamespace());
	}

	@Test
	public void testCreateMoney() {
		// Creating one
		Money amount1 = Money.of("CHF", 1.0d);
		Money amount2 = Money.of("CHF", 1.0d);
		MonetaryAmount amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}

	@Test
	public void testExchangeEZB() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(EZB_RATE_TYPE);
		assertNotNull(prov);
		ExchangeRate rate1 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("EUR"));
		ExchangeRate rate2 = prov.getExchangeRate(MoneyCurrency.of("EUR"),
				MoneyCurrency.of("CHF"));
		ExchangeRate rate3 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("USD"));
		ExchangeRate rate4 = prov.getExchangeRate(MoneyCurrency.of("USD"),
				MoneyCurrency.of("CHF"));
		System.out.println(rate1);
		System.out.println(rate2);
		System.out.println(rate3);
		System.out.println(rate4);
	}

	@Test
	public void testExchangeIMF() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(IMF_RATE_TYPE);
		assertNotNull(prov);
		ExchangeRate rate1 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("EUR"));
		ExchangeRate rate2 = prov.getExchangeRate(MoneyCurrency.of("EUR"),
				MoneyCurrency.of("CHF"));
		ExchangeRate rate3 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("GBP"));
		ExchangeRate rate4 = prov.getExchangeRate(MoneyCurrency.of("USD"),
				MoneyCurrency.of("AED"));
		System.out.println(rate1);
		System.out.println(rate2);
		System.out.println(rate3);
		System.out.println(rate4);
	}

	@Test
	public void testCurrencyConverter() {
		MonetaryOperator rounding = MoneyRoundings.getRounding(2,
				RoundingMode.HALF_UP);

		MonetaryAmount srcCHF = Money.of("CHF", 100.15);
		MonetaryAmount srcUSD = Money.of("USD", 100.15);
		MonetaryAmount srcEUR = Money.of("EUR", 100.15);

		MonetaryAmount tgt = MonetaryConversions
				.getConversionProvider(EZB_RATE_TYPE).getConverter()
				.convert(srcCHF, MoneyCurrency.of("EUR"));
		MonetaryAmount tgt2 = MonetaryConversions
				.getConversionProvider(EZB_RATE_TYPE).getConverter()
				.convert(tgt, MoneyCurrency.of("CHF"));
		assertEquals(srcCHF, tgt2);
		tgt = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter()
				.convert(srcEUR, MoneyCurrency.of("CHF"));
		tgt2 = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter()
				.convert(tgt, MoneyCurrency.of("EUR"));
		assertEquals(srcEUR, tgt2);
		tgt = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter()
				.convert(srcCHF, MoneyCurrency.of("USD"));
		tgt2 = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter()
				.convert(Money.of("CHF", 100.15d), MoneyCurrency.of("USD"));
		assertEquals(tgt, tgt2);
		tgt = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter()
				.convert(srcUSD, MoneyCurrency.of("CHF"));
		tgt2 = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter()
				.convert(Money.of("USD", 100.15d), MoneyCurrency.of("CHF"));
		assertEquals(tgt, tgt2);
	}

	@Test
	public void testGettingFormatters() throws ItemParseException {
		// Using formatters
		CurrencyUnit currency = MonetaryCurrencies.get(
				CurrencyNamespace.ISO_NAMESPACE, "CHF");
		MonetaryAmount amount = Money.of(currency, 1.0d);
		ItemFormat<MonetaryAmount> formatter = MonetaryFormats.getItemFormat(
				MonetaryAmount.class,
				new LocalizationStyle.Builder(CurrencyUnit.class, "CODE")
						.build());
		System.out.println("Formatted amount: "
				+ formatter.format(amount, Locale.GERMANY));
		assertEquals(1.0d, amount.doubleValue(), 0);

		LocalizationStyle.Builder b = new LocalizationStyle.Builder(
				CurrencyUnit.class, "CODE");
		ItemFormat<CurrencyUnit> formatter2 = MonetaryFormats.getItemFormat(
				CurrencyUnit.class, b.build());
		CurrencyUnit cur = formatter2.parse("CHF", Locale.GERMANY);
		assertNotNull(cur);
		assertEquals("CHF", cur.getCurrencyCode());
	}

	@Test
	public void testRegions() throws InterruptedException {
		System.out.println(Regions.getRegionTypes());
		System.out.println(Regions.getRegions(RegionType.CONTINENT));
		Thread.sleep(3000L);
		Region region = Regions.getRegion(RegionType.TERRITORY, "DE");
		System.out.println("Germany="
				+ ((Displayable) region).getDisplayName(Locale.GERMAN));
		System.out.println("Trees=" + Regions.getRegionTreeIds());
		System.out.println("CLDR="
				+ RegionPrinter.getAsText(Regions.getRegionTree("CLDR")));
		System.out.println("ISO="
				+ RegionPrinter.getAsText(Regions.getRegionTree("ISO")));
		System.out.println("ISO3="
				+ RegionPrinter.getAsText(Regions.getRegionTree("ISO3")));
	}

	@Test
	public void testExtendedRegionData() throws InterruptedException {
		Region region = Regions.getRegion(RegionType.TERRITORY, "DE");
		assertNotNull("Extended data available for Germany is missing",
				Regions.getExtendedRegionDataTypes(region));
		assertTrue(
				"Extended data available for Germany not containing IcuRegion.class",
				Regions.getExtendedRegionDataTypes(region).contains(
						IcuRegion.class));
		assertNotNull("Extended ICU data for Germany null.",
				Regions.getExtendedRegionData(region, IcuRegion.class));
	}

	@Test
	public void testCurrencyRegionValidities() throws InterruptedException {
		System.out.println("Validity providers: "
				+ Validities.getValidityProviderIds(CurrencyUnit.class));
		System.out.println("Related Validity providers: "
				+ Validities.getRelatedValidityProviderIds(CurrencyUnit.class,
						Region.class));
		System.out
				.println("Currencies for Germany: "
						+ Validities
								.getRelatedValidityInfo(new RelatedValidityQuery.Builder()
										.withItemType(CurrencyUnit.class)
										.withRelatedToType(Region.class)
										.withRelatedToPredicate(
												MonetaryPredicates
														.include(Regions
																.getRegion(Locale.GERMANY))
										).build()));
	}
}
