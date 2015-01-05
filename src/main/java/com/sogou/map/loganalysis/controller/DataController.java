package com.sogou.map.loganalysis.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sogou.map.loganalysis.dao.base.Page;
import com.sogou.map.loganalysis.dao.base.SelectSqlHolder;
import com.sogou.map.loganalysis.dao.base.SqlClause;
import com.sogou.map.loganalysis.service.DataService;

@Controller
@RequestMapping("/data")
public class DataController {
	
	@Autowired
	private DataService dataService;
	
//	@ResponseBody
//	@RequestMapping("/page")
	public Map<String, Object> getPageResultBySql(
			int start, int limit, 
			SqlClause sql) {
		if(StringUtils.isBlank(sql.from())) {
			return new ModelMap().addAttribute("success", false).addAttribute("message", "缺少from语句的信息!");
		}
		Page<Map<String, Object>> page = null;
		try {
			page = dataService.getPageResultBySql(start, limit, sql);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelMap().addAttribute("success", false).addAttribute("message", e.getMessage());
		}
		return new ModelMap().addAttribute("success", true).addAttribute("page", page);
	}
	
	@ResponseBody
	@RequestMapping("/page")
	public Map<String, Object> getPageResultBySql(
			@RequestParam(defaultValue="0") int start,
			@RequestParam(defaultValue="0") int limit,
			String sql
			) {
		Page<Map<String, Object>> page = null;
		try {
			SelectSqlHolder sqlHolder = SelectSqlHolder.build(sql);
			if(sqlHolder == null) {
				return new ModelMap().addAttribute("success", false).addAttribute("message", "sql有误，请输入正确的查询语句!");
			}
			page = dataService.getPageResultBySql(start, limit, sqlHolder);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelMap().addAttribute("success", false).addAttribute("message", e.getMessage());
		}
		return new ModelMap().addAttribute("success", true).addAttribute("page", page);
	}

}
