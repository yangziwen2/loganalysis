define(function(require, exports, module){
	
	require('bootstrap.pageBar');
	var $ = require('jquery'),
		common = require('app/common').init();
	
	var url = CTX_PATH + '/data/page';
	
	var start = 0, limit = 20;
	
	function initSubmitBtn() {
		$('#J_submitBtn').on('click', function() {
			var sql = $('#J_sql').val();
			start = 0; limit = 20;
			if(!sql) {
				common.alertMsg('sql不能为空!');
				return;
			}
			$.post(url, {
				sql: sql,
				start: start,
				limit: limit
			}, function(data) {
				if(!data || data.success !== true) {
					common.alertMsg(data && data.message || 'sql输入有误!');
					return;
				}
				var page = data.page;
				start = page.start;
				limit = page.limit;
				renderTable(data.metaData, page.list);
				renderPageBar(page);
			});
		});
	}
	
	function renderPageBar(page) {
		common.buildPageBar('#J_pageBar', start, limit, page.totalCount, function(i, pageNum){
			var sql = $('#J_sql').val();
			if(!sql) {
				common.alertMsg('sql不能为空!');
				return;
			}
			start = (pageNum - 1) * limit;
			$.post(url, {
				sql: sql,
				start: start,
				limit: limit
			}, function(data) {
				if(!data || data.success !== true) {
					common.alertMsg(data && data.message || 'sql输入有误!');
					return;
				}
				var page = data.page;
				start = page.start;
				limit = page.limit;
				renderTable(data.metaData, page.list);
				renderPageBar(page);
			});
		});
	}
	
	function renderTable(metaData, dataList) {
		var $tbl = $('#J_renderDataTbl').empty();
		renderThead(metaData, $tbl);
		renderTbody(metaData, dataList, $tbl);
		$('#J_dataWrapper').show();
	}
	
	function renderTbody(metaData, dataList, $tbl) {
		var $tbody = $('<tbody>');
		$.each(dataList, function(i, data){
			var $tr = $('<tr>');
			$.each(metaData, function(j, dataType){
				var value = data[dataType.key];
				if(value) {
					if(dataType.typeName == 'date') {
						value = new Date(value).format('yyyy-MM-dd');
					} else if(dataType.typeName == 'time') {
						value = new Date(value).format('hh:mm:ss');
					} else if (dataType.typeName == 'datetime') {
						value = new Date(value).format('yyyy-MM-dd hh:mm:ss');
					}
				}
				var $td = $('<td>' + value + '</td>');
				$tr.append($td);
			});
			$tbody.append($tr);
		});
		$tbl.append($tbody);
	}
	
	function renderThead(metaData, $tbl) {
		var $thead = $('<thead>'),
			$tr = $('<tr>');
		$.each(metaData, function(i, dataType) {
			$tr.append('<th>' + dataType.key + '</th>');
		});
		$tbl.append($thead.append($tr));
	}
	
	return {init: function() {
		initSubmitBtn();
	}};
});
