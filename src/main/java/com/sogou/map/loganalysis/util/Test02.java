package com.sogou.map.loganalysis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Test02 {

	public static void main(String[] args) {
		String str = "{\"name\": \"5Ԫ\n abc\"}";
		System.out.println(str);
		JSONObject jsonObj = JSON.parseObject(str);
		System.out.println(jsonObj.get("name"));
	}
}
