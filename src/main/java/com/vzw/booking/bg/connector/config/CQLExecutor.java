/**
 *
 */
package com.vzw.booking.bg.connector.config;

import com.vzw.booking.bg.connector.utils.AbstractMapper;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AuthenticationException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.datastax.driver.core.exceptions.UnsupportedFeatureException;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

/**
 * @author Fabrizio Torelli (fabrizio.torelli@ie.verizon.com)
 *
 */
public class CQLExecutor {

    private CassandraSession cassandraSession;

    private boolean useGlobal;

    public CQLExecutor() {
        super();
    }

    public CQLExecutor createSearchSession(boolean useGlobal, boolean newSession, String username, String password, String dcname, String keyspace,
            String... contactPoints) throws IllegalArgumentException, SecurityException, NoHostAvailableException, AuthenticationException, IllegalStateException {
        if (cassandraSession == null) {
            cassandraSession = new CassandraSession();
        }
        if ((!useGlobal && !this.useGlobal && cassandraSession.isSessionAvailable())
                || (useGlobal && this.useGlobal && CassandraSession.isGlobalSessionAvailable())) {
            closeSearchSession();
        }
        this.useGlobal = useGlobal;
        cassandraSession.initCassandraSession(useGlobal, newSession, username, password, dcname, keyspace, contactPoints);
        return this;
    }

    public void closeSearchSession() {
        if (useGlobal && CassandraSession.isGlobalSessionAvailable()) {
            CassandraSession.clearGlobalSession();
            cassandraSession = null;
        } else if (!useGlobal && cassandraSession != null && cassandraSession.isSessionAvailable()) {
            cassandraSession.clearSession();
            cassandraSession = null;
        }
    }

    protected Session getSession() {
        if (useGlobal && CassandraSession.isGlobalSessionAvailable()) {
            return CassandraSession.getGlobalSession();
        } else if (!useGlobal && cassandraSession != null && cassandraSession.isSessionAvailable()) {
            return cassandraSession.getSession();
        }
        return null;
    }

    protected MappingManager getMappingManager() {
        if (useGlobal && CassandraSession.isGlobalSessionAvailable()) {
            return CassandraSession.getGlobalMappingManager();
        } else if (!useGlobal && cassandraSession != null && cassandraSession.isSessionAvailable()) {
            return cassandraSession.getMappingManager();
        }
        return null;
    }

    public boolean isSearchSessionClosed() {
        if (useGlobal) {
            return CassandraSession.isGlobalSessionAvailable() || CassandraSession.getGlobalSession().isClosed();
        } else if (cassandraSession != null) {
            return cassandraSession.isSessionAvailable() || cassandraSession.getSession().isClosed();
        }
        return true;
    }

    public <T> List<T> executeCql(String cql, AbstractMapper<T> mapper, int limit, boolean async)
            throws NullPointerException, NoHostAvailableException, QueryExecutionException, InterruptedException,
            QueryValidationException, UnsupportedFeatureException, IllegalStateException, ExecutionException {
        if (cassandraSession == null) {
            throw new NullPointerException("Session was not created");
        }
        if (getSession() == null) {
            throw new NullPointerException("Cassandra Session session error");
        }
        if (getSession().isClosed()) {
            throw new IllegalStateException("Cassandra Session session closed");
        }
        if (mapper == null) {
            throw new NullPointerException("Mapper cannot be null");
        }
        Session session = getSession();
        Result<T> result = mapper.executeAndMapResults(session, cql + (limit > 0 ? " LIMIT " + limit : ""), getMappingManager(), async);
        return result.all();
    }

    public ResultSet executeCql(String cql, boolean async)
            throws NullPointerException, NoHostAvailableException, QueryExecutionException, InterruptedException,
            QueryValidationException, UnsupportedFeatureException, IllegalStateException, ExecutionException {
        if (cassandraSession == null) {
            throw new NullPointerException("Session was not created");
        }
        if (getSession() == null) {
            throw new NullPointerException("Cassandra Session session error");
        }
        if (getSession().isClosed()) {
            throw new IllegalStateException("Cassandra Session session closed");
        }
        Session session = getSession();
        ResultSet result;
        if (async) {
            ResultSetFuture future = session.executeAsync(cql);
            result = future.get();
        } else {
            result = session.execute(cql);
        }
        return result;
    }

}
