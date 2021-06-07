function Meritz(){
	this.init();
}
Meritz.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'admin.Meritz';
			root = this;
			this.topArea.root = this;
			this.charge.root = this;
			this.history.root = this;
		},
		getNameSpace  : function(){
			return namespace;
		},
		view : function(){
			root.topArea.init();
		}
	}
}());
Meritz.prototype.topArea = (function(){
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
			
			ajaxGet("/${map.loanId}/api/companyInfoSelectList", params, function(response){
				if (response.result) {
					$(response.list).each(function(k,v){
						$("#defaultSearchCompany").append(
								"<option id='"+v.id+"'>" + v.name + "</option>"
						);
					});
					
					var today = new Date();
					var year = today.getFullYear();
					var month = today.getMonth()+1;
					
					root.topArea.year('#year_select', year);
					root.topArea.month('#month_select', month);
					
					root.charge.init();
				}
			}, params);
		},
		year : function(obj, year){
			for(var i=2017; i<=2100; i++){
				$(obj).append($('<option>', { 
			        value: i,
			        text : i +"년"
			    }));
			}
			$(obj).val(year);
			$(obj+'>option[value='+year+']').attr("selected", "true");
		},
		month : function(obj, month){
			for(var i=1; i<=12; i++){
				$(obj).append($('<option>', {
			        value: i,
			        text : i +"월"
			    }));
			}
			$(obj).val(month);
			$(obj+'>option[value='+month+']').attr("selected", "true");
		},
		checkbox : function(obj){
			var check = true;
			var allCheck = $(obj).attr('id');
			if(allCheck == 'allCheck'){
				$('[name=serviceCheck]').prop("checked", $('#allCheck').prop( "checked"));     
				//업체선택 전체 안되게 막기 
				// 엑셀파일 양식 업체별로 할 수 있도록 회사 관리 탭 만들기 
				// 서비스에서 전체 선택 시 동작 기능 만들기 
				// 서비스에 체크박스는 서비스 관리에서 select 해오기
			}else{
				$('[name=serviceCheck]').each(function(k,v){
					check = $(v).prop('checked');
					if(!check){
						return false;
					}
				});
				if(check){
					$('#allCheck').prop("checked", true);
				}else{
					$('#allCheck').prop("checked", false);
				}
			}
			
		},
	}
})();

Meritz.prototype.charge = (function(){
	var root;
	return {
		init : function(){
			root = this.root;
//			var company_id = $('#company_id').val();
			var company_id = "meritz";
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			var sdate = $("#sdate").val() + " 00:00:00";
			var edate = $("#edate").val() + " 23:59:59";
			var sYear = sdate.split('-')[0];
			var sMonth = sdate.split('-')[1];;
			var eYear = edate.split('-')[0];;
			var eMonth = edate.split('-')[1];;
			
			var totalCnt = 0;
			
			var realName_monthly_use = $('#inquiry_realname_count').val();
			var transfer_monthly_use = $('#inquiry_transfer_count').val();
			var realName_monthly_use_sum = 0;
			var transfer_monthly_use_sum = 0;
			
			var year = $('#year_select').val();
			var month = $('#month_select').val();
			
			
			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
				"sdate":year+""+month,
				"edate":year+""+month,
				"sYear":sYear,
				"sMonth":sMonth,
				"eYear":eYear,
				"eMonth":eMonth,
				"company_id":company_id,
				"history":1
			};
			
			ajaxGet("/${map.loanId}/api/meritz/selectTotalUsedList", params, function(response){
				if (response.result) {
					$("#lst_table_middle tbody").empty();
					
					if(response.list.length == 0){
						$("#lst_table_middle tbody").append(
							"<tr>"+
								"<td colspan='8'>검색된 데이터가 없습니다.</td>"+
							"</tr>"
						);
					}else{
//						console.log(response.list);
						$(response.list).each(function(k,v){
							$("#lst_table_middle tbody").append(
									"<tr>"+
										"<td>"+v.typeNumber+"</td>"+
										"<td><span class='cursor_pointer company_name'>"+v.type+"</span><input type='hidden' value='"+v.typeNumber+"'></td>"+
										"<td>"+v.totalCnt+"</td>"+
									"</tr>"
							);
						});
					}
				}
			}, params);
		}
	}
})();

