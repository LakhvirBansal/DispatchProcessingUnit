package com.dpu.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Status;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	// @JsonProperty("company_id")
	private Long companyId;

	// @JsonProperty("name")
	private String name;

	// @JsonProperty("address")
	private String address;

	// @JsonProperty("unit-no")
	private String unitNo;

	// @JsonProperty("city")
	private String city;

	// @JsonProperty("province_state")
	private String provinceState;

	// @JsonProperty("zip")
	private String zip;

	// @JsonProperty("email")
	private String email;

	// @JsonProperty("website")
	private String website;

	// @JsonProperty("contact")
	private String contact;

	// @JsonProperty("position")
	private String position;

	// @JsonProperty("phone")
	private String phone;

	// @JsonProperty("ext")
	private String ext;

	// @JsonProperty("fax")
	private String fax;

	// @JsonProperty("company_prefix")
	private String companyPrefix;

	// @JsonProperty("tollfree")
	private String tollfree;

	// @JsonProperty("cellular")
	private String cellular;

	// @JsonProperty("pager")
	private String pager;

	// @JsonProperty("customer_notes")
	private String customerNotes;

	// @JsonProperty("after_hours")
	private String afterHours;

	private List<BillingLocation> billingLocations;

	private List<AdditionalContacts> additionalContacts;

	private List<Status> statusList;

	private String categoryName;

	private String divisionName;

	private String saleName;

	private Long categoryId;

	private List<CategoryModel> categoryList;

	private Long divisionId;

	private List<DivisionReq> divisionList;

	private Long saleId;

	private List<SaleReq> saleList;

	private Long countryId;

	/*private List<TypeResponse> countryList;*/

	private String countryName;

	private List<CountryStateCityModel> countryList;
	public Long getCountryId() {

		return countryId;
	}

	public void setCountryId(Long countryId) {

		this.countryId = countryId;
	}

	public String getCountryName() {

		return countryName;
	}

	public void setCountryName(String countryName) {

		this.countryName = countryName;
	}

	private List<TypeResponse> functionList;

	public List<TypeResponse> getFunctionList() {

		return functionList;
	}

	public void setFunctionList(List<TypeResponse> functionList) {

		this.functionList = functionList;
	}

/*	public List<TypeResponse> getCountryList() {

		return countryList;
	}

	public void setCountryList(List<TypeResponse> countryList) {

		this.countryList = countryList;
	}*/

	public String getCategoryName() {

		return categoryName;
	}

	public void setCategoryName(String categoryName) {

		this.categoryName = categoryName;
	}

	public String getDivisionName() {

		return divisionName;
	}

	public void setDivisionName(String divisionName) {

		this.divisionName = divisionName;
	}

	public String getSaleName() {

		return saleName;
	}

	public void setSaleName(String saleName) {

		this.saleName = saleName;
	}

	public Long getCategoryId() {

		return categoryId;
	}

	public void setCategoryId(Long categoryId) {

		this.categoryId = categoryId;
	}

	public Long getDivisionId() {

		return divisionId;
	}

	public void setDivisionId(Long divisionId) {

		this.divisionId = divisionId;
	}

	public Long getSaleId() {

		return saleId;
	}

	public void setSaleId(Long saleId) {

		this.saleId = saleId;
	}

	public List<CategoryModel> getCategoryList() {

		return categoryList;
	}

	public void setCategoryList(List<CategoryModel> categoryList) {

		this.categoryList = categoryList;
	}

	public List<DivisionReq> getDivisionList() {

		return divisionList;
	}

	public void setDivisionList(List<DivisionReq> divisionList) {

		this.divisionList = divisionList;
	}

	public List<SaleReq> getSaleList() {

		return saleList;
	}

	public void setSaleList(List<SaleReq> saleList) {

		this.saleList = saleList;
	}

	public List<Status> getStatusList() {

		return statusList;
	}

	public void setStatusList(List<Status> statusList) {

		this.statusList = statusList;
	}

	public List<BillingLocation> getBillingLocations() {

		return billingLocations;
	}

	public void setBillingLocations(List<BillingLocation> billingLocations) {

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

	public List<AdditionalContacts> getAdditionalContacts() {

		return additionalContacts;
	}

	public void setAdditionalContacts(
			List<AdditionalContacts> additionalContacts) {

		this.additionalContacts = additionalContacts;
	}

}
