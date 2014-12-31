package com.sogou.map.loganalysis.util;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;

public class MySelectVisitor implements SelectVisitor {

	@Override
	public void visit(PlainSelect plainSelect) {
		System.out.println(plainSelect);
	}

	@Override
	public void visit(SetOperationList setOpList) {
		System.out.println(setOpList);
	}

	@Override
	public void visit(WithItem withItem) {
		System.out.println(withItem);
	}

}
