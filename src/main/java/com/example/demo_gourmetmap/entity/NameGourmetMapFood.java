package com.example.demo_gourmetmap.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NameGourmetMapFood implements Serializable {

	private String name;

	private String food;

	public NameGourmetMapFood() {

	}

	public NameGourmetMapFood(String name, String food) {
		this.food = food;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

}
