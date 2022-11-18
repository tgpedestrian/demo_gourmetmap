package com.example.demo_gourmetmap.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_gourmetmap.entity.GourmetMap;
import com.example.demo_gourmetmap.entity.NameFood;
import com.example.demo_gourmetmap.entity.NameGourmetMapFood;
import com.example.demo_gourmetmap.repository.GourmetMapDao;
import com.example.demo_gourmetmap.repository.NameGourmetFoodDao;
import com.example.demo_gourmetmap.service.ifs.GourmetMapService;
import com.example.demo_gourmetmap.vo.GourmetMapForFrontEnd;
import com.example.demo_gourmetmap.vo.GourmetMapRes;

@Service
public class GourmetMapServiceImpl implements GourmetMapService {

	@Autowired
	private GourmetMapDao gourmetMapDao;

	@Autowired
	private NameGourmetFoodDao nameGourmetFoodDao;

	// 第一題 --> 新增 (城市, 店名, 店家評價) ;
	@Override
	public GourmetMapRes addGourmetMap(String city, String name) {

		// 判斷 "店名" 是否存在 ;
		Optional<GourmetMap> nameOp = gourmetMapDao.findById(name);
		if (nameOp.isPresent()) {
			return null;
		}

		GourmetMap gourmetMap = new GourmetMap();

		// 新增 "店名" ;
		gourmetMap.setName(name);

		// 新增 "城市" ;
		gourmetMap.setCity(city);

		// 新增 "店家評價" ;
		gourmetMap.setNameComment(1);
		gourmetMapDao.save(gourmetMap);

		return new GourmetMapRes(gourmetMap);

	}

	// 第一題 --> 更改 (城市) ;
	@Override
	public GourmetMapRes changGourmetMap(String city, String name) {

		// 判斷 "店名" 是否存在 ;
		Optional<GourmetMap> nameOp = gourmetMapDao.findById(name);
		if (nameOp.isPresent()) {
			GourmetMap gourmetMapCity = nameOp.get();

			// 更改 "城市" ;
			gourmetMapCity.setCity(city);
			gourmetMapDao.save(gourmetMapCity);

			return new GourmetMapRes(gourmetMapCity);
		}

		return null;
	}

	// 第二題 --> 新增 (店名,食物 ,價錢 ,餐點評價 ) ;
	@Override
	public GourmetMapRes addNameFood(String name, String food, int price, int foodComment) {

		NameGourmetMapFood nameGourmetMapFood = new NameGourmetMapFood(name, food);

		// 判斷 "店名" 是否存在(第二張表) ;
		Optional<NameFood> nameFoodNameOp = nameGourmetFoodDao.findById(nameGourmetMapFood);
		if (nameFoodNameOp.isPresent()) {
			return null;
		}

		// 新增 "店名","食物" ,"價錢" ,"食物評價 " ;
		NameFood nameFood = new NameFood(name, food, price, foodComment);
		nameGourmetFoodDao.save(nameFood);

		// 判斷 "店名" 是否存在(第一張表) ;
		Optional<GourmetMap> gourmetMapNameOp = gourmetMapDao.findById(name);
		if (!gourmetMapNameOp.isPresent()) {
			return null;
		}

		GourmetMap gourmetMap = gourmetMapNameOp.get();

		// 找到第二張表 與第一張表相同的名子 ;
		List<NameFood> allNameList = nameGourmetFoodDao.findByName(gourmetMap.getName());

		// 建立要放 "加總" 的容器 ;
		double totalComment = 0;

		for (NameFood nameFoodComment : allNameList) {

			// 計算平均處 ( += 加總 ) ;
			totalComment += nameFoodComment.getFoodComment();
		}

		// 加總完 除 多少個食物評價 ;
		totalComment = totalComment / allNameList.size();

		// 正規表達式 小數點表達 ;
		DecimalFormat decimalFormat = new DecimalFormat("#.#");

		// totalComment 變成小數點 正規表達式 ;
		String totalCommentStr = decimalFormat.format(totalComment);

		// double 要 (parse轉型) String ;
		gourmetMap.setNameComment(Double.parseDouble(totalCommentStr));
		gourmetMapDao.save(gourmetMap);

		return new GourmetMapRes(gourmetMap);
	}

