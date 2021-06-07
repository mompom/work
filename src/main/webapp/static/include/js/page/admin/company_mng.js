function Company_mng(){
	this.init();
}
Company_mng.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'admin_mng.Company_mng';
			root = this;
			this.company.root = this;
			this.company.init();
		},
		getNameSpace  : function(){
			return namespace;
		},
		view : function(func){
			if(!func){
				func = this.company.dataGet_cb;
			}
			this.company.dataGet('y', func);
		}
	}
}());
Company_mng.prototype.company = (function(){
	var root;
	
	return {
		init : function(){
			root = this.root;
		},
		dataGet : function(paging, func){
			var searchWord = $('.search_word').val();
			var pageSize = 20;
			var pageNo = $('#pageNo').val();
			var params = {
				'pageNo': pageNo, 
				'pageSize': pageSize,
				'searchWord':searchWord,
				'paging':paging
			};
			console.log(params);
			ajaxGet('/${map.loanId}/api/companyInfoSelectList', params, func, params);
		},
		dataGet_cb : function(response, data){
			var totalCnt = response.totalItems;
			var pageBlock = 10;
			var pageSize = 20;
			var pageNo = $('#pageNo').val();
			var stateNo = 0;
			if (response.result) {
				$('#lst_table01_company tbody tr').remove();
				$(response.list).each(function(k,v){
					stateNo = v.totalCnt - ((pageNo - 1) * pageSize) - k;
					$('#lst_table01_company tbody').append(
						'<tr>'+
							'<td class="border_right"><input type="checkbox" class="check_seq" name="prod_01" value="'+v.seq+'"></td>'+
							'<td class="border_right">'+stateNo+'</td>'+
							'<td class="border_right">'+v.id+'</td>'+
							'<td class="border_right"><a href="javascript:company_detail_function('+v.seq+')">'+v.name+'</a></td>'+
							'<td class="border_right">'+v.employee_name+'</td>'+
							'<td class="border_right">'+v.reg_date+'</td>'+
						'</tr>'
					);
					stateNo = stateNo + 1;
				});
				
				var html = setPaging(pageNo , pageSize , pageBlock , totalCnt);
				
				$("#paging").empty();
				$("#paging").append(html);
				
			}
		}
	}
})();
