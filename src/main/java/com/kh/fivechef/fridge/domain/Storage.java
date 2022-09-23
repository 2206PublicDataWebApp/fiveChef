package com.kh.fivechef.fridge.domain;


public class Storage {
	private int storageNo;
	private String storageName;
	private int fridgeNo;
	private String largeCatId;
	private String storageSelectNo;
	
	public Storage() {}
		
	public Storage(String storageName, int fridgeNo) {
		super();
		this.storageName = storageName;
		this.fridgeNo = fridgeNo;
	}
	
	
	public Storage(int storageNo, String storageName) {
		super();
		this.storageNo = storageNo;
		this.storageName = storageName;
	}
	

	public int getStorageNo() {
		return storageNo;
	}


	public void setStorageNo(int storageNo) {
		this.storageNo = storageNo;
	}


	public String getStorageName() {
		return storageName;
	}


	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}


	public int getFridgeNo() {
		return fridgeNo;
	}


	public void setFridgeNo(int fridgeNo) {
		this.fridgeNo = fridgeNo;
	}


	public String getLargeCatId() {
		return largeCatId;
	}


	public void setLargeCatId(String largeCatId) {
		this.largeCatId = largeCatId;
	}
	
	public String getStorageSelectNo() {
		return storageSelectNo;
	}

	public void setStorageSelectNo(String storageSelectNo) {
		this.storageSelectNo = storageSelectNo;
	}

	
	@Override
	public String toString() {
		return "Storage [storageNo=" + storageNo + ", storageName=" + storageName + ", fridgeNo=" + fridgeNo
				+ ", largeCatId=" + largeCatId + ", storageSelectNo=" + storageSelectNo + "]";
	}

	
}
