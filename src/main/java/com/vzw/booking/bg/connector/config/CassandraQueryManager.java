package com.vzw.booking.bg.connector.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.datastax.driver.core.exceptions.UnsupportedFeatureException;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.vzw.booking.bg.connector.cache.CacheException;
import com.vzw.booking.bg.connector.cache.WzwCache;
import com.vzw.booking.bg.connector.cache.CassandraTableReference;
import com.vzw.booking.bg.connector.domain.casandra.DataEvent;
import com.vzw.booking.bg.connector.domain.casandra.FinancialEventCategory;
import com.vzw.booking.bg.connector.domain.casandra.FinancialMarket;
import com.vzw.booking.bg.connector.domain.casandra.Product;
import com.vzw.booking.bg.connector.domain.casandra.WholesalePrice;
import com.vzw.booking.bg.connector.domain.casandra.mappers.DataEventCassandraMapper;
import com.vzw.booking.bg.connector.domain.casandra.mappers.FinancialEventCategoryCassandraMapper;
import com.vzw.booking.bg.connector.domain.casandra.mappers.FinancialMarketCassandraMapper;
import com.vzw.booking.bg.connector.domain.casandra.mappers.ProductCassandraMapper;
import com.vzw.booking.bg.connector.domain.casandra.mappers.WholesalePriceCassandraMapper;
import com.vzw.booking.bg.connector.domain.exceptions.CassandraQueryException;
import com.vzw.booking.bg.connector.domain.exceptions.ErrorEnum;
import com.vzw.booking.bg.connector.domain.exceptions.MultipleRowsReturnedException;
import com.vzw.booking.bg.connector.domain.exceptions.NoResultsReturnedException;

import static com.vzw.booking.bg.connector.cache.CassandraTableReference.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author khanaas
 */
