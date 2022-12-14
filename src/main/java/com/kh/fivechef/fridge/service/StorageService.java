package com.kh.fivechef.fridge.service;

import java.util.List;

import com.kh.fivechef.fridge.domain.LargeCategory;
import com.kh.fivechef.fridge.domain.SelectBox;
import com.kh.fivechef.fridge.domain.SmallCategory;
import com.kh.fivechef.fridge.domain.Storage;

public interface StorageService {
	public List<LargeCategory> printLargeCat();
	public List<SmallCategory> printSmallCat(String largeCatId);
	public int registStorage(Storage storage);
	public List<Storage> printStorage(Integer fridgeNo);
	public int removeStorage(Integer fridgeNo, Integer stSelectNo);
	public int registSelectValue(SelectBox selectBox);
	public int modifyStorage(Storage storage);
	public int registIngred(Storage storage);
	public Storage printIngred(Integer fridgeNo);
	public int deleteIngred(Storage storage);
}
