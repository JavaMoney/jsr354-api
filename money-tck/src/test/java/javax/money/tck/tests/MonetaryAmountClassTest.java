package javax.money.tck.tests;

import static org.junit.Assert.assertTrue;

import javax.money.MonetaryAdjuster;
import javax.money.MonetaryAmount;
import javax.money.tck.TCKTestSetup;
import javax.money.tck.util.ClassTester;

import org.jboss.test.audit.annotations.SpecAssertion;
import org.junit.Test;

public class MonetaryAmountClassTest {

	@SpecAssertion(
		section = "3.1.1",
		id = "EnsureAmount",
		note = "Asserts at least one MonetaryAmount implementation class is registered for testing.")
	@Test
	public void testSetup() {
		assertTrue("TCK Configuration not available.",
				TCKTestSetup.getTestConfiguration() != null);
		assertTrue(TCKTestSetup.getTestConfiguration().getAmountClasses()
				.size() > 0);
	}

	@Test
	public void testAmountClasses() {
		for (Class type : TCKTestSetup.getTestConfiguration()
				.getAmountClasses()) {
			testAmountClass(type);
		}
	}

	public void testAmountClass(Class type) {
		ClassTester.testImplementsInterface(type, MonetaryAmount.class);
		ClassTester.testImmutable(type);
		ClassTester.testSerializable(type);
		ClassTester.testComparable(type);
		ClassTester.testHasPublicMethod(type, type,
				"add", MonetaryAmount.class);
		ClassTester.testHasNotPublicMethod(type, type,
				"add", Number.class);
		ClassTester.testHasPublicMethod(type, type,
				"subtract", MonetaryAmount.class);
		ClassTester.testHasNotPublicMethod(type, type,
				"subtract", Number.class);
		ClassTester.testHasNotPublicMethod(type, byte.class,
				"byteValue");
		ClassTester.testHasNotPublicMethod(type, Byte.class,
				"byteValue");
		ClassTester.testHasNotPublicMethod(type, int.class,
				"intValue");
		ClassTester.testHasNotPublicMethod(type, Integer.class,
				"intValue");
		ClassTester.testHasNotPublicMethod(type, int.class,
				"intValueExact");
		ClassTester.testHasNotPublicMethod(type, Integer.class,
				"intValueExact");
		ClassTester.testHasNotPublicMethod(type, short.class,
				"shortValue");
		ClassTester.testHasNotPublicMethod(type, Short.class,
				"shortValue");
		ClassTester.testHasNotPublicMethod(type, short.class,
				"shortValueExact");
		ClassTester.testHasNotPublicMethod(type, Short.class,
				"shortValueExact");
		ClassTester.testHasNotPublicMethod(type, float.class,
				"floatValue");
		ClassTester.testHasNotPublicMethod(type, Float.class,
				"floatValue");
		ClassTester.testHasPublicMethod(type, double.class,
				"doubleValue");
		ClassTester.testHasPublicMethod(type, long.class,
				"longValue");
		ClassTester.testHasPublicMethod(type, long.class,
				"longValueExact");
		ClassTester.testHasPublicMethod(type, Number.class,
				"asNumber");
		// ClassTester.testHasPublicMethod(type, Number.class,
		// "asType");
		// ClassTester.testHasPublicMethod(type, Number.class,
		// "asType", Class.class);
		// ClassTester.testHasPublicMethod(type, Object.class,
		// "asType", Class.class, MonetaryAdjuster.class);

		ClassTester.testHasPublicMethod(type, type,
				"abs");
		// ClassTester.testHasPublicStaticMethod(type, type,
		// "from", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, type,
				"divide", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, type,
				"divide", Number.class);
		// ClassTester.testHasPublicMethod(type, MonetaryAmount[].class,
		// "divideAndRemainder", MonetaryAmount.class);
		// ClassTester.testHasPublicMethod(type, type,
		// "divideAndRemainder", Number.class);
		ClassTester.testHasPublicMethod(type, type,
				"divideToIntegralValue", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, type,
				"divideToIntegralValue", Number.class);
		ClassTester.testHasPublicMethod(type, Class.class,
				"getNumberType");
		ClassTester.testHasPublicMethod(type, int.class,
				"getPrecision");
		ClassTester.testHasPublicMethod(type, int.class,
				"getScale");
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isEqualTo", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isGreaterThan", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isGreaterThanOrEqualTo", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isLessThan", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isLessThanOrEqualTo", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isNegative");
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isNegativeOrZero");

		ClassTester.testHasPublicMethod(type, boolean.class,
				"isNotEqualTo", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isPositive");
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isPositiveOrZero");
		ClassTester.testHasPublicMethod(type, boolean.class,
				"isZero");
		ClassTester.testHasPublicMethod(type, type,
				"multiply", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, type,
				"multiply", Number.class);
		ClassTester.testHasPublicMethod(type, type,
				"negate");
		ClassTester.testHasPublicMethod(type, type,
				"plus");
		ClassTester.testHasPublicMethod(type, type,
				"pow", int.class);
		ClassTester.testHasPublicMethod(type, type,
				"remainder", MonetaryAmount.class);
		ClassTester.testHasPublicMethod(type, type,
				"remainder", Number.class);
		ClassTester.testHasPublicMethod(type, type,
				"scaleByPowerOfTen", int.class);
		ClassTester.testHasPublicMethod(type, int.class,
				"signum");
		ClassTester.testHasPublicMethod(type, String.class,
				"toEngineeringString");
		ClassTester.testHasPublicMethod(type, String.class,
				"toPlainString");
		ClassTester.testHasPublicMethod(type, type,
				"ulp");
		ClassTester.testHasPublicMethod(type, type,
				"with", Number.class);
		ClassTester.testHasPublicMethod(type, type,
				"with", MonetaryAdjuster.class);

		MonetaryAmount amt = (MonetaryAmount) TCKTestSetup
				.getTestConfiguration().create(type,
						"CHF", Double.valueOf(1.50d));
		ClassTester.testSerializable(amt);
	}

}
