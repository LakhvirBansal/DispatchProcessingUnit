package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The Class Success.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Success {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The code. */
//	@JsonProperty("success-code")
	private long code;

	/** The message. */
//	@JsonProperty("success-message")
	private String message;

	/** The auxiliary. */
//	@JsonProperty("success-auxiliary")
	private String auxiliary;

	/** The parms. */
//	@JsonProperty("params")
	private Object parms;
	
	private Object resultList;

	/**
	 * Instantiates a new success.
	 */
	public Success() {
	}

	/**
	 * Instantiates a new success.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param auxiliary
	 *            the auxiliary
	 */
	public Success(int code, String message, String auxiliary) {
		this.code = code;
		this.message = message;
		this.auxiliary = auxiliary;
	}

	/**
	 * Sets the success.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param auxiliary
	 *            the auxiliary
	 */
	public void setSuccess(int code, String message, String auxiliary) {
		this.code = code;
		this.message = message;
		this.auxiliary = auxiliary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.is.service.model.Response#getCode()
	 */
	public long getCode() {
		return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.is.service.model.Response#setCode(long)
	 */
	public void setCode(long code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.is.service.model.Response#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.is.service.model.Response#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.is.service.model.Response#getAuxiliary()
	 */
	public String getAuxiliary() {
		return auxiliary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.is.service.model.Response#setAuxiliary(java.lang.String)
	 */
	public void setAuxiliary(String auxiliary) {
		this.auxiliary = auxiliary;
	}

	/**
	 * Gets the parms.
	 *
	 * @return the parms
	 */
	public Object getParms() {
		return parms;
	}

	/**
	 * Sets the parms.
	 *
	 * @param parms
	 *            the parms
	 */
	public void setParms(Object parms) {
		this.parms = parms;
	}

	public Object getResultList() {
		return resultList;
	}

	public void setResultList(Object resultList) {
		this.resultList = resultList;
	}



}
