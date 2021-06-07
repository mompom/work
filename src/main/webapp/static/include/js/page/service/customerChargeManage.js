function CustomerChargeManage(){
	this.init();
	this.sequence;
}

CustomerChargeManage.prototype = (function(){
	var namespace;
	var auth;
	var root;
	var innerHtml_slide_title;
	var innerHtml_slide;
	
	return {
		init : function(){
			this.sequence = 0;
			
			namespace = 'admin.CustomerChargeManage';
			root = this;
			innerHtml_slide_title = $('#lst_table_slide_title').clone().wrapAll('<div/>').parent().html();
			innerHtml_slide = $('#lst_table_slide').clone().wrapAll('<div/>').parent().html();
			this.add.root = this;
			this.add.select.root = this;
			this.slide.root = this;
			this.basic.root = this;
			this.layerpop.root = this;
			this.layerpop.slide.root = this;
			this.layerpop.basic.root = this;
		},
		getNameSpace : function(){
			return namespace;
		},
		getInnerHtml_slide_title : function(){
			return innerHtml_slide_title;
		},
		getInnerHtml_slide : function(){
			return innerHtml_slide;
		}
	}
})();

CustomerChargeManage.prototype.add = (function(){
	var root;
	return {
		slide : function(){
			root = this.root;
			var seqNo = (root.sequence)++;
			
			$(".contents").append(root.getInnerHtml_slide_title);
			$(".contents").append(root.getInnerHtml_slide);
			
			var start_year_date = $('#start_year_date'+(seqNo-1)).val();
			var end_year_date = $('#end_year_date'+(seqNo-1)).val();
			var start_month_date = $('#start_month_date'+(seqNo-1)).val();
			var end_month_date = $('#end_month_date'+(seqNo-1)).val();

			
			if(!start_year_date){
				var today = new Date();
				start_year_date = end_year_date = today.getFullYear();
				start_month_date = end_month_date = today.getMonth()+1;
			}
			
			if(Number(end_month_date)+1 > 12){
				end_month_date = 1;
				end_year_date = Number(end_year_date)+1;
			}else{
				end_month_date = Number(end_month_date)+1;
			}
			
			start_year_date = end_year_date;
			start_month_date = end_month_date;
			
			root.add.select.year('#start_year_date', start_year_date);
			root.add.select.year('#end_year_date', end_year_date);
			root.add.select.month('#start_month_date', start_month_date);
			root.add.select.month('#end_month_date', end_month_date);
			
			
			var seqId = start_year_date + '_' + start_month_date + '_' + end_year_date + '_' + end_month_date;
			$('.disabled').attr('disabled',false);
			
			root.add.slideAttrChangeId(seqNo, seqId);
		},
		slideAttrChangeId : function(seqNo, seqId){
			root = this.root;
			$('#lst_table_slide_title').attr('id', 'lst_table_slide_title'+seqNo);
			$('#lst_table_slide').attr('id', 'lst_table_slide'+seqNo);
			
			$('#start_year_date').attr('id', 'start_year_date'+seqNo);
			$('#start_month_date').attr('id', 'start_month_date'+seqNo);
			$('#end_year_date').attr('id', 'end_year_date'+seqNo);
			$('#end_month_date').attr('id', 'end_month_date'+seqNo);
			$('#lst_table_slide_hidden').attr('id', seqId);
			
			$('#check').attr('id', 'check'+seqNo);
			$('#label').attr('id', 'label'+seqNo);
			$('#label').attr('for', 'check'+seqNo);
			
			$('#service_select_btn').attr('id', 'service_select_btn'+seqNo);
			$('#service_delete_btn').attr('id', 'service_delete_btn'+seqNo);
			
			$('#service_select_btn'+seqNo).click(function(){
				layerManage.layer.open('.service_select_layer');
				customerChargeManage.layerpop.slide.getData(seqNo);
			});
			
			$('#service_delete_btn'+seqNo).click(function(){
				root.layerpop.slide.deleteData(seqNo);
			});
		}
	}
})();

