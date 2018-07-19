package com.dpu.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.util.ErrorCodeConstants;

/**
 * The Class Failed.
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
public class Failed {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The code. */
	@JsonProperty("error-code")
	private long code;

	/** The message. */
	@JsonProperty("error-message")
	private String message;

	/** The auxiliary. */
	@JsonProperty("error-auxiliary-message")
	private String auxiliary;

	/** The parms. */
	@JsonProperty("params")
	private Object parms;

	private Object resultList;
	

	/**
	 * Instantiates a new failed.
	 */
	public Failed() {
	}

	/**
	 * Instantiates a new failed.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param auxiliary
	 *            the auxiliary
	 */
	public Failed(int code, String message, String auxiliary) {
		this.code = code;
		this.message = message;
		this.auxiliary = auxiliary;
	}

	/**
	 * Sets the failed.
	 *
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param auxiliary
	 *            the auxiliary
	 */
	public void setFailed(int code, String message, String auxiliary) {
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
		// this.code = code;
		this.code = Integer.valueOf(String.valueOf(code));
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

	public Object getResultList() {
		return resultList;
	}
	
	public void setResultList(Object resultList) {
		this.resultList = resultList;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.is.service.model.Response#setAuxiliary(java.lang.String)
	 */
	public void setAuxiliary(String auxiliary) {
		this.auxiliary = auxiliary;
	}

	public Object getParms() {
		return parms;
	}

	public void setParms( Object parms) {
		this.parms = parms;
	}

}

