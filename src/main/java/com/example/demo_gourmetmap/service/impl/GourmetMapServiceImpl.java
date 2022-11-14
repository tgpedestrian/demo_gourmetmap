package com.example.demo_gourmetmap.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_gourmetmap.constants.GourmetMapRtnCode;
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

	// 第一題 --> 新增 (城市,店名,店家評價)
	@Override
	public GourmetMap addGourmetMap(String city, String name) {

		if (gourmetMapDao.existsById(name)) {
			return null;
		}

		GourmetMap gourmetMap = new GourmetMap();
		gourmetMap.setNameComment(1);
		gourmetMap.setCity(city);
		gourmetMap.setName(name);

		return gourmetMapDao.save(gourmetMap);

	}

	// 第一題 --> 更改 (城市)
	@Override
	public GourmetMap changGourmetMap(String city, String name) {

		Optional<GourmetMap> judgeGourmetMap = gourmetMapDao.findById(name);
		if (judgeGourmetMap.isPresent()) {
			GourmetMap gourmetMapCity = judgeGourmetMap.get();
			gourmetMapCity.setCity(city);
			return gourmetMapDao.save(gourmetMapCity);
		}

		return null;

	}

	// 第二題 --> 新增 (城市,店名,餐點評價)
	@Override
	public NameFood addNameFood(String name, String food, int price, int foodComment) {

		NameFood nameFood = new NameFood(name, food, price, foodComment);

		double totalComment = 0; // 等下要放 "加總" 的容器
		NameGourmetMapFood nameGourmetMapFood = new NameGourmetMapFood(name, food);
		Optional<NameFood> judgeFoodname = nameGourmetFoodDao.findById(nameGourmetMapFood);
		if (judgeFoodname.isPresent()) {
			return null;
		}
		NameFood foodName = new NameFood();
		foodName.setFoodComment(foodComment);
		foodName.setPrice(price);
		foodName.setName(name);
		foodName.setFood(food);
		nameGourmetFoodDao.save(nameFood); // 先存取 等下要做累計

		Optional<GourmetMap> judgeGourmetMap = gourmetMapDao.findById(name);
		if (!judgeGourmetMap.isPresent()) {

			return null;

		}

		GourmetMap gourmetMap = judgeGourmetMap.get();

		// ----------------------------------------------------------------第一張表單 找到

		List<NameFood> nameFoodList = nameGourmetFoodDao.findByName(gourmetMap.getName()); // findByName 第二張表單相同的名子

		for (NameFood foreachNameFood : nameFoodList) {
			totalComment += foreachNameFood.getFoodComment(); // 計算平均處 ( += 加總 )
		}
		totalComment = totalComment / nameFoodList.size();

		DecimalFormat decimalFormat = new DecimalFormat("#.#"); // 正規表達式 小數點表達
		String totalCommentStr = decimalFormat.format(totalComment);
		gourmetMap.setNameComment(Double.parseDouble(totalCommentStr)); // double 要 (parse轉型) String
		gourmetMapDao.save(gourmetMap);

//		String string = String.valueOf(totalComment); // 轉型 double ---> 為字串
//		String newString = string.substring(0, 3	); // subString 指位置 的距離
//		double newTotalComment = Double.parseDouble(newString); // 轉型 字串 --->轉為Bouble
//		gourmetMap.setNameComment(newTotalComment);
//		gourmetMapDao.save(gourmetMap);

		return nameFood;

	}

	// 第二題 --> 修改 (食物評價) --> 影響店家評價
	@Override
	public GourmetMapRes changeNameComment(String name, String food, int price, int foodComment) {

		NameGourmetMapFood nameGourmetMapFood = new NameGourmetMapFood(name, food);
		Optional<NameFood> judgeFoodname = nameGourmetFoodDao.findById(nameGourmetMapFood);
		if (!judgeFoodname.isPresent()) {
			return null;
		}
		NameFood foodName = new NameFood();

		foodName.setFoodComment(foodComment);
		foodName.setPrice(price);
		nameGourmetFoodDao.save(foodName);
		Optional<GourmetMap> judgeGourmetMap = gourmetMapDao.findById(name);

		if (!judgeGourmetMap.isPresent()) {

			return null;

		}

		GourmetMap gourmetMap = judgeGourmetMap.get();

		// ----------------------------------------------------------------第一張表單 找到

		List<NameFood> nameFoodList = nameGourmetFoodDao.findByName(gourmetMap.getName()); // findByName2 第二張表單相同的名子
		double totalComment = 0; // 等下要放 "加總" 的容器
		for (NameFood foreachNameFood : nameFoodList) {
			totalComment += foreachNameFood.getFoodComment(); // 計算平均處 ( += 加總 )
		}
		totalComment = totalComment / nameFoodList.size();

		DecimalFormat decimalFormat = new DecimalFormat("#.#"); // 正規表達式 小數點表達
		String totalCommentStr = decimalFormat.format(totalComment);
		gourmetMap.setNameComment(Double.parseDouble(totalCommentStr)); // double 要 (parse轉型) String
		gourmetMapDao.save(gourmetMap);
//
//		
//		String string = String.valueOf(totalComment); // 轉型 double ---> 為字串
//		String newString = string.substring(0, 3); // subString 指位置 的距離
//		double newTotalComment = Double.parseDouble(newString); // 轉型 字串 --->轉為Bouble
//		gourmetMap.setNameComment(newTotalComment);
//		gourmetMapDao.save(gourmetMap);

		return new GourmetMapRes(foodName, "成功");

	}

	// 第三題 --> 搜尋特定城市 (找出所有店家)
	@Override
	public GourmetMapRes city(String city, int limit) {

		List<GourmetMap> gourmetMapList = gourmetMapDao.findAllByCity(city); // findById 只能抓到PK,想要其他的要幾定義
																				// 抓同個城市取一個以上的資料用LIST

		if (gourmetMapList.size() == 0 || limit < 0) {
			return null;
		} else if (gourmetMapList.isEmpty()) {
			return null;
		}
		// List<GourmetMap> ?? =new ArrayList<>();
		List<GourmetMap> limitGourmetMapList = gourmetMapList.subList(0, limit); // 顯示限制筆數,
		List<String> nameList = new ArrayList<>();
		for (GourmetMap item : limitGourmetMapList) {
			nameList.add(item.getName());
		}

		List<NameFood> nameFoodlist = nameGourmetFoodDao.findAllByNameIn(nameList);

		List<String> newList = new ArrayList<>(); // 做一個list 出來
		for (GourmetMap foreachGourmetMap : limitGourmetMapList) { // 先做一次 foreach 取得sql 裡面全部的值， 因為要放入地 二張表的getname
																	// 所以要用ITEM1容器 // =第2張表的SQL // 去get
			for (NameFood foreachNameFood : nameFoodlist) {
				if (foreachGourmetMap.getName().equalsIgnoreCase(foreachNameFood.getName())) {
					newList.add(" 店名 : " + foreachNameFood.getName() + ", 店家評價 : " + foreachGourmetMap.getNameComment()
							+ ", 餐點 : " + foreachNameFood.getFood() + ", 餐點價格 : " + foreachNameFood.getPrice()
							+ ", 餐點評價 : " + foreachNameFood.getFoodComment());
				}
			}
		}

		return new GourmetMapRes(newList, GourmetMapRtnCode.SUCCESSFUL.getMessage());
	}

	@Override // 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	public GourmetMapRes nameCommentNumber(double nameComment) {

		List<GourmetMap> gourmetMapList = gourmetMapDao
				.findByNameCommentGreaterThanEqualOrderByNameCommentDesc(nameComment);
		List<String> nameList = new ArrayList<>();
		for (GourmetMap item : gourmetMapList) {
			nameList.add(item.getName());
		}
		List<NameFood> nameFoodlist = nameGourmetFoodDao.findAllByNameIn(nameList);
		List<GourmetMapForFrontEnd> newList = new ArrayList<>();
		for (NameFood foreachGourmetMap : nameFoodlist) {
			GourmetMapForFrontEnd gourmetMapForFrontEnd = new GourmetMapForFrontEnd();
			for (GourmetMap foreachFoodlist : gourmetMapList) {
				if (foreachFoodlist.getName().equalsIgnoreCase(foreachGourmetMap.getName())) {
					gourmetMapForFrontEnd.setCity(foreachFoodlist.getCity());
					gourmetMapForFrontEnd.setNameComment(foreachFoodlist.getNameComment());
					gourmetMapForFrontEnd.setName(foreachFoodlist.getName());
				}
			}
			gourmetMapForFrontEnd.setFood(foreachGourmetMap.getFood());
			gourmetMapForFrontEnd.setPrice(foreachGourmetMap.getPrice());
			gourmetMapForFrontEnd.setFoodComment(foreachGourmetMap.getFoodComment());
			newList.add(gourmetMapForFrontEnd); // 記得 回傳到 List裡面
		}
		GourmetMapRes gourmetMapRes = new GourmetMapRes();
		gourmetMapRes.setGourmetMapForFrontEnd(newList);
		return gourmetMapRes;
	}

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@Override
	public GourmetMapRes nameCommentAndFoodComment(double nameComment, int foodComment) {

		List<String> list = new ArrayList<>();
		List<GourmetMapForFrontEnd> newList = new ArrayList<>();

		List<GourmetMap> gourmetMapList = gourmetMapDao
				.findByNameCommentGreaterThanEqualOrderByNameCommentDesc(nameComment); // 符合nameComment的

		for (GourmetMap judgeGourmetMap : gourmetMapList) {
			list.add(judgeGourmetMap.getName());
		}

		List<NameFood> nameFoodList = nameGourmetFoodDao
				.findByNameInAndFoodCommentGreaterThanEqualOrderByFoodCommentDesc(list, foodComment); // findBy// ???
																										// In 陣列
		GourmetMapRes gourmetMapRes = new GourmetMapRes();

		for (NameFood judgeNameFood : nameFoodList) {
			GourmetMapForFrontEnd gourmetMapForFrontEnd = new GourmetMapForFrontEnd(); // 把兩張表塞到一個新的VO ,為了前端 顯示結果,我要塞很多個
																						// 所以New在FOR 迴圈裏面
			for (GourmetMap judgeGourmetMap : gourmetMapList) { // 餐點的屬性 第一張表沒有的屬性 要把他找出來Set 到新的 VO裡面
				if (judgeNameFood.getName().equalsIgnoreCase(judgeGourmetMap.getName())) {
					gourmetMapForFrontEnd.setCity(judgeGourmetMap.getCity());
					gourmetMapForFrontEnd.setNameComment(judgeGourmetMap.getNameComment());
					gourmetMapForFrontEnd.setName(judgeGourmetMap.getName());
				}
			}
			gourmetMapForFrontEnd.setFood(judgeNameFood.getFood());
			gourmetMapForFrontEnd.setPrice(judgeNameFood.getPrice());
			gourmetMapForFrontEnd.setFoodComment(judgeNameFood.getFoodComment());
			newList.add(gourmetMapForFrontEnd); // 記得 回傳到 List裡面
		}

		gourmetMapRes.setGourmetMapForFrontEndList(newList);

		return gourmetMapRes;

	}

}
