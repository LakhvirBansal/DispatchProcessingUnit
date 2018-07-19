package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@Table(name = "billinglocationmaster")
@JsonSerialize(include = Inclusion.NON_NULL)
public class CompanyBillingLocation {

    @Id
    @Column(name = "billing_location_id")
    @GeneratedValue
    private Long billingLocationId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "unit_no")
    private String unitNo;

    @Column(name = "city")
    private String city;

    @Column(name = "province_state")
    private String provinceState;

    @Column(name = "zip")
    private String zip;

    @Column(name = "ar_cdn")
    private String arCDN;

    @Column(name = "ar_us")
    private String arUS;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;

    @Column(name = "contact")
    private String contact;

    @Column(name = "position")
    private String position;

    @Column(name = "email")
    private String email;

    @Column(name = "cellular")
    private String cellular;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ext")
    private String ext;

    @Column(name = "fax")
    private String fax;

    @Column(name = "billing_prefix")
    private String prefix;

    @Column(name = "tollfree")
    private String tollfree;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
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
