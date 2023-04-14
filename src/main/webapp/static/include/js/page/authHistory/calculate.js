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
			this.excel.root = this;
			
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
			}, params);
			
			root.topArea.service();
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
			var monthly_use_sum = 0;
			
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
			
			ajaxGet("/${map.loanId}/api/customerChargeSelectList", params, function(response){
				var stateNo = 1;
				if (response.result) {
					$("#lst_table_middle tbody").empty();
					
					if(response.list.length == 0){
						$("#lst_table_middle tbody").append(
							"<tr>"+
								"<td colspan='9'>검색된 데이터가 없습니다.</td>"+
							"</tr>"
						);
					}else{
						$(response.list).each(function(k,v){
							var inquiry_count = 0;
							var inquiry_total_count = 0;
							$(root.history.serviceCnt).each(function(){
								if(this.customer_service_seq == v.customer_service_seq){
									inquiry_count = this.inquiry_count;
									inquiry_total_count = this.inquiry_total_count;
									return false;
								}
							});
							
							if(v.monthly_use<=inquiry_count){
								//realName_monthly_use_sum = 0;
								$("#lst_table_middle tbody tr."+v.service_type+"_"+v.customer_service_type).remove();
								$("#lst_table_middle tbody").append(
									"<tr class='"+v.service_type+"_"+v.customer_service_type+"'>"+
										"<td>"+stateNo+"<input type='hidden' class='seq' value='"+v.service_seq+"'></td>"+
										"<td>"+v.customer_service_name+" : "+v.service_name+"</td>"+
										/* "<td class='width_217'><input type='text' class='short right number' id='monthly_use' value="+numberWithCommas(v.monthly_use)+">건 이하</td>"+ */
										"<td>"+numberWithCommas(v.charge)+"</td>"+
										"<td>"+numberWithCommas(v.discount)+"</td>"+
										"<td><span class='rst'>"+numberWithCommas(v.charge-v.discount)+"</span></td>"+
										"<td>"+numberWithCommas(inquiry_total_count)+"건</td>"+
										"<td>"+numberWithCommas(inquiry_count)+"건<input type='hidden' class='monthly_use' value='"+v.monthly_use+"'></td>"+
										"<td>"+numberWithCommas(inquiry_count*+(v.charge-v.discount))+"원<input type='hidden' class='monthly_use_eq' value='"+inquiry_count*+(v.charge-v.discount)+"'></td>"+
										"<td>"+numberWithCommas(v.monthly_use)+"건 이상 건별 "+numberWithCommas(v.discount)+"원할인</td>"+
									"</tr>"
								);
								stateNo=stateNo+1;
							}
						});
						
						$("#lst_table_middle tbody tr").each(function(){
							monthly_use_sum += Number($(this).find('.monthly_use_eq').val());
						});

						$('.monthly_use_sum').text(numberWithCommas(monthly_use_sum)+'원');
					}
				}
			}, params);
		}
	}
})();

