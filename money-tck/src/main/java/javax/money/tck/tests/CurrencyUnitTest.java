package javax.money.tck.tests;

import java.lang.reflect.InvocationTargetException;
import java.util.Currency;

import javax.money.CurrencyUnit;
import javax.money.tck.TCKTestSetup;
import javax.money.tck.util.ClassTester;

import org.junit.Test;

public class CurrencyUnitTest {

	@Test
	public void testCurrencyClasses() {
		for (Class type : TCKTestSetup.getTestConfiguration()
				.getCurrencyClasses()) {
			testCurrencyClass(type);
		}
	}

	public void testCurrencyClass(Class type) {
		ClassTester.testImplementsInterface(type, CurrencyUnit.class);
		ClassTester.testImmutable(type);
		ClassTester.testSerializable(type);
		ClassTester.testComparable(type);
		ClassTester.testHasPublicMethod(type, int.class,
				"getDefaultFractionDigits");
		ClassTester.testHasPublicMethod(type, int.class, "getCashRounding");
		ClassTester.testHasPublicMethod(type, int.class, "getNumericCode");
		
		ClassTester.testHasPublicMethod(type, boolean.class, "equals", Object.class);
		ClassTester.testHasPublicMethod(type, int.class, "hashCode");
		
		CurrencyUnit unit = TCKTestSetup.getTestConfiguration().create(type,
				"CHF");
		ClassTester.testSerializable(unit);
	}

	@Test
	public void testISOCurrencies() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Class type : TCKTestSetup.getTestConfiguration()
				.getCurrencyClasses()) {
			for (Currency cur : Currency.getAvailableCurrencies()) {
				CurrencyUnit unit = TCKTestSetup.getTestConfiguration()
						.create(type, cur.getCurrencyCode());
				assertEquals(cur.getCurrencyCode(), unit.getCurrencyCode());
				ClassTester.assertValue(cur.getNumericCode(), "getNumericCode", unit);
				ClassTester.assertValue(cur.getDefaultFractionDigits(), "getDefaultFractionDigits", unit);
				ClassTester.assertValue(-1, "getCashRounding", unit);
			}
		}
	}

	private void assertEquals(String currencyCode, String currencyCode2) {
		// TODO Auto-generated method stub
		
	}
}
