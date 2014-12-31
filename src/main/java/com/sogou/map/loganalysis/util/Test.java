package com.sogou.map.loganalysis.util;

import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SetOperationList;

public class Test {

	public static void main(String[] args) throws JSQLParserException {
		
		String sql = "";
		Statement stmt = null;
		Select select = null;
		
		sql = "select * from a union select * from b";
//		stmt = CCJSqlParserUtil.parse(sql);
//		select = (Select) stmt;
//		select.getSelectBody().accept(new SelectVisitorAdapter(){
//			@Override
//		    public void visit(SetOperationList setOpList) {
//				List<PlainSelect> selects = setOpList.getPlainSelects();
//				System.out.println(selects);
//				System.out.println(setOpList.getOperations());
//		    }
//		});
		
		
//		sql = "select * from tbl1";
		stmt = CCJSqlParserUtil.parse(sql);
		select = (Select) stmt;
		select.getSelectBody().accept(new SelectVisitorAdapter(){
			public void visit(PlainSelect plainSelect) {
				System.out.println(plainSelect);
			}
			
			public void visit(SetOperationList setOpList) {
				System.out.println("----------");
				System.out.println(setOpList);
		    }
		});
	}
}
