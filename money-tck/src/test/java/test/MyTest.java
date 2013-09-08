package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Test;



/**
 * Unit test for simple App.
 */
@SpecVersion(spec = "spectests", version = "1.0.0")
public class MyTest {

    @Test
    @SpecAssertion(section = "2", id = "a")
    public void simpleTestForAssertion() {
        MySample app = new MySample();
        assertEquals(app.sayHello("Markus"), "Hello Markus");
    }

    @Test
    @SpecAssertion(section = "3", id = "a")
    public void unmatchedAssertion() {
        assertTrue(true);
    }

    @Test
    @SpecAssertion(section = "2.1", id = "a")
    public void anotherSimpleTestForAssertion() {
        assertTrue(true);
    }

    @Test
    @SpecAssertion(section = "2.2", id = "a")
    public void lastSimpleTestForAssertion() {
        assertTrue(true);
    }

   
}
