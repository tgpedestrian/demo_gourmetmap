package com.example.demo_gourmetmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gourmetmap")
public class GourmetMap {
	@Id
	@Column(name = "name")
	private String name;

	@Column(name = "city")
	private String city;

	@Column(name = "name_comment")
	private double nameComment;

	public GourmetMap() {

	}

	public GourmetMap(String city, String name, double nameComment) {
		this.city = city;
		this.name = name;
		this.nameComment = nameComment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getNameComment() {
		return nameComment;
	}

	public void setNameComment(double nameComment) {
		this.nameComment = nameComment;
	}
}
