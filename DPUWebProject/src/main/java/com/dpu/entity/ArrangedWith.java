package com.dpu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "arrangedwith")
public class ArrangedWith {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="arranged_with")
	private String arrangedWith;
	
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArrangedWith() {
		return arrangedWith;
	}

	public void setArrangedWith(String arrangedWith) {
		this.arrangedWith = arrangedWith;
	}
}
