package com.dpu.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Vendor implements Serializable{

	private static final long serialVersionUID = 1L;

	//@JsonProperty("company_id")
	private Long vendorId;
	
	//@JsonProperty("name")
	private String name;
	
	//@JsonProperty("address")
	private String address;
	
	//@JsonProperty("unit-no")
	private String unitNo;
	
	//@JsonProperty("city")
	private String city;
	
	//@JsonProperty("province_state")
	private String provinceState;
	
	//@JsonProperty("zip")
	private String zip;
	
	//@JsonProperty("email")
	private String email;
	
	//@JsonProperty("website")
	private String website;
	
	//@JsonProperty("contact")
	private String contact;
	
	//@JsonProperty("position")
	private String position;
	
	//@JsonProperty("phone")
	private String phone;
	
	//@JsonProperty("ext")
	private String ext;
	
	//@JsonProperty("fax")
	private String fax;
	
	//@JsonProperty("company_prefix")
	private String vendorPrefix;
	
	//@JsonProperty("tollfree")
	private String tollfree;
	
	//@JsonProperty("cellular")
	private String cellular;
	
	//@JsonProperty("pager")
	private String pager;
	
	//@JsonProperty("customer_notes")
	private String vendorNotes;
	
	//@JsonProperty("after_hours")
	private String afterHours;

	private List<VendorBillingLocation> billingLocations;
	
	private List<VendorAdditionalContacts> additionalContacts;
	
	private List<Status> statusList;
	
	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getTollfree() {
		return tollfree;
	}

	public void setTollfree(String tollfree) {
		this.tollfree = tollfree;
	}

	public String getCellular() {
		return cellular;
	}

	public void setCellular(String cellular) {
		this.cellular = cellular;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getAfterHours() {
		return afterHours;
	}

	public void setAfterHours(String afterHours) {
		this.afterHours = afterHours;
	}

	public List<VendorBillingLocation> getBillingLocations() {
		return billingLocations;
	}

	public void setBillingLocations(List<VendorBillingLocation> billingLocations) {
		this.billingLocations = billingLocations;
	}

	public List<VendorAdditionalContacts> getAdditionalContacts() {
		return additionalContacts;
	}

	public void setAdditionalContacts(
			List<VendorAdditionalContacts> additionalContacts) {
		this.additionalContacts = additionalContacts;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorPrefix() {
		return vendorPrefix;
	}

	public void setVendorPrefix(String vendorPrefix) {
		this.vendorPrefix = vendorPrefix;
	}

	public String getVendorNotes() {
		return vendorNotes;
	}

	public void setVendorNotes(String vendorNotes) {
		this.vendorNotes = vendorNotes;
	}

}
