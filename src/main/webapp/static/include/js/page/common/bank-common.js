function Bank_mng(){
	this.init();
}
Bank_mng.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'admin_mng.Bank_mng';
			this.root = this;
			this.select.init(this);
		},
		getNameSpace  : function(){
			return namespace;
		},
		view : function(paging, func){
			var _select = this.select;
			if(!func){
				func = _select.dataGet_cb;
			}
			_select.dataGet(paging, func);
		}
	}
}());
Bank_mng.prototype.select = (function(){
	return {
		init : function(parent){
			root = parent;
		},
		dataGet : function(paging, func){
			var searchOption1 = $("#search_option1").val();
			var searchOption2 = $(".search_option2").val();
			var searchWord = $('.search_word').val();
			var pageSize = 20;
			var pageNo = $('#pageNo').val();
			var params = {
				'pageNo': pageNo, 
				'pageSize': pageSize,
				"searchOption1" : searchOption1,
				"searchOption2" : searchOption2,
				'searchWord':searchWord,
				'paging':paging
			};
			ajaxGet('/${map.loanId}/api/bankSelect', params, func, params);
		},
		dataGet_cb : function(response, data){
			var totalCnt = response.totalItems;
			var pageBlock = 10;
			var pageSize = 20;
			var pageNo = $('#pageNo').val();
			var stateNo = 0;
			if (response.result) {
				$('#lst_table01 tbody tr').remove();
				$(response.list).each(function(k,v){
					stateNo = totalCnt - ((pageNo - 1) * pageSize) - k;
					$('#lst_table01_company tbody').append(
						'<tr>'+
							'<td><input type="checkbox" class="check_seq" name="prod_01" value="'+v.seq+'"></td>'+
							'<td>'+stateNo+'</td>'+
							'<td>'+v.bank_code+'</td>'+
							'<td>'+v.bank_name+'</td>'+
							'<td>'+v.major_yn+'</td>'+
							'<td>'+v.available_yn+'</td>'+
							'<td>'+v.type+'</td>'+
							'<td>'+v.available_op+'</td>'+
						'</tr>'
					);
				});
				
				var html = setPaging(pageNo , pageSize , pageBlock , totalCnt);
				
				$("#paging").empty();
				$("#paging").append(html);
			}
		}
	}
})();

Bank_mng.prototype.insert = (function(){
	return {
		init : function(parent){
			root = parent;
		}
	}
})();

Bank_mng.prototype.delete = (function(){
	return {
		init : function(parent){
			root = parent;
		}
	}
})();