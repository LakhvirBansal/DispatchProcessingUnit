/**
 * 
 */
package com.dpu.entity;

import java.util.HashSet;
import java.util.Set;

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

/**
 * @author jagvir
 *
 */

@Entity
@Table(name = "category")
@JsonSerialize(include = Inclusion.NON_NULL)
public class Category {

	@Id
	@Column(name = "category_id")
	// @JsonProperty(value = "category_id")
	@GeneratedValue
	private Long categoryId;

	@Column(name = "name")
	// @JsonProperty(value = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;

	@Column(name = "created_on")
	// @JsonProperty(value = "created_on")
	private String createdOn;

	@Column(name = "created_by")
	// @JsonProperty(value = "created_by")
	private String createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "highlight_id")
	private Type highLight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private Type type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	private Set<Company> company = new HashSet<>();

	public Set<Company> getCompany() {

		return company;
	}

	public void setCompany(Set<Company> company) {

		this.company = company;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCreatedOn() {

		return createdOn;
	}

	public void setCreatedOn(String createdOn) {

		this.createdOn = createdOn;
	}

	public String getCreatedBy() {

		return createdBy;
	}

	public void setCreatedBy(String createdBy) {

		this.createdBy = createdBy;
	}

	public Status getStatus() {

		return status;
	}

	public void setStatus(Status status) {

		this.status = status;
	}

	public Type getHighLight() {

		return highLight;
	}

	public void setHighLight(Type highLight) {

		this.highLight = highLight;
	}

	public Type getType() {

		return type;
	}

	public void setType(Type type) {

		this.type = type;
	}

	public Long getCategoryId() {

		return categoryId;
	}

	public void setCategoryId(Long categoryId) {

		this.categoryId = categoryId;
	}

}
