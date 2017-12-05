/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.bg.connector.services;

import com.vzw.booking.bg.connector.config.CassandraQueryManager;
import com.vzw.booking.bg.connector.constants.Constants;
import com.vzw.booking.bg.connector.domain.casandra.DataEvent;
import com.vzw.booking.bg.connector.domain.casandra.FinancialEventCategory;
import com.vzw.booking.bg.connector.domain.casandra.WholesalePrice;
import com.vzw.booking.bg.connector.domain.exceptions.CassandraQueryException;
import com.vzw.booking.bg.connector.domain.exceptions.MultipleRowsReturnedException;
import com.vzw.booking.bg.connector.domain.exceptions.NoResultsReturnedException;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author smorcja
 */
@RestController
@RequestMapping("/BookingWholesaleData")
@CrossOrigin
public class BookingWholesaleDataController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingWholesaleDataController.class);
        
    @Autowired
    private CassandraQueryManager queryManager;
        
    private ResponseEntity prepareResponse(Object payload) {
        return ResponseEntity.accepted().body(payload);
    }
    
    /**
     * this endpoint doesn't work actually
     * it suppose to give an overview of all available endpoints
     * @return 
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ResponseEntity getRestInfo() {
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();
        System.out.println("location: "+location);
        return ResponseEntity.created(location).build();
    }
    
    @RequestMapping(value="/getEventCategory", method = RequestMethod.GET)
    public ResponseEntity<FinancialEventCategory> getEventCategory(@RequestParam(required=true) String fileSource,
                                                                   @RequestParam(required=true) String financialMarket,
                                                                   @RequestParam(required=true) Integer tmpProdId,
                                                                   @RequestParam(required=true) String homeEqualsServingSbid,
                                                                   @RequestParam(required=true) String altBookingInd,
                                                                   @RequestParam(required=true) Integer interExchangeCarrierCode,
                                                                   @RequestParam(required=true) String financialeventnormalsign) {
        
        FinancialEventCategory dbResult = this.getEventCategoryFromDb(fileSource, financialMarket, tmpProdId, 
                homeEqualsServingSbid, altBookingInd, interExchangeCarrierCode, financialeventnormalsign);
        
        if (dbResult==null)
            return ResponseEntity.notFound().build();
        else
            return this.prepareResponse(dbResult);
    }
    
    @RequestMapping(value="/getDataEvent", method = RequestMethod.GET)
    public ResponseEntity<DataEvent> getDataEvent(@RequestParam(required=true) Integer productId) {
        DataEvent dbResult = this.getDataEventFromDb(productId);
        if (dbResult==null)
            return ResponseEntity.notFound().build();
        else
            return this.prepareResponse(dbResult);
    }
    
    @RequestMapping(value="/getWholesalePrice", method = RequestMethod.GET)
    public ResponseEntity<WholesalePrice> getWholesalePrice(@RequestParam(required=true) Integer productId, 
                                                            @RequestParam(required=true) String searchHomeSbid) {
        
        WholesalePrice dbResult = this.getWholesalePriceFromDb(productId, searchHomeSbid);
        if (dbResult==null)
            return ResponseEntity.notFound().build();
        else
            return this.prepareResponse(dbResult);
    }
        
    private FinancialEventCategory getEventCategoryFromDb(String fileSource, String financialMarket, Integer tmpProdId, String homeEqualsServingSbid,
            String altBookingInd, int interExchangeCarrierCode, String financialeventnormalsign) {
        FinancialEventCategory result = null;
        List<FinancialEventCategory> dbResult;
        try {
            dbResult = queryManager.getFinancialEventCategoryNoClusteringRecord(
                    tmpProdId, homeEqualsServingSbid, altBookingInd, interExchangeCarrierCode, financialeventnormalsign);

        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "FinancialEventCategory", ex.getMessage()));
            dbResult = null;
        }
        if (dbResult == null && financialeventnormalsign.equals("DR")) {
            LOGGER.warn(Constants.FEC_NOT_FOUND_MESSAGE);
            if (fileSource.equals("M")) {
                tmpProdId = 0;
                if (!financialMarket.equals("003"))
                    interExchangeCarrierCode = 1;                
            } else {
                tmpProdId = 36;
            }
            try {
                dbResult = queryManager.getFinancialEventCategoryNoClusteringRecord(
                        tmpProdId, homeEqualsServingSbid, altBookingInd, interExchangeCarrierCode, financialeventnormalsign);

                LOGGER.warn(Constants.DEFAULT_FEC_OBTAINED);
            } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
                LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "FinancialEventCategory", ex.getMessage()));
                LOGGER.error(Constants.DEFAULT_FEC_NOT_FOUND);
            }
        }
        if (dbResult.size() == 1) {
            result = dbResult.get(0);
        }
        return result;
    }

    private DataEvent getDataEventFromDb(Integer productId) {
        DataEvent result = null;
        try {
            List<DataEvent> dbResult = queryManager.getDataEventRecords(productId);
            if (dbResult.size() == 1) {
                result = dbResult.get(0);
            }
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "DataEvent", ex.getMessage()));
        }
        return result;
    }

    private WholesalePrice getWholesalePriceFromDb(Integer tmpProdId, String searchHomeSbid) {
        WholesalePrice result = null;
        List<WholesalePrice> dbResult;
        try {
            dbResult = queryManager.getWholesalePriceRecords(tmpProdId, searchHomeSbid);
            
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "WholesalePrice", ex.getMessage()));
            dbResult = null;
        }
        if (dbResult==null) {
            LOGGER.warn(Constants.WHOLESALE_PRICE_NOT_FOUND);
            try {
                dbResult = queryManager.getWholesalePriceRecords(tmpProdId, "00000");
                LOGGER.warn(Constants.DEFAULT_WP_OBTAINED);
            } catch (CassandraQueryException | MultipleRowsReturnedException | NoResultsReturnedException ex) {
                LOGGER.warn(String.format(Constants.DB_CALL_FAILURE, "WholesalePrice", ex.getMessage()));
                LOGGER.error(Constants.DEFAULT_WP_NOT_FOUND);
            }
        }
        if (dbResult.size() == 1) {
            result = dbResult.get(0);
        }
        return result;
    }
}
