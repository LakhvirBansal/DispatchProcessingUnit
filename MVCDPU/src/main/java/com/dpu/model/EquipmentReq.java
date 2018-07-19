package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.dpu.entity.Type;

@JsonIgnoreProperties(ignoreUnknown = true) 
@JsonSerialize(include = Inclusion.NON_NULL)
public class EquipmentReq {

	 
	//@JsonProperty(value = "equipment_id")
	private Long equipmentId;

	//@JsonProperty(value = "equipment_name")
	private String equipmentName;

	//@JsonProperty(value = "description")
	private String description;
	
	private String type;
	
	private Long typeId;
	
	private List<TypeResponse> typeList;
	
	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TypeResponse> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TypeResponse> typeList) {
		this.typeList = typeList;
	}
}

