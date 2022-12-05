package com.example.demo_gourmetmap.vo;

public class GourmetMapReq {

	//店家店名
	private String storeName;

	//餐點
	private String mealFood;

	//店家店名
	private String mealName;

	//店家城市
	private String storeCity;

	//限制比數
	private int limit;

	//餐點價錢
	private int mealPrice;

	//餐點評價
	private int mealComment;

	//店家評價
	private double storeComment;

	public GourmetMapReq() {

	}

	public GourmetMapReq(String storeName, String storeCity) {
		this.storeName = storeName;
		this.storeCity = storeCity;
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

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getStoreCity() {
		return storeCity;
	}

	public void setStoreCity(String storeCity) {
		this.storeCity = storeCity;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
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

	public double getStoreComment() {
		return storeComment;
	}

	public void setStoreComment(double storeComment) {
		this.storeComment = storeComment;
	}

}
