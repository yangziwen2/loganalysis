define(function(require, exports, module){
	
	var $ = require('jquery');
	
	function initCreateApiKeyBtn() {
		$('#J_createApiKeyBtn').on('click', function() {
			location.href = CTX_PATH + '/apikey/toCreate';
		});
	}
	
	function initDeleteApiKeyBtn() {
		$('#J_deleteApiKeyBtn').on('click', function() {
			location.href = CTX_PATH + '/apikey/toDelete';
		});
	}
	
	function initConvertContentBtn() {
		$('#J_convertContentBtn').on('click', function() {
			location.href = CTX_PATH + '/apikey/toConvertContent';
		});
	}
	
	return {init: function() {
		initCreateApiKeyBtn();
		initDeleteApiKeyBtn();
		initConvertContentBtn();
	}};
});