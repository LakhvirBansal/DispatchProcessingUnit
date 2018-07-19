/*package com.dpu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carrieradditionalcontact")
public class CarrierAdditionalContact  {

	*//**
	 * 
	 *//*
	@Id
	@GeneratedValue
	@Column(name = "additional_contact_id")
	private Long additionalContactId;

	@Column(name = "inc_company")
	private String incCompany;

	@Column(name = "policy_number")
	private String policyNumber;

	@Column(name = "inc_broker")
	private String incBroker;

	@Column(name = "broker_contact")
	private String brokerContact;

	@Column(name = "broker_phone")
	private String brokerPhone;

	@Column(name = "ext")
	private String ext;

	@Column(name = "broker_fax")
	private String brokerFax;

	@Column(name = "email")
	private String email;

	@Column(name = "expiry_date")
	private Date expiryDate;

	@Column(name = "cong_coverage")
	private String congCoverage;

	@Column(name = "libility_coverage")
	private String libilityCoverage;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carrier_id")
	private Carrier carrier;


	public Long getAdditionalContactId() {
		return additionalContactId;
	}


	public void setAdditionalContactId(Long additionalContactId) {
		this.additionalContactId = additionalContactId;
	}


	public String getIncCompany() {
		return incCompany;
	}


	public void setIncCompany(String incCompany) {
		this.incCompany = incCompany;
	}


	public String getPolicyNumber() {
		return policyNumber;
	}


	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}


	public String getIncBroker() {
		return incBroker;
	}


	public void setIncBroker(String incBroker) {
		this.incBroker = incBroker;
	}


	public String getBrokerContact() {
		return brokerContact;
	}


	public void setBrokerContact(String brokerContact) {
		this.brokerContact = brokerContact;
	}


	public String getBrokerPhone() {
		return brokerPhone;
	}


	public void setBrokerPhone(String brokerPhone) {
		this.brokerPhone = brokerPhone;
	}


	public String getExt() {
		return ext;
	}


	public void setExt(String ext) {
		this.ext = ext;
	}


	public String getBrokerFax() {
		return brokerFax;
	}


	public void setBrokerFax(String brokerFax) {
		this.brokerFax = brokerFax;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public String getCongCoverage() {
		return congCoverage;
	}


	public void setCongCoverage(String congCoverage) {
		this.congCoverage = congCoverage;
	}


	public String getLibilityCoverage() {
		return libilityCoverage;
	}


	public void setLibilityCoverage(String libilityCoverage) {
		this.libilityCoverage = libilityCoverage;
	}


	public Carrier getCarrier() {
		return carrier;
	}


	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	


}
*/