	// 第二題 --> 修改 (食物評價) 去新增店家評價
	@Override
	public GourmetMapRes changeNameComment(String name, String food, int price, int foodComment) {

		NameGourmetMapFood nameGourmetMapFood = new NameGourmetMapFood(name, food);

		// 判斷 "店名" 是否存在(第二張表) ;
		Optional<NameFood> nameFoodOp = nameGourmetFoodDao.findById(nameGourmetMapFood);
		if (!nameFoodOp.isPresent()) {
			return null;
		}

		NameFood foodName = new NameFood();

		// 更改 "食物評價" ;
		foodName.setFoodComment(foodComment);

		// 更改 "價錢" ;
		foodName.setPrice(price);
		nameGourmetFoodDao.save(foodName);

		// 判斷 "店名" 是否存在(第一張表) ;
		Optional<GourmetMap> gourmetMapOp = gourmetMapDao.findById(name);
		if (!gourmetMapOp.isPresent()) {
			return null;
		}

		GourmetMap gourmetMap = gourmetMapOp.get();

		// 找到第二張表 與第一張表相同的名子 ;
		List<NameFood> allNameList = nameGourmetFoodDao.findByName(gourmetMap.getName());

		// 建立要放 "加總" 的容器 ;
		double totalComment = 0;

		for (NameFood nameFoodComment : allNameList) {

			// 計算平均處 ( += 加總 ) ;
			totalComment += nameFoodComment.getFoodComment();
		}

		// 加總 除 多少個食物評價 ;
		totalComment = totalComment / allNameList.size();

		// 正規表達式 小數點表達 ;
		DecimalFormat decimalFormat = new DecimalFormat("#.#");

		// totalComment 變成小數點 正規表達式 ;
		String totalCommentStr = decimalFormat.format(totalComment);

		// double 要 (parse轉型) String ;
		gourmetMap.setNameComment(Double.parseDouble(totalCommentStr));
		gourmetMapDao.save(gourmetMap);

		return new GourmetMapRes(gourmetMap);
	}

	// 第三題 --> 搜尋特定城市 (找出所有店家)
	@Override
	public GourmetMapRes city(String city, int limit) {

		// 判斷 "城市" 是否存在 ;
		List<GourmetMap> cityList = gourmetMapDao.findAllByCity(city);
		if (cityList.isEmpty()) {
			return null;
		}

		List<GourmetMap> limitList = cityList;

		// 如果 輸入筆數 大於0 和 輸入筆數 小於 資料庫
		if (limit > 0 && limit < cityList.size()) {
			limitList = cityList.subList(0, limit);
		}

		List<String> nameList = new ArrayList<>();

		// 取得 限制比數的店名 ;
		for (GourmetMap item : cityList) {
			nameList.add(item.getName());
		}

		// 找到 第二張表全部店名 ;
		List<NameFood> allNameList = nameGourmetFoodDao.findAllByNameIn(nameList);

		// NameFood和GourmetMap有些屬性互相沒有,有些屬性不想讓前端看到,所以創造出新的呈現方式
		List<GourmetMapForFrontEnd> newList = new ArrayList<>();

		for (GourmetMap foreachGourmetMap : limitList) {
			for (NameFood foreachNameFood : allNameList) {

				// 如果找到兩張表相同的 "店名" ;
				if (foreachGourmetMap.getName().equalsIgnoreCase(foreachNameFood.getName())) {
					newList.add(new GourmetMapForFrontEnd(foreachGourmetMap.getName(),
							foreachGourmetMap.getNameComment(), foreachNameFood));
				}
			}
		}
		GourmetMapRes gourmetMapRes = new GourmetMapRes();
		gourmetMapRes.setGourmetMapForFrontEnd(newList);

		return gourmetMapRes;
	}

	@Override // 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	public GourmetMapRes nameCommentNumber(double nameComment) {

