package com.example.demo_gourmetmap.vo;

import java.util.List;

import com.example.demo_gourmetmap.entity.Store;
import com.example.demo_gourmetmap.entity.Meal;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GourmetMapRes {
	
    //店家名稱
	private String storeName;

	//城市
	private String storeCity;

	//店家評價
	private Integer storeComment;

	//店家
	private Store store;

	//餐點
	private Meal meal;

	//訊息
	private String message;

	//餐點名稱
	private String mealName;
 
	//餐點食物
	private String mealFood;

	//價錢
	private String mealPrice;

	//餐點評價
	private String mealComment;

	//Res
	private GourmetMapRes res;

	//List 店家
	private List<Store> storeList;
	
    //新呈現給使用者看的畫面
	private List<GourmetMapForFrontEnd> gourmetMapForFrontEndList;

	//List餐點
	private List<Meal> mealList;

	public GourmetMapRes() {

	}

	public GourmetMapRes(Store store, String message) {
		this.store = store;
		this.message = message;
	}

	public GourmetMapRes(String message) {
		this.message = message;
	}

	public GourmetMapRes(GourmetMapRes res, String message) {
		this.res = res;
		this.message = message;
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

	public Integer getStoreComment() {
		return storeComment;
	}

	public void setStoreComment(Integer storeComment) {
		this.storeComment = storeComment;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getMealFood() {
		return mealFood;
	}

	public void setMealFood(String mealFood) {
		this.mealFood = mealFood;
	}

	public String getMealPrice() {
		return mealPrice;
	}

	public void setMealPrice(String mealPrice) {
		this.mealPrice = mealPrice;
	}

	public String getMealComment() {
		return mealComment;
	}

	public void setMealComment(String mealComment) {
		this.mealComment = mealComment;
	}

	public GourmetMapRes getRes() {
		return res;
	}

	public void setRes(GourmetMapRes res) {
		this.res = res;
	}

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public List<GourmetMapForFrontEnd> getGourmetMapForFrontEndList() {
		return gourmetMapForFrontEndList;
	}

	public void setGourmetMapForFrontEndList(List<GourmetMapForFrontEnd> gourmetMapForFrontEndList) {
		this.gourmetMapForFrontEndList = gourmetMapForFrontEndList;
	}

	public List<Meal> getMealList() {
		return mealList;
	}

	public void setMealList(List<Meal> mealList) {
		this.mealList = mealList;
	}

}