package org.javamoney.format.util;

import junit.framework.Assert;


import org.javamoney.format.tokens.StringGrouper;
import org.junit.Test;

public class StringGrouperTest {

	@Test
	public void testSize1() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 1 });
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' },
				new int[] { 1, 1, 1 });
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' }, new int[] { 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
	}

	@Test
	public void testSize1223() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 1, 2, 2, 3 });
		Assert.assertEquals("1'234'56'78'9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' }, new int[] { 1, 2, 2,
				3, 3, 3, 3, 3 });
		Assert.assertEquals("1'234'56'78'9", token.group("123456789"));
	}

	@Test
	public void testSize223() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 2, 2, 3 });
		Assert.assertEquals("12'345'67'89", token.group("123456789"));
	}

	@Test
	public void testSize4212() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 4, 2, 1, 2 });
		Assert.assertEquals("12'3'45'6789", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' }, new int[] { 4, 2, 1,
				2, 1 });
		Assert.assertEquals("12'3'45'6789", token.group("123456789"));
	}
	
	@Test
	public void testChars1() {
		StringGrouper token = new StringGrouper(new char[] { '=' },
				new int[] { 2 });
		Assert.assertEquals("1=23=45=67=89", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' },
				new int[] { 1 });
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
	}
	
	@Test
	public void testChars2() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 1 });
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
		token = new StringGrouper(new char[] { '-','-',',','-','-','-','-','-','-','-','-' },
				new int[] { 1 });
		Assert.assertEquals("1-2-3-4-5-6,7-8-9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'','x',',' },
				new int[] { 1 });
		Assert.assertEquals("1,2,3,4,5,6,7x8'9", token.group("123456789"));
	}
	
	@Test
	public void testMixedAll() {
		StringGrouper token = new StringGrouper(new char[] { '\'', '-', '=','-','@' },
				new int[] { 1, 3, 2, 1, 2});
		Assert.assertEquals("12@34@56@78@90-1=23-456'7", token.group("12345678901234567"));
	}

	@Test
	public void testSize1Reverse() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 1 }).setReverse(true);
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' },
				new int[] { 1, 1, 1 }).setReverse(true);
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' }, new int[] { 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }).setReverse(true);
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
	}

	@Test
	public void testSize1223Reverse() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 1, 2, 2, 3 }).setReverse(true);
		Assert.assertEquals("1'23'45'678'9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' }, new int[] { 1, 2, 2,
				3, 3, 3, 3, 3 }).setReverse(true);
		Assert.assertEquals("1'23'45'678'9", token.group("123456789"));
	}

	@Test
	public void testSize223Reverse() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 2, 2, 3 }).setReverse(true);
		Assert.assertEquals("12'34'567'89", token.group("123456789"));
	}

	@Test
	public void testSize4212Reverse() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 4, 2, 1, 2 }).setReverse(true);
		Assert.assertEquals("1234'56'7'89", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' }, new int[] { 4, 2, 1,
				2, 1 }).setReverse(true);
		Assert.assertEquals("1234'56'7'89", token.group("123456789"));
	}
	
	@Test
	public void testChars1Reverse() {
		StringGrouper token = new StringGrouper(new char[] { '=' },
				new int[] { 2 }).setReverse(true);
		Assert.assertEquals("12=34=56=78=9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'' },
				new int[] { 1 }).setReverse(true);
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
	}
	
	@Test
	public void testChars2Reverse() {
		StringGrouper token = new StringGrouper(new char[] { '\'' },
				new int[] { 1 }).setReverse(true);
		Assert.assertEquals("1'2'3'4'5'6'7'8'9", token.group("123456789"));
		token = new StringGrouper(new char[] { '-','-',',','-','-','-','-','-','-','-','-' },
				new int[] { 1 }).setReverse(true);
		Assert.assertEquals("1-2-3,4-5-6-7-8-9", token.group("123456789"));
		token = new StringGrouper(new char[] { '\'','x',',' },
				new int[] { 1 }).setReverse(true);
		Assert.assertEquals("1'2x3,4,5,6,7,8,9", token.group("123456789"));
	}
	
	@Test
	public void testMixedAllReverse() {
		StringGrouper token = new StringGrouper(new char[] { '\'', '-', '=','-','@' },
				new int[] { 1, 3, 2, 1, 2}).setReverse(true);
		Assert.assertEquals("1'234-56=7-89@01@23@45@67", token.group("12345678901234567"));
	}
	
}
