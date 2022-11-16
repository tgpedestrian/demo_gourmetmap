package com.example.demo_gourmetmap.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
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

	// 第一題 --> 新增 (城市,店名,店家評價)
	@Override
	public GourmetMap addGourmetMap(String city, String name) {

		if (gourmetMapDao.existsById(name)) { // 判斷名子是否存在
			
			return null;
		}

		GourmetMap gourmetMap = new GourmetMap(); // 使 gourmetMap 實體化
		gourmetMap.setNameComment(1); // 新增店家評價
		gourmetMap.setCity(city); // 新增城市
		gourmetMap.setName(name); // 新增店名

		return gourmetMapDao.save(gourmetMap); // 存入 gourmetMap

	}

	// 第一題 --> 更改 (城市)
	@Override
	public GourmetMap changGourmetMap(String city, String name) {

		Optional<GourmetMap> judgeGourmetMap = gourmetMapDao.findById(name); // 尋找到ID 判斷有無值
		if (judgeGourmetMap.isPresent()) { // 如果 judgeGourmetMap 存在(有值)
			GourmetMap gourmetMapCity = judgeGourmetMap.get(); // judgeGourmetMap 取得
			gourmetMapCity.setCity(city); // 給城市值
			
			return gourmetMapDao.save(gourmetMapCity); // 存入 judgeGourmetMap
		}

		return null;

	}

	// 第二題 --> 新增 (城市,店名,餐點評價)
	@Override
	public NameFood addNameFood(String name, String food, int price, int foodComment) {

		NameFood nameFood = new NameFood(name, food, price, foodComment); // 使 NameFood 實體化
		NameGourmetMapFood nameGourmetMapFood = new NameGourmetMapFood(name, food); // 使 NameGourmetMapFood 實體化

		Optional<NameFood> judgeFoodname = nameGourmetFoodDao.findById(nameGourmetMapFood); // 尋找到ID 判斷有無值
		if (judgeFoodname.isPresent()) { // 如果 judgeFoodname 有值
			return null;
		}

		NameFood foodName = new NameFood(); // 使 NameFood 變成物件
		foodName.setFoodComment(foodComment); // 新增餐點評價
		foodName.setPrice(price); // 新增價錢
		foodName.setName(name); // 新增店名
		foodName.setFood(food); // 新增餐點
		nameGourmetFoodDao.save(nameFood); // 先存取 等下要做累計

		Optional<GourmetMap> judgeGourmetMap = gourmetMapDao.findById(name); // 尋找到ID 判斷有無值
		if (!judgeGourmetMap.isPresent()) { // 如果 judgeGourmetMap 有無值
			return null;
		}

		GourmetMap gourmetMap = judgeGourmetMap.get(); // 沒有值的話,judgeGourmetMap 取得, gourmetMap去接收

		// ----------------------第一張表單 找到--------------------------

		List<NameFood> nameFoodList = nameGourmetFoodDao.findByName(gourmetMap.getName()); // findByName 第二張表單相同的名子
		double totalComment = 0; // 建立要放 "加總" 的容器
		
		for (NameFood foreachNameFood : nameFoodList) {
			totalComment += foreachNameFood.getFoodComment(); // 計算平均處 ( += 加總 )
		}
		
		totalComment = totalComment / nameFoodList.size(); // 加總完 除 多少個食物評價

		DecimalFormat decimalFormat = new DecimalFormat("#.#"); // 正規表達式 小數點表達
		String totalCommentStr = decimalFormat.format(totalComment); // totalComment 變成小數點 正規表達式
		gourmetMap.setNameComment(Double.parseDouble(totalCommentStr)); // double 要 (parse轉型) String
		gourmetMapDao.save(gourmetMap); // 存入gourmetMap

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

		NameGourmetMapFood nameGourmetMapFood = new NameGourmetMapFood(name, food); // 使NameGourmetMapFood 實體化

		Optional<NameFood> judgeFoodname = nameGourmetFoodDao.findById(nameGourmetMapFood); // 尋找到ID 判斷有無值
		if (!judgeFoodname.isPresent()) { // 如果 judgeFoodname 有值
			return null;
		}

		NameFood foodName = new NameFood(); // 使 NameFood 實體化
		foodName.setFoodComment(foodComment); // 修改 食物評價
		foodName.setPrice(price); // 修改 價錢
		nameGourmetFoodDao.save(foodName); // 存入 foodName
		Optional<GourmetMap> judgeGourmetMap = gourmetMapDao.findById(name); // 尋找到ID 判斷有無值
		if (!judgeGourmetMap.isPresent()) { // 如果 judgeGourmetMap 有值
			return null;
		}

		GourmetMap gourmetMap = judgeGourmetMap.get(); // judgeGourmetMap 取的

		// ----------------------------------------------------------------第一張表單 找到

		List<NameFood> nameFoodList = nameGourmetFoodDao.findByName(gourmetMap.getName()); // findByName2 第二張表單相同的名子
		double totalComment = 0; // 建立要放 "加總" 的容器

		for (NameFood foreachNameFood : nameFoodList) {
			totalComment += foreachNameFood.getFoodComment(); // 計算平均處 ( += 加總 )
		}

		totalComment = totalComment / nameFoodList.size(); // 加總完 / 多少個食物評價

		DecimalFormat decimalFormat = new DecimalFormat("#.#"); // 正規表達式 小數點表達
		String totalCommentStr = decimalFormat.format(totalComment); // totalComment 變成小數點 正規表達式
		gourmetMap.setNameComment(Double.parseDouble(totalCommentStr)); // double 要 (parse轉型) String
		gourmetMapDao.save(gourmetMap); // 存入gourmetMap

		return new GourmetMapRes(foodName, "成功");
	}

	// 第三題 --> 搜尋特定城市 (找出所有店家)
	@Override
	public GourmetMapRes city(String city, int limit) {

		List<GourmetMap> gourmetMapList = gourmetMapDao.findAllByCity(city); // findById 只能抓到PK,想要其他的要幾定義
		// 抓同個城市取一個以上的資料用LIST

		if (gourmetMapList.size() == 0 || limit < 0) {    // 判斷 gourmetMapList的 筆數 不能為0或小於0
			return null;
		} else if (gourmetMapList.isEmpty()) {            // 判斷 gourmetMapList 不能為空
			return null;
		}

			List<GourmetMapForFrontEnd> newList = new ArrayList<>(); // 使GourmetMapForFrontEnd實體化,
			                                                         // NameFood和GourmetMap有些屬性互相沒有,有些屬性不想讓前端看到,所以創造出新的呈現方式
			for (GourmetMap item : gourmetMapList) {                 // foreach gourmetMapList (第一張表)
				List<NameFood> nameFoodlist = nameGourmetFoodDao.findAllByName(item.getName()); // 第二張表
				GourmetMapForFrontEnd gourmetMapForFrontEnd = new GourmetMapForFrontEnd(item.getName(),
						item.getNameComment(), nameFoodlist); // 呼叫 創立新的 呈現方式 GourmetMapForFrontEnd
				newList.add(gourmetMapForFrontEnd);
			}
			
			GourmetMapRes gourmetMapRes = new GourmetMapRes();
			
			if (gourmetMapList.size() < limit || limit == 0) {  // 判斷 gourmetMapList的 筆數 不能為0或小於0
			gourmetMapRes.setGourmetMapForFrontEnd(newList);
			
			return gourmetMapRes;
		}
			
			List<GourmetMapForFrontEnd> limitGourmetMapList = newList.subList(0, limit); // 顯示限制筆數,
			gourmetMapRes.setGourmetMapForFrontEnd(limitGourmetMapList);

		return gourmetMapRes;

	}

	@Override // 第四題 --> 搜尋店家評價幾等以上 (依照"店家評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	public GourmetMapRes nameCommentNumber(double nameComment) {

		List<GourmetMap> gourmetMapList = gourmetMapDao
				.findByNameCommentGreaterThanEqualOrderByNameCommentDesc(nameComment); // 尋找到大於等於並且由大排到小的店家評價

		List<GourmetMapForFrontEnd> newList = new ArrayList<>(); // 使GourmetMapForFrontEnd實體化,
																	// NameFood和GourmetMap有些屬性互相沒有,有些屬性不想讓前端看到,所以創造出新的呈現方式
		for (GourmetMap item : gourmetMapList) { // foreach gourmetMapList (第一張表)
			List<NameFood> nameFoodlist = nameGourmetFoodDao.findAllByName(item.getName());// 第二張表
			GourmetMapForFrontEnd gourmetMapForFrontEnd = new GourmetMapForFrontEnd(item.getCity(), item.getName(),
					item.getNameComment(), nameFoodlist); // 呼叫 創立新的 呈現方式 GourmetMapForFrontEnd
			newList.add(gourmetMapForFrontEnd); // newList 去接收
		}

		GourmetMapRes gourmetMapRes = new GourmetMapRes();
		gourmetMapRes.setGourmetMapForFrontEnd(newList);

		return gourmetMapRes;
	}

	// 第五題 --> 搜尋店家評價 + 餐點評價 幾等以上 (依照"店家評價+餐點評價"排序顯示 : 城市,店名,店家評價,餐點,價格,餐點評價)
	@Override
	public GourmetMapRes nameCommentAndFoodComment(double nameComment, int foodComment) {

		List<GourmetMap> gourmetMapList = gourmetMapDao.findAllByNameComment(nameComment); // 尋找到店家評價
		List<NameFood> nameFoodList = nameGourmetFoodDao.findAllByFoodComment(foodComment);// 尋找到 食物評價
		List<GourmetMapForFrontEnd> newList = new ArrayList<>(); // 創立新的List // In 陣列

		for (NameFood judgeNameFood : nameFoodList) {
			// 把兩張表塞到一個新的VO ,為了前端 顯示結果,我要塞很多個
			GourmetMapForFrontEnd gourmetMapForFrontEnd = new GourmetMapForFrontEnd();// 使GourmetMapForFrontEnd實體化,
																						// NameFood和GourmetMap有些屬性互相沒有,有些屬性不想讓前端看到,所以創造出新的呈現方式
			// 所以New在FOR 迴圈裏面
			for (GourmetMap judgeGourmetMap : gourmetMapList) { // 餐點的屬性 第一張表沒有的屬性 要把他找出來Set 到新的 VO裡面
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

		newList.sort(Comparator.comparing(GourmetMapForFrontEnd::getNameComment).reversed()
				.thenComparing(GourmetMapForFrontEnd::getFoodComment).reversed());
		// sort 排序 (Comparator.comparing 我創出來的 新的呈現方式 :: 的 店家評價.顛倒
		// ( 如果要比較兩個用 thenComparing )(比較.比較 我創出來的 新的呈現方式 :: 的 店家評價.顛倒 )
		GourmetMapRes gourmetMapRes = new GourmetMapRes();
		gourmetMapRes.setGourmetMapForFrontEndList(newList);

		return gourmetMapRes;

	}

}
