package com.dpu.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;


@JsonSerialize(include = Inclusion.NON_NULL)
public class VendorModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long vendorId;
	
	private String name;
	
	private String address;
	
	private String unitNo;
	
	private String city;
	
	private String zip;
	
	private String email;
	
	private String website;
	
	private String contact;
	
	private String position;
	
	private String phone;
	
	private String ext;
	
	private String fax;
	
	private String vendorPrefix;
	
	private String tollfree;
	
	private String cellular;
	
	private String pager;
	
	private String vendorNotes;
	
	private String afterHours;
	
	private String stateName;
	private Long stateId;
	private List<CountryStateCityModel> stateList;
	
	private String countryName;
	private Long countryId;
	private List<CountryStateCityModel> countryList;

	private List<VendorBillingLocationModel> billingLocations;
	
	private List<VendorAdditionalContactsModel> additionalContacts;
	
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

	public List<VendorBillingLocationModel> getBillingLocations() {
		return billingLocations;
	}

	public void setBillingLocations(List<VendorBillingLocationModel> billingLocations) {
		this.billingLocations = billingLocations;
	}

	public List<VendorAdditionalContactsModel> getAdditionalContacts() {
		return additionalContacts;
	}

	public void setAdditionalContacts(
			List<VendorAdditionalContactsModel> additionalContacts) {
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

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public List<CountryStateCityModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<CountryStateCityModel> stateList) {
		this.stateList = stateList;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public List<CountryStateCityModel> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<CountryStateCityModel> countryList) {
		this.countryList = countryList;
	}

}
