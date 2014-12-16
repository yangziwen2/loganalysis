define(function(require, exports, module){
	
	var common = require('app/common').init();
	var $ = require('jquery');
	
	function initEncodeContentBtn() {
		$('#J_encodeContentBtn').on('click', function() {
			$.post(CTX_PATH + '/apikey/encodeContent', collectParams(), function(data) {
				if(!data || data.success !== true) {
					common.alertMsg(data.message);
					return;
				}
				$('#J_targetContent').val(data.content);
			})
		});
	}
	
	function initDecodeContentBtn() {
		$('#J_decodeContentBtn').on('click', function() {
			$.post(CTX_PATH + '/apikey/decodeContent', collectParams(), function(data) {
				if(!data || data.success !== true) {
					common.alertMsg(data.message);
					return;
				}
				$('#J_targetContent').val(data.content);
			})
		});
	}
	
	function collectParams() {
		return {
			content: $('#J_sourceContent').val().replace(/%25u/g, '\\u'),
			charset: $('#J_charsetSel').val()
		}
	}
	
	return {init: function() {
		initEncodeContentBtn();
		initDecodeContentBtn();
	}};
});