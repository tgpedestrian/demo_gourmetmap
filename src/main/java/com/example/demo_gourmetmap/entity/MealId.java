package com.example.demo_gourmetmap.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MealId implements Serializable {

	private String storeName;

	private String mealFood;

	public MealId() {

	}

	public MealId(String storeName, String mealFood) {
		this.mealFood = mealFood;
		this.storeName = storeName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getMealFood() {
		return mealFood;
	}

	public void setMealFood(String mealFood) {
		this.mealFood = mealFood;
	}

}
