/**
 * 
 */
package com.dpu.request;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.model.CarrierModel;

/**
 * @author jagvir
 *
 */

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include= Inclusion.NON_NULL)
public class CarrierResponse {

	
	@JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("resultList")
    private List<CarrierModel> resultList = null;

    @JsonProperty("code")
    public Integer getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("resultList")
    public List<CarrierModel> getResultList() {
        return resultList;
    }

    @JsonProperty("resultList")
    public void setResultList(List<CarrierModel> resultList) {
        this.resultList = resultList;
    }

}
