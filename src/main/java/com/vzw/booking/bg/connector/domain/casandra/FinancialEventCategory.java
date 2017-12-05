package com.vzw.booking.bg.connector.domain.casandra;

import java.io.Serializable;

/*
 * PRIMARY KEY ((ProductId,HomeSidEqualsServingSidIndicator,AlternateBookingIndicator),FinancialMarketId,InterExchangeCarrierCode))
 */
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;


/**
 *
 * @author smorcja
 */
@Table(name = "financialeventcategory")
public class FinancialEventCategory implements Serializable {

    /**
	 * Entity Serial Version ID
	 */
	private static final long serialVersionUID = -6270238000461251943L;

	@PartitionKey(value = 0)
    @Column(name = "productid")
    private Integer productid;

    @PartitionKey(value = 1)
    @Column(name = "homesidequalsservingsidindicator")
    private String homesidequalsservingsidindicator;
    
    @PartitionKey(value = 2)
    @Column(name = "financialeventnormalsign")    
   private String financialeventnormalsign;

    @PartitionKey(value = 3)
    @Column(name = "alternatebookingindicator")
    private String alternatebookingindicator;

    @ClusteringColumn(value = 0)
    @Column(name = "financialmarketid")
    private String financialmarketid;

    @ClusteringColumn(value = 1)
    @Column(name = "interexchangecarriercode")
    private Integer interexchangecarriercode;
    
    private Integer financialeventnumber;
    
    private Integer financialcategory;
    private String financialmappingeffectivedate;
    private String glaccountnumber;
    private String billtypecode;
    private String financialeventcategorycode;
    private String financialeventeffectivedate;
    private String financialeventenddate;
    private String transactionitemtypecode;
    private String financialgroupcode;
    private String financialcategoryeffectivedate;
    private String financialcategoryenddate;
    private String taxjurisdictionlevel;
    private String taxtypecode;
    private Integer contracttermsid;
    private Integer miscfinancialtransactionnumber;
    private String statecode;
    private String adminfeereasoncode;
    private String financialmappingenddate;
    private String glfinancialaccounttypecode;    
    private String bamsaffiliateindicator;    
    private String billingaccrualindicator;
    private String cashpostindicator;
    private String cellularcarrierrslcode;    
    private String companycode;
    private String creditcardtypecode;    
    private String debitcreditindicator;
    private String directindirectindicator;
    private String exceptioncode;
    private String financialcategorydescription;
    private String financialeventcategorytype;
    private String financialeventdescription;
    private String financialeventdetails;
    private String financialeventprocessingcode;
    private String financialmarketsourcecode;    
    private String foreignservedindicator;
    private String glaccountdescription;
    private String glcostcenternumber;
    private String gleffectivedate;
    private String glenddate;
    private String gllocationid;
    private String glproductid;
    private String legalentityeqindicator;
    private String paymentmediacode;
    private String paymentsourcecode;
    private String pricebandtypecode;
    private String processinggroupnumber;

    public Integer getFinancialeventnumber() {
        return financialeventnumber;
    }

    public void setFinancialeventnumber(Integer financialeventnumber) {
        this.financialeventnumber = financialeventnumber;
    }

    public Integer getFinancialcategory() {
        return financialcategory;
    }

    public void setFinancialcategory(Integer financialcategory) {
        this.financialcategory = financialcategory;
    }

    public String getFinancialmappingeffectivedate() {
        return financialmappingeffectivedate;
    }

    public void setFinancialmappingeffectivedate(String financialmappingeffectivedate) {
        this.financialmappingeffectivedate = financialmappingeffectivedate;
    }

    public String getGlaccountnumber() {
        return glaccountnumber;
    }

    public void setGlaccountnumber(String glaccountnumber) {
        this.glaccountnumber = glaccountnumber;
    }

    public String getBilltypecode() {
        return billtypecode;
    }

    public void setBilltypecode(String billtypecode) {
        this.billtypecode = billtypecode;
    }

    public String getFinancialeventcategorycode() {
        return financialeventcategorycode;
    }

    public void setFinancialeventcategorycode(String financialeventcategorycode) {
        this.financialeventcategorycode = financialeventcategorycode;
    }

    public String getFinancialeventeffectivedate() {
        return financialeventeffectivedate;
    }

    public void setFinancialeventeffectivedate(String financialeventeffectivedate) {
        this.financialeventeffectivedate = financialeventeffectivedate;
    }

    public String getFinancialeventenddate() {
        return financialeventenddate;
    }

    public void setFinancialeventenddate(String financialeventenddate) {
        this.financialeventenddate = financialeventenddate;
    }

