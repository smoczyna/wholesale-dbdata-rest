package com.vzw.booking.bg.connector.domain.casandra;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 *
 * @author smorcja
 */
@Table(name = "product")
public class Product implements Serializable {

	/**
	 * Entity Serial Version ID
	 */
	private static final long serialVersionUID = -1362749733515266788L;

	@PartitionKey(value = 0)
	@Column(name = "productid")
	private Integer productid;

	private String dpcgroupid;
	private String dpcitemid;
	private String enddate;
	private String etfindicator;
	private String financialgroupcode;
	private String geocodetype;
	private String introductiondate;
	private String occindicator;
	private String pricetype;
	private String productallocationcode;
	private String productbilllevelcode;
	private String productcategorycode;
	private String productname;
	private String productstatus;
	private String producttype;
	private String unitofmeasure;
	private String wholesalebillingcode;

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public String getDpcgroupid() {
		return dpcgroupid;
	}

	public void setDpcgroupid(String dpcgroupid) {
		this.dpcgroupid = dpcgroupid;
	}

	public String getDpcitemid() {
		return dpcitemid;
	}

	public void setDpcitemid(String dpcitemid) {
		this.dpcitemid = dpcitemid;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getEtfindicator() {
		return etfindicator;
	}

	public void setEtfindicator(String etfindicator) {
		this.etfindicator = etfindicator;
	}

	public String getFinancialgroupcode() {
		return financialgroupcode;
	}

	public void setFinancialgroupcode(String financialgroupcode) {
		this.financialgroupcode = financialgroupcode;
	}

	public String getGeocodetype() {
		return geocodetype;
	}

	public void setGeocodetype(String geocodetype) {
		this.geocodetype = geocodetype;
	}

	public String getIntroductiondate() {
		return introductiondate;
	}

	public void setIntroductiondate(String introductiondate) {
		this.introductiondate = introductiondate;
	}

	public String getOccindicator() {
		return occindicator;
	}

	public void setOccindicator(String occindicator) {
		this.occindicator = occindicator;
	}

	public String getPricetype() {
		return pricetype;
	}

	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}

	public String getProductallocationcode() {
		return productallocationcode;
	}

	public void setProductallocationcode(String productallocationcode) {
		this.productallocationcode = productallocationcode;
	}

	public String getProductbilllevelcode() {
		return productbilllevelcode;
	}

	public void setProductbilllevelcode(String productbilllevelcode) {
		this.productbilllevelcode = productbilllevelcode;
	}

	public String getProductcategorycode() {
		return productcategorycode;
	}

	public void setProductcategorycode(String productcategorycode) {
		this.productcategorycode = productcategorycode;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductstatus() {
		return productstatus;
	}

	public void setProductstatus(String productstatus) {
		this.productstatus = productstatus;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getUnitofmeasure() {
		return unitofmeasure;
	}

	public void setUnitofmeasure(String unitofmeasure) {
		this.unitofmeasure = unitofmeasure;
	}

	public String getWholesalebillingcode() {
		return wholesalebillingcode;
	}

	public void setWholesalebillingcode(String wholesalebillingcode) {
		this.wholesalebillingcode = wholesalebillingcode;
	}
}
