package com.sogou.map.loganalysis.dao.base;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SqlClause implements Serializable {
	
	private static final long serialVersionUID = -7924280276778261017L;

	private String select;
	
	private String from;
	
	private String where;
	
	private String groupBy;
	
	private String orderBy;
	
	
	public String select() {
		return select;
	}
	
	public String from() {
		return from;
	}
	
	public String where() {
		return where;
	}
	
	public String groupBy() {
		return groupBy;
	}
	
	public String orderBy() {
		return orderBy;
	}
	
	public SqlClause select(String select) {
		this.select = select;
		return this;
	}
	
	public SqlClause from(String from) {
		this.from = from;
		return this;
	}
	
	public SqlClause where(String where) {
		this.where = where;
		return this;
	}
	
	public SqlClause groupBy(String groupBy) {
		this.groupBy = groupBy;
		return this;
	}
	
	public SqlClause orderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}
	
	public void setSelect(String select) {
		this.select = select;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String toSql() {
		return toSql(false);
	}
	
	public String toCountSql() {
		return "select count(*) " + toSql(true);
	}
	
	public String toSql(boolean withoutSelect) {
		if(StringUtils.isBlank(select)) {
			select = "*";
		}
		StringBuilder buff = new StringBuilder();
		if(!withoutSelect) {
			buff.append(" select ").append(select);
		}
		buff.append(" from ").append(from);
		if(StringUtils.isNotBlank(where)) {
			buff.append(" where ").append(where);
		}
		if(StringUtils.isNotBlank(groupBy)) {
			buff.append(" group by ").append(groupBy);
		}
		if(StringUtils.isNotBlank(orderBy)) {
			buff.append(" order by ").append(orderBy);
		}
		return buff.toString();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
