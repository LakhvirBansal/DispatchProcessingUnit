package com.dpu.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javafx.beans.property.SimpleStringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
// @JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

		"fierCode", "borderAgent", "borderCrossing", "ps", "phoneNo", "ext", "faxNo", "email", "openFrom", "openTo",
		"Comments"

})
public class NewBorderAgent implements Serializable {

	private Long borderAgentId = null;
	private Long customBrokerId;
	private SimpleStringProperty fierCode = new SimpleStringProperty();
	private SimpleStringProperty borderAgent = new SimpleStringProperty();
	private SimpleStringProperty borderCrossing = new SimpleStringProperty();
	private SimpleStringProperty ps = new SimpleStringProperty();
	private SimpleStringProperty phoneNo = new SimpleStringProperty();
	private SimpleStringProperty ext = new SimpleStringProperty();
	private SimpleStringProperty faxNo = new SimpleStringProperty();
	private SimpleStringProperty email = new SimpleStringProperty();
	private SimpleStringProperty openFrom = new SimpleStringProperty();
	private SimpleStringProperty openTo = new SimpleStringProperty();
	private SimpleStringProperty comments = new SimpleStringProperty();
	// private SimpleStringProperty faxNo = new SimpleStringProperty();

	public Long getBorderAgentId() {
		return borderAgentId;
	}

	public NewBorderAgent(SimpleStringProperty fierCode, SimpleStringProperty borderAgent,
			SimpleStringProperty borderCrossing, SimpleStringProperty ps, SimpleStringProperty phoneNo,
			SimpleStringProperty ext, SimpleStringProperty faxNo, SimpleStringProperty email,
			SimpleStringProperty openFrom, SimpleStringProperty openTo, SimpleStringProperty comments) {
		super();
		this.fierCode = fierCode;
		this.borderAgent = borderAgent;
		this.borderCrossing = borderCrossing;
		this.ps = ps;
		this.phoneNo = phoneNo;
		this.ext = ext;
		this.faxNo = faxNo;
		this.email = email;
		this.openFrom = openFrom;
		this.openTo = openTo;
		comments = comments;
	}

	public void setBorderAgentId(Long borderAgentId) {
		this.borderAgentId = borderAgentId;
	}

	public Long getCustomBrokerId() {
		return customBrokerId;
	}

	public void setCustomBrokerId(Long customBrokerId) {
		this.customBrokerId = customBrokerId;
	}

	public SimpleStringProperty getFierCode() {
		return fierCode;
	}

	public void setFierCode(SimpleStringProperty fierCode) {
		this.fierCode = fierCode;
	}

	public SimpleStringProperty getBorderAgent() {
		return borderAgent;
	}

	public void setBorderAgent(SimpleStringProperty borderAgent) {
		this.borderAgent = borderAgent;
	}

	public SimpleStringProperty getBorderCrossing() {
		return borderCrossing;
	}

	public void setBorderCrossing(SimpleStringProperty borderCrossing) {
		this.borderCrossing = borderCrossing;
	}

	public SimpleStringProperty getPs() {
		return ps;
	}

	public void setPs(SimpleStringProperty ps) {
		this.ps = ps;
	}

	public SimpleStringProperty getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(SimpleStringProperty phoneNo) {
		this.phoneNo = phoneNo;
	}

	public SimpleStringProperty getExt() {
		return ext;
	}

	public void setExt(SimpleStringProperty ext) {
		this.ext = ext;
	}

	public SimpleStringProperty getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(SimpleStringProperty faxNo) {
		this.faxNo = faxNo;
	}

	public SimpleStringProperty getEmail() {
		return email;
	}

	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}

	public SimpleStringProperty getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom(SimpleStringProperty openFrom) {
		this.openFrom = openFrom;
	}

	public SimpleStringProperty getOpenTo() {
		return openTo;
	}

	public void setOpenTo(SimpleStringProperty openTo) {
		this.openTo = openTo;
	}

	public SimpleStringProperty getComments() {
		return comments;
	}

	public void setComments(SimpleStringProperty comments) {
		comments = comments;
	}

}
