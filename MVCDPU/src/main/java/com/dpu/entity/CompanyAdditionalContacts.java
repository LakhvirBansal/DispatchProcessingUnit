package com.dpu.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@Table(name = "additionalcontactmaster")
@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyAdditionalContacts {

	@Id
	@Column(name = "add_contact_id")
	@GeneratedValue
	private Long additionalContactId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "position")
	private String position;

	@Column(name = "phone")
	private String phone;

	@Column(name = "ext")
	private String ext;

	@Column(name = "fax")
	private String fax;

	@Column(name = "additional_contact_prefix")
	private String prefix;

	@Column(name = "cellular")
	private String cellular;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;

	@Column(name = "email")
	private String email;

	// private String functionName;

	// private Long functionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "function_id")
	private Type function;

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private Type country;*/

	/*public Type getCountry() {

		return country;
	}

	public void setCountry(Type country) {

		this.country = country;
	}*/

	public Type getFunction() {

		return function;
	}

	public void setFunction(Type function) {

		this.function = function;
	}

	@Transient
	private Map<Integer, List<CompanyWorkingHours>> map = new HashMap<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

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

	public Status getStatus() {

		return status;
	}

	public void setStatus(Status status) {

		this.status = status;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public Map<Integer, List<CompanyWorkingHours>> getMap() {

		return map;
	}

	public void setMap(Map<Integer, List<CompanyWorkingHours>> map) {

		this.map = map;
	}

	public Company getCompany() {

		return company;
	}

	public void setCompany(Company company) {

		this.company = company;
	}
}
