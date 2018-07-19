package com.dpu.model;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author jagvir
 *
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class Category {


	//@JsonProperty(value = "category_id")
		private Long categoryId;

		//@JsonProperty(value = "type_id")
		private Long typeId;
		
		private String typeName;

		//@JsonProperty(value = "name")
		private String name;

		//@JsonProperty(value = "status")
		private Long statusId;
		
		private String statusName;
		
		private Long highlightId;
		
		private String highlightName;

		//@JsonProperty(value = "created_on")
		private String createdOn;

		//@JsonProperty(value = "created_by")
		private String createdBy;
		
		private List<Status> statusList;
		
		private List<Type> typeList;
		
		private List<Type> highlightList;

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

		public Long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}

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

		public Long getStatusId() {
			return statusId;
		}

		public void setStatusId(Long statusId) {
			this.statusId = statusId;
		}

		public String getStatusName() {
			return statusName;
		}

		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}

		public Long getHighlightId() {
			return highlightId;
		}

		public void setHighlightId(Long highlightId) {
			this.highlightId = highlightId;
		}

		public String getHighlightName() {
			return highlightName;
		}

		public void setHighlightName(String highlightName) {
			this.highlightName = highlightName;
		}

		public List<Status> getStatusList() {
			return statusList;
		}

		public void setStatusList(List<Status> statusList) {
			this.statusList = statusList;
		}

		public List<Type> getTypeList() {
			return typeList;
		}

		public void setTypeList(List<Type> typeList) {
			this.typeList = typeList;
		}

		public List<Type> getHighlightList() {
			return highlightList;
		}

		public void setHighlightList(List<Type> highlightList) {
			this.highlightList = highlightList;
		}
}
