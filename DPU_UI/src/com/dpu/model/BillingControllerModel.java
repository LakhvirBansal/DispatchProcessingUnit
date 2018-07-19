
package com.dpu.model;

import java.util.List;

public class BillingControllerModel {

	private String name;
	private String address;
	private String city;
	private String phone;
	private String contact;
	private String zip;
	private String fax;
	private String address2;
	private String province;
	private Long countryId;
	public Long companyId;
	public Long billingLocationId = null;
	
	private List<Country> countryList;
	
	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public BillingControllerModel(String company, String address, String city, String phone, String contact, String zip,
			String fax, String address2, String province, Long countryId) {
		this.name = company;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.contact = contact;
		this.zip = zip;
		this.fax = fax;
		this.address2 = address2;
		this.province = province;
		this.countryId = countryId;

	}

	public BillingControllerModel() {

	}
	
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

}