CustomerChargeManage.prototype.add.select = (function(){
	return {
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
		validate : function(obj){
			$('#lst_table_slide_save').attr('disabled',false);
			$('#lst_table_slide_save').css('border', '1px solid #80b441');
			
			$('.selectYearDate').css('border', '1px solid #80b441');
			$('.selectMonthDate').css('border', '1px solid #80b441');
			
			$('#service_insert_slide_feedback').hide(0);
			
			var preSy, preEy, preSmd, preEmd, preThis;
			var check = true;
			$('.lst_table_slide_title').each(function(k, v){
				var sy, ey;
				var syd = $(this).find('.start_year_date').val();
				var eyd = $(this).find('.end_year_date').val();
				var smd = $(this).find('.start_month_date').val();
				var emd = $(this).find('.end_month_date').val();
				
				if(smd < 10){
					syd+='0';
				}
				if(emd < 10){
					eyd+='0';
				}
				sy = syd+""+smd;
				ey = eyd+""+emd;
				if(Number(sy) > Number(ey)){
					check = false;
					$(this).find('.selectYearDate').css('border', '1px dashed red');
					$(this).find('.selectMonthDate').css('border', '1px dashed red');
				}
				if(Number(preEy) >= Number(sy)){
					check = false;
					$(this).find('.selectYearDate').css('border', '1px dashed red');
					$(this).find('.selectMonthDate').css('border', '1px dashed red');
					$(preThis).find('.selectYearDate').css('border', '1px dashed red');
					$(preThis).find('.selectMonthDate').css('border', '1px dashed red');
				}
				preSy = sy;
				preEy = ey;
				preThis = this;
			});
			
			if(!check){
				$('#service_insert_slide_feedback').hide(0);
				$('#service_insert_slide_feedback').text('이용기간을 확인하시기 바랍니다.');
				$('#service_insert_slide_feedback').show(500);
				$('#lst_table_slide_save').attr('disabled',true);
				$('#lst_table_slide_save').css('border', '1px solid red');
			}
		}
	}
})();

