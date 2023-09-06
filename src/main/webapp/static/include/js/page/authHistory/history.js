function AccountAuthHistory(){
	this.init();
}
AccountAuthHistory.prototype = (function(){
	var namespace;
	var auth;
	var root;
	return {
		init: function(){
			namespace = 'admin.AccountAuthHistory';
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
AccountAuthHistory.prototype.topArea = (function(){
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
					
				}
				
				root.history.view();
				root.charge.init();
			}, params);
			
			//root.topArea.service();
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
		service : function(){
			//serviceManage Call
			var serviceManage = new ServiceManage;
			
			var params = {};
			
			var cb = function(response, company_id){
				if (response.result) {
					$(response.list).each(function(k,v){
						/*if(k%4==0){
							$('.pure-checkbox').append('<br>');
						}*/
						$('.pure-checkbox').append(
							'<input id="serviceCheck'+k+'" name="serviceCheck" type="checkbox" class="checkbox" style="position:absolute; left:-10000%;" checked="checked">'+
	                        '<label for="serviceCheck'+k+'">'+v.customer_service_name +'('+ v.service_name+')</label>'
						);
					});
				}
			};
			
			serviceManage.service.getData(params, cb, null);
		}
	}
})();

AccountAuthHistory.prototype.charge = (function(){
	var root;
	return {
		view : function(){
			this.init();
		},
		init : function(){
			root = this.root;
			var company_id = $('#company_id').val();
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
			
			ajaxGet("/${map.loanId}/api/historyPublicTotalCountSelect", params, function(response){
				if (response.result) {
					var inquiry_realname_sum = 0;
					var inquiry_realname_sum_y = 0;
					var inquiry_realname_sum_n = 0;
					var inquiry_transfer_sum = 0;
					var inquiry_transfer_sum_y = 0;
					var inquiry_transfer_sum_n = 0;
					
					$("#lst_table_middle tbody").empty();
					if(response.list.length == 0){
						$("#lst_table_middle tbody").append(
							"<tr>"+
								"<td colspan='10'>검색된 데이터가 없습니다.</td>"+
							"</tr>"
						);
					}else{
						$(response.list).each(function(k,v){
							inquiry_realname_sum += v.inquiry_realname_count;
							inquiry_realname_sum_y += v.inquiry_realname_count_y;
							inquiry_realname_sum_n += v.inquiry_realname_count_n;
							inquiry_transfer_sum += v.inquiry_transfer_count;
							inquiry_transfer_sum_y += v.inquiry_transfer_count_y;
							inquiry_transfer_sum_n += v.inquiry_transfer_count_n;
							$("#lst_table_middle tbody").append(
								"<tr>"+
									"<td class='border_right'>"+v.company_id+"</td>"+
									"<td class='border_right'><span class='cursor_pointer company_name'>"+v.company_name+"</span><input type='hidden' value='"+v.company_id+"'></td>"+
									"<td class='border_right'>"+v.service_name+"</td>"+
									"<td class='border_right'>"+v.using_platform+"</td>"+
									"<td class='border_right' style='font-weight:300; color:black;'>"+v.inquiry_realname_count+"</td>"+
									"<td class='border_right' style='font-weight:500; color:blue;'>"+v.inquiry_realname_count_y+"</td>"+
									"<td class='border_right' style='font-weight:300; color:black;'>"+v.inquiry_realname_count_n+"</td>"+
									"<td class='border_right' style='font-weight:300; color:black;'>"+v.inquiry_transfer_count+"</td>"+
									"<td class='border_right' style='font-weight:500; color:blue;'>"+v.inquiry_transfer_count_y+"</td>"+
									"<td style='font-weight:300; color:black;'>"+v.inquiry_transfer_count_n+"</td>"+
								"</tr>"
							);
						});
					}
					$('#company_all_input').val(company_id);
					$('.inquiry_realname_sum').text(inquiry_realname_sum);
					$('.inquiry_realname_sum_y').text(inquiry_realname_sum_y);
					$('.inquiry_realname_sum_n').text(inquiry_realname_sum_n);
					$('.inquiry_transfer_sum').text(inquiry_transfer_sum);
					$('.inquiry_transfer_sum_y').text(inquiry_transfer_sum_y);
					$('.inquiry_transfer_sum_n').text(inquiry_transfer_sum_n);
					
					updateChart(null, chartData);
				}
			}, params);
		}
	}
})();

