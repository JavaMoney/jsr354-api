/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import javax.money.Money;

import org.junit.Test;

/**
 * @author Werner Keil
 *
 */
public class PercentTest {

	@Test
	public void testOf() {
		Percent perc = Percent.of(BigDecimal.ONE);
		assertNotNull(perc);
	}
	
	
	@Test
	public void testApply() {
		Money m = Money.of("CHF", BigDecimal.valueOf(2.35d));
		assertEquals(Money.of("CHF", BigDecimal.valueOf(0.235d)), Percent.of(BigDecimal.TEN).apply(m));
	}
	
	@Test
	public void testApply10() {
		Money m = Money.of("CHF", 3);
		assertEquals(Money.of("CHF", BigDecimal.valueOf(0.3d)), Percent.of(BigDecimal.TEN).apply(m));
	}

	@Test
	public void testApply20() {
		Money m = Money.of("CHF", 120);
		assertEquals(Money.of("CHF", BigDecimal.valueOf(24d)), Percent.of(BigDecimal.valueOf(20)).apply(m));
	}
	
	@Test
	public void testApply30() {
		Money m = Money.of("CHF", 120);
		assertEquals(Money.of("CHF", BigDecimal.valueOf(36d)), Percent.of(BigDecimal.valueOf(30)).apply(m));
	}
	
	@Test
	public void testToString() {
		assertEquals("15 %",
				Percent.of(BigDecimal.valueOf(15)).toString());
	}
}
