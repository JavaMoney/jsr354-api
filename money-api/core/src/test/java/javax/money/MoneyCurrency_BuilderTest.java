/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Enumeration;

import org.junit.Test;

/**
 * Tests for {@link MoneyCurrency.Builder}
 * 
 * @author Anatole Tresch
 */
public class MoneyCurrency_BuilderTest {

	/**
	 * Test method for {@link javax.money.MoneyCurrency.Builder#Builder()}.
	 */
	@Test
	public void testBuilder() {
		new MoneyCurrency.Builder();
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#Builder(java.lang.String)}.
	 */
	@Test
	public void testBuilderString() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder("test");
		assertEquals("test", builder.getCurrencyCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#Builder(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBuilderString_BadCase() {
		new MoneyCurrency.Builder(null);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#Builder(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testBuilderStringString() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder("ns", "test");
		assertEquals("test", builder.getCurrencyCode());
		assertEquals("ns", builder.getNamespace());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setNamespace(java.lang.String)}.
	 */
	@Test
	public void testGetSetNamespace() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setNamespace("ns1");
		assertEquals("ns1", builder.getNamespace());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setCurrencyCode(java.lang.String)}
	 * and {@link javax.money.MoneyCurrency.Builder#getCurrencyCode()} .
	 */
	@Test
	public void testGetSetCurrencyCode() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setCurrencyCode("code1");
		assertEquals("code1", builder.getCurrencyCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setDefaultFractionDigits(int)}
	 * and {@link javax.money.MoneyCurrency.Builder#getDefaultFractionDigits()}.
	 */
	@Test
	public void testGetSetDefaultFractionDigits() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setDefaultFractionDigits(10);
		assertEquals(10, builder.getDefaultFractionDigits());
		builder.setDefaultFractionDigits(-1);
		assertEquals(-1, builder.getDefaultFractionDigits());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setDefaultFractionDigits(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSetDefaultFractionDigits_InvalidInput() {
		new MoneyCurrency.Builder().setDefaultFractionDigits(-10);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setNumericCode(int)} and
	 * {@link javax.money.MoneyCurrency.Builder#getNumericCode()}.
	 */
	@Test
	public void testGetSetNumericCode() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setNumericCode(10);
		assertEquals(10, builder.getNumericCode());
		builder.setNumericCode(-1);
		assertEquals(-1, builder.getNumericCode());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setNumericCode(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSetNumericCode_InvalidInput() {
		new MoneyCurrency.Builder().setNumericCode(-10);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setValidFrom(java.lang.Long)}
	 * and {@link javax.money.MoneyCurrency.Builder#getValidFrom()}.
	 */
	@Test
	public void testGetSetValidFrom() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setValidFrom(Long.valueOf(10));
		assertEquals(Long.valueOf(10), builder.getValidFrom());
		builder.setValidFrom(null);
		assertNull(builder.getValidFrom());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setValidUntil(java.lang.Long)}.
	 */
	@Test
	public void testSetValidUntil() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setValidUntil(Long.valueOf(10));
		assertEquals(Long.valueOf(10), builder.getValidUntil());
		builder.setValidUntil(null);
		assertNull(builder.getValidUntil());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setLegalTender(boolean)} and
	 * {@link javax.money.MoneyCurrency.Builder#hasLegalTender()}.
	 */
	@Test
	public void testGetSetLegalTender() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setLegalTender(true);
		assertTrue(builder.isLegalTender());
		builder.setLegalTender(false);
		assertFalse(builder.isLegalTender());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setVirtual(boolean)} and
	 * {@link javax.money.MoneyCurrency.Builder#isVirtual()}.
	 */
	@Test
	public void testGetSetVirtual() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setVirtual(true);
		assertTrue(builder.isVirtual());
		builder.setVirtual(false);
		assertFalse(builder.isVirtual());
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#setAttribute(java.lang.String, java.lang.Object)}
	 * and
	 * {@link javax.money.MoneyCurrency.Builder#getAttribute(java.lang.String, java.lang.Class)}
	 * . .
	 */
	@Test
	public void testGetSetAttribute() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setAttribute("testGetSetAttribute", Boolean.TRUE);
		assertNotNull(builder
				.getAttribute("testGetSetAttribute", Boolean.class));
		assertNotNull(builder.getAttribute("testGetSetAttribute", Object.class));
		assertTrue(builder.getAttribute("testGetSetAttribute", Boolean.class) == builder
				.getAttribute("testGetSetAttribute", Object.class));
		assertTrue(builder.getAttribute("testGetSetAttribute", Boolean.class) == Boolean.TRUE);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#removeAttribute(java.lang.String)}
	 * .
	 */
	@Test
	public void testRemoveAttribute() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setAttribute("testRemoveAttribute", Boolean.TRUE);
		assertNotNull(builder
				.getAttribute("testRemoveAttribute", Boolean.class));
		builder.removeAttribute("test2");
		assertNotNull(builder.removeAttribute("testRemoveAttribute"));
		assertNull(builder.getAttribute("testRemoveAttribute", Boolean.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#getAttributeKeys()}.
	 */
	@Test
	public void testGetAttributeKeys() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder();
		Enumeration<String> keys = builder.getAttributeKeys();
		assertNotNull(keys);
		builder.setAttribute("attr1", Boolean.TRUE).setAttribute("attr2",
				"attr2Value");
		keys = builder.getAttributeKeys();
		assertNotNull(keys);
		assertTrue(keys.hasMoreElements());
		boolean a1Found = false;
		boolean a2Found = false;
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if ("attr1".equals(key)) {
				a1Found = true;
			} else if ("attr2".equals(key)) {
				a2Found = true;
			}
		}
		assertTrue(a1Found);
		assertTrue(a2Found);
	}

	/**
	 * Test method for
	 * {@link javax.money.MoneyCurrency.Builder#getAttributeType(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetAttributeType() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder();
		builder.setAttribute("attr1", Boolean.TRUE).setAttribute("attr2",
				"attr2Value");
		assertNotNull(builder.getAttributeType("attr1"));
		assertNotNull(builder.getAttributeType("attr2"));
		assertNull(builder.getAttributeType("attr3"));
		assertTrue(builder.getAttributeType("attr1") == Boolean.class);
		assertTrue(builder.getAttributeType("attr2") == String.class);
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency.Builder#getValidUntil()}
	 * .
	 */
	@Test
	public void testGetSetValidUntil() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setValidUntil(Long.valueOf(10));
		assertEquals(Long.valueOf(10), builder.getValidUntil());
		builder.setValidUntil(null);
		assertNull(builder.getValidUntil());
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency.Builder#isBuildable()}.
	 */
	@Test
	public void testIsBuildable() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder();
		assertFalse(builder.isBuildable());
		builder.setAttribute("attr1", Boolean.TRUE).setAttribute("attr2",
				"attr2Value");
		assertFalse(builder.isBuildable());
		builder.setNamespace("ns");
		assertFalse(builder.isBuildable());
		builder.setCurrencyCode("cd");
		assertTrue(builder.isBuildable());
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency.Builder#build()}.
	 */
	@Test
	public void testBuild() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setAttribute("attr1", Boolean.TRUE)
				.setAttribute("attr2", "attr2Value").setNamespace("ns")
				.setCurrencyCode("cd").setLegalTender(true).setVirtual(false)
				.setDefaultFractionDigits(101).setNumericCode(7)
				.setValidFrom(10L).setValidUntil(100L);
		CurrencyUnit cu = builder.build();
		assertEquals("ns", cu.getNamespace());
		assertEquals("cd", cu.getCurrencyCode());
		assertEquals(101, cu.getDefaultFractionDigits());
		assertEquals(7, cu.getNumericCode());
		assertEquals(Long.valueOf(10L), cu.getValidFrom());
		assertEquals(Long.valueOf(100L), cu.getValidUntil());
		assertTrue(cu.isLegalTender());
		assertFalse(cu.isVirtual());
	}

	/**
	 * Test method for {@link javax.money.MoneyCurrency.Builder#build(boolean)}.
	 */
	@Test
	public void testBuildBoolean() {
		MoneyCurrency.Builder builder = new MoneyCurrency.Builder()
				.setAttribute("attr1", Boolean.TRUE)
				.setAttribute("attr2", "attr2Value")
				.setNamespace("testBuildBoolean").setCurrencyCode("cd")
				.setLegalTender(true).setVirtual(false)
				.setDefaultFractionDigits(101).setNumericCode(7)
				.setValidFrom(10L).setValidUntil(100L);
		CurrencyUnit cu = builder.build(true);
		assertEquals("testBuildBoolean", cu.getNamespace());
		assertEquals("cd", cu.getCurrencyCode());
		assertEquals(101, cu.getDefaultFractionDigits());
		assertEquals(7, cu.getNumericCode());
		assertEquals(Long.valueOf(10L), cu.getValidFrom());
		assertEquals(Long.valueOf(100L), cu.getValidUntil());
		assertTrue(cu.isLegalTender());
		assertFalse(cu.isVirtual());
		CurrencyUnit cu2 = MoneyCurrency.getInstance("testBuildBoolean", "cd");
		assertTrue(cu2 != cu);
		builder.setValidFrom(System.currentTimeMillis());
		builder.setValidUntil(null);
		cu = builder.build(true);
		cu2 = MoneyCurrency.getInstance("testBuildBoolean", "cd");
		assertTrue(cu2 == cu);
	}

}
