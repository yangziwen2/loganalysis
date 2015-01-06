package com.sogou.map.loganalysis.dao.base;

import java.util.Arrays;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SetOperationList;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectSqlHolder {
	
	private static final Logger logger = LoggerFactory.getLogger(SelectSqlHolder.class);
	
	private PlainSelect plainSelect;
	
	private List<SelectItem> selectItems;
	
	private Limit limit = new Limit();
	
	private static final List<SelectItem> COUNT_ITEM_LIST = initCountItemList();
	
	public static SelectSqlHolder build(final String sql) {
		if(StringUtils.isBlank(sql)) {
			return null;
		}
		final String trimmedSql = sql.trim().replaceAll(";\\s*$", "");
		final SelectSqlHolder holder = new SelectSqlHolder();
		try {
			Statement stmt = CCJSqlParserUtil.parse(trimmedSql);
			if(!(stmt instanceof Select)) {
				return null;
			}
			Select select = (Select) stmt;
			select.getSelectBody().accept(new SelectVisitorAdapter(){
				public void visit(PlainSelect plainSelect) {
					holder.plainSelect = plainSelect;
					holder.selectItems = plainSelect.getSelectItems();
					plainSelect.setLimit(holder.limit);
			    }
			    public void visit(SetOperationList setOpList) {
			    	String reformedSql = "select * from (" + trimmedSql + ") as temp_result";
			    	try {
						Select select = (Select)CCJSqlParserUtil.parse("select * from (" + reformedSql + ") as temp_result");
						select.getSelectBody().accept(new SelectVisitorAdapter(){
							public void visit(PlainSelect plainSelect) {
								holder.plainSelect = plainSelect;
								holder.selectItems = plainSelect.getSelectItems();
								plainSelect.setLimit(holder.limit);
						    }
						});
					} catch (JSQLParserException e) {
						logger.error("SQL error [{}]", reformedSql);
					}
			    }
			});
			return holder;
		} catch (JSQLParserException e) {
			logger.error("SQL error [{}]", sql);
		}
		return null;
	}
	
	private static List<SelectItem> initCountItemList() {
		SelectExpressionItem sei = new SelectExpressionItem(new Column("count(*)"));
		sei.setAlias(new Alias("cnt"));
		return Arrays.asList(new SelectItem[]{sei});
	}
	
	public String toQuerySql(long offset, long rowCount) {
		limit.setOffset(offset);
		limit.setRowCount(rowCount);
		plainSelect.setSelectItems(selectItems);
		return plainSelect.toString();
	}
	
	public String toCountSql() {
		limit.setOffset(0);
		limit.setRowCount(0);
		if(CollectionUtils.isNotEmpty(plainSelect.getGroupByColumnReferences()) || isDirectStats()) {
			return "select count(*) from (" + plainSelect.toString() + ") as result";
		} else {
			plainSelect.setSelectItems(COUNT_ITEM_LIST);
			return plainSelect.toString();
		}
	}
	
	private boolean isDirectStats() {
		if(CollectionUtils.isNotEmpty(plainSelect.getGroupByColumnReferences())) {
			return false;
		}
		for(SelectItem selectItm: plainSelect.getSelectItems()) {
			String selectItemStr = selectItm.toString().trim().toLowerCase();
			if(selectItemStr.startsWith("count(")) {
				return true;
			}
			if(selectItemStr.startsWith("max(")) {
				return true;
			}
			if(selectItemStr.startsWith("min(")) {
				return true;
			}
			if(selectItemStr.startsWith("sum(")) {
				return true;
			}
			if(selectItemStr.startsWith("avg(")) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		String sql = "select ac.id, ac.name, ac.phone_number as mobile, (select count(*) from study where study.account_id = ac.id) from account as ac where id > 10000 and name like '%Со'";
		String sql2 = "select * from tbl_1 union select * from tbl_2";
		SelectSqlHolder holder = SelectSqlHolder.build(sql);
		System.out.println(holder.toQuerySql(0, 100));
		System.out.println(holder.toCountSql());
		System.out.println(holder.toQuerySql(100, 100));
	}

}
