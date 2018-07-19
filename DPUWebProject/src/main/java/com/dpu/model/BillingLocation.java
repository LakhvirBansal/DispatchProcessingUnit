package com.dpu.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class BillingLocation {

	//@JsonProperty( "billing_location_id")
	private Long billingLocationId;

	//@JsonProperty( "name")
	private String name;

	//@JsonProperty( "address")
	private String address;

	private String address2;
	//@JsonProperty( "unit_no")
	/*private String unitNo;*/

	//@JsonProperty( "city")
	private String city;

	//@JsonProperty( "province_state")
	private String provinceState;

	//@JsonProperty( "zip")
	
	private String contact;

	private String zip;

	private String phone;
	
	private String fax;
	
	private Long statusId;
	private String statusName;

	public Long getBillingLocationId() {
		return billingLocationId;
	}

	public void setBillingLocationId(Long billingLocationId) {
		this.billingLocationId = billingLocationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvinceState() {
		return provinceState;
	}

	public void setProvinceState(String provinceState) {
		this.provinceState = provinceState;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
