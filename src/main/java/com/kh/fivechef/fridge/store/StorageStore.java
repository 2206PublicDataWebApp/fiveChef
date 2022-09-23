package com.kh.fivechef.fridge.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.fivechef.fridge.domain.LargeCategory;
import com.kh.fivechef.fridge.domain.SelectBox;
import com.kh.fivechef.fridge.domain.SmallCategory;
import com.kh.fivechef.fridge.domain.Storage;


public interface StorageStore {
	public List<LargeCategory> selectLargeCat(SqlSession session);
	public List<SmallCategory> selectSmallCat(SqlSession session, String largeCatId);
	public int insertStorage(SqlSession session, Storage storage);
	public List<Storage> selectStorage(SqlSession session, Integer fridgeNo);
	public int deleteStorage(SqlSession session, List<Storage> stList);
	public int updateSelectValue(SqlSession session, SelectBox selectBox);
}
