package com.example.demo_gourmetmap.vo;

import java.util.List;

import com.example.demo_gourmetmap.entity.NameFood;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GourmetMapForFrontEnd {

	private String city;   //城市

	private String name;   //店名

	private double nameComment;  //店家評價

	private String food;   //食物

	private Integer price; //價錢

	private Integer foodComment; //食物評價

	private NameFood namefood;   //namefood

	private List<NameFood> listnamefood;  //List的NameFood

	public GourmetMapForFrontEnd() {

	}

	public GourmetMapForFrontEnd(String name, double nameComment, NameFood namefood) {
		this.name = name;
		this.nameComment = nameComment;
		this.namefood = namefood;
	}

	public GourmetMapForFrontEnd(String city, String name, double nameComment, NameFood namefood) {
		this.city = city;
		this.name = name;
		this.nameComment = nameComment;
		this.namefood = namefood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getNameComment() {
		return nameComment;
	}

	public void setNameComment(double nameComment) {
		this.nameComment = nameComment;
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

	public Integer getFoodComment() {
		return foodComment;
	}

	public void setFoodComment(Integer foodComment) {
		this.foodComment = foodComment;
	}

	public List<NameFood> getListnamefood() {
		return listnamefood;
	}

	public void setListnamefood(List<NameFood> listnamefood) {
		this.listnamefood = listnamefood;
	}

	public NameFood getNamefood() {
		return namefood;
	}

	public void setNamefood(NameFood namefood) {
		this.namefood = namefood;
	}

}
