package com.github.bewaremypower.config;

import org.apache.bookkeeper.client.BKException;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerHandle;
import org.apache.bookkeeper.conf.ClientConfiguration;

import java.io.IOException;

/**
 * 
 * https://bookkeeper.apache.org/archives/docs/master/bookkeeperProgrammer.html
 * https://bookkeeper.apache.org/docs/latest/api/ledger-api/
 * https://bookkeeper.apache.org/docs/latest/api/ledger-adv-api/
 * https://bookkeeper.apache.org/docs/latest/api/distributedlog-api/
 * Default config of BookKeeper/Ledger
 */
public class DefaultConfig {
    // a comma separated string of "ip:port" that represents ZooKeeper servers
    public static final String ZK_SERVERS = "10.23.191.242:2181";

    // default ZooKeeper timeout
    public static final int ZK_TIMEOUT_MS = 1000;

    // default digest type when a ledger's created or opened
    public static final BookKeeper.DigestType DIGEST_TYPE = BookKeeper.DigestType.CRC32;

    // password when a ledger's created or opened
    public static final byte[] PASSWORD = "".getBytes();

    /**
     * Creates a `BookKeeper` with the default ZooKeeper servers and timeout
     * @return the new `BookKeeper`
     * @throws BKException
     * @throws InterruptedException
     * @throws IOException
     */
    public static BookKeeper newBookKeeper() throws BKException, InterruptedException, IOException {
        ClientConfiguration clientConfiguration = new ClientConfiguration()
                .setZkTimeout(ZK_TIMEOUT_MS)
               .setMetadataServiceUri("zk+null://" + ZK_SERVERS + "/ledgers");
       // .setZkServers(ZK_SERVERS);
        //return new BookKeeper(clientConfiguration);
        return new BookKeeper(ZK_SERVERS);
    }

    /**
     * Creates a ledger, with the default digest type and password
     * @param bookKeeper the `BookKeeper` of ledgers
     * @return a handle to the newly ledger
     * @throws BKException
     * @throws InterruptedException
     */
    public static LedgerHandle createLedger(BookKeeper bookKeeper) throws BKException, InterruptedException {
        return bookKeeper.createLedger(3,2,DIGEST_TYPE, PASSWORD);
    }

    /**
     * Opens a ledger
     * @param bookKeeper the `BookKeeper` of ledgers
     * @param ledgerId id of the ledger to open
     * @return a handle to the newly ledger
     * @throws BKException
     * @throws InterruptedException
     */
    public static LedgerHandle openLedger(BookKeeper bookKeeper, long ledgerId) throws BKException, InterruptedException {
        return bookKeeper.openLedger(ledgerId, DIGEST_TYPE, PASSWORD);
    }
}
