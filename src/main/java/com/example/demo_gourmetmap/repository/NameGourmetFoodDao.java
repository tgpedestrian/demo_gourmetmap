package com.example.demo_gourmetmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_gourmetmap.entity.NameFood;
import com.example.demo_gourmetmap.entity.NameGourmetMapFood;

@Repository
public interface NameGourmetFoodDao extends JpaRepository<NameFood, NameGourmetMapFood> {

	// 第二題 --> 修改 (店家評價) 第二張表單
	public List<NameFood> findByName(String name);

	// 第三題 --> 搜尋特定城市 (找出所有店家)
	public List<NameFood> findAllByName(String name);
	
	public List<NameFood> findAllByNameIn(List<String> nameList);

	// 第五題 --> 搜尋 第二張表單的名子 in 食物評價大於等於並且依序    在List裡面用 In
	public List<NameFood> findByNameInAndFoodCommentGreaterThanEqualOrderByFoodCommentDesc(List<String> nameList,
			int foodcomment);

}
