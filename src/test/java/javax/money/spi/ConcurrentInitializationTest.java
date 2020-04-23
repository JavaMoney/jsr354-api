/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
