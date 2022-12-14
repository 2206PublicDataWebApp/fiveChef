package com.kh.fivechef.fridge.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.fivechef.fridge.domain.Fridge;

public interface FridgeStore {
	public int insertFridge(SqlSession session, Fridge fridge);
	public int updateFridge(SqlSession session, Fridge fridge);
	public List<Fridge> selectAllFridge(SqlSession session, String userId);
	public int deleteOneByNo(SqlSession session, Integer fridgeNo);
	public Fridge selectOneByNo(SqlSession session, Integer fridgeNo);
}
