/**
 * 
 */
package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author jagvir
 *
 */

@Entity
@Table(name = "typemaster")
@JsonSerialize(include = Inclusion.NON_NULL)
public class Type {

	@Id
	@Column(name = "type_id")
	@GeneratedValue
	private Long typeId;

	@Column(name = "type_name")
	private String typeName;
	
	@Column(name="type_value")
	private Long value;
	
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
	private Set<Equipment> equipmentSet = new HashSet<Equipment>();*/

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	

}