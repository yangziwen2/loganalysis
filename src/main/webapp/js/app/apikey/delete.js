define(function(require, exports, module){
	
	var common = require('app/common').init();
	var $ = require('jquery');
	
	function initGenerateDeleteLinkBtn() {
		$('#J_generateDeleteLinkBtn').on('click', function() {
			renderApiKeyResult();
		});
	}
	
	function renderApiKeyResult() {
		var params = collectApiKeyParams();
		if(!checkApiKeyParams(params)) {
			return;
		}
		generateApiKeyLink({
			urlPrefix: 'http://10.136.120.67:3080/service/ApikeyService/DelApikeyServlet',
			copyClipboardBtn: '#J_copyTestLinkBtn'
		}, params, '#J_testLinkResult', buildApiKeyLinkWithParams);
		generateApiKeyLink({
			urlPrefix: 'http://sdk.map.sogou.com/ApikeyService/DelApikeyServlet',
			copyClipboardBtn: '#J_copyOnlineLinkBtn'
		}, params, '#J_onlineLinkResult', buildApiKeyLinkWithParams);
		
		generateSohuHeader(params, '#J_sohuHeaderResult', '#J_copySohuHeaderBtn');
	}
	
	function collectApiKeyParams() {
		var params = {
			appName: $('#J_appName').val(),
			appDesc: $('#J_appDesc').val(),
			userid: $('#J_userid').val(),
			apikey: $('#J_apikey').val()
		};
		if(params['userid'] && params['userid'].indexOf('@') == -1) {
			params['userid'] = params['userid'] + '@sogou-inc.com';
		}
		return params;
	}
	
	function checkApiKeyParams(params) {
		if(!params) {
			common.alertMsg('参数采集有误!');
			return false;
		}
		if(!params['appName']) {
			common.alertMsg('[appName]不能为空!');
			return false;
		}
		if(!params['appDesc']) {
			common.alertMsg('[appDesc]不能为空!');
			return false;
		}
		if(!params['userid']) {
			common.alertMsg('[userid]不能为空!');
			return false;
		}
		if(!params['apikey']) {
			common.alertMsg('[apikey]不能为空!');
			return false;
		}
		return true;
	}
	
	function generateApiKeyLink(options, params, linkResultEl, callback) {
		$.post(CTX_PATH + "/apikey/encodeParamsGBK", {
			appName: params['appName'],
			appDesc: params['appDesc']
		}, function(data) {
			if(!data  || data.success !== true) {
				common.alertMsg('参数编码失败!');
				return;
			}
			var encodedParams = $.extend({}, params, data.encodedParams);
			$(linkResultEl).text(callback(options, encodedParams));
		});
	}
	
	function buildApiKeyLinkWithParams(options, encodedParams) {
		var urlPrefix = options['urlPrefix'],
			$copyClipboardBtn = $(options['copyClipboardBtn']);
		var url = urlPrefix + '?' + [
		    'appName=' + encodedParams['appName'],
		    'appDesc=' + encodedParams['appDesc'],
		    'userid=' + encodedParams['userid'],
		    'apikey=' + encodedParams['apikey']
		].join('&');
		$copyClipboardBtn.attr('data-clipboard-text', url);
		return url;
	}
	
	function initCopyToClipboardBtn(btnEl) {
		var client = new ZeroClipboard($(btnEl)[0]);
		client.on('ready', function(readyEvent){
			client.on('aftercopy', function(event){});
		});
	}
	
	function generateSohuHeader(params, headerResultEl, headerCopyBtnEl) {
		$(headerResultEl).text('X-SohuPassport-UserId: ' + params['userid']);
		$(headerCopyBtnEl).attr('data-clipboard-text', params['userid']);
	}
	
	return {init: function() {
		initGenerateDeleteLinkBtn();
		initCopyToClipboardBtn('#J_copyTestLinkBtn');
		initCopyToClipboardBtn('#J_copyOnlineLinkBtn');
		initCopyToClipboardBtn('#J_copySohuHeaderBtn');
	}};
});