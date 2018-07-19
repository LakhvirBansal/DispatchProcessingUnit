/**
 * 
 */
package com.dpu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author jagvir
 *
 */

@Entity
@Table(name = "equipmentmaster")
public class Equipment {

	@Id
	@Column(name = "equipment_id")
	@GeneratedValue
	private Long equipmentId;

	@Column(name = "equipment_name")
	private String equipmentName;

	@Column(name = "description")
	private String description;

	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "modified_on")
	private Date modifiedOn;
	
	
	@ManyToOne
	//@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_id")
	private Type type;
	
 
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
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
}
