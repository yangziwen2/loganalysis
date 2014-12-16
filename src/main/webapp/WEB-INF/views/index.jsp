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

.btn {
	width: 180px;
	height: 40px;
	font-size: 25px;
	font-weight: bold;
	font-family: '微软雅黑'
}

.wrapper div {
	margin: 20px auto;
}

</style>
</head>
<body>
<%@ include file="./include/header.jsp" %>
<div class="wrapper">
	<h3>请输入sql</h3>
	<textarea style="width: 500px; height: 200px;"></textarea>
	<hr/>
	<div style="width: 1000px; display:none;">
		<table id="J_renderDataTbl" class="table table-bordered table-condensed table-hover" ></table>
	</div>
</div>
<%@ include file="./include/includeJs.jsp" %>
<script>
</script>
</html>