AccountAuthHistory.prototype.history = (function(){
	var root;
	var historyPublic;
	var serviceCnt = {};
	var totalCnt = 0;
	
	return {
		view : function(){
			totalCnt = 0;
			this.getCount(this.countView);
		},
		getData : function(func){
			root = this.root;
			historyPublic = this;
			
			var company_id = $('#company_id').val();
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			
			var year = $('#year_select').val();
			var month = $('#month_select').val();

			var searchType = $("#search_type").val();
			var searchWord = $("#search_word").val();

			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
				"sdate":year+""+month,
				"edate":year+""+month,
				"company_id":company_id,
				"searchType" : searchType,
				"searchWord" : searchWord
			};
			ajaxGet("/${map.loanId}/api/historyPublicSelect", params, func, params);
		},
		dataView : function(response){
			var pageBlock = 10;``
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			var stateNo = 0;
			
			if (response.result) {
				$("#historyPublic tbody").empty();
				if(!response.list.length){
					$('#historyPublic').height('auto');
					$("#historyPublic tbody").append(
							"<tr class='center'>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td class='border_right'>-</td>"+
								"<td>-</td>"+
							"</tr>"
					);
				}
				var listLen = response.list.length;
				var innerHtml = "";
				for(var i = 0; i<listLen; i++){
					var v = response.list[i];
					
					stateNo = totalCnt - ((pageNo - 1) * pageSize) - i;
					
					innerHtml += 
					"<tr>"+
						"<td class='border_right' style='text-align:center;'>"+stateNo+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.company_name+"</td>"+
						"<td class='border_right' style='text-align:center; mso-number-format:\"###\";'>"+v.req_code+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.account_holder_name+"</td>"+
						"<td class='border_right' style='text-align:center; mso-number-format:\"###\";'>"+v.account_holder_number+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.account_bank_name+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.inquiry_realname_yn+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.inquiry_realname_date+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.inquiry_transfer_deposit_yn+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.inquiry_transfer_deposit_date+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.authcode_confirm_yn+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.authcode_confirm_date+"</td>"+
						
						"<td class='border_right' style='text-align:center;'>"+v.using_platform+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.rsp_message+"</td>"+
						"<td class='border_right' style='text-align:center;'>"+v.bank_rsp_message+"</td>"+
						"<td style='text-align:center;'>"+v.bank_rsp_name+"</td>"+
					"</tr>";
						
				}
				$("#historyPublic tbody").append(innerHtml);
				
				var html = setPaging(pageNo , pageSize , pageBlock , totalCnt);

				$("#paging").empty();
				$("#paging").append(html);
				
				/*var company_id = $('#company_id').val();
				if(company_id){
					$(".hide").hide();
				}else{
					$(".hide").show();
				}*/
			}
			$('body').loading('stop');
		},
		getCount : function(func){
			$('body').loading();
			
			root = this.root;
			historyPublic = this;
			var company_id = $('#company_id').val();
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			
			var year = $('#year_select').val();
			var month = $('#month_select').val();

			var searchType = $("#search_type").val();
			var searchWord = $("#search_word").val();
			
			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
				"sdate":year+""+month,
				"edate":year+""+month,
				"company_id":company_id,
				"searchType" : searchType,
				"searchWord" : searchWord
			};
			
			ajaxGet("/${map.loanId}/api/historyPublicCountSelect", params, func, params);
		},
		countView : function(response){
			var pageBlock = 10;
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			var stateNo = 0;
			if (response.result) {
				//카운트
				totalCnt = response.map.totalCnt;
				$(".countTxt").text("총 "+totalCnt+"건의 결과가 검색되었습니다.");
				//페이징 세팅
				
				root.history.getData(root.history.dataView);
			}
		}
	}
})();

AccountAuthHistory.prototype.fileUpload = (function(){
	var root;
	return {}
})();