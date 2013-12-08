package javax.money;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class MonetaryAmountsTest {

	@Test
	public void testGetAmountDouble() {
		MonetaryAmount amt = MonetaryAmounts.getAmount("test1", 10.0d);
		assertNotNull(amt);
	}
	
	@Test
	public void testGetAmountLong() {
		MonetaryAmount amt = MonetaryAmounts.getAmount("test1", 10L);
		assertNotNull(amt);
	}
	
	@Test
	public void testGetAmountNumber() {
		MonetaryAmount amt = MonetaryAmounts.getAmount("test1", Integer.valueOf(10));
		assertNotNull(amt);
	}
	
	@Test
	public void testGetAmountDoubleMonetaryContext() {
		MonetaryAmount amt = MonetaryAmounts.getAmount("test1", 10.0d, new MonetaryContext.Builder(BigDecimal.class).build());
		assertNotNull(amt);
	}
	
	@Test
	public void testGetAmountLongMonetaryContext() {
		MonetaryAmount amt = MonetaryAmounts.getAmount("test1", 10L, new MonetaryContext.Builder(BigDecimal.class).build());
		assertNotNull(amt);
	}
	
	@Test
	public void testGetAmountNumberMonetaryContext() {
		MonetaryAmount amt = MonetaryAmounts.getAmount("test1", Integer.valueOf(10), new MonetaryContext.Builder(BigDecimal.class).build());
		assertNotNull(amt);
	}

}
