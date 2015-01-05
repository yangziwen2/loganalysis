define(function(require, exports, module){
	
	var $ = require('jquery'),
		common = require('app/common').init();
	
	function initSubmitBtn() {
		$('#J_submitBtn').on('click', function() {
			var sql = $('#J_sql').val(),
				start = $('#J_pageStart').val(),
				limit = $('#J_pageLimit').val();
			if(!sql) {
				common.alertMsg('sql不能为空!');
				return;
			}
			var url = CTX_PATH + '/data/page';
			$.post(url, {
				sql: sql,
				start: start,
				limit: limit
			}, function(data) {
				console.dir(data);
			})
		});
	}
	
	return {init: function() {
		initSubmitBtn();
	}};
});
