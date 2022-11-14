package com.example.demo_gourmetmap.constants;

public enum GourmetMapRtnCode {
	
	SUCCESSFUL("200", "successful"),
	
	FAILED("400","failed"),

	NAME_REQUIRED("400", "name is required"),

	CITY_REQUIRED("400", "city is required"),

	FOOD_REQUIRED("400", "food is required"),

	NAME_COMMENNT_FALLURE("400", "nameComment active failure"),

	FOOD_COMMENNT_FALLURE("400", "foodComment active failure"),

	PRICE_REQURED("400", "price is required"),

	LIMIT_REQURED("400", "limit is required"),

	NAME_EXISTED("403", "name is not existed"),
	
	CITY_EXISTED("403", "city is not existed");

	private String message;

	private String code;

	private GourmetMapRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

}
