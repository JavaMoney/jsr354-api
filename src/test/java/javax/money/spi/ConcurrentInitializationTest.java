package javax.money.spi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.money.CurrencyUnit;
import javax.money.DummyAmount;
import javax.money.MonetaryAmount;
import javax.money.MonetaryRoundings;

import org.junit.Test;

public class ConcurrentInitializationTest {

    static class Round implements Runnable {
        private final MonetaryAmount amount;

        Round(final MonetaryAmount amount) {
            this.amount = amount;
        }

        @Override
        public void run() {
            MonetaryRoundings.getRounding().apply(amount);
        }
    }

    private static final int THREAD_COUNT = 100;

    /**
     * https://github.com/JavaMoney/jsr354-ri/issues/30.
     */
    @Test
    public void shouldSupportConcurrentInitialization() throws ExecutionException, InterruptedException {
        final DummyAmount amount = new DummyAmount();

        final List<Thread> threads = new ArrayList<>(THREAD_COUNT);
        final List<Throwable> throwables = Collections.synchronizedList(new ArrayList<Throwable>());
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                throwables.add(e);
            }
        };

        for (int i = 0; i < THREAD_COUNT; i++) {
            final Thread thread = new Thread(new Round(amount));
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (Throwable throwable : throwables) {
            throwable.printStackTrace();
        }

        assertEquals(0, throwables.size());

    }
}