		// 找到 大於等於 並且 由大排到小的 "店家評價"
		List<GourmetMap> nameCommentList = gourmetMapDao
				.findByNameCommentGreaterThanEqualOrderByNameCommentDesc(nameComment);

		if (nameCommentList.isEmpty()) {
			return null;
		}

		List<String> strList = new ArrayList<>();

		for (GourmetMap nameList : nameCommentList) {
			strList.add(nameList.getName());
		}

		List<NameFood> listNamefood = nameGourmetFoodDao.findAllByNameIn(strList);

		// NameFood和GourmetMap有些屬性互相沒有,有些屬性不想讓前端看到,所以創造出新的呈現方式
		List<GourmetMapForFrontEnd> newList = new ArrayList<>();

		for (GourmetMap foreachGourmetMap : nameCommentList) {
			for (NameFood foreachNameFood : listNamefood) {

				// 如果找到兩張表相同的 "店名" ;
				if (foreachGourmetMap.getName().equalsIgnoreCase(foreachNameFood.getName())) {
					newList.add(new GourmetMapForFrontEnd(foreachGourmetMap.getCity(), foreachGourmetMap.getName(),
							foreachGourmetMap.getNameComment(), foreachNameFood));

				}
			}
		}
		GourmetMapRes gourmetMapRes = new GourmetMapRes();
		gourmetMapRes.setGourmetMapForFrontEnd(newList);

		return gourmetMapRes;
	}

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@Override
	public GourmetMapRes nameCommentAndFoodComment(double nameComment, int foodComment) {

		// 找到 大於等於 並且 由大排到小的 "店家評價"
		List<GourmetMap> gourmetMapList = gourmetMapDao
				.findByNameCommentGreaterThanEqualOrderByNameCommentDesc(nameComment);

		if (gourmetMapList.isEmpty()) {
			return null;
		}

		List<String> nameList = new ArrayList<>();

		for (GourmetMap forGourmetMap : gourmetMapList) {
			nameList.add(forGourmetMap.getName());
		}

		// 找到 大於等於 並且 由大排到小的 "食物評價"
		List<NameFood> nameFoodList = nameGourmetFoodDao
				.findByNameInAndFoodCommentGreaterThanEqualOrderByFoodCommentDesc(nameList, foodComment);

		// NameFood和GourmetMap有些屬性互相沒有,有些屬性不想讓前端看到,所以創造出新的呈現方式
		List<GourmetMapForFrontEnd> newList = new ArrayList<>();

		for (GourmetMap judgeGourmetMap : gourmetMapList) {

			GourmetMapForFrontEnd gourmetMapForFrontEnd = new GourmetMapForFrontEnd();
			for (NameFood judgeNameFood : nameFoodList) {

				// 如果找到兩張表相同的 "店名" ;
				if (judgeNameFood.getName().equalsIgnoreCase(judgeGourmetMap.getName())) {
					gourmetMapForFrontEnd.setCity(judgeGourmetMap.getCity());
					gourmetMapForFrontEnd.setNameComment(judgeGourmetMap.getNameComment());
					gourmetMapForFrontEnd.setName(judgeGourmetMap.getName());
					gourmetMapForFrontEnd.setFood(judgeNameFood.getFood());
					gourmetMapForFrontEnd.setPrice(judgeNameFood.getPrice());
					gourmetMapForFrontEnd.setFoodComment(judgeNameFood.getFoodComment());
					newList.add(gourmetMapForFrontEnd); // 記得 回傳到 List裡面
				}
			}

		}

		//newList.sort(Comparator.comparing(GourmetMapForFrontEnd::getNameComment).reversed()
		//		.thenComparing(GourmetMapForFrontEnd::getFoodComment).reversed());
		// sort 排序 (Comparator.comparing 我創出來的 新的呈現方式 :: 的 店家評價.顛倒
		// ( 如果要比較兩個用 thenComparing )(比較.比較 我創出來的 新的呈現方式 :: 的 店家評價.顛倒 )
		GourmetMapRes gourmetMapRes = new GourmetMapRes();
		gourmetMapRes.setGourmetMapForFrontEndList(newList);

		return gourmetMapRes;

	}

}
