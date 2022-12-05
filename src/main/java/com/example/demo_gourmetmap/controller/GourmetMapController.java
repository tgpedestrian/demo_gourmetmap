package com.example.demo_gourmetmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_gourmetmap.constants.GourmetMapMsgCode;
import com.example.demo_gourmetmap.service.ifs.GourmetMapService;
import com.example.demo_gourmetmap.vo.GourmetMapReq;
import com.example.demo_gourmetmap.vo.GourmetMapRes;

@CrossOrigin // 連接前端
@RestController
public class GourmetMapController {

	@Autowired
	GourmetMapService gourmetMapService;

	// 第一題 --> 新增 店家的 (城市,店名,店家評價)
	@PostMapping(value = "/api/add_store")
	public GourmetMapRes addStore(@RequestBody GourmetMapReq req) {

		// 判斷 "店名" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getStoreName())) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_REQUIRED.getMessage());
		}

		// 判斷 "城市" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getStoreCity())) {
			return new GourmetMapRes(GourmetMapMsgCode.CITY_REQUIRED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.addStore(req.getStoreCity(), req.getStoreName());

		return res;
	}

	// 第一題 --> 更改 店家的 (城市)
	@PostMapping(value = "/api/change_store")
	public GourmetMapRes changeStore(@RequestBody GourmetMapReq req) {

		// 判斷 "店名" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getStoreName())) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_REQUIRED.getMessage());
		}

		// 判斷 "城市" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getStoreCity())) {
			return new GourmetMapRes(GourmetMapMsgCode.CITY_REQUIRED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.changeStore(req.getStoreCity(), req.getStoreName());

		return res;
	}

	// 第二題 --> 新增 餐點 (店名,餐點,價格,餐點評價)
	@PostMapping(value = "/api/add_meal")
	public GourmetMapRes addMeal(@RequestBody GourmetMapReq req) {

		// 判斷 "店名" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getMealName())) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_REQUIRED.getMessage());
		}

		// 判斷 "食物" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getMealFood())) {
			return new GourmetMapRes(GourmetMapMsgCode.FOOD_REQUIRED.getMessage());
		}

		// 如果 "食物評價" 不能 大於 5 或者 小於 1 ;
		if (req.getMealComment() > 5 || req.getMealComment() < 1) {
			return new GourmetMapRes(GourmetMapMsgCode.FOOD_COMMENNT_FALLURE.getMessage());

		}

		// 如果 "價錢" 小於等於 0 ;
		if (req.getMealPrice() <= 0) {
			return new GourmetMapRes(GourmetMapMsgCode.PRICE_FAILURE.getMessage());
		}

		GourmetMapRes res = gourmetMapService.addMeal(req.getMealName(), req.getMealFood(), req.getMealPrice(),
				req.getMealComment());

		return res;
	}

	// 第二題 --> 修改 店家的 (店家評價)
	@PostMapping(value = "/api/change_meal_comment")
	public GourmetMapRes changeMealComment(@RequestBody GourmetMapReq req) {

		// 判斷 "店名" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getMealName())) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_REQUIRED.getMessage());
		}

		// 判斷 "食物" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getMealFood())) {
			return new GourmetMapRes(GourmetMapMsgCode.FOOD_REQUIRED.getMessage());
		}

		// 如果 "食物評價" 不能 大於 5 或者 小於 1 ;
		if (req.getMealComment() > 5 || req.getMealComment() < 1) {
			return new GourmetMapRes(GourmetMapMsgCode.FOOD_COMMENNT_FALLURE.getMessage());
		}

		// 如果 "價錢" 小於等於0 ;
		if (req.getMealPrice() <= 0) {
			return new GourmetMapRes(GourmetMapMsgCode.PRICE_FAILURE.getMessage());
		}

		GourmetMapRes res = gourmetMapService.changeNameComment(req.getMealName(), req.getMealFood(),
				req.getMealPrice(), req.getMealComment());

		return res;
	}

	// 第三題 --> 搜尋在 特定城市 的所有店家 並限制筆數
	@PostMapping(value = "/api/search_city")
	public GourmetMapRes cityChange(@RequestBody GourmetMapReq req) {

		// 如果 "筆數" 小於 0 ;
		if (req.getLimit() < 0) {
			return new GourmetMapRes(GourmetMapMsgCode.LIMIT_FAILURE.getMessage());
		}

		// 判斷 "城市" 不能為 空字串,null ;
		if (!StringUtils.hasText(req.getStoreCity())) {
			return new GourmetMapRes(GourmetMapMsgCode.CITY_REQUIRED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.storeCity(req.getStoreCity(), req.getLimit());

		return res;
	}

	// 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@PostMapping(value = "/api/store_comment_star")
	public GourmetMapRes nameCommentStar(@RequestBody GourmetMapReq req) {

		// 如果 "店家評價" 大於等於 5 或者 小於 1 ;
		if (req.getStoreComment() > 5 || req.getStoreComment() < 1) {
			return new GourmetMapRes(GourmetMapMsgCode.STORE_COMMENNT_FAILURE.getMessage());
		}

		GourmetMapRes res = gourmetMapService.storeCommentNumber(req.getStoreComment());

		return res;
	}

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@PostMapping(value = "/api/store_comment_and_meal_comment_star")
	public GourmetMapRes nameCommentAndFoodComment(@RequestBody GourmetMapReq req) {

		// 如果 "店家評價" 大於 5 或者 小於 1 ;
		if (req.getStoreComment() > 5 || req.getStoreComment() < 1) {
			return new GourmetMapRes(GourmetMapMsgCode.STORE_COMMENNT_FAILURE.getMessage());
		}

		// 如果 "食物評價" 大於等於 5 或者 小於 1 ;
		if (req.getMealComment() > 5 || req.getMealComment() < 1) {
			return new GourmetMapRes(GourmetMapMsgCode.FOOD_COMMENNT_FALLURE.getMessage());
		}

		GourmetMapRes res = gourmetMapService.storeCommentAndMealComment(req.getStoreComment(), req.getMealComment());

		return res;
	}
}
