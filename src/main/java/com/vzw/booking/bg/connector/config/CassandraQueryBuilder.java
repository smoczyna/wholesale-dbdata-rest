package com.vzw.booking.bg.connector.config;

import com.vzw.booking.bg.connector.utils.AbstractMapper;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.datastax.driver.core.exceptions.AuthenticationException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.datastax.driver.core.exceptions.UnsupportedFeatureException;

/**
 * @author Fabrizio Torelli (fabrizio.torelli@ie.verizon.com)
 * @param <T>
 *
 */
public final class CassandraQueryBuilder<T> {

    protected static final String ENV_KEY = "ENV";

    /**
     * Executor is internal type that connect effectively to Cassandra and
     * execute queries
     */
    private final CQLExecutor executor;

    /**
     * Host list name (or host colon port list), part of configuration file as
     * {@link ConfigItems#CASSANDRA_HOSTS}
     */
    protected String[] hosts;

    /**
     * Cassandra connection user name or nothing in case of generic access, if
     * allowed. It is part of configuration file as
     * {@link ConfigItems#CASSANDRA_USERNAME}
     */
    protected String username;

    /**
     * Cassandra connection user password or nothing in case of generic access,
     * if allowed. It is part of configuration file as
     * {@link ConfigItems#CASSANDRA_PASSWORD}
     */
    protected String password;

    /**
     * Cassandra connection keyspace or nothing in case of generic keyspece, if
     * allowed. It is part of configuration file as
     * {@link ConfigItems#CASSANDRA_KEYSPACE}
     */
    protected String keySpace;

    /**
     * Cassandra Cluster Dc name, if needed. It is part of configuration file as
     * {@link ConfigItems#CASSANDRA_DC_NAME}
     */
    protected String dcName;

    /**
     * Single query statement you are want to run in Cassandra Session.
     */
    protected String cql;

    /**
     * Limit of rows returned by Cassandra statement.
     */
    protected int limit = 0;

    /**
     * Execute statement asynchronously. It is useful for long time running
     * statements.
     */
    protected boolean async;

    /**
     * Reuse Statically Cassandra Connections and Sessions, just connected first
     * time and reuse it as well as you need crossing multiple Cassandra
     * Building types and instances.
     */
    protected boolean useStatic;

    /**
     * {@link AbstractMapper} base Data Extraction mapper. It allows to extract
     * data from Cassandra query.
     */
    protected AbstractMapper<T> mapper;

    /**
     * Open a new session, in case of Static Session Management.
     */
    protected boolean newSession;

    private List<T> results = null;

    /**
     * Default Builder Constructor
     */
    public CassandraQueryBuilder() {
        super();
        this.executor = new CQLExecutor();
    }

    public CassandraQueryBuilder<T> withConDetails(String host, String keyspace, String username, String password) throws NullPointerException {
        this.hosts = host.split(",");
        this.username = username;
        this.password = password;
        this.keySpace = keyspace;
        this.dcName = "";
        return this;
    }
    
    /**
     * Set asynchronous nature of query execution. In order to support long
     * lasting look ups in Cassandra Database Cluster.
     *
     * @param async
     * @return
     */
    public CassandraQueryBuilder<T> asyncQuery(boolean async) {
        this.async = async;
        return this;
    }

    /**
     * Reuse Statically Cassandra Connections and Sessions, just connected first
     * time and reuse it as well as you need crossing multiple Cassandra
     * Building types and instances.
     *
     * @param useStatic
     * @return
     */
    public CassandraQueryBuilder<T> staticSession(boolean useStatic) {
        this.useStatic = useStatic;
        return this;
    }

    /**
     * Set current CQL statement string
     *
     * @param Cql
     * @return
     */
    public CassandraQueryBuilder<T> withCql(String Cql) {
        this.cql = Cql;
        return this;
    }

    /**
     * Start new session, if you considered in past a static session approach
     * (It saves connection time). For non static Sessions, it will grant
     * reconnection to keyspace.
     *
     * @param newSession
     * @return
     */
    public CassandraQueryBuilder<T> openNewSession(boolean newSession) {
        this.newSession = newSession;
        return this;
    }

    /**
     * {@link AbstractMapper} base Data Extraction mapper. It allows to extract
     * data from Cassandra query.
     *
     * @param mapper
     * @return
     */
    public CassandraQueryBuilder<T> withMapper(AbstractMapper<T> mapper) {
        this.mapper = mapper;
        return this;
    }

    /**
     * Define limit to CQL statement results.
     *
     * @param limit
     * @return
     */
    public CassandraQueryBuilder<T> withResultsLimit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Create Cluster and Run CQL Statement.
     *
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws AuthenticationException
     * @throws NoHostAvailableException
     * @throws InterruptedException
     * @throws QueryExecutionException
     * @throws QueryValidationException
     * @throws UnsupportedFeatureException
     * @throws IllegalStateException
     * @throws ExecutionException
     */
    public void build()
            throws NullPointerException, IllegalArgumentException, SecurityException, AuthenticationException, NoHostAvailableException, InterruptedException,
            QueryExecutionException, QueryValidationException, UnsupportedFeatureException, IllegalStateException, ExecutionException {
        executor.createSearchSession(useStatic, newSession, username, password, dcName, keySpace, hosts);
        results = executor.executeCql(cql, mapper, limit, async);
    }

    /**
     * Collect Results from previous CQL Statement execution, or
     * {@link NullPointerException} in case of no query execution.
     *
     * @return
     * @throws NullPointerException
     */
    public List<T> getResults()
            throws NullPointerException {
        if (results == null) {
            throw new NullPointerException(getClass().getName() + " has not been built yet!!");
        }
        return results;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        if (!this.useStatic) {
            if (this.executor != null && !this.executor.isSearchSessionClosed()) {
                this.executor.closeSearchSession();
            }
        }
        super.finalize();
    }

}
