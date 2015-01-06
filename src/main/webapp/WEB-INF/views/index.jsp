<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SQL查询工具</title>
<%@ include file="./include/includeCss.jsp" %>
<link rel="stylesheet" href="${ctx_path}/css/codemirror.css" >
<style>
.wrapper {
	text-align: center;
	width: 1000px;
	margin: 0px auto 100px;
}

.title {
	font-family: 'Microsoft YaHei';
}

.btn {
	font-size: 18px;
	font-weight: bold;
	font-family: 'Microsoft YaHei';
	width: 80px;
	height: 35px;
	padding-top: 0px;
}

.wrapper div {
	mMicrosoft YaHeiargin: 20px auto;
}

input[type=text] {
	margin-bottom: 0px;
}

.label-wrapper {
	width: 50px;
}

label {
	font-size: 18px;
	text-align: right;
}

#J_sqlTbl {
	width: 800px;
	margin: 0px auto;
}

.cm-keyword {
    font-weight: bold;
    /*
	font-family: 'Microsoft Yahei';
	*/
}

#J_dataWrapper {
	width: 1000px; display:none; overflow-x: auto;
}

</style>
</head>
<body>
<%@ include file="./include/header.jsp" %>
<div class="wrapper">
	<h3 class="title">请输入查询SQL</h3>
	<table id="J_sqlTbl" class="table table-bordered">
		<tbody>
				<!-- <tr>
					<td class="label-wrapper">
						<label for="J_select">select:&nbsp;</label>
					</td>
					<td>
						<input id="J_select" name="select" type="text" class="input-medium" style="width: 670px;;"/>
					</td>
				</tr>
				<tr>
					<td class="label-wrapper">
						<label for="J_from">from:&nbsp;</label>
					</td>
					<td>
						<input id="J_from" name="from" type="text" class="input-medium" style="width: 670px;"/>
					</td>
				</tr>
				<tr>
					<td class="label-wrapper">
						<label for="J_where">where:&nbsp;</label>
					</td>
					<td>
						<input id="J_where" name="where" type="text" class="input-medium" style="width: 670px;"/>
					</td>
				</tr>
				<tr>
					<td class="label-wrapper">
						<label for="J_groupBy">groupBy:&nbsp;</label>
					</td>
					<td>
						<input id="J_groupBy" name="groupBy" type="text" class="input-medium" style="width: 670px;"/>
					</td>
				</tr>
				<tr>
					<td class="label-wrapper">
						<label for="J_orderBy">groupBy:&nbsp;</label>
					</td>
					<td>
						<input id="J_orderBy" name="orderBy" type="text" class="input-medium" style="width: 670px;"/>
					</td>
				</tr> -->
				<tr>
					<td colspan="2" style="font-size: 20px">
						<textarea id="J_sql" style="width: 790px; height: 200px; font-size: 18px; margin-bottom: 0px;"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;">
						<button id="J_submitBtn" class="btn btn-primary">提交</button>
					</td>
				</tr>
		</tbody>
	</table>
	<hr style="margin-bottom: 0px;"/>
	<div class="row-fluid">
		<div class="span2">
		</div>
		<div class="span10">
			<div id="J_pageBar" style="height: 30px;" class="pagination"></div>
		</div>
	</div>
	<div id="J_dataWrapper">
		<table id="J_renderDataTbl" class="table table-bordered table-condensed table-hover" ></table>
	</div>
	<input type="hidden" id="J_pageStart" value="${page.start}"/>
	<input type="hidden" id="J_pageLimit" value="${page.limit}"/>
</div>
<%@ include file="./include/includeJs.jsp" %>
<script type="text/javascript" src="${ctx_path}/js/codemirror/codemirror.js"></script>
<script type="text/javascript" src="${ctx_path}/js/codemirror/mysql.js"></script>
<script>
seajs.use('app/index', function(index){
	index.init();
});
</script>
</html>