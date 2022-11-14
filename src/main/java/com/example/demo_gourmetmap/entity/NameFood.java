package com.example.demo_gourmetmap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "namefood")
@IdClass(value = NameGourmetMapFood.class)
public class NameFood {
	@Id
	@Column(name = "name2")
	private String name;

	@Id
	@Column(name = "food")
	private String food;

	@Column(name = "price")
	private int price;

	@Column(name = "food_comment")
	private int foodComment;

	public NameFood() {

	}

	public NameFood(String name, String food, int price, int foodComment) {
		this.food = food;
		this.foodComment = foodComment;
		this.name = name;
		this.price = price;
	}

	public NameFood(String name, String food) {
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
}
