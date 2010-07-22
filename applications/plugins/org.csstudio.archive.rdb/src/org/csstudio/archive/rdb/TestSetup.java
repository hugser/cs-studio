package org.csstudio.archive.rdb;

import org.apache.log4j.Logger;
import org.csstudio.platform.logging.CentralLogger;
import org.csstudio.platform.test.TestDataProvider;
import org.csstudio.platform.test.TestProviderException;

/** My test setup(s).
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class TestSetup
{
    private static final Logger LOG = CentralLogger.getInstance().getLogger(TestSetup.class);

    private static TestDataProvider PROV;
    static {
        try {
            PROV = TestDataProvider.getInstance(Activator.ID);
        } catch (final TestProviderException e) {
            LOG.error("Test configuration file could not be loaded.");
        }
    }

    /**
     * Don't instantiate
     */
    private TestSetup() {
        // EMPTY
    }

    /** Default database URL */
    final public static String URL = (String) PROV.get("TestSetup.dbUrl");
    // SNS Test, read-only
    //   "jdbc:oracle:thin:sns_reports/sns@//172.31.73.122:1521/prod";

    // MySQL Test
    // "jdbc:mysql://titan-terrier.sns.ornl.gov/archive?user=fred&password=$fred";

    /** Database user/password (null if already in URL) */
    public static final String USER = (String) PROV.get("TestSetup.dbUser");
    public static final String PASSWORD = (String) PROV.get("TestSetup.dbPassword");

    /** Data types to test and the PVs to use for them */
    public enum TestType
    {
        DOUBLE((String) PROV.get("TestSetup.pvDouble")),
        LONG((String) PROV.get("TestSetup.pvLong")),
        ENUM((String) PROV.get("TestSetup.pvEnum")),
        STRING((String) PROV.get("TestSetup.pvString")),
        ARRAY((String) PROV.get("TestSetup.pvArray"));

        final private String _pvName;

        private TestType(final String pvName)
        {
            this._pvName = pvName;
        }

        public String getPvName()
        {
            return _pvName;
        }
    }
}
