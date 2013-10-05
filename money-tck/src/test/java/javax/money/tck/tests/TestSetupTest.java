package javax.money.tck.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.money.tck.TCKTestSetup;

import org.jboss.test.audit.annotations.SpecAssertion;
import org.junit.Ignore;
import org.junit.Test;

public class TestSetupTest {

	@SpecAssertion(
		section = "3.1.2",
		id = "EnsureSetup",
		note = "Asserts the basic test setup is working.")
	@Test
	public void testTestSetup() {
		assertTrue("TCK Configuration not available.",
				TCKTestSetup.getTestConfiguration() != null);
		assertNotNull(TCKTestSetup.getTestConfiguration());
	}

	@SpecAssertion(
		section = "3.1.2",
		id = "EnsureSetup",
		note = "Asserts the basic implementation packages are registered.")
	@Test
	public void testExceptionClassesSetup() {
		assertTrue(
				"Implementation Packages not registered.",
				TCKTestSetup.getTestConfiguration().getExceptionClasses() != null);
		assertFalse("Implementation Packages not registered.",
				TCKTestSetup.getTestConfiguration().getExceptionClasses()
						.isEmpty());
	}

	@SpecAssertion(
		section = "3.1.2",
		id = "EnsureSetup",
		note = "Asserts the basic amount classes are registered.")
	@Test
	public void testAmountTestSetup() {
		assertNotNull(TCKTestSetup.getTestConfiguration().getAmountClasses());
		assertFalse(TCKTestSetup.getTestConfiguration().getAmountClasses()
				.isEmpty());
	}

	@SpecAssertion(
		section = "3.1.2",
		id = "EnsureSetup",
		note = "Asserts the basic currency classes are registered.")
	@Test
	public void testCurrencyTestSetup() {

		assertNotNull(TCKTestSetup.getTestConfiguration().getCurrencyClasses());
		assertFalse(TCKTestSetup.getTestConfiguration().getCurrencyClasses()
				.isEmpty());
	}

	@SpecAssertion(
		section = "3.1.2",
		id = "EnsureSetup",
		note = "Asserts the basic adjuster classes are registered.")
	@Test
	@Ignore("why is this not working?")
	public void testAdjusterTestSetup() {

		assertNotNull(TCKTestSetup.getTestConfiguration().getAdjusters());
		assertFalse(TCKTestSetup.getTestConfiguration().getAdjusters()
				.isEmpty());
	}

	@SpecAssertion(
		section = "3.1.2",
		id = "EnsureSetup",
		note = "Asserts the basic currency classes are registered.")
	@Test
	@Ignore("why is this not working?")
	public void testQueryTestSetup() {
		assertNotNull(TCKTestSetup.getTestConfiguration().getQueries());
		assertFalse(TCKTestSetup.getTestConfiguration().getQueries().isEmpty());
	}
}
