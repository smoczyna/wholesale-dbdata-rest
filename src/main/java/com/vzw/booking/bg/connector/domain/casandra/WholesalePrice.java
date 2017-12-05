package com.vzw.booking.bg.connector.domain.casandra;

/*
 * PRIMARY KEY((ProductId,HomeSidBid),ServeSidBid))
 */
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "wholesaleprice")
public class WholesalePrice implements Serializable {

    /**
	 * Entity Serial Version ID
	 */
	private static final long serialVersionUID = 2623593349729735227L;

	@PartitionKey(value = 0)
    @Column(name = "productid")
    private Integer productid;

    @PartitionKey(value = 1)
    @Column(name = "homesidbid")
    private String homesidbid;

    @ClusteringColumn
    @Column(name = "servesidbid")
    private String servesidbid;

    private String rateperiodclassificationid;
    private String effectivedate;
    private String enddate;
    private BigDecimal productwholesaleprice;
    private BigDecimal productdiscountpercent;

    @Override
    public String toString() {
        return "WholesalePrice [productwholesaleprice=" + productwholesaleprice + ", productdiscountpercent="
                + productdiscountpercent + "]";
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getHomesidbid() {
        return homesidbid;
    }

    public void setHomesidbid(String homesidbid) {
        this.homesidbid = homesidbid;
    }

    public String getServesidbid() {
        return servesidbid;
    }

    public void setServesidbid(String servesidbid) {
        this.servesidbid = servesidbid;
    }

    public String getRateperiodclassificationid() {
        return rateperiodclassificationid;
    }

    public void setRateperiodclassificationid(String rateperiodclassificationid) {
        this.rateperiodclassificationid = rateperiodclassificationid;
    }

    public String getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(String effectivedate) {
        this.effectivedate = effectivedate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public BigDecimal getProductwholesaleprice() {
        return productwholesaleprice;
    }

    public void setProductwholesaleprice(BigDecimal productwholesaleprice) {
        this.productwholesaleprice = productwholesaleprice;
    }

    public BigDecimal getProductdiscountpercent() {
        return productdiscountpercent;
    }

    public void setProductdiscountpercent(BigDecimal productdiscountpercent) {
        this.productdiscountpercent = productdiscountpercent;
    }

}
