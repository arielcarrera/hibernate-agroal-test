package com.github.arielcarrera.hibernate.agroal.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestEntity {

	@Id
	private Integer id;

	private Integer value;

	public TestEntity() {
		super();
	}

	public TestEntity(Integer id, Integer value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
