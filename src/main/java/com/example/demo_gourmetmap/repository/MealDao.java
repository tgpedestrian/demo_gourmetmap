package com.example.demo_gourmetmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_gourmetmap.entity.Meal;
import com.example.demo_gourmetmap.entity.MealId;

@Repository
public interface MealDao extends JpaRepository<Meal, MealId> {

	public List<Meal> findByStoreName(String storeName);

	/**
	 * 第二題 --> 修改 (店家評價) 第二張表單
	 * 
	 * @param storeName
	 * @return
	 */

	public List<Meal> findAllByStoreName(String storeName);

	/**
	 * 第三題 --> 搜尋特定城市 (找出所有店家)
	 * 
	 * @param storeNameList
	 * @return
	 */

	public List<Meal> findAllByStoreNameIn(List<String> storeNameList);

	/**
	 * 第四題 --> 尋找名子
	 * 
	 * @param mealComment
	 * @return
	 */

	public List<Meal> findByStoreNameInAndMealCommentGreaterThanEqualOrderByMealCommentDesc(List<String> storeNameList,
			int mealComment);
	/**
	 * 第五題 --> 搜尋 第二張表單的名子 in 食物評價大於等於並且依序 在List裡面用 In
	 */

//    public List<NameFood> findAllByFoodComment(int foodcomment);
//	/**
//	 * 第五題 --> 找出餐點評價
//	 */

}
