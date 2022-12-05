package com.example.demo_gourmetmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_gourmetmap.entity.Store;

@Repository
public interface StoreDao extends JpaRepository<Store, String> {

	public List<Store> findAllByStoreCity(String storeCity);

	/**
	 * 第三題 --> 搜尋特定城市 (找出所有店家)
	 *
	 * @return
	 */

	public List<Store> findByStoreCommentGreaterThanEqualOrderByStoreCommentDesc(double storeComment);
	/**
	 * 第四題 --> 找出大於等於並且依序 storeComment
	 *
	 * @return
	 */

//	public List<GourmetMap> findAllByNameComment(double nameComment);
//	/**
//	 * 第五題 --> 找出店家評價
//	 */

}
