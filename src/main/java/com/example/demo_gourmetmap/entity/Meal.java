package com.example.demo_gourmetmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "namefood")
@IdClass(value = MealId.class)
public class Meal {
	@Id
	@Column(name = "name")
	private String storeName;

	@Id
	@Column(name = "food")
	private String mealFood;

	@Column(name = "price")
	private int mealPrice;

	@Column(name = "food_comment")
	private int mealComment;

	public Meal() {

	}

	public Meal(String storeName, String mealFood, int mealPrice, int mealComment) {
		this.mealFood = mealFood;
		this.mealComment = mealComment;
		this.storeName = storeName;
		this.mealPrice = mealPrice;
	}

	public Meal(String storeName, String mealFood) {
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

	public int getMealPrice() {
		return mealPrice;
	}

	public void setMealPrice(int mealPrice) {
		this.mealPrice = mealPrice;
	}

	public int getMealComment() {
		return mealComment;
	}

	public void setMealComment(int mealComment) {
		this.mealComment = mealComment;
	}

}
