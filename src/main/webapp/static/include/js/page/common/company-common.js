function CompanyManage(){
	this.init();
}
CompanyManage.prototype = (function(){
	var namespace = 'CompanyManage';
	var root;
	return {
		init: function(){
			root = this;
		},
		getNameSpace  : function(){
			return namespace;
		},
		company: {
			listGet : function(paging){
				var searchWord = $('#companyInfoSelectList_searchWord').val();
				var pageSize = 10;
				var pageNo = $("#pageNo").val();
				var totalCnt = 0;
				var params = {
					"pageNo": pageNo, 
					"pageSize": pageSize,
					"searchWord":searchWord,
					"paging":paging
				};
				ajaxGet("/${map.loanId}/api/companyInfoSelectList", params, root.company.listGet_cb, params);
			},
			listGet_cb : function(response, data){
				var totalCnt = 0;
				var pageBlock = 10;
				var pageSize = 10;
				var pageNo = $("#pageNo").val();
				var stateNo = 0;
				if (response.result) {
					$("#lst_table01_companyList tbody").empty();
					$(response.list).each(function(k,v){
						if(!stateNo){
							stateNo = v.totalCnt - ((pageNo-1) * pageSize);
						}
						totalCnt = v.totalCnt;
						$("#lst_table01_companyList tbody").append(
							"<tr>"+
								"<td class='center border_right'>"+stateNo+"</td>"+
								"<td><span class='cursor_pointer companyInfoName' id='"+v.seq+"'>"+v.name+"</span></td>"+
								"<td></td>"+
							"</tr>"
						);
						stateNo = stateNo - 1;
					});
					var html = setPaging(pageNo , pageSize , pageBlock , totalCnt);
					
					$("#paging").empty();
					$("#paging").append(html);
					
					if(!response.list.length){
						$("#lst_table01_companyList tbody").append(
							"<tr>"+
								"<td class='center border_right'>-</td>"+
								"<td><span class='cursor_pointer companyInfoName' id=''>검색 결과가 없습니다.</span></td>"+
								"<td></td>"+
							"</tr>"
						);
					}
				}
			},
			infoGet : function(seq, func){
				var params = {
					"seq": seq, 
				};
				ajaxGet("/${map.loanId}/api/companyInfoOne", params, root.company.infoGet_cb, func);
			},
			infoGet_cb : function(response, func){
				console.log(response);
				if (response.result) {
					$(response.map).each(function(k,v){
						$('#company_seq').val(v.seq);
						$('#company_id').val(v.id);
						$('#company_name').val(v.name);
						$('#employee_name').val(v.employee_name);
						$('#employee_tel').val(v.employee_tel);
						$('#employee_phone').val(v.employee_phone);
						$('#employee_email').val(v.employee_email);
						
						if(func){
							func();
						}
					});
					$("#lst_table_middle").attr("style","display:block;");
					$("#lst_table_middle_title").attr("style","display:block;");
					
					$('.disabled').attr('disabled',false);
					//$('input').attr('readonly','true');
				}
			},
			infoUpdate : function(){
				var name = $('#employee_name').val();
				var tel = $('#employee_tel').val();
				var phone = $('#employee_phone').val();
				var email = $('#employee_email').val();
				
				if(!name){
					alert('성명을 입력해 주세요.');
					return;
				}else if(!tel){
					alert('사무실 번호를 입력해 주세요.');
					return;
				}else if(!phone){
					alert('연락처를 입력해 주세요.');
					return;
				}else if(!email){
					alert('이메일을 입력해 주세요.');
					return;
				}
				
				var company_id = $('#company_id').val();
				var params = {
					"employee_name": name,
					"employee_tel" : tel,
					"employee_phone" : phone,
					"employee_email" : email,
					"company_id" : company_id
				};
				ajaxGet("/${map.loanId}/api/companyInfoUpdate", params, root.company.infoUpdate_cb);
			},
			infoUpdate_cb : function(response){
				if(!response.result){
					$('#service_insert_company_feedback').hide(0);
					$('#service_insert_company_feedback').text('저장에 실패하였습니다.');
					$('#service_insert_company_feedback').show(500);
				}else{
					$('#service_insert_company_feedback').hide(0);
					$('#service_insert_company_feedback').text('저장을 완료하였습니다.');
					$('#service_insert_company_feedback').show(500);
				}
			},
			reset : function(){
				var company_seq = $('#company_seq').val();
				root.company.infoGet(company_seq);
			}
		}
	}
}());
