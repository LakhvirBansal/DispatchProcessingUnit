package com.dpu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class AdditionalContacts {

	private Long additionalContactId;

	private String customerName;

	private String position;

	private String phone;

	private String ext;

	private String fax;

	private String prefix;

	private String cellular;

	private Long statusId;

	private String statusName;

	private String email;

	private String functionName;

	private Long functionId;

	private String countryName;

	private Long countryId;

	private TypeResponse country;

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

	public TypeResponse getCountry() {

		return country;
	}

	public void setCountry(TypeResponse country) {

		this.country = country;
	}

	private Long companyId;

	public Long getCompanyId() {

		return companyId;
	}

	public void setCompanyId(Long companyId) {

		this.companyId = companyId;
	}

	private TypeResponse function;

	public String getFunctionName() {

		return functionName;
	}

	public void setFunctionName(String functionName) {

		this.functionName = functionName;
	}

	public TypeResponse getFunction() {

		return function;
	}

	public void setFunction(TypeResponse function) {

		this.function = function;
	}

	public Long getFunctionId() {

		return functionId;
	}

	public void setFunctionId(Long functionId) {

		this.functionId = functionId;
	}

	public Long getAdditionalContactId() {

		return additionalContactId;
	}

	public void setAdditionalContactId(Long additionalContactId) {

		this.additionalContactId = additionalContactId;
	}

	public String getCustomerName() {

		return customerName;
	}

	public void setCustomerName(String customerName) {

		this.customerName = customerName;
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

	public String getPrefix() {

		return prefix;
	}

	public void setPrefix(String prefix) {

		this.prefix = prefix;
	}

	public String getCellular() {

		return cellular;
	}

	public void setCellular(String cellular) {

		this.cellular = cellular;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getStatusName() {

		return statusName;
	}

	public void setStatusName(String statusName) {

		this.statusName = statusName;
	}

	public Long getStatusId() {

		return statusId;
	}

	public void setStatusId(Long statusId) {

		this.statusId = statusId;
	}
}
