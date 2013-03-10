package net.java.javamoney.ri.ext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.money.ext.RegionType;

import org.junit.Test;

public class RegionTypeImplTest {

	@Test
	public void testHashCode() {
		RegionType t1 = RegionTypeImpl.of("TypeA");
		RegionType t2 = RegionTypeImpl.of("TypeB");
		assertNotSame(t1.hashCode(), t2.hashCode());
	}

	@Test
	public void testOf() {
		RegionType t1 = RegionTypeImpl.of("");
		RegionType t2 = RegionTypeImpl.of("1");
		RegionType t3 = RegionTypeImpl.of("1");
		RegionType t4 = RegionTypeImpl.of("2");
		assertTrue(t2 == t3);
		assertFalse(t1 == t2);
		assertFalse(t3 == t4);
	}

	@Test
	public void testGetTypes() {
		RegionTypeImpl.of("");
		RegionTypeImpl.of("1");
		RegionTypeImpl.of("1");
		RegionTypeImpl.of("2");
		Enumeration<RegionType> types = RegionTypeImpl.getTypes();
		int count = 0;
		Set<String> ids = new HashSet<String>();
		while (types.hasMoreElements()) {
			RegionType type = (RegionType) types.nextElement();
			ids.add(type.getId());
			count++;
		}
		assertTrue(ids.contains(""));
		assertTrue(ids.contains("1"));
		assertTrue(ids.contains("2"));
		assertFalse(ids.contains("3"));
	}

	@Test
	public void testSingletonRegionType() {
		new RegionTypeImpl("abc");
	}

	@Test
	public void testGetId() {
		RegionTypeImpl t = new RegionTypeImpl("abc");
		"abc".equals(t.getId());
	}

	@Test
	public void testEqualsObject() {
		RegionType t1 = new RegionTypeImpl("1");
		RegionType t2 = RegionTypeImpl.of("2");
		assertNotSame(t1, t2);
		t1 = new RegionTypeImpl("2");
		assertEquals(t1, t2);
	}

	@Test
	public void testToString() {
		RegionType t1 = new RegionTypeImpl("idid");
		String s = t1.toString();
		assertEquals("RegionType [id=idid, class=net.java.javamoney.ri.ext.RegionTypeImpl]", s);
	}

	@Test
	public void testCompareTo() {
		RegionType t1 = new RegionTypeImpl("aa");
		RegionType t2 = RegionTypeImpl.of("aa");
		assertTrue(((Comparable<RegionType>)t1).compareTo(t2)==0);
		assertTrue(((Comparable<RegionType>)t2).compareTo(t1)==0);
	}
}
