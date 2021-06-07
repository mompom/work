function Notice(){
	this.init();
}
Notice.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'admin_mng.Notice';
			auth = '김웅희';
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
Notice.prototype.topArea = (function(){
	var root;
	
	return {
		init : function(){
			root = this.root;
		}
	}
})();
