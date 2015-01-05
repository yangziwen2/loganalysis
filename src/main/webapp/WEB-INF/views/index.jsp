<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>API-KEY工具</title>
<%@ include file="./include/includeCss.jsp" %>
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

</style>
</head>
<body>
<%@ include file="./include/header.jsp" %>
<div class="wrapper">
	<h3 class="title">请输入sql</h3>
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
					<td colspan="2">
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
	<hr/>
	<div style="width: 1000px; display:none;">
		<table id="J_renderDataTbl" class="table table-bordered table-condensed table-hover" ></table>
	</div>
	<input type="hidden" id="J_pageStart" value="${page.start}"/>
	<input type="hidden" id="J_pageLimit" value="${page.limit}"/>
</div>
<%@ include file="./include/includeJs.jsp" %>
<script>
seajs.use('app/index', function(index){
	index.init();
});
</script>
</html>