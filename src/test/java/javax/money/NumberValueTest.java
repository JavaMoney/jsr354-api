package javax.money;

import org.testng.annotations.Test;

import javax.money.convert.TestNumberValue;

import static org.testng.Assert.*;

public class NumberValueTest {

    @Test
    public void testCompareTo() throws Exception {
        NumberValue nv1a = new TestNumberValue(1);
        NumberValue nv2 = new TestNumberValue(2);
        NumberValue nv1b = new TestNumberValue(1);

        assertEquals(nv1a.compareTo(nv2), -1);
        assertEquals(nv2.compareTo(nv1a), 1);
        assertEquals(nv1a.compareTo(nv1b), 0);
        assertEquals(nv2.compareTo(nv2), 0);
    }
}