package com.example.demo_gourmetmap.service.ifs;

import com.example.demo_gourmetmap.vo.GourmetMapRes;

public interface GourmetMapService {
	
	// 題目 : 美食地圖

	// 第一題 --> 新增 店家的(城市,店名,店家評價)
	public GourmetMapRes addStore(String storeCity, String storeName);

	// 第一題 --> 更改 店家的(城市)
	public GourmetMapRes changeStore(String storeCity, String storeName);
	
	// 第二題 --> 新增 餐點的(店名,餐點,價格,餐點評價)
	public GourmetMapRes addMeal(String mealName, String mealFood, int mealPrice, int mealComment);
	
	// 第二題 --> 修改 (店家評價)
	public GourmetMapRes changeNameComment(String mealName, String mealFood, int mealPrice, int mealComment);

	// 第三題 --> 搜尋特定城市 (找出所有店家)
	public GourmetMapRes storeCity(String storeCity, int limit);

	// 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	public GourmetMapRes storeCommentNumber(double storeComment);

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	public GourmetMapRes storeCommentAndMealComment(double storeComment, int mealComment);
	
	
}
