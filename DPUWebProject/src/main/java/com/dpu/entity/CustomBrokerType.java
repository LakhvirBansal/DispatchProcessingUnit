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
@Table(name = "custombrokertype")
@JsonSerialize(include = Inclusion.NON_NULL)
public class CustomBrokerType {
	
	@Id
	@Column(name = "custombrokertype_id")
	@GeneratedValue
	private Long customBrokerTypeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_broker_id")
	private CustomBroker customBroker;

	@Column(name = "contact_name")
	private String contactName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operation")
	private Type operation;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "extension")
	private String extention;
	
	@Column(name = "fax")
	private String faxNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "24hrs")
	private Type timeZone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "tracker_link")
	private String trackerLink;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private Type type;

	public Long getCustomBrokerTypeId() {
		return customBrokerTypeId;
	}

	public void setCustomBrokerTypeId(Long customBrokerTypeId) {
		this.customBrokerTypeId = customBrokerTypeId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public CustomBroker getCustomBroker() {
		return customBroker;
	}

	public void setCustomBroker(CustomBroker customBroker) {
		this.customBroker = customBroker;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Type getOperation() {
		return operation;
	}

	public void setOperation(Type operation) {
		this.operation = operation;
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

	public Type getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Type timeZone) {
		this.timeZone = timeZone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTrackerLink() {
		return trackerLink;
	}

	public void setTrackerLink(String trackerLink) {
		this.trackerLink = trackerLink;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	
}