CustomerChargeManage.prototype.slide = (function(){
	var root;
	
	return {
		serviceLine : function(){
			var preTd, currTd, preThis;
			$('.lst_table_slide tbody tr').each(function(k,v){
				if(!k){
					preThis = this;
				}else{
					preTd = $(preThis).find('td').eq(0).text();
					currTd = $(this).find('td').eq(0).text();
					if(preTd != currTd){
						$(preThis).css('border-bottom', '1px dashed #8fc050');
					}
					preThis = this;
				}
			});
			$('.lst_table_slide tbody').each(function(k,v){
				var trList = $(this).find('tr');
				trList.eq(trList.length-1).css('border-bottom', '1px solid #ddd'); //이거수정
			});
		},
		init : function(company_id){
			root = this.root;
			root.sequence = 0;
			
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			var sdate = $("#sdate").val() + " 00:00:00";
			var edate = $("#edate").val() + " 23:59:59";
			var totalCnt = 0;
			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
				"sdate":sdate,
				"edate":edate,
				"company_id":company_id
			};
			ajaxGet("/${map.loanId}/api/companyInfoTitleSelect", params, function(response){
				if (response.result) {
					var basic_seqId = $('#basic_seqId').val();

					$('#lst_table_slide_title').remove();
					$('#lst_table_slide').remove();
					$('.lst_table_rm').remove();
					
					if(!response.list.length){
						var seqNo = (root.sequence)++;
						var today = new Date();
						var year = today.getFullYear();
						var month = today.getMonth()+1;
						
						//var seqNo = $('.lst_table_slide').length;
						
						var seqId = year + '_' + month + '_' + year + '_' + month;
						
						$(".contents").append(root.getInnerHtml_slide_title());
						$(".contents").append(root.getInnerHtml_slide());
						
						root.add.slideAttrChangeId(seqNo, seqId);
						
						root.add.select.year('#start_year_date'+seqNo, year);
						root.add.select.year('#end_year_date'+seqNo, year);
						root.add.select.month('#start_month_date'+seqNo, month);
						root.add.select.month('#end_month_date'+seqNo, month);  //여기 중복되는 코드 없애기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					}else{
						$(response.list).each(function(k,v){
							var seqNo = (root.sequence)++;
							var seqId = v.start_year_date + '_' + v.start_month_date + '_' + v.end_year_date + '_' + v.end_month_date;
							$(".contents").append(root.getInnerHtml_slide_title());
							$(".contents").append(root.getInnerHtml_slide());
							
							root.add.slideAttrChangeId(seqNo, seqId);
							
							root.add.select.year('#start_year_date'+seqNo, v.start_year_date);
							root.add.select.year('#end_year_date'+seqNo, v.end_year_date);
							root.add.select.month('#start_month_date'+seqNo, v.start_month_date);
							root.add.select.month('#end_month_date'+seqNo, v.end_month_date);

						});
					}
					
					root.basic.getData(company_id);
					root.slide.view(company_id);
					//getViewCustomerChargeInfoAjax(company_id);
				} else {
					if(response.message != "")
						alert(response.message);
					if(response.url != "")
						window.location.href=response.url;
				}
			}, params);
		},
		view : function(company_id){
			var pageSize = $("#pageSize option:selected").val();
			var pageNo = $("#pageNo").val();
			var sdate = $("#sdate").val() + " 00:00:00";
			var edate = $("#edate").val() + " 23:59:59";
			var totalCnt = 0;
			var params = {
				"pageNo": pageNo, 
				"pageSize": pageSize,
				"sdate":sdate,
				"edate":edate,
				"company_id":company_id
			};
			ajaxGet("/${map.loanId}/api/customerChargeSelectList", params, function(response){
				if (response.result) {
					$("#lst_table_slide tbody").empty();
					
					var seqNo = (root.sequence);

					if(response.list.length == 0){
						$("#lst_table_slide tbody").append(
							"<tr>"+
								"<td colspan='5'>검색된 데이터가 없습니다.</td>"+
							"</tr>"
						);
					}else{
						$(".lst_table_rm tbody").empty();
						$(response.list).each(function(k,v){
							seqId = v.start_year_date + '_' + v.start_month_date + '_' + v.end_year_date + '_' + v.end_month_date;
							
							$('#'+seqId).closest('.lst_table_slide').find('tbody').append(
								"<tr>"+
									"<input type='hidden' class='service_type' value='"+v.service_type+"'>"+
									"<input type='hidden' class='customer_service_type' value='"+v.customer_service_type+"'>"+
									"<td class='width_217'>"+v.customer_service_name+"("+v.service_name+")<input type='hidden' class='seq' value='"+v.service_seq+"'></td>"+
									"<td class='width_217'><input type='text' class='short right number monthly_use' value="+numberWithCommas(v.monthly_use)+">건 이상</td>"+
									"<td class='width_217'><input type='text' class='short right number customer_charge_change_ev charge' value="+numberWithCommas(v.charge)+">원</td>"+
									"<td class='width_217'><input type='text' class='short right number customer_charge_change_ev discount' value="+numberWithCommas(v.discount)+">원</td>"+
									"<td class='width_217'><span class='rst'>"+numberWithCommas(v.charge-v.discount)+"</span>원</td>"+
								"</tr>"
							);
						});
					}
					$('.disabled').attr('disabled',false);
				} else {
					if(response.message != "")
						alert(response.message);
					if(response.url != "")
						window.location.href=response.url;
				}
				root.slide.serviceLine();
			}, params);
		},
		insertData : function(){/////////////////////
			var company_id = $('#company_id').val();
			var list = new Array();
			
			$('.lst_table_slide').each(function(k,v){
				/*var dates = $(this).find('.lst_table_slide_hidden').attr('id');
				var date = dates.toString().split('_');

				var start_year_date = date[0];
				var end_year_date = date[1];
				var start_month_date = date[2];
				var end_month_date = date[3];*/
				
				var dates = new Array();
				$('#lst_table_slide_title'+k).find('select').each(function(k,v){
					dates[k] = $(this).val();
				});
				
				var start_year_date = dates[0];
				var start_month_date = dates[1];
				var end_year_date = dates[2];
				var end_month_date = dates[3];
				
				
				$(this).find('tbody tr').each(function(k,v){
					var monthly_use = $(this).find('.monthly_use').val();
					var charge = $(this).find('.charge').val();
					var discount = $(this).find('.discount').val();
					var service_type = $(this).find('.service_type').val();
					var customer_service_type = $(this).find('.customer_service_type').val();
					
					monthly_use = removeComma(monthly_use);
					charge = removeComma(charge);
					discount = removeComma(discount);
					
					var param = {
						'company_id':company_id,
						'service_type':service_type,
						'customer_service_type':customer_service_type,
						'charge': charge,
						'monthly_use':monthly_use,
						'discount':discount,
						'start_year_date':start_year_date,
						'end_year_date':end_year_date,
						'start_month_date':start_month_date,
						'end_month_date':end_month_date,
						'endless_yn':'n'
					};
					
					if(!service_type){
						return true;
					}
					
					list.push(param);
				});
			});
			
			var params = {
				'list' : list,
				'company_id' : company_id
			};
			var rst = ajaxGet("/${map.loanId}/api/customerServiceSlideInsert", params, function(response){
				if(!response.result){
					$('#service_insert_slide_feedback').hide(0);
					$('#service_insert_slide_feedback').text('저장에 실패하였습니다. 서비스 이용기간 또는 월 이용건수를 확인하시기 바랍니다.');
					$('#service_insert_slide_feedback').show(500);
				}else{
					$('#service_insert_slide_feedback').hide(0);
					$('#service_insert_slide_feedback').text('저장을 완료하였습니다.');
					$('#service_insert_slide_feedback').show(500);
				}
			}, null);
		}
	}
})();