@Component
public class CassandraQueryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraQueryManager.class);

    @Value("${com.springbatch.db.cassandra.contactpoints}")
    private String contactpoints; // = "170.127.114.154";

    @Value("${com.springbatch.db.cassandra.keyspace}")
    private String keyspace; // = "j6_dev";

    @Value("${com.springbatch.db.cassandra.username}")
    private String username; // = "j6_dev_user";

    //private @Value("${com.springbatch.db.cassandra.password}") String password="";
    private String password = ArgumentsHelper.getCassandraPassword();

    //@Value("${com.springbatch.db.cassandra.dcname}")
    private String dcname = "";

    //@Value("${com.springbatch.db.query.constraints.financialmarketmapenddate}")
    private String financialmarketmapenddate = "12/31/9999";

    //@Value("${com.springbatch.db.query.constraints.glmarketlegalentityenddate}")
    private String glmarketlegalentityenddate = "12/31/9999";

    //@Value("${com.springbatch.db.query.constraints.glmarketmaptype}")
    private String glmarketmaptype = "D";

    //@Value("${com.springbatch.db.query.constraints.glmarketenddate}")
    private String glmarketenddate = "12/31/9999";

    private static Session cassandraSession;

    private final String finMarketQuery = "SELECT * FROM financialmarket"
            + " WHERE financialmarketmapenddate=? AND glmarketlegalentityenddate=? "
            + " AND glmarketmaptype=? AND glmarketenddate=? ALLOW FILTERING";

    private final String finEventCatQuery = "SELECT * FROM financialeventcategory ALLOW FILTERING";

    private final String dataEventQuery = "SELECT *  FROM dataevent ALLOW FILTERING";

    private final String wholesalePriceQuery = "SELECT * FROM wholesaleprice WHERE servesidbid=? ALLOW FILTERING";

    private PreparedStatement productStatement;

    private WzwCache localCache = null;

    @PostConstruct
    public synchronized void init() {
        LOGGER.info("Cassandra Query Contraints ...");
        LOGGER.info("Constraint : Financial Market Map End Date : " + financialmarketmapenddate);
        LOGGER.info("Constraint : GL Market Legal Entity End Date : " + glmarketlegalentityenddate);
        LOGGER.info("Constraint : GL Market Map Type : " + glmarketmaptype);
        LOGGER.info("Constraint : GL Market End Date : " + glmarketenddate);

        localCache = WzwCache.getInstance();
        try {
            localCache.checkLoad();
        } catch (CacheException e) {
            LOGGER.error("Unable to initialize cache: {}", e.getMessage());
        }

        boolean anyChange = false;

        if (!localCache.existsCacheItem(CassandraTableReference.CACHE_ITEM_FINANCIAL_MARKET)) {
            if (this.getCassandraSession() == null
                    || this.getCassandraSession().isClosed()) {
                connectToCassandra();
            }
            LOGGER.info("Loading all data for table : financialmarket ...");
            try {
                PreparedStatement finMarketStatement = cassandraSession.prepare(finMarketQuery);
                BoundStatement statement = finMarketStatement.bind(financialmarketmapenddate, glmarketlegalentityenddate, glmarketmaptype, glmarketenddate);
                Result<FinancialMarket> result = new FinancialMarketCassandraMapper().executeAndMapResults(this.getCassandraSession(), statement, new MappingManager(this.getCassandraSession()), false);
                List<FinancialMarket> fmr = result.all();
                LOGGER.info("Caching records " + fmr.size() + " for table : financialmarket ...");
                localCache.createCacheItem(CassandraTableReference.CACHE_ITEM_FINANCIAL_MARKET, FinancialMarket.class);
                localCache.addAllToItem(CassandraTableReference.CACHE_ITEM_FINANCIAL_MARKET, CassandraTableReference::FinancialMaketIndexHelper, fmr);
                LOGGER.info("Chached records " + fmr.size() + " for table : financialmarket!!");
                anyChange = true;
            } catch (Exception e) {
                LOGGER.error("Error Chaching records for table : financialmarket => : {}", e.getMessage());
            }

        }

        if (!localCache.existsCacheItem(CassandraTableReference.CACHE_ITEM_BILLED_FINANCIAL_EVENT_CATEGORY)
                || !localCache.existsCacheItem(CassandraTableReference.CACHE_ITEM_UNBILLED_FINANCIAL_EVENT_CATEGORY)) {
            if (this.getCassandraSession() == null
                    || this.getCassandraSession().isClosed()) {
                connectToCassandra();
            }
            LOGGER.info("Loading all data for table : financialeventcategory ...");
            try {
                SimpleStatement statement = new SimpleStatement(finEventCatQuery);
                Result<FinancialEventCategory> result = new FinancialEventCategoryCassandraMapper().executeAndMapResults(this.getCassandraSession(), statement, new MappingManager(this.getCassandraSession()), false);
                List<FinancialEventCategory> fecr = result.all();
                LOGGER.info("Caching records " + fecr.size() + " for table : financialeventcategory ...");
                localCache.createCacheItem(CassandraTableReference.CACHE_ITEM_BILLED_FINANCIAL_EVENT_CATEGORY, FinancialEventCategory.class);
                localCache.addAllToItem(CassandraTableReference.CACHE_ITEM_BILLED_FINANCIAL_EVENT_CATEGORY, CassandraTableReference::BilledFinancialEventCategoryIndexHelper, fecr);
                localCache.createCacheItem(CassandraTableReference.CACHE_ITEM_UNBILLED_FINANCIAL_EVENT_CATEGORY, FinancialEventCategory.class);
                localCache.addAllToItem(CassandraTableReference.CACHE_ITEM_UNBILLED_FINANCIAL_EVENT_CATEGORY, CassandraTableReference::UnBilledFinancialEventCategoryIndexHelper, fecr);
                LOGGER.info("Chached records " + fecr.size() + " for table : financialeventcategory!!");
                anyChange = true;
            } catch (Exception e) {
                LOGGER.error("Error Chaching records for table : financialeventcategory => {}", e.getMessage());
            }
        }
        if (!localCache.existsCacheItem(CassandraTableReference.CACHE_ITEM_DATA_EVENT)) {
            if (this.getCassandraSession() == null
                    || this.getCassandraSession().isClosed()) {
                connectToCassandra();
            }
            LOGGER.info("Loading all data for table : dataevent ...");
            try {
                SimpleStatement statement = new SimpleStatement(dataEventQuery);
                Result<DataEvent> result = new DataEventCassandraMapper().executeAndMapResults(this.getCassandraSession(), statement, new MappingManager(this.getCassandraSession()), false);
                List<DataEvent> fecr = result.all();
                LOGGER.info("Caching records " + fecr.size() + " for table : dataevent ...");
                localCache.createCacheItem(CassandraTableReference.CACHE_ITEM_DATA_EVENT, DataEvent.class);
                localCache.addAllToItem(CassandraTableReference.CACHE_ITEM_DATA_EVENT, CassandraTableReference::DataEventIndexHelper, fecr);
                LOGGER.info("Chached records " + fecr.size() + " for table : dataevent!!");
                anyChange = true;
            } catch (Exception e) {
                LOGGER.error("Error Chaching records for table : dataevent => {}", e.getMessage());
            }
        }
        if (!localCache.existsCacheItem(CassandraTableReference.CACHE_ITEM_WHOLESALE_PRICE)) {
            if (this.getCassandraSession() == null
                    || this.getCassandraSession().isClosed()) {
                connectToCassandra();
            }
            LOGGER.info("Loading all data for table : wholesaleprice ...");
            try {
                PreparedStatement wholesalePriceStatement = cassandraSession.prepare(wholesalePriceQuery);
                BoundStatement statement = wholesalePriceStatement.bind("00000");
                Result<WholesalePrice> result = new WholesalePriceCassandraMapper().executeAndMapResults(this.getCassandraSession(), statement, new MappingManager(this.getCassandraSession()), false);
                List<WholesalePrice> whsr = result.all();
                LOGGER.info("Caching records " + whsr.size() + " for table : wholesaleprice ...");
                localCache.createCacheItem(CassandraTableReference.CACHE_ITEM_WHOLESALE_PRICE, WholesalePrice.class);
                localCache.addAllToItem(CassandraTableReference.CACHE_ITEM_WHOLESALE_PRICE, CassandraTableReference::WholesalePriceIndexHelper, whsr);
                LOGGER.info("Chached records " + whsr.size() + " for table : wholesaleprice!!");
                anyChange = true;
            } catch (Exception e) {
                LOGGER.error("Error Chaching records for table : wholesaleprice => {}", e.getMessage());
            }
        }

        try {
            if (anyChange) {
                localCache.checkSave();
            }
        } catch (CacheException e) {
            LOGGER.error("Error Refreshing cache on disk => {}", e.getMessage());
        }

        LOGGER.info("After construction/load of Verizon Wireless L2 Cache");
    }

    private final void connectToCassandra() {
        LOGGER.info("Cassandra Connection parameters ...");
        LOGGER.info("Cassandra Contact Points: " + contactpoints);
        LOGGER.info("Cassandra User: " + username);
        LOGGER.info("Cassandra Password: " + password);
        LOGGER.info("Cassandra DataCenter: " + dcname);
        LOGGER.info("Cassandra Keyspace: " + keyspace);
        AuthProvider authProvider = new PlainTextAuthProvider(username, password);
        Cluster.Builder builder = Cluster.builder()
                .addContactPoints(contactpoints.split(","))
                .withAuthProvider(authProvider);
        if (dcname != null && !dcname.trim().isEmpty()) {
            builder = builder.withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc(dcname).build());
        }
        Cluster cluster = builder.build();
        cassandraSession = cluster.connect(keyspace);

        LOGGER.info("After construction of Cassandra connection");
    }

    /**
     * Pre-ready session used across all queries
     *
     * @return
     */
    public Session getCassandraSession() {
        return cassandraSession;
    }

    /**
     * Returns matching rows, output List<FinancialMarket> list. If there are
     * multiple rows returned, send an error code to the program.
     * <p>
     * Cassandra Table Name=FinancialMarket
     *
     * @param session
     * @param file2financialmarketid
     * @return List<FinancialMarket>
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    @Cacheable("FinancialMarket")
    public List<FinancialMarket> getFinancialMarketRecord(String financialmarketid) throws CassandraQueryException, NoResultsReturnedException, MultipleRowsReturnedException {
        List<FinancialMarket> fms = localCache.getValueFromItem(CassandraTableReference.CACHE_ITEM_FINANCIAL_MARKET, "" + financialmarketid);
        if (fms.isEmpty()) {
            LOGGER.info("Error message:" + ErrorEnum.NO_ROWS + "Table: FinancialMarket, Input params["
                    + financialmarketid + "," + financialmarketmapenddate + "," + glmarketlegalentityenddate + "," + glmarketmaptype + "," + glmarketenddate + "]");
            throw new NoResultsReturnedException(ErrorEnum.NO_ROWS);
        }
        if (fms.size() >= 2) {
            LOGGER.info("Error message:" + ErrorEnum.MULTIPLE_ROWS + "Table: FinancialMarket, Input params["
                    + financialmarketid + "," + financialmarketmapenddate + "," + glmarketlegalentityenddate + "," + glmarketmaptype + "," + glmarketenddate + "]");
            LOGGER.info("Number of rows returned:" + Integer.toString(fms.size()));
            throw new MultipleRowsReturnedException(ErrorEnum.MULTIPLE_ROWS,
                    " rows returned: " + Integer.toString(fms.size()));
        }
        return fms;
    }

    /**
     * Check if the product is Wholesale product or not. Retrieve matching row,
     * output character 'Y' or 'N'
     * <p>
     * Cassandra Table Name=Product
     *
     * @param session
     * @param TmpProdId
     * @return char
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    @Cacheable("WholesaleProduct")
    public char isWholesaleProduct(Integer TmpProdId) throws CassandraQueryException {
        char isWholesaleProduct;
        List<Product> listoffep = new ArrayList<>();
        BoundStatement statement = new BoundStatement(this.productStatement);
        statement.bind(TmpProdId);
        try {
            Result<Product> result = new ProductCassandraMapper().executeAndMapResults(this.getCassandraSession(), statement, new MappingManager(this.cassandraSession), false);
            listoffep = result.all();
        } catch (NoHostAvailableException | QueryExecutionException | QueryValidationException | UnsupportedFeatureException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Casandra Query Exception", e);
        } catch (NullPointerException | InterruptedException | ExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query Execution exception", e);
        }
        if (listoffep.size() == 1) {
            isWholesaleProduct = listoffep.get(0).getWholesalebillingcode().charAt(0);
        } else {
            isWholesaleProduct = 'N';
        }
        return isWholesaleProduct;
    }

    /**
     * Returns list of FinancialEventCategory records
     * <p>
     * Cassandra Table Name=FinancialEventCategory
     *
     * @param session
     * @param TmpProdId
     * @param File2FinancialMarketId
     * @param InterExchangeCarrierCode
     * @param homesidequalsservingsidindicator
     * @param alternatebookingindicator
     * @return List<FinancialEventCategory>
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    @Cacheable("FinancialEventCategory")
    public List<FinancialEventCategory> getFinancialEventCategoryNoClusteringRecord(Integer TmpProdId,
            String homesidequalsservingsidindicator, String alternatebookingindicator, int interExchangeCarrierCode, String financialeventnormalsign)
            throws MultipleRowsReturnedException, NoResultsReturnedException, CassandraQueryException {
        List<FinancialEventCategory> listoffec = new ArrayList<>();
        if (financialeventnormalsign == null) {
            listoffec = localCache.getValueFromItem(CassandraTableReference.CACHE_ITEM_UNBILLED_FINANCIAL_EVENT_CATEGORY,
                    "" + TmpProdId + CACHE_ITEM_KEY_SEPARATOR + homesidequalsservingsidindicator
                    + CACHE_ITEM_KEY_SEPARATOR + alternatebookingindicator + CACHE_ITEM_KEY_SEPARATOR + interExchangeCarrierCode);
        } else {
            listoffec = localCache.getValueFromItem(CassandraTableReference.CACHE_ITEM_BILLED_FINANCIAL_EVENT_CATEGORY,
                    "" + TmpProdId + CACHE_ITEM_KEY_SEPARATOR + homesidequalsservingsidindicator
                    + CACHE_ITEM_KEY_SEPARATOR + alternatebookingindicator + CACHE_ITEM_KEY_SEPARATOR + interExchangeCarrierCode
                    + CACHE_ITEM_KEY_SEPARATOR + financialeventnormalsign);
        }
        if (listoffec.isEmpty()) {
            LOGGER.info("Error message:" + ErrorEnum.NO_ROWS + "Table: FinancialEventCategory, Input params["
                    + TmpProdId + "," + homesidequalsservingsidindicator + "," + alternatebookingindicator + "," + interExchangeCarrierCode + "," + financialeventnormalsign + "]");
            throw new NoResultsReturnedException(ErrorEnum.NO_ROWS);
        }
        if (listoffec.size() > 1) {
            LOGGER.info("Error message:" + ErrorEnum.MULTIPLE_ROWS + "Table: FinancialEventCategory, Input params["
                    + TmpProdId + "," + homesidequalsservingsidindicator + "," + alternatebookingindicator + "," + interExchangeCarrierCode + "," + financialeventnormalsign + "]");
            LOGGER.info("Number of rows returned:" + Integer.toString(listoffec.size()));
            throw new MultipleRowsReturnedException(ErrorEnum.MULTIPLE_ROWS, " rows returned: " + Integer.toString(listoffec.size()));
        }
        return listoffec;
    }

    /**
     * Returns first record of the List<DataEvent>
     * <p>
     * Cassandra Table Name=DataEvent
     *
     * @param session
     * @param productid
     * @return DataEvent
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    @Cacheable("DataEvent")
    public List<DataEvent> getDataEventRecords(Integer productid) throws MultipleRowsReturnedException, CassandraQueryException, NoResultsReturnedException {
        List<DataEvent> listofde = localCache.getValueFromItem(CassandraTableReference.CACHE_ITEM_DATA_EVENT, "" + productid);
        if (listofde.isEmpty()) {
            LOGGER.info("Error message:" + ErrorEnum.NO_ROWS + "Table: DataEvent, Input params[" + productid + "]");
            throw new NoResultsReturnedException(ErrorEnum.NO_ROWS);
        }
        return listofde;
    }

    /**
     * Returns first record of the WholesalePrice object/one record
     * <p>
     * Cassandra Table Name=WholesalePrice
     *
     * @param session
     * @param productid
     * @param homesidbid
     * @return WholesalePrice
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    @Cacheable("WholesalePrice")
    public List<WholesalePrice> getWholesalePriceRecords(Integer productid, String homesidbid) throws MultipleRowsReturnedException, CassandraQueryException, NoResultsReturnedException {
        List<WholesalePrice> listofwp = localCache.getValueFromItem(CassandraTableReference.CACHE_ITEM_WHOLESALE_PRICE, "" + productid + CACHE_ITEM_KEY_SEPARATOR + homesidbid);
        if (listofwp.isEmpty()) {
            LOGGER.info("Error message:" + ErrorEnum.NO_ROWS + "Table: WholesalePrice, Input params[" + productid + "," + homesidbid + ",00000]");
            throw new NoResultsReturnedException(ErrorEnum.NO_ROWS);
        }
        if (listofwp.size() >= 2) {
            LOGGER.info("Error message:" + ErrorEnum.MULTIPLE_ROWS + "Table: WholesalePrice, Input params[" + productid + "," + homesidbid + ",00000]");
            LOGGER.info("Number of rows returned:" + Integer.toString(listofwp.size()));
            throw new MultipleRowsReturnedException(ErrorEnum.MULTIPLE_ROWS,
                    " rows returned: " + Integer.toString(listofwp.size()));
        }
        return listofwp;
    }
}
