package com.example.demo_gourmetmap.constants;

public enum GourmetMapMsgCode {
	
	SUCCESSFUL("200", "成功"),
	
	FAILED("400","失敗"),

	NAME_REQUIRED("400", "姓名必填"),

	CITY_REQUIRED("400", "城市必填"),

	FOOD_REQUIRED("400", "食物必填"),
	
	STORE_COMMENNT_REQUIRED("400", "店家評價必填"),

	STORE_COMMENNT_FAILURE("400", "店家評價不符合"),

	FOOD_COMMENNT_FALLURE("400", "餐點評價不符合"),

	PRICE_REQURED("400", "價錢必填"),
	
	PRICE_FAILURE("400", "價錢不符合"),

	LIMIT_REQURED("400", "限制比數必填"),
	
	LIMIT_FAILURE("400", "限制比數不符合"),

	NAME_NOT_EXISTED("403", "店名不存在"),
	
	NAME_EXISTED("403", "店名已存在"),
	
	CITY_NOT_EXISTED("403", "城市不存在");

	private String message;

	private String code;

	private GourmetMapMsgCode(String code, String message) {
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
