package com.example.demo_gourmetmap.vo;

public class GourmetMapReq {

	private String name;

	private String food;

	private String city;

	private int limit;

	private int price;

	private int foodComment;

	private double nameComment;

	public GourmetMapReq() {

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getFoodComment() {
		return foodComment;
	}

	public void setFoodComment(int foodComment) {
		this.foodComment = foodComment;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public double getNameComment() {
		return nameComment;
	}

	public void setNameComment(double nameComment) {
		this.nameComment = nameComment;
	}

}
