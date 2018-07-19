package com.dpu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "companymaster")
public class Company {

	@Id
	@Column(name = "company_id")
	@GeneratedValue
	private Long companyId;

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

	@Column(name = "email")
	private String email;

	@Column(name = "website")
	private String website;

	@Column(name = "contact")
	private String contact;

	@Column(name = "position")
	private String position;

	@Column(name = "phone")
	private String phone;

	@Column(name = "ext")
	private String ext;

	@Column(name = "fax")
	private String fax;

	@Column(name = "company_prefix")
	private String companyPrefix;

	@Column(name = "tollfree")
	private String tollfree;

	@Column(name = "cellular")
	private String cellular;

	@Column(name = "pager")
	private String pager;

	@Column(name = "customer_notes")
	private String customerNotes;

	@Column(name = "after_hours")
	private String afterHours;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private Set<CompanyBillingLocation> billingLocations = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private Set<CompanyAdditionalContacts> additionalContacts = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "division_id")
	private Division division;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sale_id")
	private Sale sale;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private Type country;
	
	
	public Type getCountry() {
		return country;
	}

	public void setCountry(Type country) {
		this.country = country;
	}

	public Division getDivision() {

		return division;
	}

	public void setDivision(Division division) {

		this.division = division;
	}

	public Sale getSale() {

		return sale;
	}

	public void setSale(Sale sale) {

		this.sale = sale;
	}

	public Category getCategory() {

		return category;
	}

	public void setCategory(Category category) {

		this.category = category;
	}

	public Set<CompanyAdditionalContacts> getAdditionalContacts() {

		return additionalContacts;
	}

	public void setAdditionalContacts(
			Set<CompanyAdditionalContacts> additionalContacts) {

		this.additionalContacts = additionalContacts;
	}

	public Set<CompanyBillingLocation> getBillingLocations() {

		return billingLocations;
	}

	public void setBillingLocations(Set<CompanyBillingLocation> billingLocations) {

		this.billingLocations = billingLocations;
	}

	public Long getCompanyId() {

		return companyId;
	}

	public void setCompanyId(Long companyId) {

		this.companyId = companyId;
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

	public String getCompanyPrefix() {

		return companyPrefix;
	}

	public void setCompanyPrefix(String companyPrefix) {

		this.companyPrefix = companyPrefix;
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

	public String getCustomerNotes() {

		return customerNotes;
	}

	public void setCustomerNotes(String customerNotes) {

		this.customerNotes = customerNotes;
	}

	public String getAfterHours() {

		return afterHours;
	}

	public void setAfterHours(String afterHours) {

		this.afterHours = afterHours;
	}

}
