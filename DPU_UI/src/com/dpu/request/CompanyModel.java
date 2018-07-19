
package com.dpu.request;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.model.Category;
import com.dpu.model.Division;
import com.dpu.model.Sale;
import com.dpu.model.Type;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)

public class CompanyModel {

	@JsonProperty("companyId")
	private Long companyId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("address")
	private String address;
	@JsonProperty("unitNo")
	private String unitNo;
	@JsonProperty("city")
	private String city;
	@JsonProperty("provinceState")
	private String provinceState;
	@JsonProperty("zip")
	private String zip;
	@JsonProperty("email")
	private String email;
	@JsonProperty("website")
	private String website;
	@JsonProperty("categoryId")
	private Long categoryId;
	@JsonProperty("divisionId")
	private Long divisionId;
	@JsonProperty("saleId")
	private Long saleId;
	@JsonProperty("countryId")
	private Long countryId;
	@JsonProperty("categoryName")
	private String categoryName;
	@JsonProperty("divisionName")
	private String divisionName;
	@JsonProperty("saleName")
	private String saleName;
	@JsonProperty("countryName")
	private String countryName;
	@JsonProperty("categoryList")
	private List<Category> categoryList;
	@JsonProperty("divisionList")
	private List<Division> divisionList;
	@JsonProperty("saleList")
	private List<Sale> saleList;
	@JsonProperty("countryList")
	private List<Type> countryList;

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

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Division> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<Division> divisionList) {
		this.divisionList = divisionList;
	}

	public List<Sale> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<Sale> saleList) {
		this.saleList = saleList;
	}

	public List<Type> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Type> countryList) {
		this.countryList = countryList;
	}

	@JsonProperty("billingLocations")
	private List<BillingLocation> billingLocations = null;
	@JsonProperty("additionalContacts")
	private List<AdditionalContact> additionalContacts = null;

	@JsonProperty("companyId")
	public Long getCompanyId() {
		return companyId;
	}

	@JsonProperty("companyId")
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("address")
	public String getAddress() {
		return address;
	}

	@JsonProperty("address")
	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty("unitNo")
	public String getUnitNo() {
		return unitNo;
	}

	@JsonProperty("unitNo")
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	@JsonProperty("city")
	public String getCity() {
		return city;
	}

	@JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}

	@JsonProperty("provinceState")
	public String getProvinceState() {
		return provinceState;
	}

	@JsonProperty("provinceState")
	public void setProvinceState(String provinceState) {
		this.provinceState = provinceState;
	}

	@JsonProperty("zip")
	public String getZip() {
		return zip;
	}

	@JsonProperty("zip")
	public void setZip(String zip) {
		this.zip = zip;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("website")
	public String getWebsite() {
		return website;
	}

	@JsonProperty("website")
	public void setWebsite(String website) {
		this.website = website;
	}

	@JsonProperty("billingLocations")
	public List<BillingLocation> getBillingLocations() {
		return billingLocations;
	}

	@JsonProperty("billingLocations")
	public void setBillingLocations(List<BillingLocation> billingLocations) {
		this.billingLocations = billingLocations;
	}

	@JsonProperty("additionalContacts")
	public List<AdditionalContact> getAdditionalContacts() {
		return additionalContacts;
	}

	@JsonProperty("additionalContacts")
	public void setAdditionalContacts(List<AdditionalContact> additionalContacts) {
		this.additionalContacts = additionalContacts;
	}

}
