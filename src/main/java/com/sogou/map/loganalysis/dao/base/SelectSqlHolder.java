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

public class SelectSqlHolder {
	
	private PlainSelect plainSelect;
	
	private List<SelectItem> selectItems;
	
	private Limit limit = new Limit();
	
	private static final List<SelectItem> COUNT_ITEM_LIST = initCountItemList();
	
	
	
	public static SelectSqlHolder build(String sql) {
		Statement stmt;
		try {
			stmt = CCJSqlParserUtil.parse(sql);
			if(!(stmt instanceof Select)) {
				return null;
			}
			final SelectSqlHolder holder = new SelectSqlHolder();
			Select select = (Select) stmt;
			select.getSelectBody().accept(new SelectVisitorAdapter(){
				public void visit(PlainSelect plainSelect) {
					holder.plainSelect = plainSelect;
					holder.selectItems = plainSelect.getSelectItems();
					plainSelect.setLimit(holder.limit);
			    }
			});
			return holder;
		} catch (JSQLParserException e) {
			e.printStackTrace();
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
		plainSelect.setSelectItems(COUNT_ITEM_LIST);
		return plainSelect.toString();
	}
	
	public static void main(String[] args) {
		
		String sql = "select ac.id, ac.name, ac.phone_number as mobile, (select count(*) from study where study.account_id = ac.id) from account as ac where id > 10000 and name like '%Со'";
		SelectSqlHolder holder = SelectSqlHolder.build(sql);
		System.out.println(holder.toQuerySql(0, 100));
		System.out.println(holder.toCountSql());
		System.out.println(holder.toQuerySql(100, 100));
	}

}
