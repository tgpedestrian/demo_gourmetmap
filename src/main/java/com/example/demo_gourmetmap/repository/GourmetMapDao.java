package com.example.demo_gourmetmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_gourmetmap.entity.GourmetMap;

@Repository
public interface GourmetMapDao extends JpaRepository<GourmetMap, String> {
     
	public List<GourmetMap> findAllByCity(String city);
	/**
	 * 第三題 --> 搜尋特定城市 (找出所有店家)
	 * @param nameComment
	 * @return
	 */
	
  
	public List<GourmetMap> findByNameCommentGreaterThanEqualOrderByNameCommentDesc(double nameComment);
	/**
	 * 第四題 -->  找出大於等於並且依序 NameComment
	 * @param nameComment
	 * @return
	 */
	
	
//	public List<GourmetMap> findAllByNameComment(double nameComment);
//	/**
//	 * 第五題 --> 找出店家評價
//	 */

}