CustomerChargeManage.prototype.basic = (function(){
	return {
		getData : function(company_id){
			var params = {
				'company_id':company_id
			};
			
			ajaxGet("/${map.loanId}/api/customerChargeBasicSelectList", params, function(response) {
				if (response.result) {
					$("#lst_table_basic tbody").empty();
					if(!response.list.length){
						$("#lst_table_basic tbody").append(
							"<tr>"+
								"<td>이용서비스, 기본요금 관리 정보가 없습니다.<input type='hidden' id='basic_service_seq' value=''></td>"+
								"<td><input type='text' class='short right number' id='' value='0'>원</td>"+
								"<td></td>"+
							"</tr>"
						);
					}else{
						$(response.list).each(function(k,v){
							$("#lst_table_basic tbody").append(
								"<tr>"+	
									"<td class='customer_service_name'>"+v.customer_service_name+"("+v.service_name+")"+
										"<input type='hidden' id='basic_service_seq' value='"+v.seq+"'>"+
										"<input type='hidden' id='service_type' value='"+v.service_type+"'>"+
										"<input type='hidden' id='customer_service_type' value='"+v.customer_service_type+"'>"+
									"</td>"+
									"<td><input type='text' class='short right number' id='charge' value="+numberWithCommas(v.charge)+">원</td>"+
									"<td><button type='button' id='basic_service_delete_btn' class='btn_st_100 bt02'><span>- 삭제</span></button></td>"+
								"</tr>"
							);
						});
					}
					
					//$('#lst_table_service_bottom').find('#seqId').val(seqId);
				} else {
					if(response.message != "")
						alert(response.message);
					if(response.url != "")
						window.location.href=response.url;
				}
			}, company_id);
		},
		insertData : function(){
			var company_id = $('#company_id').val();
			var list = new Array();
			$('#lst_table_basic tbody tr').each(function(k,v){
				var service_type = $(this).find('#service_type').val();
				var customer_service_type = $(this).find('#customer_service_type').val();
				var charge = $(this).find('#charge').val();
				charge = removeComma(charge);
				
				var param = {
					'company_id':company_id,
					'service_type':service_type,
					'customer_service_type':customer_service_type,
					'charge':charge
				};
				
				list.push(param);
				
			});
			var params = {
				'list' : list
			};
			ajaxGet("/${map.loanId}/api/customerServiceBasicInsert", params, function(){
				$('#service_insert_basic_feedback').hide(0)
				$('#service_insert_basic_feedback').show(500);
			}, null);
			
		}
		
	}
})();


