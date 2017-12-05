package com.vzw.booking.bg.connector.domain.casandra;

import java.io.Serializable;

/*
 * PRIMARY KEY (ProductId),DataEventSubType))
 */
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "dataevent")
public class DataEvent implements Serializable {

	/**
	 * Entity Serial Version ID
	 */
	private static final long serialVersionUID = 5863188732889055228L;

	@PartitionKey
	@Column(name = "dataeventtype")
	private String dataeventtype;

	@ClusteringColumn
	@Column(name = "dataeventsubtype")
	private String dataeventsubtype;

	private String contentreporttype;
	private String productbilllevelcode;
	private String productbillfrequencycode;
	private String contentcomponenttype;
	private String contentrevenuesharetype;
	private String billsectioncode;
	private Integer productid;

	public String getDataeventtype() {
		return dataeventtype;
	}

	public void setDataeventtype(String dataeventtype) {
		this.dataeventtype = dataeventtype;
	}

	public String getContentreporttype() {
		return contentreporttype;
	}

	public void setContentreporttype(String contentreporttype) {
		this.contentreporttype = contentreporttype;
	}

	public String getDataeventsubtype() {
		return dataeventsubtype;
	}

	public void setDataeventsubtype(String dataeventsubtype) {
		this.dataeventsubtype = dataeventsubtype;
	}

	public String getProductbilllevelcode() {
		return productbilllevelcode;
	}

	public void setProductbilllevelcode(String productbilllevelcode) {
		this.productbilllevelcode = productbilllevelcode;
	}

	public String getProductbillfrequencycode() {
		return productbillfrequencycode;
	}

	public void setProductbillfrequencycode(String productbillfrequencycode) {
		this.productbillfrequencycode = productbillfrequencycode;
	}

	public String getContentcomponenttype() {
		return contentcomponenttype;
	}

	public void setContentcomponenttype(String contentcomponenttype) {
		this.contentcomponenttype = contentcomponenttype;
	}

	public String getContentrevenuesharetype() {
		return contentrevenuesharetype;
	}

	public void setContentrevenuesharetype(String contentrevenuesharetype) {
		this.contentrevenuesharetype = contentrevenuesharetype;
	}

	public String getBillsectioncode() {
		return billsectioncode;
	}

	public void setBillsectioncode(String billsectioncode) {
		this.billsectioncode = billsectioncode;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	@Override
	public String toString() {
		return "DataEvent [dataeventsubtype=" + dataeventsubtype + "]";
	}

}