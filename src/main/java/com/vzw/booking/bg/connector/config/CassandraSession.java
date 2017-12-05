package com.vzw.booking.bg.connector.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AuthenticationException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.mapping.MappingManager;

/**
 * @author Fabrizio Torelli (fabrizio.torelli@ie.verizon.com)
 *
 */
public class CassandraSession {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraSession.class);

    private static boolean createdGlobalSession;

    private static Cluster globalCluster;
    private static Session globalSession;
    private static MappingManager globalMappingManager;

    private Cluster localCluster;
    private Session localSession;
    private MappingManager localMappingManager;

    protected static Session getGlobalSession() {
        return globalSession;
    }

    protected static synchronized void clearGlobalSession() {
        if (globalSession != null) {
            if (!globalSession.isClosed()) {
                globalSession.close();
                globalSession = null;
            }
        }
        if (globalCluster != null) {
            if (!globalCluster.isClosed()) {
                globalCluster.close();
                globalSession = null;
            }
        }
        globalSession = null;
        globalCluster = null;
        globalMappingManager = null;
    }

    protected static boolean isGlobalSessionAvailable() {
        return globalSession != null;
    }

    protected Session getSession() {
        return localSession;
    }

    protected void clearSession() {
        if (localSession != null) {
            if (!localSession.isClosed()) {
                localSession.close();
                localSession = null;
            }
        }
        if (localCluster != null) {
            if (!localCluster.isClosed()) {
                localCluster.close();
                localSession = null;
            }
        }
        localSession = null;
        localCluster = null;
        localMappingManager = null;
    }

    protected boolean isSessionAvailable() {
        return localSession != null;
    }

    protected static MappingManager getGlobalMappingManager() {
        if (globalMappingManager == null) {
            globalMappingManager = new MappingManager(globalSession);
        }
        return globalMappingManager;
    }

    protected MappingManager getMappingManager() {
        if (localMappingManager == null) {
            localMappingManager = new MappingManager(localSession);
        }
        return localMappingManager;
    }

    public CassandraSession() {
        super();
        localSession = null;
        localMappingManager = null;
        localCluster = null;
    }

    protected void initCassandraSession(boolean useGlobal, boolean openNew, String username, String password, String dcname, String keyspace,
            String... contactPoints) throws IllegalArgumentException, SecurityException, NoHostAvailableException, AuthenticationException, IllegalStateException {
        LOGGER.debug("initSearchSession: useGlobal=" + useGlobal);
        LOGGER.debug("initSearchSession: openNew=" + openNew);
        LOGGER.debug("initSearchSession: globalSession=" + globalSession);
        LOGGER.debug("initSearchSession: localSession=" + localSession);
        if (useGlobal && (globalSession != null || openNew)) {
            LOGGER.info("Recreate Global Session ...");
            clearGlobalSession();
            Builder builder = Cluster.builder()
                    .addContactPoints(contactPoints);
            if (username != null && username.trim().length() > 0
                    && password != null && password.trim().length() > 0) {
                builder = builder.withCredentials(username, password);
            }
            if (dcname != null && dcname.trim().length() > 0) {
                builder = builder.withLoadBalancingPolicy(new TokenAwarePolicy(
                        DCAwareRoundRobinPolicy.builder()
                                .withLocalDc(dcname)
                                .build()));
            }
            globalCluster = builder.build();
            if (keyspace != null && keyspace.trim().length() > 0) {
                globalSession = globalCluster.connect(keyspace);
            } else {
                globalSession = globalCluster.connect();
            }
            return;
        } else if (useGlobal && globalSession != null) {
            LOGGER.info("Session already open; Connected to: " + globalSession.getCluster().getMetadata().getClusterName());
            return;
        } else if (!useGlobal && !openNew && localSession != null) {
            LOGGER.info("Session already open; Connected to: " + localSession.getCluster().getMetadata().getClusterName());
            return;
        } else {
            clearGlobalSession();
            clearSession();
            LOGGER.info("Recreate Session : Global=" + useGlobal);
            Builder builder = Cluster.builder()
                    .addContactPoints(contactPoints);
            if (username != null && username.trim().length() > 0
                    && password != null && password.trim().length() > 0) {
                builder = builder.withCredentials(username, password);
            }
            if (dcname != null && dcname.trim().length() > 0) {
                builder = builder.withLoadBalancingPolicy(new TokenAwarePolicy(
                        DCAwareRoundRobinPolicy.builder()
                                .withLocalDc(dcname)
                                .build()));
            }
            if (useGlobal) {
                globalCluster = builder.build();
            } else {
                localCluster = builder.build();
            }
        }
        if (useGlobal) {
            if (!createdGlobalSession) {
                Runtime.getRuntime().addShutdownHook(new Thread() {

                    @Override
                    public void run() {
                        if (CassandraSession.isGlobalSessionAvailable()) {
                            CassandraSession.clearGlobalSession();
                        }
                    }
                });
            }
            if (openNew || globalSession == null) {
                LOGGER.info("Connection new Global Session : KeySpace=" + keyspace);
                if (keyspace != null && keyspace.trim().length() > 0) {
                    globalSession = globalCluster.connect(keyspace);
                } else {
                    globalSession = globalCluster.connect();
                }
            }
        } else {
            if (openNew || localSession == null) {
                LOGGER.info("Connection new Local Session : KeySpace=" + keyspace);
                if (keyspace != null && keyspace.trim().length() > 0) {
                    localSession = localCluster.connect(keyspace);
                } else {
                    localSession = localCluster.connect();
                }
            }
        }
        LOGGER.info("initSearchSession: useGlobal=" + useGlobal);
        LOGGER.info("initSearchSession: openNew=" + openNew);
        LOGGER.info("initSearchSession: globalCluster=" + globalCluster);
        LOGGER.info("initSearchSession: localCluster=" + localCluster);
        LOGGER.info("initSearchSession: globalSession=" + globalSession);
        LOGGER.info("initSearchSession: localSession=" + localSession);
        LOGGER.info("Created/Recovered Session type " + (useGlobal ? "GLOBAL" : "LOCAL") + "; Connected to: " + (useGlobal ? globalSession : localSession).getCluster().getMetadata().getClusterName());
    }

}
