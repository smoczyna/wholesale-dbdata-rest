/**
 *
 */
package com.vzw.booking.bg.connector.cache;

import com.vzw.booking.bg.connector.domain.casandra.DataEvent;
import com.vzw.booking.bg.connector.domain.casandra.FinancialEventCategory;
import com.vzw.booking.bg.connector.domain.casandra.FinancialMarket;
import com.vzw.booking.bg.connector.domain.casandra.WholesalePrice;

/**
 * @author torelfa
 *
 */
public class CassandraTableReference {

    /**
     * Constructor
     */
    private CassandraTableReference() {
        throw new IllegalStateException("CassandraTableReference: Reference Data");
    }

    /**
     *
     */
    public static final String CACHE_ITEM_KEY_SEPARATOR = "-";

    /**
     *
     */
    public static final String CACHE_ITEM_BILLED_FINANCIAL_EVENT_CATEGORY = "BILLEDFINANCIALEVENTCATEGORY";
    /**
     *
     */
    public static final String CACHE_ITEM_UNBILLED_FINANCIAL_EVENT_CATEGORY = "UNBILLEDFINANCIALEVENTCATEGORY";
    /**
     *
     */
    public static final String CACHE_ITEM_FINANCIAL_MARKET = "FINANCIALMARKET";
    /**
     *
     */
    public static final String CACHE_ITEM_DATA_EVENT = "DATAEVENT";
    /**
     *
     */
    public static final String CACHE_ITEM_WHOLESALE_PRICE = "WHOLESALEPRICE";

    /**
     * Index Helper for Billed FinancialEventCategory Data Object
     *
     * @param c
     * @return
     */
    public static final String UnBilledFinancialEventCategoryIndexHelper(FinancialEventCategory c) {
        return "" + c.getProductid() + CACHE_ITEM_KEY_SEPARATOR + c.getHomesidequalsservingsidindicator()
                + CACHE_ITEM_KEY_SEPARATOR + c.getAlternatebookingindicator() + CACHE_ITEM_KEY_SEPARATOR + c.getInterexchangecarriercode();
    }

    /**
     * Index Helper for UnBilled FinancialEventCategory Data Object
     *
     * @param c
     * @return
     */
    public static final String BilledFinancialEventCategoryIndexHelper(FinancialEventCategory c) {
        return "" + c.getProductid() + CACHE_ITEM_KEY_SEPARATOR + c.getHomesidequalsservingsidindicator()
                + CACHE_ITEM_KEY_SEPARATOR + c.getAlternatebookingindicator() + CACHE_ITEM_KEY_SEPARATOR + c.getInterexchangecarriercode()
                + CACHE_ITEM_KEY_SEPARATOR + c.getFinancialeventnormalsign();
    }

    /**
     * Index Helper for FinancialMaket Data Object
     *
     * @param c
     * @return
     */
    public static final String FinancialMaketIndexHelper(FinancialMarket c) {
        return "" + c.getFinancialmarketid();
    }

    /**
     * Index Helper for DataEvent Data Object
     *
     * @param c
     * @return
     */
    public static final String DataEventIndexHelper(DataEvent c) {
        return "" + c.getProductid();
    }

    /**
     * Index Helper for WholesalePrice Data Object
     *
     * @param c
     * @return
     */
    public static final String WholesalePriceIndexHelper(WholesalePrice c) {
        return "" + c.getProductid() + CACHE_ITEM_KEY_SEPARATOR + c.getHomesidbid();
    }
}
