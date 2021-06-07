function Index(){
	this.init();
}
Index.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'members.Index';
			root = this;
			this.topArea.root = this;
		},
		getNameSpace  : function(){
			return namespace;
		},
		view : function(){
			root.topArea.init();
		}
	}
}());

Index.prototype.topArea = (function(){
	var root;
	
	return {
		init : function(){
			root = this.root;
			var pageNo = $("#pageNo").val();
			var pageSize = 10;
			var pageBlock = 10;
			var totalCnt = 0;
			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
			};
			
			/*
			ajaxGet("/${map.loanId}/api/index", params, function(response){
				//function
				if (response.result) {
					$(response.list).each(function(k,v){
						//each
					});
				}
			}, params);
			*/
		}
	}
})();