/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification: JSR-354 Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RoundingQueryBuilderTest {

    @Test
    public void testSetScale() throws Exception {
        RoundingQueryBuilder b = RoundingQueryBuilder.of().setScale(6);
        assertEquals(b.build().getScale(), Integer.valueOf(6));
    }

    @Test
    public void testOf() throws Exception {
        RoundingQueryBuilder b1 = RoundingQueryBuilder.of().setScale(6);
        RoundingQueryBuilder b2 = RoundingQueryBuilder.of(b1.build());
        assertEquals(b1.build().getScale(), Integer.valueOf(6));
        assertEquals(b2.build().getScale(), Integer.valueOf(6));
    }
}