Meritz.prototype.history = (function(){
	var root;
	var historyPublic;
	var serviceCnt = {};
	var totalCnt = 0;
	
	return {
		view : function(){
			totalCnt = 0;
			this.getCount(this.countView);
			//this.getData(this.dataView);
			root.charge.init();
		},
		getData : function(func){
			root = this.root;
			historyPublic = this;
			var typeNumber = $('#company_id').val();
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			
			var year = $('#year_select').val();
			var month = $('#month_select').val();
			
			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
				"sdate":year+""+month,
				"edate":year+""+month,
				"typeNumber":typeNumber
			};
			ajaxGet("/${map.loanId}/api/meritz/selectUsedList", params, func, params);
		},
		dataView : function(response){
			var pageBlock = 10;
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			var stateNo = 0;
			if (response.result) {
				console.log(response.list);
				$("#lst_table_history tbody").empty();
				if(!response.list.length){
					$('#lst_table_history').height('auto');
					$("#lst_table_history tbody").append(
							"<tr class='center'>"+
								"<td colspan='9'>계좌인증 내역이 없습니다.</td>"+
								"<td colspan='5'></td>"+
							"</tr>"
					);
				}
				var listLen = response.list.length;
				var innerHtml = "";
				for(var i = 0; i<listLen; i++){
					stateNo = totalCnt - ((pageNo - 1) * pageSize) - i;
					
					var v = response.list[i];
					if(!v.param1){v.param1 = '-';}
					if(!v.param2){v.param2 = '-';}
					if(!v.param3){v.param3 = '-';}
					if(!v.param4){v.param4 = '-';}
					if(!v.eform_id){v.eform_id = '-';}
					
					innerHtml += 
					"<tr>"+
						"<td class='border_right'>"+stateNo       +"</td>"+
						"<td class='border_right'>"+v.type_name        +"</td>"+
						"<td class='border_right'>"+v.seq             +"</td>"+
						"<td class='border_right'>"+v.progress_type    +"</td>"+
						"<td class='border_right'>"+v.service_type     +"</td>"+
						"<td class='border_right'>"+v.contract_code    +"</td>"+
						"<td class='border_right'>"+v.customer_code    +"</td>"+
						"<td class='border_right'>"+v.progress_status  +"</td>"+
						"<td class='border_right'>"+v.uuids            +"</td>"+
						"<td class='border_right'>"+v.eform_id         +"</td>"+
						"<td class='border_right'>"+v.param1           +"</td>"+
						"<td class='border_right'>"+v.param2           +"</td>"+
						"<td class='border_right'>"+v.param3           +"</td>"+
						"<td class='border_right'>"+v.param4           +"</td>"+
						"<td style='mso-number-format:\"yyyy-mm-dd hh:mm:ss\";'>"+v.reg_date         +"</td>"+
					"</tr>";
				}
				$("#lst_table_history tbody").append(innerHtml);
				
				var html = setPaging(pageNo , pageSize , pageBlock , totalCnt);

				$("#paging").empty();
				$("#paging").append(html);
			}
			$('body').loading('stop');
		},
		getCount : function(func){
			$('body').loading();
			
			root = this.root;
			historyPublic = this;
			var typeNumber = $('#company_id').val();
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			
			var year = $('#year_select').val();
			var month = $('#month_select').val();
			
			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
				"sdate":year+""+month,
				"edate":year+""+month,
				"typeNumber":typeNumber
			};
			
			ajaxGet("/${map.loanId}/api/meritz/countUsedList", params, func, params);
		},
		countView : function(response){
			var pageBlock = 10;
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			var stateNo = 0;
			if (response.result) {
				totalCnt = response.totalItems;
				console.log(totalCnt);
				//카운트
				$(".countTxt").text("총 "+response.totalItems+"건의 결과가 검색되었습니다.");
				//페이징 세팅
				
				root.history.getData(root.history.dataView);
			}
		}
	}
})();