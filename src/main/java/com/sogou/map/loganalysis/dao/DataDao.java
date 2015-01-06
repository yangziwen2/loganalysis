package com.sogou.map.loganalysis.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sogou.map.loganalysis.dao.base.MetaDataColumnMapRowMapper;
import com.sogou.map.loganalysis.dao.base.Page;
import com.sogou.map.loganalysis.dao.base.SelectSqlHolder;
import com.sogou.map.loganalysis.dao.base.SqlClause;

@Repository
public class DataDao {

	protected NamedParameterJdbcTemplate jdbcTemplate;
	
	protected static final int DEFAULT_LIMIT = 100;
	
	protected static final Map<String, Object> EMPTY_PARAM_MAP = Collections.emptyMap();
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Deprecated
	public int countBySql(SqlClause sqlClause) {
		if(sqlClause == null || StringUtils.isBlank(sqlClause.from())) {
			return 0;
		}
		return jdbcTemplate.queryForObject(sqlClause.toCountSql(), EMPTY_PARAM_MAP, Integer.class);
	}
	
	@Deprecated
	public List<Map<String, Object>> listBySql(int start, int limit, SqlClause sqlClause) {
		if(start <= 0) {
			start = 0;
		}
		if(limit <= 0) {
			limit = DEFAULT_LIMIT;
		}
		return jdbcTemplate.queryForList(sqlClause.toSql(), EMPTY_PARAM_MAP);
	}
	
	@Deprecated
	public Page<Map<String, Object>> paginateBySql(int start, int limit, SqlClause sql) {
		return new Page<Map<String, Object>>(start, limit, countBySql(sql), listBySql(start, limit, sql));
	}
	
	// ----------------------------------------- //
	
	public int countBySql(String sql) {
		return countBySql(SelectSqlHolder.build(sql));
	}
	
	public int countBySql(SelectSqlHolder sqlHolder) {
		if(sqlHolder == null) {
			return 0;
		}
		return jdbcTemplate.queryForObject(sqlHolder.toCountSql(), EMPTY_PARAM_MAP, Integer.class);
	}
	
	public List<Map<String, Object>> listBySql(int start, int limit, String sql, Map<String, Object> context) {
		return listBySql(start, limit, SelectSqlHolder.build(sql), context);
	}
	
	public List<Map<String, Object>> listBySql(int start, int limit, SelectSqlHolder sqlHolder, Map<String, Object> context) {
		if(sqlHolder == null) {
			return Collections.emptyList();
		}
		if(start <= 0) {
			start = 0;
		}
		if(limit <= 0) {
			limit = DEFAULT_LIMIT;
		}
		MetaDataColumnMapRowMapper metaDataRowMapper = new MetaDataColumnMapRowMapper();
		context.put("metaDataRowMapper", metaDataRowMapper);
		return jdbcTemplate.query(sqlHolder.toQuerySql(start, limit), new MapSqlParameterSource(EMPTY_PARAM_MAP), metaDataRowMapper);
	}
	
	public Page<Map<String, Object>> paginateBySql(int start, int limit, String sql, Map<String, Object> context) {
		SelectSqlHolder sqlHolder = SelectSqlHolder.build(sql);
		return paginateBySql(start, limit, sqlHolder, context);
	}
	
	public Page<Map<String, Object>> paginateBySql(int start, int limit, SelectSqlHolder sqlHolder, Map<String, Object> context) {
		if(start <= 0) {
			start = 0;
		}
		if(limit <= 0) {
			limit = DEFAULT_LIMIT;
		}
		return new Page<Map<String, Object>>(start, limit, countBySql(sqlHolder), listBySql(start, limit, sqlHolder, context));
	}
	
}
