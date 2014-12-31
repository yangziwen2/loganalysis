package com.sogou.map.loganalysis.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.util.SelectUtils;

public class TNFinder {
	
	public static void main(String[] args) throws JSQLParserException {
		
		String sql = "select ac.a as \"中文\", ac.b as b1, (select count(*) from study where ac_id = ac.id) as study_cnt from account as ac where id = 1 and name like \"中文\" limit 5, 10";
		
		Statement stmt = CCJSqlParserUtil.parse(sql);
		Select select = (Select) stmt;
		select.getSelectBody().accept(new SelectVisitorAdapter(){
			@Override
		    public void visit(PlainSelect plainSelect) {
				Limit limit = new Limit();
				limit.setOffset(1);
				limit.setRowCount(10);
				plainSelect.setLimit(limit);
				Column column = new Column();
				column.setColumnName("count(*)");
				SelectExpressionItem countItem = new SelectExpressionItem(column);
				countItem.setAlias(new Alias("cnt"));
				List<SelectItem> list = new ArrayList<SelectItem>();
				list.add(countItem);
				plainSelect.setSelectItems(list);
		    }
		});
		System.out.println(select);
		
		select = SelectUtils.buildSelectFromTable(new Table("mytable"));
		System.out.println(select);
		
	}

}
