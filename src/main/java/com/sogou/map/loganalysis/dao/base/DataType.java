package com.sogou.map.loganalysis.dao.base;

public class DataType {

	private String key;
	private String typeName;
	
	public DataType(String key, String typeName) {
		this.key = key;
		this.typeName = typeName;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
