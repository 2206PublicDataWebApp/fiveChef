package com.kh.fivechef.fridge.service;

import java.util.List;

import com.kh.fivechef.fridge.domain.Fridge;

public interface FridgeService {
	public int registerFridge(Fridge fridge);
	public int modifyFridge(Fridge fridge);
	public List<Fridge> printAllFridge(String userId);
	public int removeOneByNo(Integer fridgeNo);
	public Fridge printOneByNo(Integer fridgeNo);
}
