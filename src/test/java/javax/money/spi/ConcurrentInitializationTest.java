/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.money.DummyAmount;
import javax.money.DummyAmountBuilder;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Test concurrent initialization/bootstrapping of {@link javax.money.spi.Bootstrap}.
 */
public class ConcurrentInitializationTest {

    static class Round implements Runnable {
        private final MonetaryAmount amount;

        Round(final MonetaryAmount amount) {
            this.amount = amount;
        }

        @Override
        public void run() {
            Monetary.getDefaultRounding().apply(amount);
        }
    }

    private static final int THREAD_COUNT = 100;

    /**
     * https://github.com/JavaMoney/jsr354-ri/issues/30.
     */
    @Test
    public void shouldSupportConcurrentInitialization() throws InterruptedException {
        final DummyAmount amount = new DummyAmountBuilder().create();

        final List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        final List<Throwable> throwables = Collections.synchronizedList(new ArrayList<>());
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) -> throwables.add(e);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final Thread thread = new Thread(new Round(amount));
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        throwables.forEach(java.lang.Throwable::printStackTrace);
        assertEquals(0, throwables.size());

    }
}
