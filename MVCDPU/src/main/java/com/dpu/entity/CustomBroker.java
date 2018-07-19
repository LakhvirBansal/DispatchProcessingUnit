package com.dpu.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


@Entity
@Table(name = "custombrokermaster")
@JsonSerialize(include = Inclusion.NON_NULL)
public class CustomBroker {
	
	@Id
	@Column(name = "custom_broker_id")
	@GeneratedValue
	private Long customBrokerId;
	

	@Column(name = "custom_broker")
	private String customBrokerName;
	
	/*@Column(name = "contact_name")
	private String contactName;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "ext")
	private String extention;
	
	@Column(name = "fax_no")
	private String faxNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private Type type;
	
	/*@Column(name = "email")
	private String email;
	
	@Column(name = "website")
	private String website;*/
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = CustomBrokerType.class)
	@JoinColumn(name = "custom_broker_id", referencedColumnName = "custom_broker_id")
	private List<CustomBrokerType> customerBrokerTypes;

	public Long getCustomBrokerId() {
		return customBrokerId;
	}

	public void setCustomBrokerId(Long customBrokerId) {
		this.customBrokerId = customBrokerId;
	}

	public String getCustomBrokerName() {
		return customBrokerName;
	}

	public void setCustomBrokerName(String customBrokerName) {
		this.customBrokerName = customBrokerName;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<CustomBrokerType> getCustomerBrokerTypes() {
		return customerBrokerTypes;
	}

	public void setCustomerBrokerTypes(List<CustomBrokerType> customerBrokerTypes) {
		this.customerBrokerTypes = customerBrokerTypes;
	}

	/*public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
	}*/

	
	
}
