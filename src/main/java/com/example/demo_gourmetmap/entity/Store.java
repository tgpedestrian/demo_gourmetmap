package com.example.demo_gourmetmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gourmetmap")
public class Store {
	@Id
	@Column(name = "name")
	private String storeName;

	@Column(name = "city")
	private String storeCity;

	@Column(name = "name_comment")
	private double storeComment;

	public Store() {

	}

	public Store(String storeCity, String storeName, double storeComment) {
		this.storeCity = storeCity;
		this.storeName = storeName;
		this.storeComment = storeComment;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreCity() {
		return storeCity;
	}

	public void setStoreCity(String storeCity) {
		this.storeCity = storeCity;
	}

	public double getStoreComment() {
		return storeComment;
	}

	public void setStoreComment(double storeComment) {
		this.storeComment = storeComment;
	}

	
}
