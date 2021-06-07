function BlockUser_mng(){
	this.init();
}
BlockUser_mng.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'admin_mng.BlockUser_mng';
			this.root = this;
			this.select.init(this);
			this.insert.init(this);
			this.delete.init(this);
		},
		getNameSpace  : function(){
			return namespace;
		},
		view : function(func){
			var _select = this.select;
			if(!func){
				func = _select.dataGet_cb;
			}
			_select.dataGet('y', func);
		},
		add : function(func){
			var _insert = this.insert;
			if(!func){
				func = _insert.dataGet_cb;
			}
			_insert.dataGet(func);
		},
		remove : function(func){
			var _delete = this.delete;
			if(!func){
				func = _delete.dataGet_cb;
			}
			_delete.dataGet(func);
		}
	}
}());
BlockUser_mng.prototype.select = (function(){
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
			ajaxGet('/${map.loanId}/api/blockUserSelect', params, func, params);
		},
		dataGet_cb : function(response, data){
			var totalCnt = response.totalItems;
			var pageBlock = 10;
			var pageSize = 20;
			var pageNo = $('#pageNo').val();
			var stateNo = 0;
			if (response.result) {
				$('.lst_table01 tbody tr').remove();
				$(response.list).each(function(k,v){
					var note = v.note;
					stateNo = totalCnt - ((pageNo - 1) * pageSize) - k;
					
					if(!v.note){
						note = '-';
					}
					if(note.length >= 10){
						note = note.substr(0, 10)+'...';
					}
					$('.lst_table01 tbody').append(
						'<tr>'+
							'<td><input type="checkbox" class="check_seq" name="prod_01" value="'+v.seq+'"></td>'+
							'<td>'+stateNo+'</td>'+
							'<td>'+v.user_key+'</td>'+
							'<td>'+v.account_holder_name+'</td>'+
							'<td>'+v.account_holder_number+'</td>'+
							'<td>'+v.account_holder_code+'</td>'+
							'<td>'+v.account_bank_name+'</td>'+
							'<td class="note cursor_pointer">'+note+'<input type="hidden" value="'+v.note+'"/></td>'+
							'<td>'+v.reg_date+'</td>'+
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

BlockUser_mng.prototype.insert = (function(){
	return {
		init : function(parent){
			root = parent;
		},
		dataGet : function(func){
			var account_holder_name = $('.insert_account_holder_name').val();
			var account_holder_number = $('.insert_account_holder_number').val();
			var account_holder_code = $('.insert_account_bank_name').val();
			var account_bank_name = $('.insert_account_bank_name option:selected').text();
			var note = $('.insert_note').val();
			
			if(account_holder_name == ""){
				alert('이름을 입력해 주세요.')
				return;
			}
			if(account_holder_number == ""){
				alert('계좌번호를 입력해 주세요.')
				return;
			}
			if(account_bank_name == ""){
				alert('은행을 선택해 주세요.')
				return;
			}
			
			var method="POST";
			
			var requestUrl="/${map.loanId}/api/blockUserInsert";
			var params = {
				"account_holder_name" : account_holder_name,
				"account_holder_number" : account_holder_number,
				"account_holder_code" : account_holder_code,
				"account_bank_name" : account_bank_name,
				"note" : note
			};
			var getType="json";
			var contType="application/json; charset=UTF-8";
			
			ajaxGet('/${map.loanId}/api/blockUserInsert', params, func, params);
		},
		dataGet_cb : function(response, data){
			location.reload();
		}
	}
})();

BlockUser_mng.prototype.delete = (function(){
	return {
		init : function(parent){
			root = parent;
		},
		dataGet : function(func){
			var str_checkList = "";
			
			if(checkList.length == 0){
				alert("삭제하실 사용자를 선택해 주세요.");
				return;
			}
			
			for(var i = 0; i < checkList.length; i++){
				if(i != 0){
					str_checkList = str_checkList+",";
				}
				str_checkList = str_checkList+checkList[i];
			}
			
			var method="POST";
			var params = {"check_list":str_checkList};
			var getType="json";
			var contType="application/json; charset=UTF-8";
			
			ajaxGet('/${map.loanId}/api/blockUserDelete', params, func, params);
		},
		dataGet_cb : function(response, data){
			location.reload();
		}
	}
})();