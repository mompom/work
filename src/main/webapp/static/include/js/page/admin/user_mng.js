function User_mng(){
	this.init();
}
User_mng.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'admin_mng.User_mng';
			root = this;
			this.user.root = this;
			this.user.init();
		},
		getNameSpace  : function(){
			return namespace;
		},
		view : function(func){
			if(!func){
				func = this.user.dataGet_cb;
			}
			this.user.dataGet('y', func);
		}
	}
}());
User_mng.prototype.user = (function(){
	var root;
	
	return {
		init : function(){
			root = this.root;
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
			console.log(params);
			ajaxGet('/${map.loanId}/api/adminInfoSelectList', params, func, params);
		},
		dataGet_cb : function(response, data){
			console.log(response);
			var totalCnt = response.totalItems;
			var pageBlock = 10;
			var pageSize = 20;
			var pageNo = $('#pageNo').val();
			var stateNo = 0;
			if (response.result) {
				$('#lst_table01_company tbody tr').remove();
				$(response.list).each(function(k,v){
					stateNo = v.totalCnt - ((pageNo - 1) * pageSize) - k;
					if(!v.note){v.note = '-';}
					if(!v.team){v.team = '-';}
					if(!v.tel_number){v.tel_number = '-';}
					if(v.use_yn == 1){
						$('#lst_table01_company tbody').append(
							'<tr>'+
								'<td><input type="checkbox" class="check_seq" name="prod_01" value="'+v.seq+'"></td>'+
								'<td>'+stateNo+'</td>'+
								'<td>'+v.loan_id+'</td>'+
								'<td>'+v.id+'</td>'+
								'<td>'+v.level_name+'</td>'+
								/*'<td><a href="javascript:user_detail_function('+v.seq+')">'+v.name+'</a></td>'+*/
								'<td>'+v.name+'</td>'+
								'<td>'+v.tel_number+'</td>'+
								'<td>'+v.team+'</td>'+
								'<td>'+v.note+'</td>'+
								'<td>'+v.reg_date+'</td>'+
								'<td>'+v.modify_date+'</td>'+
							'</tr>'
						);
						stateNo = stateNo + 1;
					}else{
						$('#lst_table01_company tbody').append(
							'<tr style="text-decoration: line-through; text-decoration-color: #46b8da;">'+
								'<td></td>'+
								'<td>'+stateNo+'</td>'+
								'<td>'+v.loan_id+'</td>'+
								'<td>'+v.id+'</td>'+
								'<td>'+v.level_name+'</td>'+
								/*'<td><a href="javascript:user_detail_function('+v.seq+')">'+v.name+'</a></td>'+*/
								'<td>'+v.name+'</td>'+
								'<td>'+v.tel_number+'</td>'+
								'<td>'+v.team+'</td>'+
								'<td>'+v.note+'</td>'+
								'<td>'+v.reg_date+'</td>'+
								'<td>'+v.modify_date+'</td>'+
							'</tr>'
						);
						stateNo = stateNo + 1;
					}
				});
				
				var html = setPaging(pageNo , pageSize , pageBlock , totalCnt);
				
				$("#paging").empty();
				$("#paging").append(html);
				
			}
		}
	}
})();