    public String getTransactionitemtypecode() {
        return transactionitemtypecode;
    }

    public void setTransactionitemtypecode(String transactionitemtypecode) {
        this.transactionitemtypecode = transactionitemtypecode;
    }

    public String getFinancialgroupcode() {
        return financialgroupcode;
    }

    public void setFinancialgroupcode(String financialgroupcode) {
        this.financialgroupcode = financialgroupcode;
    }

    public String getFinancialcategoryeffectivedate() {
        return financialcategoryeffectivedate;
    }

    public void setFinancialcategoryeffectivedate(String financialcategoryeffectivedate) {
        this.financialcategoryeffectivedate = financialcategoryeffectivedate;
    }

    public String getFinancialcategoryenddate() {
        return financialcategoryenddate;
    }

    public void setFinancialcategoryenddate(String financialcategoryenddate) {
        this.financialcategoryenddate = financialcategoryenddate;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getTaxjurisdictionlevel() {
        return taxjurisdictionlevel;
    }

    public void setTaxjurisdictionlevel(String taxjurisdictionlevel) {
        this.taxjurisdictionlevel = taxjurisdictionlevel;
    }

    public String getTaxtypecode() {
        return taxtypecode;
    }

    public void setTaxtypecode(String taxtypecode) {
        this.taxtypecode = taxtypecode;
    }

    public Integer getInterexchangecarriercode() {
        return interexchangecarriercode;
    }

    public void setInterexchangecarriercode(Integer interexchangecarriercode) {
        this.interexchangecarriercode = interexchangecarriercode;
    }

    public Integer getContracttermsid() {
        return contracttermsid;
    }

    public void setContracttermsid(Integer contracttermsid) {
        this.contracttermsid = contracttermsid;
    }

    public Integer getMiscfinancialtransactionnumber() {
        return miscfinancialtransactionnumber;
    }

    public void setMiscfinancialtransactionnumber(Integer miscfinancialtransactionnumber) {
        this.miscfinancialtransactionnumber = miscfinancialtransactionnumber;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getAdminfeereasoncode() {
        return adminfeereasoncode;
    }

    public void setAdminfeereasoncode(String adminfeereasoncode) {
        this.adminfeereasoncode = adminfeereasoncode;
    }

    public String getFinancialmappingenddate() {
        return financialmappingenddate;
    }

    public void setFinancialmappingenddate(String financialmappingenddate) {
        this.financialmappingenddate = financialmappingenddate;
    }

    public String getGlfinancialaccounttypecode() {
        return glfinancialaccounttypecode;
    }

    public void setGlfinancialaccounttypecode(String glfinancialaccounttypecode) {
        this.glfinancialaccounttypecode = glfinancialaccounttypecode;
    }

    public String getAlternatebookingindicator() {
        return alternatebookingindicator;
    }

    public void setAlternatebookingindicator(String alternatebookingindicator) {
        this.alternatebookingindicator = alternatebookingindicator;
    }

    public String getBamsaffiliateindicator() {
        return bamsaffiliateindicator;
    }

    public void setBamsaffiliateindicator(String bamsaffiliateindicator) {
        this.bamsaffiliateindicator = bamsaffiliateindicator;
    }

    public String getBillingaccrualindicator() {
        return billingaccrualindicator;
    }

    public void setBillingaccrualindicator(String billingaccrualindicator) {
        this.billingaccrualindicator = billingaccrualindicator;
    }

    public String getCashpostindicator() {
        return cashpostindicator;
    }

    public void setCashpostindicator(String cashpostindicator) {
        this.cashpostindicator = cashpostindicator;
    }

    public String getCellularcarrierrslcode() {
        return cellularcarrierrslcode;
    }

    public void setCellularcarrierrslcode(String cellularcarrierrslcode) {
        this.cellularcarrierrslcode = cellularcarrierrslcode;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getCreditcardtypecode() {
        return creditcardtypecode;
    }

    public void setCreditcardtypecode(String creditcardtypecode) {
        this.creditcardtypecode = creditcardtypecode;
    }

    public String getDebitcreditindicator() {
        return debitcreditindicator;
    }

    public void setDebitcreditindicator(String debitcreditindicator) {
        this.debitcreditindicator = debitcreditindicator;
    }

    public String getDirectindirectindicator() {
        return directindirectindicator;
    }

    public void setDirectindirectindicator(String directindirectindicator) {
        this.directindirectindicator = directindirectindicator;
    }

    public String getExceptioncode() {
        return exceptioncode;
    }

    public void setExceptioncode(String exceptioncode) {
        this.exceptioncode = exceptioncode;
    }

    public String getFinancialcategorydescription() {
        return financialcategorydescription;
    }

    public void setFinancialcategorydescription(String financialcategorydescription) {
        this.financialcategorydescription = financialcategorydescription;
    }

    public String getFinancialeventcategorytype() {
        return financialeventcategorytype;
    }

    public void setFinancialeventcategorytype(String financialeventcategorytype) {
        this.financialeventcategorytype = financialeventcategorytype;
    }

    public String getFinancialeventdescription() {
        return financialeventdescription;
    }

    public void setFinancialeventdescription(String financialeventdescription) {
        this.financialeventdescription = financialeventdescription;
    }

    public String getFinancialeventdetails() {
        return financialeventdetails;
    }

    public void setFinancialeventdetails(String financialeventdetails) {
        this.financialeventdetails = financialeventdetails;
    }

    public String getFinancialeventnormalsign() {
        return financialeventnormalsign;
    }

    public void setFinancialeventnormalsign(String financialeventnormalsign) {
        this.financialeventnormalsign = financialeventnormalsign;
    }

    public String getFinancialeventprocessingcode() {
        return financialeventprocessingcode;
    }

    public void setFinancialeventprocessingcode(String financialeventprocessingcode) {
        this.financialeventprocessingcode = financialeventprocessingcode;
    }

    public String getFinancialmarketid() {
        return financialmarketid;
    }

    public void setFinancialmarketid(String financialmarketid) {
        this.financialmarketid = financialmarketid;
    }

    public String getFinancialmarketsourcecode() {
        return financialmarketsourcecode;
    }

    public void setFinancialmarketsourcecode(String financialmarketsourcecode) {
        this.financialmarketsourcecode = financialmarketsourcecode;
    }

    public String getForeignservedindicator() {
        return foreignservedindicator;
    }

    public void setForeignservedindicator(String foreignservedindicator) {
        this.foreignservedindicator = foreignservedindicator;
    }

    public String getGlaccountdescription() {
        return glaccountdescription;
    }

    public void setGlaccountdescription(String glaccountdescription) {
        this.glaccountdescription = glaccountdescription;
    }

    public String getGlcostcenternumber() {
        return glcostcenternumber;
    }

    public void setGlcostcenternumber(String glcostcenternumber) {
        this.glcostcenternumber = glcostcenternumber;
    }

    public String getGleffectivedate() {
        return gleffectivedate;
    }

    public void setGleffectivedate(String gleffectivedate) {
        this.gleffectivedate = gleffectivedate;
    }

    public String getGlenddate() {
        return glenddate;
    }

    public void setGlenddate(String glenddate) {
        this.glenddate = glenddate;
    }

    public String getGllocationid() {
        return gllocationid;
    }

    public void setGllocationid(String gllocationid) {
        this.gllocationid = gllocationid;
    }

    public String getGlproductid() {
        return glproductid;
    }

    public void setGlproductid(String glproductid) {
        this.glproductid = glproductid;
    }

    public String getHomesidequalsservingsidindicator() {
        return homesidequalsservingsidindicator;
    }

    public void setHomesidequalsservingsidindicator(String homesidequalsservingsidindicator) {
        this.homesidequalsservingsidindicator = homesidequalsservingsidindicator;
    }

    public String getLegalentityeqindicator() {
        return legalentityeqindicator;
    }

    public void setLegalentityeqindicator(String legalentityeqindicator) {
        this.legalentityeqindicator = legalentityeqindicator;
    }

    public String getPaymentmediacode() {
        return paymentmediacode;
    }

    public void setPaymentmediacode(String paymentmediacode) {
        this.paymentmediacode = paymentmediacode;
    }

    public String getPaymentsourcecode() {
        return paymentsourcecode;
    }

    public void setPaymentsourcecode(String paymentsourcecode) {
        this.paymentsourcecode = paymentsourcecode;
    }

    public String getPricebandtypecode() {
        return pricebandtypecode;
    }

    public void setPricebandtypecode(String pricebandtypecode) {
        this.pricebandtypecode = pricebandtypecode;
    }

    public String getProcessinggroupnumber() {
        return processinggroupnumber;
    }

    public void setProcessinggroupnumber(String processinggroupnumber) {
        this.processinggroupnumber = processinggroupnumber;
    }

    @Override
    public String toString() {
        return "FinancialEventCategory [alternatebookingindicator=" + alternatebookingindicator
                + ", bamsaffiliateindicator=" + bamsaffiliateindicator + ", companycode=" + companycode
                + ", foreignservedindicator=" + foreignservedindicator + ", homesidequalsservingsidindicator="
                + homesidequalsservingsidindicator + "]";
    }

}
