package com.dpu.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

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

	/**
	 * Instantiates a new failed.
	 */
	public Failed() {
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuxiliary() {
		return auxiliary;
	}

	public void setAuxiliary(String auxiliary) {
		this.auxiliary = auxiliary;
	}

	public Object getParms() {
		return parms;
	}

	public void setParms(Object parms) {
		this.parms = parms;
	}
}

