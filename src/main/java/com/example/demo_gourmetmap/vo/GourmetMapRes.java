package com.example.demo_gourmetmap.vo;

import java.util.List;

import com.example.demo_gourmetmap.entity.GourmetMap;
import com.example.demo_gourmetmap.entity.NameFood;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GourmetMapRes {

	private String name;

	private String city;

	private Integer nameComment;

	private NameFood nameFood;

	private String message;

	private GourmetMapRes reg;

	private GourmetMap gourmetMap;

	private GourmetMapRes res;

	private String string;

	private List<GourmetMap> gourmetMapList;

	private List<GourmetMapForFrontEnd> gourmetMapForFrontEndList;

	private List<NameFood> nameFoodList;

//	private List<String> strList;

	private List<GourmetMapForFrontEnd> gourmetMapForFrontEnd;

	public GourmetMapRes(GourmetMapRes res, String message) {
		this.res = res;
		this.message = message;
	}

//	public GourmetMapRes(List<String> strList, String message) {
//		this.strList = strList;
//		this.message = message;
//	}
	public GourmetMapRes() {

	}

	public GourmetMapRes(GourmetMap gourmetMap) {
		this.gourmetMap = gourmetMap;
	}

	public GourmetMapRes(NameFood nameFood, String message) {
		this.message = message;
		this.nameFood = nameFood;
	}

	public GourmetMapRes(String message) {
		this.message = message;
	}

	public GourmetMapRes(GourmetMap gourmetMap, String string) {
		this.gourmetMap = gourmetMap;
		this.string = string;
	}

	public GourmetMapRes(GourmetMapRes reg) {
		this.reg = reg;
	}

	public NameFood getNameFood() {
		return nameFood;
	}

	public void setNameFood(NameFood nameFood) {
		this.nameFood = nameFood;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GourmetMapRes getReg() {
		return reg;
	}

	public void setReg(GourmetMapRes reg) {
		this.reg = reg;
	}

	public GourmetMap getGourmetMap() {
		return gourmetMap;
	}

	public void setGourmetMap(GourmetMap gourmetMap) {
		this.gourmetMap = gourmetMap;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

//	public List<String> getStrList() {
//		return strList;
//	}
//
//	public void setStrList(List<String> strList) {
//		this.strList = strList;
//	}

	public List<NameFood> getNameFoodList() {
		return nameFoodList;
	}

	public void setNameFoodList(List<NameFood> nameFoodList) {
		this.nameFoodList = nameFoodList;
	}

	public List<GourmetMapForFrontEnd> getGourmetMapForFrontEndList() {
		return gourmetMapForFrontEndList;
	}

	public void setGourmetMapForFrontEndList(List<GourmetMapForFrontEnd> gourmetMapForFrontEndList) {
		this.gourmetMapForFrontEndList = gourmetMapForFrontEndList;
	}

	public List<GourmetMapForFrontEnd> getGourmetMapForFrontEnd() {
		return gourmetMapForFrontEnd;
	}

	public void setGourmetMapForFrontEnd(List<GourmetMapForFrontEnd> gourmetMapForFrontEnd) {
		this.gourmetMapForFrontEnd = gourmetMapForFrontEnd;
	}

	public List<GourmetMap> getGourmetMapList() {
		return gourmetMapList;
	}

	public void setGourmetMapList(List<GourmetMap> gourmetMapList) {
		this.gourmetMapList = gourmetMapList;
	}

	public GourmetMapRes getRes() {
		return res;
	}

	public void setRes(GourmetMapRes res) {
		this.res = res;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getNameComment() {
		return nameComment;
	}

	public void setNameComment(Integer nameComment) {
		this.nameComment = nameComment;
	}

}
