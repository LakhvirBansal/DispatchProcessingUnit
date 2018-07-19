package com.dpu.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class CompanyBillingLocation {

    private Long billingLocationId;

    private String name;

    private String address;

    private String unitNo;

    private String city;

    private String provinceState;

    private String zip;

    private String arCDN;

    private String arUS;

	private Status status;

    private String contact;

    private String position;

    private String email;

    private String cellular;

    private String phone;

    private String ext;

    private String fax;

    private String prefix;

    private String tollfree;

    private Company company;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
