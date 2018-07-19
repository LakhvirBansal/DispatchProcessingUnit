package com.dpu.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class VendorBillingLocationModel {

	//@JsonProperty( "billing_location_id")
	private Long vendorBillingLocationId;

	//@JsonProperty( "name")
	private String name;

	//@JsonProperty( "address")
	private String address;

	//@JsonProperty( "unit_no")
	private String unitNo;

	//@JsonProperty( "city")
	private String city;

	//@JsonProperty( "province_state")
	private String provinceState;

	//@JsonProperty( "zip")
	private String zip;

	//@JsonProperty( "ar_cdn")
	private String arCDN;

	//@JsonProperty( "ar_us")
	private String arUS;

	//@JsonProperty( "status")
	private Long statusId;
	private String statusName;

	//@JsonProperty( "contact")
	private String contact;

	//@JsonProperty( "position")
	private String position;

	//@JsonProperty( "email")
	private String email;

	//@JsonProperty( "cellular")
	private String cellular;

	//@JsonProperty( "phone")
	private String phone;

	//@JsonProperty( "ext")
	private String ext;

	//@JsonProperty( "fax")
	private String fax;

	//@JsonProperty( "billing_prefix")
	private String prefix;

	//@JsonProperty( "tollfree")
	private String tollfree;

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

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
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

	public String getArCDN() {
		return arCDN;
	}

	public void setArCDN(String arCDN) {
		this.arCDN = arCDN;
	}

	public String getArUS() {
		return arUS;
	}

	public void setArUS(String arUS) {
		this.arUS = arUS;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getTollfree() {
		return tollfree;
	}

	public void setTollfree(String tollfree) {
		this.tollfree = tollfree;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getVendorBillingLocationId() {
		return vendorBillingLocationId;
	}

	public void setVendorBillingLocationId(Long vendorBillingLocationId) {
		this.vendorBillingLocationId = vendorBillingLocationId;
	}
}
