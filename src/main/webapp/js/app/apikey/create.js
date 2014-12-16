define(function(require, exports, module){
	
	var common = require('app/common').init();
	var $ = require('jquery');
	require('jquery.tmpl');
	
	function initGenerateApiKeyBtn() {
		$("#J_generateApiKeyBtn").on('click', function() {
			renderApiKeyResult();
		});
	}
	
	function renderApiKeyResult() {
		var params = collectApiKeyParams();
		if(!checkApiKeyParams(params)) {
			return;
		}
		generateApiKeyLink({
			urlPrefix: 'http://10.136.120.67:3080/service/ApikeyService/CreateApikeyServlet',
			copyClipboardBtn: '#J_copyTestLinkBtn'
		}, params, '#J_testLinkResult', buildApiKeyLinkWithParams);
		generateApiKeyLink({
			urlPrefix: 'http://sdk.map.sogou.com/ApikeyService/CreateApikeyServlet',
			copyClipboardBtn: '#J_copyOnlineLinkBtn'
		}, params, '#J_onlineLinkResult', buildApiKeyLinkWithParams);
		
		generateApiKeySql(params, '#J_sqlResult', '#J_copySqlBtn');
	}
	
	function collectApiKeyParams() {
		var params = {
			appName: $('#J_appName').val(),
			appDesc: $('#J_appDesc').val(),
			userid: $('#J_userid').val(),
			visitCountDefault: $('#J_visitCountDefault').val(),
			visitCountLocation: $('#J_visitCountLocation').val(),
			type: 'other'
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
		var visitCountDefault = parseInt(params['visitCountDefault']);
		if(isNaN(visitCountDefault) || visitCountDefault <= 1000) {
			common.alertMsg('[visitCount:default]参数有误!');
			return false;
		}
		var visitCountLocation = parseInt(params['visitCountLocation']);
		if(isNaN(visitCountLocation) || visitCountLocation <= 1000) {
			common.alertMsg('[visitCount:location]参数有误!');
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
		    'type=' + encodedParams['type'],
		    'visitCount=default:' + encodedParams['visitCountDefault'] + ',location:' + encodedParams['visitCountLocation']
		].join('&');
		$copyClipboardBtn.attr('data-clipboard-text', url);
		return url;
	}
	
	function generateApiKeySql(params, sqlResultEl, copySqlBtn) {
		$(sqlResultEl).text([
		    "insert into t_api_userinfo ",
		    "(id, clientid, username, userdesc, visitlimit, type, limitlevel) ",
		    "values ",
		    $.tmpl(" (?, '?', '${appName}', '${appDesc}', ${visitCountDefault}, 'other', 'S'); ", params).text()
		].join('\n'));
		$(copySqlBtn).attr('data-clipboard-text', $(sqlResultEl).text());
	}
	
	function initCopyToClipboardBtn(btnEl) {
		var client = new ZeroClipboard($(btnEl)[0]);
		client.on('ready', function(readyEvent){
			client.on('aftercopy', function(event){});
		});
	}
	
	return {init: function(){
		initGenerateApiKeyBtn();
		initCopyToClipboardBtn('#J_copyTestLinkBtn');
		initCopyToClipboardBtn('#J_copyOnlineLinkBtn');
		initCopyToClipboardBtn('#J_copySqlBtn');
	}};
	
});