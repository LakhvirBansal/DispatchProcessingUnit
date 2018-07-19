package com.dpu.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
@Table(name = "ordermaster")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "billing_location_id")
	private CompanyBillingLocation billingLocation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private CompanyAdditionalContacts contact;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "temperature_id")
	private Type temperature;
	
	@Column(name = "temperature_value")
	private Double temperatureValue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "temperature_type")
	private Type temperatureType;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "po_no")
	private Long poNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	private Type currency;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Probil.class)
	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	private List<Probil> probils;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "handling_id")
	private Handling handling;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyBillingLocation getBillingLocation() {
		return billingLocation;
	}

	public void setBillingLocation(CompanyBillingLocation billingLocation) {
		this.billingLocation = billingLocation;
	}

	public CompanyAdditionalContacts getContact() {
		return contact;
	}

	public void setContact(CompanyAdditionalContacts contact) {
		this.contact = contact;
	}

	public Type getTemperature() {
		return temperature;
	}

	public void setTemperature(Type temperature) {
		this.temperature = temperature;
	}

	public Double getTemperatureValue() {
		return temperatureValue;
	}

	public void setTemperatureValue(Double temperatureValue) {
		this.temperatureValue = temperatureValue;
	}

	public Type getTemperatureType() {
		return temperatureType;
	}

	public void setTemperatureType(Type temperatureType) {
		this.temperatureType = temperatureType;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getPoNo() {
		return poNo;
	}

	public void setPoNo(Long poNo) {
		this.poNo = poNo;
	}

	public Type getCurrency() {
		return currency;
	}

	public void setCurrency(Type currency) {
		this.currency = currency;
	}

	public List<Probil> getProbils() {
		return probils;
	}

	public void setProbils(List<Probil> probils) {
		this.probils = probils;
	}

	public Handling getHandling() {
		return handling;
	}

	public void setHandling(Handling handling) {
		this.handling = handling;
	}
	
	
	/*@ManyToOne
	@JoinColumn(name = "shipper_id")
	private Shipper shipper;

	@ManyToOne
	@JoinColumn(name = "consine_id")
	private Shipper consine;*/

	/*@ManyToOne
	@JoinColumn(name = "functional_id")
	private Long functional;*/
	
	/*@ManyToOne
	@JoinColumn(name = "pickup")
	private Type pickUp;
	
	@ManyToOne
	@JoinColumn(name = "delivery")
	private Type delivery;
	
	@Column(name = "probil")
	private Long probilNo;
	
	@Column(name = "pickup_no")
	private String pickUpNo;
	
	@Column(name = "delivery_no")
	private String deliveryNo;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
//	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;*/


}
