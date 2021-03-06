package com.sogou.map.loganalysis.service;

import java.util.Map;

import com.sogou.map.loganalysis.dao.base.Page;
import com.sogou.map.loganalysis.dao.base.SelectSqlHolder;
import com.sogou.map.loganalysis.dao.base.SqlClause;

public interface DataService {

	Page<Map<String, Object>> getPageResultBySql(int start, int limit, SqlClause sql);
	
	Page<Map<String, Object>> getPageResultBySql(int start, int limit, String sql, Map<String, Object> context);
	
	Page<Map<String, Object>> getPageResultBySql(int start, int limit, SelectSqlHolder sqlHolder, Map<String, Object> context);

}