AccountAuthHistory.prototype.history = (function(){
	var root;
	var history;
	var serviceCnt = {};
	var totalCnt = 0;
	
	return {
		view : function(){
			totalCnt = 0;
			this.count();
		},
		getData : function(){
			root = this.root;
			history = this;
			var company_id = $('#company_id').val();
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			
			var year = $('#year_select').val();
			var month = $('#month_select').val();
			
			/*
			var sdate = $("#sdate").val() + " 00:00:00";
			var edate = $("#edate").val() + " 23:59:59";
			
			*/
			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
				"sdate":year+""+month,
				"edate":year+""+month,
				"company_id":company_id
			};
			ajaxGet("/${map.loanId}/api/historySelectList", params, function(response){
				var pageBlock = 10;
				var pageSize = $("#pageSize option:selected").val();
				var pageNo = $("#pageNo").val();
				var stateNo = 0;
				
				if (response.result) {
					$("#lst_table_history tbody").empty();
					if(!response.list.length){
						$("#lst_table_history tbody").append(
								"<tr class='center'>"+
									"<td colspan='14'>계좌인증 내역이 없습니다.</td>"+
								"</tr>"
						);
					}
					/*$(response.list).each(function(k,v){*/
					var listLen = response.list.length;
					var innerHtml = "";
					for(var i = 0; i<listLen; i++){
						var v = response.list[i];
						stateNo = totalCnt - ((pageNo - 1) * pageSize) - i;
						
						var yn_text = '성공'
						if(v.inquiry_yn=='n'){
							yn_text = '실패';
						}
						
						//$("#lst_table_history tbody").append(
						innerHtml +=
							"<tr>"+
								"<td>"+stateNo+"</td>"+
								"<td>"+v.customer_service_name + " : " +v.service_name+"</td>"+
								"<td>"+yn_text+"</td>"+
								"<td>"+v.inquiry_date+"</td>"+
							"</tr>";
						//);
					}
					//});
					$("#lst_table_history tbody").append(innerHtml);
					var html = setPaging(pageNo , pageSize , pageBlock , totalCnt);
					
					$("#paging").empty();
					$("#paging").append(html);
					
					//카운트
					$(".countTxt").text("총 "+totalCnt+"건의 결과가 검색되었습니다.");
					//페이징 세팅
					
					root.charge.init();
					root.excel.init();
					
					
				}
				$('body').loading('stop');
			}, params);
		},
		count : function(){
			$('body').loading();
			
			root = this.root;
			var company_id = $('#company_id').val();
			
			var year = $('#year_select').val();
			var month = $('#month_select').val();
			
			var params = {
				"sdate":year+""+month,
				"edate":year+""+month,
				"company_id":company_id
			};
			ajaxGet("/${map.loanId}/api/historyCountSelectList", params, function(response){
				if (response.result) {
					root.history.serviceCnt = response.list;
					$(response.list).each(function(k,v){
						totalCnt += Number(v.inquiry_count);
					});
				}
			}, params);
			
			this.getData();
		}
	}
})();

AccountAuthHistory.prototype.excel = (function(){
	var root;
	
	return {
		init : function(){
			root = this.root;
			var sdate = $("#sdate").val() + " 00:00:00";
			var edate = $("#edate").val() + " 23:59:59";
			var params = {
				"sdate":sdate,
				"edate":edate,
			};
			
			ajaxGet("/${map.loanId}/api/accountAuthHistorySelectListExcel", params, function(response){
				var stateNo = 0;
				
				if (response.result) {
					$(".lst_table01_hidden tbody").empty();
					$(response.list).each(function(k,v){
						var rsp_message = '-';
						var bank_rsp_message = '-';
						
						v.rsp_message != null?v.rsp_message : v.rsp_message = '-';
						v.bank_rsp_message != null?v.bank_rsp_message : v.bank_rsp_message = '-';
						v.bank_rsp_name != null?v.bank_rsp_name : v.bank_rsp_name = '-';
						
						$(".lst_table01_hidden tbody").append(
							"<tr>"+
								"<td>"+(stateNo+1)+"</td>"+
								"<td>"+v.req_code+"</td>"+
								"<td>"+v.account_holder_name+"</td>"+
								"<td>"+v.account_bank_name+"</td>"+
								"<td style='mso-number-format:\"\@\";'>"+v.account_holder_number+"</td>"+
								"<td style='text-align:center;'>"+v.inquiry_realname_yn+"</td>"+
								"<td>"+v.inquiry_realname_date+"</td>"+
								"<td style='text-align:center;'>"+v.inquiry_transfer_deposit_yn+"</td>"+
								"<td>"+v.inquiry_transfer_deposit_date+"</td>"+
								"<td style='text-align:center;'>"+v.authcode_confirm_yn+"</td>"+
								"<td>"+v.authcode_confirm_date+"</td>"+
								"<td>"+v.rsp_message+"</td>"+
								"<td>"+v.bank_rsp_message+"</td>"+
								"<td>"+v.bank_rsp_name+"</td>"+
							"</tr>"
						);
						stateNo = stateNo + 1;
					});
				}
			}, params);
		}
	}
})();