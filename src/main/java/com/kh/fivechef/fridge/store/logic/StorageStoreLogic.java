package com.kh.fivechef.fridge.store.logic;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.fivechef.fridge.domain.Fridge;
import com.kh.fivechef.fridge.domain.LargeCategory;
import com.kh.fivechef.fridge.domain.SelectBox;
import com.kh.fivechef.fridge.domain.SmallCategory;
import com.kh.fivechef.fridge.domain.Storage;
import com.kh.fivechef.fridge.store.StorageStore;

@Repository
public class StorageStoreLogic implements StorageStore {

	@Override
	public List<LargeCategory> selectLargeCat(SqlSession session) {
		List<LargeCategory> lList = session.selectList("FridgeMapper.selectLargeCat");
		return lList;
	}

	@Override
	public List<SmallCategory> selectSmallCat(SqlSession session, String largeCatId) {
		List<SmallCategory> sList = session.selectList("FridgeMapper.selectSmallCat", largeCatId);
		return sList;
	}

	@Override
	public int insertStorage(SqlSession session, Storage storage) {
		int result = session.insert("FridgeMapper.insertStorage", storage);
		return result;
	}

	@Override
	public List<Storage> selectStorage(SqlSession session,Integer fridgeNo) {
		List<Storage> storage = session.selectList("FridgeMapper.selectStorage", fridgeNo);
		return storage;
	}

	@Override
	public int deleteStorage(SqlSession session, Integer fridgeNo, Integer stSelectNo) {
		HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("fridgeNo", fridgeNo);
		paramMap.put("stSelectNo", stSelectNo);
		int result = session.delete("FridgeMapper.deleteStorage", paramMap);
		return result;
	}

	@Override
	public int updateSelectValue(SqlSession session, SelectBox selectBox) {
		int result = session.update("FridgeMapper.updateSelectBox", selectBox);
		return result;
	}

	@Override
	public int updateStorage(SqlSession session, Storage storage) {
		int result = session.update("FridgeMapper.updateStorage", storage);
		return result;
	}

	@Override
	public int updateIngred(SqlSession session, Storage storage) {
		int result = session.update("FridgeMapper.updateIngred", storage);
		return result;
	}

	@Override
	public Storage selectIngred(SqlSession session, Integer fridgeNo) {
		Storage ibundle = session.selectOne("FridgeMapper.selectIngred", fridgeNo);
		return ibundle;
	}

	@Override
	public int deleteIngred(SqlSession session, Storage storage) {
		int result = session.update("FridgeMapper.deleteIngred", storage);
		return result;
	}

}
