function LayerManage(){
	this.init();
}
LayerManage.prototype = (function(){
	var namespace = 'LayerManage';
	var root;
	return {
		init: function(){
			root = this;
		},
		getNameSpace: function(){
			return namespace;
		},
		layer : {
			open: function(target){
				$(target).find(".layerpop").css("top", $(document).scrollTop()+200);
				$(target).attr("style","display:block");
			},
			close: function(target, func){
				$(target).attr("style","display:none;");
				$(target).find('tbody').empty();
				if(func){
					func();
				}
			}
		}
	}
}());
