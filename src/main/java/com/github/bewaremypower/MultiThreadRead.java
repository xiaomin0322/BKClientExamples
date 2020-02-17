package com.github.bewaremypower;

import com.github.bewaremypower.config.DefaultConfig;
import com.github.bewaremypower.util.ExceptionUtil;
import org.apache.bookkeeper.client.BKException;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerEntry;
import org.apache.bookkeeper.client.LedgerHandle;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadRead {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.err.println("Arguments must be: LedgerId [ThreadNum=8]");
            return;
        }

        final long ledgerId = Long.parseLong(args[0]);
        final int threadNum = (args.length > 1) ? Integer.parseInt(args[1]) : 8;

        try (BookKeeper bookKeeper = DefaultConfig.newBookKeeper()) {
            final ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
            final CountDownLatch threadsDone = new CountDownLatch(threadNum);

            for (int i = 0; i < threadNum; i++) {
                final String threadName = "Thread " + i;
                threadPool.execute(() -> {
                    try (LedgerHandle ledgerHandle = DefaultConfig.openLedger(bookKeeper, ledgerId)) {
                        Enumeration<LedgerEntry> entries = ledgerHandle.readEntries(0, ledgerHandle.getLastAddConfirmed());
                        StringBuilder builder = new StringBuilder();
                        while (entries.hasMoreElements()) {
                            LedgerEntry entry = entries.nextElement();
                            builder.append(entry.getEntry());
                        }
                        System.out.println(threadName + ": " + Arrays.toString(builder.toString().getBytes()));
                    } catch (BKException | InterruptedException e) {
                        ExceptionUtil.handle(e, threadName);
                    } finally {
                        threadsDone.countDown();
                    }
                });
            }

            threadsDone.await();
            threadPool.shutdown();
        } catch (IOException | BKException e) {
            ExceptionUtil.handle(e);
        }
    }
}
