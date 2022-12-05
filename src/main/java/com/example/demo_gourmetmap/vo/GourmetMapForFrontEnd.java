package com.example.demo_gourmetmap.vo;

import java.util.List;

import com.example.demo_gourmetmap.entity.Meal;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GourmetMapForFrontEnd {

	// 城市
	private String city;
    
	// 店名
	private String storeName; 

	// 店家評價
	private double storeComment; 

	// 食物
	private String food; 

	// 價錢
	private Integer price; 

	// 食物評價
	private Integer mealComment; 

	// meal
	private Meal meal; 

	// List的meal
	private List<Meal> mealList; 

	public GourmetMapForFrontEnd() {

	}

	public GourmetMapForFrontEnd(String storeName, double storeComment, Meal meal) {
		this.storeName = storeName;
		this.storeComment = storeComment;
		this.meal = meal;
	}

	public GourmetMapForFrontEnd(String city, String storeName, double storeComment, Meal meal) {
		this.city = city;
		this.storeName = storeName;
		this.storeComment = storeComment;
		this.meal = meal;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public double getStoreComment() {
		return storeComment;
	}

	public void setStoreComment(double storeComment) {
		this.storeComment = storeComment;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getMealComment() {
		return mealComment;
	}

	public void setMealComment(Integer mealComment) {
		this.mealComment = mealComment;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public List<Meal> getMealList() {
		return mealList;
	}

	public void setMealList(List<Meal> mealList) {
		this.mealList = mealList;
	}

}
