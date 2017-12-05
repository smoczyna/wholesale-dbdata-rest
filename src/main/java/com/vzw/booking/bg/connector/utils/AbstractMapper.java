package com.vzw.booking.bg.connector.utils;

import java.util.concurrent.ExecutionException;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.datastax.driver.core.exceptions.UnsupportedFeatureException;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

/**
 * Cassandra Abstract Mapper This class provides convenience methods for the
 * other Mapper Classes
 *
 * @author Fabrizio Torelli (fabrizio.torelli@ie.verizon.com)
 *
 */
public abstract class AbstractMapper<T> {

    protected abstract Mapper<T> getMapper(MappingManager manager);

    public Result<T> executeAndMapResults(Session session, String statementString, MappingManager manager, boolean async)
            throws NullPointerException, NoHostAvailableException, QueryExecutionException,
            QueryValidationException, UnsupportedFeatureException, InterruptedException, ExecutionException {
        return executeAndMapResults(session, new SimpleStatement(statementString), manager, async);
    }

    public Result<T> executeAndMapResults(Session session, Statement statement, MappingManager manager, boolean async)
            throws NullPointerException, NoHostAvailableException, QueryExecutionException,
            QueryValidationException, UnsupportedFeatureException, InterruptedException, ExecutionException {
        return getMapper(manager).map(executeCql(session, statement, async));
    }

    private static final ResultSet executeCql(Session session, Statement statement, boolean async) throws InterruptedException, ExecutionException {
        if (async) {
            ResultSetFuture future = session.executeAsync(statement);
            return future.get();
        } else {
            return session.execute(statement);
        }
    }

}
