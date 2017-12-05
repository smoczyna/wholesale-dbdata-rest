package com.vzw.booking.bg.connector.domain.casandra;

/*
 * PRIMARY KEY((FinancialMarketId),FccCgsaMapEndDate,FinancialMarketMapEndDate,GLMarketLegalEntityEndDate,GLMarketMapType,GLMarketEndDate,AlternateBookingType))
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.ClusteringColumn;

/**
 *
 * @author smorcja
 */
@Table(name = "financialmarket")
public class FinancialMarket implements Serializable {

	/**
	 * Entity Serial Version ID
	 */
	private static final long serialVersionUID = 2112479617262123110L;

	@PartitionKey
	@Column(name = "financialmarketid")
	private String financialmarketid;

	@ClusteringColumn(value = 0)
	@Column(name = "fcccgsamapenddate")
	private String fcccgsamapenddate;

	@ClusteringColumn(value = 1)
	@Column(name = "financialmarketmapenddate")
	private String financialmarketmapenddate;

	@ClusteringColumn(value = 2)
	@Column(name = "glmarketlegalentityenddate")
	private String glmarketlegalentityenddate;

	@ClusteringColumn(value = 3)
	@Column(name = "glmarketmaptype")
	private String glmarketmaptype;

	@ClusteringColumn(value = 4)
	@Column(name = "glmarketenddate")
	private String glmarketenddate;

	@ClusteringColumn(value = 5)
	@Column(name = "alternatebookingtype")
	private String alternatebookingtype;

	private String financialmarketmapeffectivedate;
	private String fcccgsanumber;
	private String fcccgsamapeffectivedate;
	private String glmarketid;
	private String glmarketeffectivedate;
	private String glmarketlegalentityeffectivedate;
	private String financialmarketeffectivedate;
	private String financialmarketenddate;

	private String financialmarketdefaultgeocode;
	private String financialmarketname;
	private String financialmarkettypecode;
	private String gllegalentityid;
	private String glmarketdescription;
	private String multiplemarketindicator;
	private String sidbid;

	public String getSidbid() {
		return sidbid;
	}

	public void setSidbid(String sidbid) {
		this.sidbid = sidbid;
	}

	public String getFinancialmarketid() {
		return financialmarketid;
	}

	public void setFinancialmarketid(String financialmarketid) {
		this.financialmarketid = financialmarketid;
	}

	public String getFinancialmarketmapeffectivedate() {
		return financialmarketmapeffectivedate;
	}

	public void setFinancialmarketmapeffectivedate(String financialmarketmapeffectivedate) {
		this.financialmarketmapeffectivedate = financialmarketmapeffectivedate;
	}

	public String getFcccgsanumber() {
		return fcccgsanumber;
	}

	public void setFcccgsanumber(String fcccgsanumber) {
		this.fcccgsanumber = fcccgsanumber;
	}

	public String getFcccgsamapeffectivedate() {
		return fcccgsamapeffectivedate;
	}

	public void setFcccgsamapeffectivedate(String fcccgsamapeffectivedate) {
		this.fcccgsamapeffectivedate = fcccgsamapeffectivedate;
	}

	public String getGlmarketid() {
		return glmarketid;
	}

	public void setGlmarketid(String glmarketid) {
		this.glmarketid = glmarketid;
	}

	public String getGlmarketeffectivedate() {
		return glmarketeffectivedate;
	}

	public void setGlmarketeffectivedate(String glmarketeffectivedate) {
		this.glmarketeffectivedate = glmarketeffectivedate;
	}

	public String getGlmarketlegalentityeffectivedate() {
		return glmarketlegalentityeffectivedate;
	}

	public void setGlmarketlegalentityeffectivedate(String glmarketlegalentityeffectivedate) {
		this.glmarketlegalentityeffectivedate = glmarketlegalentityeffectivedate;
	}

	public String getFinancialmarketeffectivedate() {
		return financialmarketeffectivedate;
	}

	public void setFinancialmarketeffectivedate(String financialmarketeffectivedate) {
		this.financialmarketeffectivedate = financialmarketeffectivedate;
	}

	public String getFinancialmarketenddate() {
		return financialmarketenddate;
	}

	public void setFinancialmarketenddate(String financialmarketenddate) {
		this.financialmarketenddate = financialmarketenddate;
	}

	public String getFinancialmarketmapenddate() {
		return financialmarketmapenddate;
	}

	public void setFinancialmarketmapenddate(String financialmarketmapenddate) {
		this.financialmarketmapenddate = financialmarketmapenddate;
	}

	public String getGlmarketmaptype() {
		return glmarketmaptype;
	}

	public void setGlmarketmaptype(String glmarketmaptype) {
		this.glmarketmaptype = glmarketmaptype;
	}

	public String getFcccgsamapenddate() {
		return fcccgsamapenddate;
	}

	public void setFcccgsamapenddate(String fcccgsamapenddate) {
		this.fcccgsamapenddate = fcccgsamapenddate;
	}

	public String getGlmarketenddate() {
		return glmarketenddate;
	}

	public void setGlmarketenddate(String glmarketenddate) {
		this.glmarketenddate = glmarketenddate;
	}

	public String getAlternatebookingtype() {
		return alternatebookingtype;
	}

	public void setAlternatebookingtype(String alternatebookingtype) {
		this.alternatebookingtype = alternatebookingtype;
	}

	public String getGlmarketlegalentityenddate() {
		return glmarketlegalentityenddate;
	}

	public void setGlmarketlegalentityenddate(String glmarketlegalentityenddate) {
		this.glmarketlegalentityenddate = glmarketlegalentityenddate;
	}

	public String getFinancialmarketdefaultgeocode() {
		return financialmarketdefaultgeocode;
	}

	public void setFinancialmarketdefaultgeocode(String financialmarketdefaultgeocode) {
		this.financialmarketdefaultgeocode = financialmarketdefaultgeocode;
	}

	public String getFinancialmarketname() {
		return financialmarketname;
	}

	public void setFinancialmarketname(String financialmarketname) {
		this.financialmarketname = financialmarketname;
	}

	public String getFinancialmarkettypecode() {
		return financialmarkettypecode;
	}

	public void setFinancialmarkettypecode(String financialmarkettypecode) {
		this.financialmarkettypecode = financialmarkettypecode;
	}

	public String getGllegalentityid() {
		return gllegalentityid;
	}

	public void setGllegalentityid(String gllegalentityid) {
		this.gllegalentityid = gllegalentityid;
	}

	public String getGlmarketdescription() {
		return glmarketdescription;
	}

	public void setGlmarketdescription(String glmarketdescription) {
		this.glmarketdescription = glmarketdescription;
	}

	public String getMultiplemarketindicator() {
		return multiplemarketindicator;
	}

	public void setMultiplemarketindicator(String multiplemarketindicator) {
		this.multiplemarketindicator = multiplemarketindicator;
	}

	@Override
	public String toString() {
		return "FinancialMarket [glmarketid=" + glmarketid + ", alternatebookingtype=" + alternatebookingtype
				+ ", gllegalentityid=" + gllegalentityid + ", sidbid=" + sidbid + "]";
	}

}
