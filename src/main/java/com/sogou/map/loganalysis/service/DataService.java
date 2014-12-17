package com.sogou.map.loganalysis.service;

import java.util.Map;

import com.sogou.map.loganalysis.dao.base.Page;
import com.sogou.map.loganalysis.dao.base.SqlClause;

public interface DataService {

	Page<Map<String, Object>> getPageResultBySql(int start, int limit, SqlClause sql);

}
