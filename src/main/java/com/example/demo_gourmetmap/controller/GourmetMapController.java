package com.example.demo_gourmetmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_gourmetmap.constants.GourmetMapRtnCode;
import com.example.demo_gourmetmap.service.ifs.GourmetMapService;
import com.example.demo_gourmetmap.vo.GourmetMapReq;
import com.example.demo_gourmetmap.vo.GourmetMapRes;

@RestController
public class GourmetMapController {

	@Autowired
	GourmetMapService gourmetMapService;

	// 第一題 --> 新增 (城市,店名,店家評價)
	@PostMapping(value = "/api/add_gourmet_map")
	public GourmetMapRes addGourmetMap(@RequestBody GourmetMapReq req) {

		// 判斷 "店名" 不能為空,null ;
		if (!StringUtils.hasText(req.getName())) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_REQUIRED.getMessage());
		}

		// 判斷 "城市" 不能為空,null ;
		if (!StringUtils.hasText(req.getCity())) {
			return new GourmetMapRes(GourmetMapRtnCode.CITY_REQUIRED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.addGourmetMap(req.getCity(), req.getName());

		// 如果 "店名" 已經存在 ;
		if (res == null) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_EXISTED.getMessage());
		}

		return new GourmetMapRes(res, GourmetMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 第一題 --> 更改 (城市)
	@PostMapping(value = "/api/change_gourmet_map")
	public GourmetMapRes changeGourmetMap(@RequestBody GourmetMapReq req) {

		// 判斷 "店名" 不能為空,null ;
		if (!StringUtils.hasText(req.getName())) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_REQUIRED.getMessage());
		}

		// 判斷 "城市" 不能為空,null ;
		if (!StringUtils.hasText(req.getCity())) {
			return new GourmetMapRes(GourmetMapRtnCode.CITY_REQUIRED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.changGourmetMap(req.getCity(), req.getName());

		// 如果 "店名" 不存在 ;
		if (res == null) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_NOT_EXISTED.getMessage());
		}

		return new GourmetMapRes(res, GourmetMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 第二題 --> 新增 (城市,店名,餐點評價)
	@PostMapping(value = "/api/add_name_food")
	public GourmetMapRes addNameFood(@RequestBody GourmetMapReq req) {

		// 判斷 "店名" 不能為空,null ;
		if (!StringUtils.hasText(req.getName())) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_REQUIRED.getMessage());
		}

		// 判斷 "食物" 不能為空,null ;
		if (!StringUtils.hasText(req.getFood())) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_REQUIRED.getMessage());
		}

		// 如果 "食物評價" 不能大於 5 或者 小於等於0 ;
		if (req.getFoodComment() > 5 || req.getFoodComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_COMMENNT_FALLURE.getMessage());

		}

		// 如果 "價錢" 小於等於0 ;
		if (req.getPrice() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.PRICE_REQURED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.addNameFood(req.getName(), req.getFood(), req.getPrice(),
				req.getFoodComment());

		// 如果 第二張表存在,第一張表不存在 ;
		if (res == null) {
			return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		}

		return new GourmetMapRes(res, GourmetMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 第二題 --> 修改 (店家評價)
	@PostMapping(value = "/api/change_name_comment")
	public GourmetMapRes changeNameComment(@RequestBody GourmetMapReq req) {

		// 判斷 "店名" 不能為空,null ;
		if (!StringUtils.hasText(req.getName())) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_REQUIRED.getMessage());
		}

		// 判斷 "食物" 不能為空,null ;
		if (!StringUtils.hasText(req.getFood())) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_REQUIRED.getMessage());
		}

		// 如果 "食物評價" 不能大於 5 或者 小於等於0 ;
		if (req.getFoodComment() > 5 || req.getFoodComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_COMMENNT_FALLURE.getMessage());
		}

		// 如果 "價錢" 小於等於0 ;
		if (req.getPrice() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.PRICE_REQURED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.changeNameComment(req.getName(), req.getFood(), req.getPrice(),
				req.getFoodComment());

		// 如果 第二張表不存在,第一張表不存在 ;
		if (res == null) {
			return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		}

		return new GourmetMapRes(res, GourmetMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 第三題 --> 搜尋特定城市 (找出所有店家)
	@PostMapping(value = "/api/city_change")
	public GourmetMapRes cityChange(@RequestBody GourmetMapReq req) {

		// 如果 "筆數" 小於 0 ;
		if (req.getLimit() < 0) {
			return new GourmetMapRes(GourmetMapRtnCode.LIMIT_REQURED.getMessage());
		}

		// 判斷 "城市" 不能為空,null ;
		if (!StringUtils.hasText(req.getCity())) {
			return new GourmetMapRes(GourmetMapRtnCode.CITY_REQUIRED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.city(req.getCity(), req.getLimit());

		// 如果 "城市" 不存在 ;
		if (res == null) {
			return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		}

		return new GourmetMapRes(res, GourmetMapRtnCode.SUCCESSFUL.getMessage());

	}

	// 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@PostMapping(value = "/api/name_comment_star")
	public GourmetMapRes nameCommentStar(@RequestBody GourmetMapReq req) {

		// 如果 "店家評價" 大於等於 5 或者 小於等於 0
		if (req.getNameComment() >= 5 || req.getNameComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_COMMENNT_FALLURE.getMessage());
		}

		GourmetMapRes res = gourmetMapService.nameCommentNumber(req.getNameComment());

		// 如果 "店家評價" null 或者 空 ;
		if (res == null) {
			return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		}

		return new GourmetMapRes(res, GourmetMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@PostMapping(value = "/api/name_comment_and_food_comment")
	public GourmetMapRes nameCommentAndFoodComment(@RequestBody GourmetMapReq req) {

		// 如果 "店家評價" 大於等於 5 或者 小於等於 0
		if (req.getNameComment() >= 5 || req.getNameComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_COMMENNT_FALLURE.getMessage());
		}

		// 如果 "食物評價" 大於等於 5 或者 小於等於 0
		if (req.getFoodComment() >= 5 || req.getFoodComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_COMMENNT_FALLURE.getMessage());
		}

		GourmetMapRes res = gourmetMapService.nameCommentAndFoodComment(req.getNameComment(), req.getFoodComment());

		// 如果 "店家評價, 食物評價 " null 或者 空 ;
		if (res == null) {
			return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		}

		return new GourmetMapRes(res, GourmetMapRtnCode.SUCCESSFUL.getMessage());
	}
}
