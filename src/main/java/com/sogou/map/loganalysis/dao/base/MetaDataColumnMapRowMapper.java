package com.sogou.map.loganalysis.dao.base;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class MetaDataColumnMapRowMapper extends ColumnMapRowMapper {
	
	private static final Map<String, Class<?>> CLASS_MAPPING = new ConcurrentHashMap<String, Class<?>>();
	
	private ResultSetMetaData rsmd;
	
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		this.rsmd = rs.getMetaData();
		return super.mapRow(rs, rowNum);
	}
	
	public Map<String, String> lookupColumnTypeInfo(){
		if(rsmd == null) {
			return Collections.emptyMap();
		}
		Map<String, String> metaDataMap = null;
		try {
			int columnCount = rsmd.getColumnCount();
			metaDataMap = new LinkedCaseInsensitiveMap<String>(columnCount);
			for(int i = 1; i <= columnCount; i++) {
				String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
				String columnType = rsmd.getColumnTypeName(i);
				metaDataMap.put(key, columnType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metaDataMap;
	}
	
	public Map<String, String> lookupColumnClassInfo(){
		if(rsmd == null) {
			return Collections.emptyMap();
		}
		Map<String, String> metaDataMap = null;
		try {
			int columnCount = rsmd.getColumnCount();
			metaDataMap = new LinkedCaseInsensitiveMap<String>(columnCount);
			for(int i = 1; i <= columnCount; i++) {
				String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
				String columnClass = rsmd.getColumnClassName(i);
				metaDataMap.put(key, columnClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metaDataMap;
	}
	
	public Map<String, String> lookupColumnDataTypeInfo() {
		if(rsmd == null) {
			return Collections.emptyMap();
		}
		Map<String, String> dataTypeMap = null;
		try {
			int columnCount = rsmd.getColumnCount();
			dataTypeMap = new LinkedCaseInsensitiveMap<String>(columnCount);
			for(int i = 1; i <= columnCount; i++) {
				String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
				String className = rsmd.getColumnClassName(i);
				
				Class<?> clazz = CLASS_MAPPING.get(className);
				if(clazz == null) {
					clazz = Class.forName(className);
					CLASS_MAPPING.put(className, clazz);
				}
				if(Number.class.isAssignableFrom(clazz)) {
					dataTypeMap.put(key, "number");
				} else if (java.sql.Date.class.isAssignableFrom(clazz)) {
					dataTypeMap.put(key, "date");
				} else if (java.sql.Time.class.isAssignableFrom(clazz)) {
					dataTypeMap.put(key, "time");
				} else if (java.util.Date.class.isAssignableFrom(clazz)) {
					dataTypeMap.put(key, "datetime");
				} else if (String.class.equals(clazz)) {
					dataTypeMap.put(key, "string");
				} else {
					dataTypeMap.put(key, "unknown");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataTypeMap;
	}
	
	public List<DataType> getDataTypeList() {
		List<DataType> dataTypeList = new ArrayList<DataType>();
		for(Entry<String, String> entry: lookupColumnDataTypeInfo().entrySet()) {
			dataTypeList.add(new DataType(entry.getKey(), entry.getValue()));
		}
		return dataTypeList;
	}
}
