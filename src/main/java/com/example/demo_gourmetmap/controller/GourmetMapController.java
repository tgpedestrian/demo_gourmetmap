package com.example.demo_gourmetmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo_gourmetmap.constants.GourmetMapRtnCode;
import com.example.demo_gourmetmap.entity.GourmetMap;
import com.example.demo_gourmetmap.entity.NameFood;
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

		if (!StringUtils.hasText(req.getName())) {    //如果裡面的值是一樣的
			return new GourmetMapRes(GourmetMapRtnCode.NAME_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getCity())) {    //判斷 如果裡面的值是一樣的
			return new GourmetMapRes(GourmetMapRtnCode.CITY_REQUIRED.getMessage());
		}

		GourmetMapRes res = new GourmetMapRes();
		GourmetMap gourmetMap = gourmetMapService.addGourmetMap(req.getCity(), req.getName());
		res.setMessage(GourmetMapRtnCode.SUCCESSFUL.getMessage());
		res.setGourmetMap(gourmetMap);
		return new GourmetMapRes(res);

	}

	// 第一題 --> 更改 (城市)
	@PostMapping(value = "/api/change_gourmet_map")
	public GourmetMapRes changeGourmetMap(@RequestBody GourmetMapReq req) {

		if (!StringUtils.hasText(req.getName())) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getCity())) {
			return new GourmetMapRes(GourmetMapRtnCode.CITY_REQUIRED.getMessage());
		}

		GourmetMap gourmetMap = gourmetMapService.changGourmetMap(req.getCity(), req.getName());
		
		if(gourmetMap == null) {              //city 為 ㄙㄧㄠ
			return new GourmetMapRes(GourmetMapRtnCode.CITY_EXISTED.getMessage());
		}
		
		GourmetMapRes res = new GourmetMapRes();
		res.setMessage(GourmetMapRtnCode.SUCCESSFUL.getMessage());
		res.setGourmetMap(gourmetMap);
		
		return new GourmetMapRes(res);

	}

	// 第二題 --> 新增 (城市,店名,餐點評價)
	@PostMapping(value = "/api/add_name_food")
	public GourmetMapRes addNameFood(@RequestBody GourmetMapReq req) {

		if (!StringUtils.hasText(req.getName())) {     //字串 為 0 ,空, NULL 
			return new GourmetMapRes(GourmetMapRtnCode.NAME_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getFood())) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_REQUIRED.getMessage());
		}
		if (req.getFoodComment() > 5 || req.getFoodComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_COMMENNT_FALLURE.getMessage());
		}
		if (req.getPrice() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.PRICE_REQURED.getMessage());
		}
		

		 NameFood nameFood = gourmetMapService.addNameFood(req.getName(), req.getFood(), req.getPrice(),
				req.getFoodComment());
		 
		 if(nameFood == null) {
			 return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		 }
		 
		return new GourmetMapRes(nameFood , GourmetMapRtnCode.SUCCESSFUL.getMessage());
	}

	// 第二題 --> 修改 (店家評價)
	@PostMapping(value = "/api/change_name_comment")
	public GourmetMapRes changeNameComment(@RequestBody GourmetMapReq req) {

		if (!StringUtils.hasText(req.getName())) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_REQUIRED.getMessage());
		}
		if (!StringUtils.hasText(req.getFood())) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_REQUIRED.getMessage());
		}
		if (req.getFoodComment() > 5 || req.getFoodComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_COMMENNT_FALLURE.getMessage());
		}
		if (req.getPrice() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.PRICE_REQURED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.changeNameComment(req.getName(), req.getFood(), req.getPrice(),
				req.getFoodComment());

		 if(res == null) {
			 return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		 }

		return new GourmetMapRes(res);

	}

	// 第三題 --> 搜尋特定城市 (找出所有店家)
	@PostMapping(value = "/api/city_change")
	public GourmetMapRes cityChange(@RequestBody GourmetMapReq req) {

		if (req.getLimit() < 0) {
			return new GourmetMapRes(GourmetMapRtnCode.LIMIT_REQURED.getMessage());
		}
		if (!StringUtils.hasText(req.getCity())) {
			return new GourmetMapRes(GourmetMapRtnCode.CITY_REQUIRED.getMessage());
		}

		GourmetMapRes res = gourmetMapService.city(req.getCity(), req.getLimit());

		if (res == null) {
			return new GourmetMapRes(GourmetMapRtnCode.CITY_REQUIRED.getMessage());
		}

		return new GourmetMapRes(res);

	}

	// 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@PostMapping(value = "/api/name_comment_star")
	public GourmetMapRes nameCommentStar(@RequestBody GourmetMapReq req) {

		if (req.getNameComment() > 5 || req.getNameComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_COMMENNT_FALLURE.getMessage());
		}

		GourmetMapRes res = gourmetMapService.nameCommentNumber(req.getNameComment());
		
		if(res == null) {
			 return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		 }

		return new GourmetMapRes(res);

	}

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@PostMapping(value = "/api/name_comment_and_food_comment")
	public GourmetMapRes nameCommentAndFoodComment(@RequestBody GourmetMapReq req) {

		if (req.getNameComment() >= 6 || req.getNameComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.NAME_COMMENNT_FALLURE.getMessage());
		}
		if (req.getFoodComment() >= 6 || req.getFoodComment() <= 0) {
			return new GourmetMapRes(GourmetMapRtnCode.FOOD_COMMENNT_FALLURE.getMessage());
		}

		GourmetMapRes res = gourmetMapService.nameCommentAndFoodComment(req.getNameComment(), req.getFoodComment());
		if(res == null) {
			 return new GourmetMapRes(GourmetMapRtnCode.FAILED.getMessage());
		 }


		return new GourmetMapRes(res);

	}
}
