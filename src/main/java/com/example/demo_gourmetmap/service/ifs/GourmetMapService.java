package com.example.demo_gourmetmap.service.ifs;

import com.example.demo_gourmetmap.vo.GourmetMapRes;

public interface GourmetMapService {
	
	// 題目 : 美食地圖

	// 第一題 --> 新增 (城市,店名,店家評價)
	public GourmetMapRes addGourmetMap(String city, String name);

	// 第一題 --> 更改 (城市)
	public GourmetMapRes changGourmetMap(String city, String name);
	
	// 第二題 --> 新增 (城市,店名,餐點評價)
	public GourmetMapRes addNameFood(String name, String food, int price, int foodComment);
	
	// 第二題 --> 修改 (店家評價)
	public GourmetMapRes changeNameComment(String name, String food, int price, int foodComment);

	// 第三題 --> 搜尋特定城市 (找出所有店家)
	public GourmetMapRes city(String city, int limit);

	// 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	public GourmetMapRes nameCommentNumber(double nameComment);

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	public GourmetMapRes nameCommentAndFoodComment(double nameComment, int foodComment);
	
	
}
