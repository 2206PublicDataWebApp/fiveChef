package com.kh.fivechef.fridge.service.logic;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.fivechef.fridge.domain.Fridge;
import com.kh.fivechef.fridge.domain.LargeCategory;
import com.kh.fivechef.fridge.domain.SmallCategory;
import com.kh.fivechef.fridge.service.StorageService;
import com.kh.fivechef.fridge.store.StorageStore;

@Service
public class StorageServiceImpl implements StorageService {
	@Autowired
	private SqlSessionTemplate session;
	@Autowired
	private StorageStore sStore;
	

	@Override
	public List<LargeCategory> printLargeCat() {
		List<LargeCategory> lList = sStore.selectLargeCat(session);
		return lList;
	}


	@Override
	public List<SmallCategory> printSmallCat(String largeCatId) {
		List<SmallCategory> sList = sStore.selectSmallCat(session, largeCatId);
		return sList;
	}

		
	
	
}
