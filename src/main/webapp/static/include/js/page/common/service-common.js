function ServiceManage(){
	this.init();
}
ServiceManage.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'ServiceManage';
			root = this;
			this.service.root = this;
		},
		getNameSpace  : function(){
			return namespace;
		}
	}
})();

ServiceManage.prototype.service = (function(){
	return {
		getData : function(params, func){
			ajaxGet("/${map.loanId}/api/customerServiceSelectList", params, func, params);
		}
	}
})();
