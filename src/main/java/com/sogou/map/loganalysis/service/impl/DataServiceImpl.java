package com.sogou.map.loganalysis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogou.map.loganalysis.dao.DataDao;
import com.sogou.map.loganalysis.dao.base.Page;
import com.sogou.map.loganalysis.dao.base.SelectSqlHolder;
import com.sogou.map.loganalysis.dao.base.SqlClause;
import com.sogou.map.loganalysis.service.DataService;

@Service
public class DataServiceImpl implements DataService {
	
	@Autowired
	private DataDao dataDao;
	
	@Override
	public Page<Map<String, Object>> getPageResultBySql(int start, int limit, SqlClause sql) {
		return dataDao.paginateBySql(start, limit, sql);
	}

	@Override
	public Page<Map<String, Object>> getPageResultBySql(int start, int limit, String sql, Map<String, Object> context) {
		return dataDao.paginateBySql(start, limit, SelectSqlHolder.build(sql), context);
	}
	
	@Override
	public Page<Map<String, Object>> getPageResultBySql(int start, int limit, SelectSqlHolder sqlHolder, Map<String, Object> context) {
		return dataDao.paginateBySql(start, limit, sqlHolder, context);
	}
	
}
