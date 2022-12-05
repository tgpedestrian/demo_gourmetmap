package com.example.demo_gourmetmap.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_gourmetmap.entity.Store;
import com.example.demo_gourmetmap.constants.GourmetMapMsgCode;
import com.example.demo_gourmetmap.entity.Meal;
import com.example.demo_gourmetmap.entity.MealId;
import com.example.demo_gourmetmap.repository.StoreDao;
import com.example.demo_gourmetmap.repository.MealDao;
import com.example.demo_gourmetmap.service.ifs.GourmetMapService;
import com.example.demo_gourmetmap.vo.GourmetMapForFrontEnd;
import com.example.demo_gourmetmap.vo.GourmetMapRes;

@Service
public class GourmetMapServiceImpl implements GourmetMapService {

	@Autowired
	private StoreDao storeDao;

	@Autowired
	private MealDao mealDao;

	// 第一題 --> 新增 店家的 (城市,店名,店家評價) ;
	@Override
	public GourmetMapRes addStore(String storeCity, String storeName) {

		// 透過 店家的店名 取得 Optional "店家" ;
		Optional<Store> storeOp = storeDao.findById(storeName);
		if (storeOp.isPresent()) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_EXISTED.getMessage());
		}

		Store store = new Store();

		// 設置 "店名" ;
		store.setStoreName(storeName);

		// 設置 "城市" ;
		store.setStoreCity(storeCity);

		// 設置 "店家評價" ;
		store.setStoreComment(1);

		// 新增並儲存
		storeDao.save(store);

		return new GourmetMapRes(store, GourmetMapMsgCode.SUCCESSFUL.getMessage());

	}

	// 第一題 --> 更改 店家的 (城市) ;
	@Override
	public GourmetMapRes changeStore(String storeCity, String storeName) {

		// 透過 店家的店名 取得 Optional "店家" ;
		Optional<Store> storeOp = storeDao.findById(storeName);
		if (storeOp.isPresent()) {
			Store store = storeOp.get();

			// 更改 店家的 "城市" ;
			store.setStoreCity(storeCity);
			storeDao.save(store);

			return new GourmetMapRes(store, GourmetMapMsgCode.SUCCESSFUL.getMessage());
		}

		// 顯示 "店名" 不存在 ;
		return new GourmetMapRes(GourmetMapMsgCode.NAME_NOT_EXISTED.getMessage());
	}

	// 第二題 --> 新增 餐點 的(店名 ,餐點, 價錢, 餐點評價 ) ;
	@Override
	public GourmetMapRes addMeal(String mealName, String mealFood, int mealPrice, int mealComment) {

		// 透過 餐點的店名 取得 Optional"店家" ;
		Optional<Store> storeOp = storeDao.findById(mealName);
		if (!storeOp.isPresent()) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_NOT_EXISTED.getMessage());

		}

		MealId mealId = new MealId(mealName, mealFood);

		// 透過(店家.餐點 雙主KEY) 取得 Optional "餐點" ;
		Optional<Meal> mealOp = mealDao.findById(mealId);
		if (mealOp.isPresent()) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_EXISTED.getMessage());
		}

		// 透過 "店名,食物 ,價錢 ,食物評價" 新增 餐點 ;
		Meal meal = new Meal(mealName, mealFood, mealPrice, mealComment);
		mealDao.save(meal);

		// 從 Optional(店家) 取得 店家
		Store obtainStore = storeOp.get();

		// 找到擁有 參數 店名 的 餐點列表 ;
		List<Meal> mealList = mealDao.findByStoreName(obtainStore.getStoreName());

		// 宣告 "加總" 的變數 ;
		double totalComment = 0;

		for (Meal mealItem : mealList) {

			// 計算平均處 ( += 加總 ) ;
			totalComment += mealItem.getMealComment();
		}

		// 加總完 除 多少個食物評價 = 餐點的平均評價 ;
		totalComment = totalComment / mealList.size();

		// 新增 小數點只有一位的 正規表達式 ;  
		DecimalFormat decimalFormat = new DecimalFormat("#.#");

		// 透過上面的正規表達式去 格式化 餐點的平均評價 為 小數點只有一位 的 小數 ;
		String totalCommentStr = decimalFormat.format(totalComment);

		// 新增 店家評價
		obtainStore.setStoreComment(Double.parseDouble(totalCommentStr));
		storeDao.save(obtainStore);

		return new GourmetMapRes(obtainStore, GourmetMapMsgCode.SUCCESSFUL.getMessage());
	}

	// 第二題 --> 修改 餐點 的(食物評價) 去新增店家評價
	@Override
	public GourmetMapRes changeNameComment(String mealName, String mealFood, int mealPrice, int mealComment) {

		// 透過 餐點的店名 取得 Optional"店家" ;
		Optional<Store> storeOp = storeDao.findById(mealName);
		if (!storeOp.isPresent()) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_NOT_EXISTED.getMessage());
		}

		MealId mealId = new MealId(mealName, mealFood);

		// 透過(店家.餐點 雙主KEY) 取得 Optional"餐點" ;
		Optional<Meal> mealOp = mealDao.findById(mealId);
		if (!mealOp.isPresent()) {
			return new GourmetMapRes(GourmetMapMsgCode.NAME_NOT_EXISTED.getMessage());
		}

		Meal meal = new Meal();

		// 更改 "食物評價" ;
		meal.setMealComment(mealComment);

		// 更改 "價錢" ;
		meal.setMealPrice(mealPrice);
		mealDao.save(meal);

		// 從 Optional(店家) 取得 店家
		Store obtainStore = storeOp.get();

		// 找到擁有 參數 店名 的 餐點列表 ;
		List<Meal> mealList = mealDao.findByStoreName(obtainStore.getStoreName());

		// 宣告 "加總" 的變數 ;
		double totalComment = 0;

		for (Meal mealItem : mealList) {

			// 計算平均處 ( += 加總 ) ;
			totalComment += mealItem.getMealComment();
		}

		// 加總完 除 多少個食物評價 = 餐點的平均評價 ;
		totalComment = totalComment / mealList.size();

		// 新增 小數點只有一位的 正規表達式 ;
		DecimalFormat decimalFormat = new DecimalFormat("#.#");

		// 透過上面的正規表達式去 格式化 餐點的平均評價 為 小數點只有一位 的 小數 ;
		String totalCommentStr = decimalFormat.format(totalComment);

		// 更改 店家評價
		obtainStore.setStoreComment(Double.parseDouble(totalCommentStr));
		storeDao.save(obtainStore);

		return new GourmetMapRes(obtainStore, GourmetMapMsgCode.SUCCESSFUL.getMessage());
	}

	// 第三題 --> 搜尋在 特定城市 的所有店家 並限制筆數
	@Override
	public GourmetMapRes storeCity(String storeCity, int limit) {

		// 透過 城市 取得 符合該條件的 店家 ;
		List<Store> storeList = storeDao.findAllByStoreCity(storeCity);
		if (storeList.isEmpty()) {
			return new GourmetMapRes(GourmetMapMsgCode.CITY_NOT_EXISTED.getMessage());
		}

		List<Store> limitStoreList = storeList;

		// 如果 輸入筆數 大於0 和 輸入筆數 小於 資料庫
		if (limit > 0 && limit < limitStoreList.size()) {
			limitStoreList = limitStoreList.subList(0, limit);
		}

		List<String> obtainNameList = new ArrayList<>();

		// 取得 限制比數的店名 ;
		for (Store store : limitStoreList) {
			obtainNameList.add(store.getStoreName());
		}

		// 找到符合名子 同時存在於 餐點表 與 店家表 的 餐點列表 ;
		List<Meal> mealList = mealDao.findAllByStoreNameIn(obtainNameList);

		// 創建新的class 其擁有前端欲見的 meal和store 的部分屬性 ;
		List<GourmetMapForFrontEnd> resultList = new ArrayList<>();

		for (Store store : limitStoreList) {
			for (Meal mealItem : mealList) {

				// 找到符合名子 同時存在於 meal 與 store ;
				if (store.getStoreName().equalsIgnoreCase(mealItem.getStoreName())) {
					resultList.add(new GourmetMapForFrontEnd(store.getStoreName(), store.getStoreComment(), mealItem));
				}
			}
		}

		GourmetMapRes gourmetMapRes = new GourmetMapRes();

		// gourmetMapRes 設置為了前端的 list ;
		gourmetMapRes.setGourmetMapForFrontEndList(resultList);

		return new GourmetMapRes(gourmetMapRes, GourmetMapMsgCode.SUCCESSFUL.getMessage());
	}

	@Override // 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	public GourmetMapRes storeCommentNumber(double storeComment) {

		// 透過 "店家評價" 取得符合該條件的 店家 ;
		List<Store> storeList = storeDao.findByStoreCommentGreaterThanEqualOrderByStoreCommentDesc(storeComment);
		if (storeList.isEmpty()) {
			return new GourmetMapRes(GourmetMapMsgCode.STORE_COMMENNT_REQUIRED.getMessage());
		}

		List<String> storeNameList = new ArrayList<>();

		// 取得 "店家評價 排序顯示"的店名 ;
		for (Store store : storeList) {
			storeNameList.add(store.getStoreName());
		}

		// 找到符合名子 同時存在於 餐點表 與 店家表 的 餐點列表 ;
		List<Meal> mealList = mealDao.findAllByStoreNameIn(storeNameList);

		// 創建新的class 其擁有前端欲見的 meal和store 的部分屬性 ;
		List<GourmetMapForFrontEnd> resultList = new ArrayList<>();

		for (Store store : storeList) {
			for (Meal mealItem : mealList) {

				// 找到符合名子 同時存在於 meal 與 store ;
				if (store.getStoreName().equalsIgnoreCase(mealItem.getStoreName())) {
					resultList.add(new GourmetMapForFrontEnd(store.getStoreCity(), store.getStoreName(),
							store.getStoreComment(), mealItem));

				}
			}
		}

		GourmetMapRes gourmetMapRes = new GourmetMapRes();

		// gourmetMapRes 設置為了前端的 list ;
		gourmetMapRes.setGourmetMapForFrontEndList(resultList);

		return new GourmetMapRes(gourmetMapRes, GourmetMapMsgCode.SUCCESSFUL.getMessage());

	}

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@Override
	public GourmetMapRes storeCommentAndMealComment(double storeComment, int mealComment) {

		// 透過 "店家評價" 取得符合該條件的 店家 ;
		List<Store> storeInNameComment = storeDao
				.findByStoreCommentGreaterThanEqualOrderByStoreCommentDesc(storeComment);

		// 判斷 店家列表 是否為空 ;
		if (storeInNameComment.isEmpty()) {
			return new GourmetMapRes(GourmetMapMsgCode.STORE_COMMENNT_REQUIRED.getMessage());
		}

		List<String> receiveStoreName = new ArrayList<>();

		// 取得 "店家評價 排序顯示"的店名 ;
		for (Store store : storeInNameComment) {
			receiveStoreName.add(store.getStoreName());
		}

		// 透過 "餐點評價"和"店家名稱"取得符合該條件的 餐點 ;
		List<Meal> mealInFoodComment = mealDao
				.findByStoreNameInAndMealCommentGreaterThanEqualOrderByMealCommentDesc(receiveStoreName, mealComment);

		// 創建新的class 其擁有前端欲見的 meal和store 的部分屬性 ;
		List<GourmetMapForFrontEnd> resultList = new ArrayList<>();

		for (Store store : storeInNameComment) {

			GourmetMapForFrontEnd gourmetMapForFrontEnd = new GourmetMapForFrontEnd();
			for (Meal meal : mealInFoodComment) {

				// 找到符合名子 同時存在於 meal 與 store ;
				if (meal.getStoreName().equalsIgnoreCase(store.getStoreName())) {
					gourmetMapForFrontEnd.setCity(store.getStoreCity());
					gourmetMapForFrontEnd.setStoreComment(store.getStoreComment());
					gourmetMapForFrontEnd.setStoreName(store.getStoreName());
					gourmetMapForFrontEnd.setFood(meal.getMealFood());
					gourmetMapForFrontEnd.setPrice(meal.getMealPrice());
					gourmetMapForFrontEnd.setStoreComment(meal.getMealComment());
					resultList.add(gourmetMapForFrontEnd);
				}
			}

		}

		GourmetMapRes gourmetMapRes = new GourmetMapRes();

		// gourmetMapRes 設置為了前端的 list ;
		gourmetMapRes.setGourmetMapForFrontEndList(resultList);

		return new GourmetMapRes(gourmetMapRes, GourmetMapMsgCode.SUCCESSFUL.getMessage());
	}

}