CustomerChargeManage.prototype.layerpop = (function(){return {}})();

CustomerChargeManage.prototype.layerpop.slide = (function(){
	var root;
	return {
		deleteData : function(seq){
			$("#lst_table_slide_title"+seq).remove();
			$("#lst_table_slide"+seq).remove();
		},
		getData : function(seqId){
			root = this.root;
			var params = {
			};
			ajaxGet("/${map.loanId}/api/customerServiceSelectList", params, function(response){
				if (response.result) {
					$("#lst_table_service tbody").empty();
					$(response.list).each(function(k,v){
						$("#lst_table_service tbody").append(
							"<tr>"+
								"<input type='hidden' class='service_type' value='"+v.service_type+"'>"+
								"<input type='hidden' class='customer_service_type' value='"+v.customer_service_type+"'>"+
								"<td class='width_50'>"+
									"<div class='pure-checkbox'>"+
										"<input id='radio_"+v.seq+"_y' name='radio_"+v.seq+"' type='checkbox' class='radio'>"+
										"<label for='radio_"+v.seq+"_y'></label>"+
										"<input type='hidden' class='seq' value='"+v.seq+"'>"+
									"</div>"+
								"</td>"+
								"<td class='width_300 center customer_service_name'>"+v.customer_service_name+"("+v.service_name+")</td>"+
								"<td class='al'>"+v.customer_service_comment+"</td>"+
								"<td class='center'><input class='discount_cnt width_30' type='text' value=1></td>"+
							"</tr>"
						);
					});
					$('#lst_table_service_bottom').find('#seqId').val(seqId);
				} else {
					if(response.message != "")
						alert(response.message);
					if(response.url != "")
						window.location.href=response.url;
				}
			}, company_id);
			
		},
		confirm : function(){
			var seqId = $('#lst_table_service_bottom').find('#seqId').val();
			var confirm = true;
			var target = $("#lst_table_service tbody").find('tr');

			$("#lst_table_service tbody").find('tr').each(function(k,v){
				var chk = $(this).find("input:checkbox").is(":checked");
				if(chk){
					var cnt  = $(this).find('.discount_cnt').val();
					if(cnt == '' || cnt<1){
						confirm = false;
					}
				}
			});
			if(confirm){
				$("#lst_table_slide"+seqId+" tbody").empty();
				$("#lst_table_service tbody").find('tr').each(function(k,v){
					var chk = $(this).find("input:checkbox").is(":checked");
					if(chk){
						var customer_service_name = $(this).find('.customer_service_name').text();
						var service_type = $(this).find('.service_type').val();
						var customer_service_type = $(this).find('.customer_service_type').val();
						var seq  = $(this).find('.seq').val();
						var cnt  = $(this).find('.discount_cnt').val();
						for(var i = 0; i < cnt ; i++){
							$("#lst_table_slide"+seqId+" tbody").append(
								"<tr>"+	
									"<input type='hidden' class='service_type' value='"+service_type+"'>"+
									"<input type='hidden' class='customer_service_type' value='"+customer_service_type+"'>"+
									"<td class='width_217'>"+customer_service_name+"</td>"+
									"<td class='width_217'><input type='text' class='short right number monthly_use' value='"+numberWithCommas(i*10000)+"'>건 이상</td>"+
									"<td class='width_217'><input type='text' class='short right number customer_charge_change_ev charge' value='0'>원</td>"+
									"<td class='width_217'><input type='text' class='short right number customer_charge_change_ev discount' value='0'>원</td>"+
									"<td class='width_217'><span class='rst'>0</span>원</td>"+
								"</tr>"
							);
						}
					}
				});
				
				$("#lst_table_slide"+seqId+" tbody tr").each(function(){
					var charge = $(this).find('.charge').val();
					var discount = $(this).find('.discount').val();
					$(this).find('.rst').text(charge - discount);
				});
				
				
				$('.disabled').attr('disabled',false);
				layerManage.layer.close(".service_select_layer");
				root.slide.serviceLine();
			}else{
				alert("선택한 서비스의 요금할인구간을 입력하십시오.");
			}
		}
		
	}
})();
CustomerChargeManage.prototype.layerpop.basic = (function(){
	return {
		getData : function(){
			var params = {
			};
			ajaxGet("/${map.loanId}/api/customerServiceSelectList", params, function(response){
				if (response.result) {
					$(response.list).each(function(k,v){
						var check = true;
						$('#lst_table_basic tbody tr td.customer_service_name').each(function(){
							var customer_service_name = $(this).text();
							if(customer_service_name == (v.customer_service_name+"("+v.service_name+")")){
								check = false;
								return;
							}
						});
						if(check){
							$("#lst_table_service_basic tbody").append(
								"<tr>"+
									"<td class='width_50'>"+
										"<div class='pure-checkbox'>"+
											"<input id='radio_"+v.seq+"_y' name='radio_"+v.seq+"' type='checkbox' class='radio'>"+
											"<label for='radio_"+v.seq+"_y'></label>"+
											"<input type='hidden' class='seq' value='"+v.seq+"'>"+
											"<input type='hidden' id='service_type' value='"+v.service_type+"'>"+
											"<input type='hidden' id='customer_service_type' value='"+v.customer_service_type+"'>"+
										"</div>"+
									"</td>"+
									"<td class='width_300 center customer_service_name'>"+v.customer_service_name+"("+v.service_name+")</td>"+
									"<td class='al'>"+v.customer_service_comment+"</td>"+
								"</tr>"
							);
						}
					});
					if($("#lst_table_service_basic tbody tr").length == 0){
						$("#lst_table_service_basic tbody").append(
							"<tr class='center'>"+
								"<td colspan='3'>추가할 수 있는 서비스가 없습니다."+
								"</td>"+
							"</tr>"
						);
						/*layerManage.layer.close(".service_select_basic_layer", function(){
							alert('추가할 수 있는 서비스가 없습니다.')
						});*/
					}
					$('#lst_table_service_basic_bottom').find('#seqId').val(seqId);
				} else {
					if(response.message != "")
						alert(response.message);
					if(response.url != "")
						window.location.href=response.url;
				}
			});
		},
		confirm : function(){
			var seqId = $('#lst_table_service_basic_bottom').find('#seqId').val();
			var confirm = true;
			var target = $("#lst_table_service_basic tbody").find('tr');

			$("#lst_table_service_basic tbody").find('tr').each(function(k,v){
				var chk = $(this).find("input:checkbox").is(":checked");
				if(chk){
					var cnt  = $(this).find('.discount_cnt').val();
					if(cnt == '' || cnt<1){
						confirm = false;
					}
				}
			});
			if(confirm){
				var empty = $('#lst_table_basic #basic_service_seq').val();
				if(!empty){
					$("#lst_table_basic tbody").empty();
				}
				
				$("#lst_table_service_basic tbody").find('tr').each(function(){
					var chk = $(this).find("input:checkbox").is(":checked");
					if(chk){
						var seq = $(this).find('.seq').val();
						var customer_service_name = $(this).find('.customer_service_name').text();
						var customer_service_type = $(this).find('#customer_service_type').val();
						var service_type = $(this).find('#service_type').val();
						$("#lst_table_basic tbody").append(
							"<tr>"+	
								"<td class='customer_service_name'>"+customer_service_name+
									"<input type='hidden' id='basic_service_seq' value='"+seq+"'>"+
									"<input type='hidden' id='service_type' value='"+service_type+"'>"+
									"<input type='hidden' id='customer_service_type' value='"+customer_service_type+"'>"+
								"</td>"+
								"<td><input type='text' class='short right number' id='charge' value='"+numberWithCommas(0)+"'>원</td>"+
								"<td></td>"+
							"</tr>"
						);
					}
				});
				layerManage.layer.close(".service_select_basic_layer");
				//$(".service_select_layer").attr("style","display:none;");
			}else{
				alert("선택한 서비스의 요금할인구간을 입력하십시오.");
			}
		}
	}
})();
