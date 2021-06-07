function DomainManage(){
	this.init();
}
DomainManage.prototype = (function(){
	var namespace = 'admin.DomainManage';
	var root;
	return {
		init: function(){
			root = this;
		},
		getNameSpace  : function(){
			return namespace;
		},
		domain : {
			listGet : function(company_id){
				var params = {
					"company_id": company_id
				};
				
				ajaxGet("/${map.loanId}/api/domainListSelect", params, root.domain.listGet_cb, company_id);
			},
			listGet_cb : function(response){
				var stateNo = 1;
				if (response.result) {
					$("#lst_table_domainInfo tbody").empty();
					if(response.list.length >= 1){
						$(response.list).each(function(k,v){
							if(v.domain_code != 'delete'){
								$("#lst_table_domainInfo tbody").append(
									"<tr class='height_50'>"+
										"<td class='border_right'><input type='hidden' id='seq_domainInfo' value='"+v.seq+"'>"+stateNo+"</td>"+
										"<td class='border_right' id='domain_domainInfo'>"+v.domain+"</td>"+
										"<td class='border_right'>"+v.service_name+"</td>"+
										"<td><button type='button' id='domain_delete_btn' class='btn_st_100 bt02'><span>- 도메인 삭제</span></button></td>"+
									"</tr>"
								);
							}else{
								$("#lst_table_domainInfo tbody").append(
									"<tr class='height_50'>"+
										"<td class='border_right'><input type='hidden' id='seq_domainInfo' value='"+v.seq+"'>"+stateNo+"</td>"+
										"<td class='border_right text_lineThrough' id='domain_domainInfo'>"+v.domain+"</td>"+
										"<td class='border_right'>"+v.service_name+"</td>"+
										"<td>사용안함</td>"+
									"</tr>"
								);
							}
							stateNo += 1;
						});
					}else{
						$("#lst_table_domainInfo tbody").append(
								"<tr class='height_50'>"+
									"<td class='border_right'>-</td>"+
									"<td class='border_right'>등록된 도메인이 없습니다.</td>"+
									"<td class='border_right'>-</td>"+
									"<td>-</td>"+
								"</tr>"
							);
					}
				}
			},
			infoGet : function(company_id){
				var params = {
					"company_id": company_id
				};
				
				ajaxGet("/${map.loanId}/api/domainInfoSelect", params, root.domain.infoGet_cb, company_id);
			},
			infoGet_cb : function(response, company_id){
				//$('#company_id').val(company_id);
				var stateNo = 1;
				if (response.result) {
					$("#lst_table_urlInfo tbody").empty();
					$(response.list).each(function(k,v){
						if(v.use_yn == 'y'){
							var innerHtml ="<tr class='height_50'>"+
							"<td class='border_right'><input type='hidden' id='seq_uriInfo' value='"+v.seq+"'>"+stateNo+"</td>"+
							"<td class='border_right'><span id='domain_uriInfo'>"+v.domain+"</span><br>"+v.service_name+"<br>"+
							"</td>"+
							"<td class='border_right'><input id='uri_uriInfo' type='text' class='width_90p' value='"+v.uri+"' placeholder='등록된 URL이 없습니다.' />"+
							"</td>"+
							"<td class='border_right'><input id='service_code_uriInfo' type='text' class='width_90p' value='"+v.service_code+"' placeholder='ex) ib' /></td>"+
							"<td id='reg_date_uriInfo' class='border_right'>"+v.reg_date+"</td>"+
							"<td>"+
							"<button type='button' id='uri_update_btn' class='btn_st_100 bt02'><span>= URL 수정</span></button>";
							if(v.domain_code == 'ready'){
								innerHtml += "</td></tr>";
							}else{
								innerHtml += "<button type='button' id='uri_delete_btn' class='btn_st_100 bt02'><span>- URL 삭제</span></button>"+
										"</td></tr>";
							}
							$("#lst_table_urlInfo tbody").append(innerHtml);
						}else{
							$("#lst_table_urlInfo tbody").append(
								"<tr class='height_50'>"+
									"<td class='border_right'><input type='hidden' id='seq_uriInfo' value='"+v.seq+"'>"+stateNo+"</td>"+
									"<td class='border_right'><span id='domain_uriInfo'>"+v.domain+"</span><br>"+v.service_name+"<br>"+
									"</td>"+
									"<td class='border_right text_lineThrough'><input class='width_90p text_lineThrough' type='text' value='"+v.uri+"' readonly='readonly' disabled='disabled'>"+
									"</td>"+
									"<td id='service_code_uriInfo' class='border_right'>"+v.service_code+"</td>"+
									"<td id='reg_date_uriInfo' class='border_right'>"+v.reg_date+"</td>"+
									"<td>사용안함"+
									"</td>"+
								"</tr>"
							);
						}
						stateNo += 1;
					});
					$('#lst_table_urlInfo .table').rowspan(1);
				}
			},
			selectBox_view: function(company_id){
				var params = {
					"company_id": company_id
				};
				
				ajaxGet("/${map.loanId}/api/domainListSelect", params, root.domain.selectBox_view_cb, company_id);
			},
			selectBox_view_cb: function(response){
				if(response.result){
					if (response.result) {
						$("#domainList_select").empty();
						$("#domainList_select").append('<option value="">도메인선택</option>');
						$(response.list).each(function(k,v){
							if(v.domain_code != 'delete'){
								$("#domainList_select").append(
										"<option id='"+v.service_type+"' value='"+v.service_name+"'>"+ v.domain+"</option>"
								);
							}
						});
					}
				}
			},
			check : function(obj){
				var service_type = $('#service_select').val();
				//var protocol_type = $('#protocol_select').val();
				var domain_text = $('#domain_input_text').val();
				var uri_input_text = $('#uri_input_text').val();
				var serviceCode_input_text = $('#serviceCode_input_text').val();
				var company_id = $('#company_id').val();
				
				if(!company_id){
					alert('회사를 선택해 주세요.')
					return;
				}else if(!service_type){
					alert('서비스를 선택해 주세요.')
					return;
				}/*else if(!protocol_type){
					alert('프로토콜을 선택해 주세요.')
					return;
				}*/else if(!domain_text){
					alert('도메인을 입력해 주세요.')
					return;
				}else if(!uri_input_text){
					alert('uri를 입력해 주세요.')
					return;
				}else if(!serviceCode_input_text){
					alert('서비스코드를 입력해 주세요.')
					return;
				}
				
				var params = {
					"company_id": company_id,
					"service_type": service_type,
					"domain": domain_text,
					"uri": uri_input_text,
					"service_code": serviceCode_input_text
				};
				
				ajaxGet("/${map.loanId}/api/domainCheck", params, root.domain.check_cb, params);
			},
			
			check_cb : function(response, data){
				if(response.result){
					root.domain.insert(response.list[0].domain_cnt, data);
				}
			},
			insert : function(check, data){
				if(check == 0){
					var params = {
						"company_id": data.company_id,
						"service_type": data.service_type,
						"domain": data.domain,
						"domain_code": data.company_id,
						"uri": data.uri,
						"service_code": data.service_code
					};
					ajaxGet("/${map.loanId}/api/domainInsert", params, root.domain.insert_cb, params);
				}else{
					alert('이미 등록된 주소 또는 기존에 사용내역이 있습니다.');
				}
			},
			insert_cb : function(response, data){
				if(response.result){
					root.domain.infoGet(data.company_id);
					root.domain.listGet(data.company_id);
					root.domain.selectBox_view(data.company_id);
					$('#domain_input_text').val('');
					$('#uri_input_text').val('');
					$('#serviceCode_input_text').val('');
					
				}
			},
			blind : function(obj){
				var company_id = $('#company_id').val();
				var seq = $(obj).closest('tr').find('#seq_domainInfo').val();
				var domain = $(obj).closest('tr').find('#domain_domainInfo').text();
				
				var params = {
					"company_id": company_id,
					"seq": seq,
					"domain": domain
				};
				
				ajaxGet("/${map.loanId}/api/domainDelete", params, root.domain.blind_cb, params);
			},
			blind_cb : function(response, data){
				if(response.result){
					root.domain.infoGet(data.company_id);
					root.domain.listGet(data.company_id);
					root.domain.selectBox_view(data.company_id);
				}
			}
		},
		uri : {
			check : function(){
				var company_id = $('#company_id').val();
				var service_type = $('#domainServiceType').val();
				var domain = $('#domainList_select').children("option:selected").text();
				var domain_check = $('#domainList_select').children("option:selected").val();
				var uri = $('#uri_domainInfo_insert').val();
				var service_code = $('#serviceCode_domainInfo_insert').val();
				
				if(!company_id){
					alert('회사를 선택해 주세요.')
					return;
				}else if(!domain_check){
					alert('도메인을 선택해 주세요.')
					return;
				}else if(!uri){
					alert('URL을 입력해 주세요.')
					return;
				}else if(!service_code){
					alert('서비스 번호를 입력해 주세요.')
					return;
				}
				
				var params = {
					"company_id": company_id,
					"service_type": service_type,
					"service_code": service_code,
					"domain": domain,
					"uri": uri,
					"domain_code": company_id
				};
				ajaxGet("/${map.loanId}/api/domainUriCheck", params, root.uri.check_cb, params);
			},
			check_cb : function(response, data){
				if(response.result){
					var check = response.list[0].domain_cnt;
					if(check){
						alert('이미 등록된 URL주소 입니다.')
						//root.uri.confirm();
					}else{
						root.uri.insert();
						//root.uri.count();
					}
					
				}
			},
			count: function(){
				var company_id = $('#company_id').val();
				var service_type = $('#domainServiceType').val();
				var domain = $('#domainList_select').children("option:selected").text();
				var uri = $('#uri_domainInfo_insert').val();
				
				var params = {
					"company_id": company_id,
					"service_type": service_type,
					"domain": domain,
					"uri": uri,
					"domain_code": company_id
				};
				ajaxGet("/${map.loanId}/api/domainUriInsertCheck", params, root.uri.count_cb, params);
			},
			count_cb : function(response, data){
				if(response.result){
					var check = response.list[0].domain_cnt;
					console.log("!");
					if(check){
						alert('이미 등록된 URL 입니다.')
					}else{
						console.log("@");
						root.uri.insert();
					}
					
				}
			},
			confirm : function(){
				var company_id = $('#company_id').val();
				var service_type = $('#domainServiceType').val();
				var domain = $('#domainList_select').children("option:selected").text();
				var uri = $('#uri_domainInfo_insert').val();
				var service_code = $('#serviceCode_domainInfo_insert').val();
				
				var params = {
					"company_id": company_id,
					"service_type": service_type,
					"service_code": service_code,
					"domain": domain,
					"uri": uri,
					"domain_code": company_id
				};
				ajaxGet("/${map.loanId}/api/domainUriCheckIsYes", params, root.uri.confirm_cb, params);
			},
			confirm_cb : function(response, data){
				if(response.result){
					root.domain.infoGet(data.company_id);
					$('#uri_domainInfo_insert').val('');
				}
			},
			insert: function(){
				var company_id = $('#company_id').val();
				var service_type = $('#domainServiceType').val();
				var domain = $('#domainList_select').children("option:selected").text();
				var uri = $('#uri_domainInfo_insert').val();
				var service_code = $('#serviceCode_domainInfo_insert').val();
				var params = {
					"company_id": company_id,
					"service_type": service_type,
					"service_code": service_code,
					"domain": domain,
					"uri": uri,
					"domain_code": company_id
				};
				ajaxGet("/${map.loanId}/api/domainUriInsert", params, root.uri.insert_cb, params);
			},
			insert_cb : function(response, data){
				if(response.result){
					//모든 reload 함수에 data parameter 받아오기, 버그 확인 
					root.domain.infoGet(data.company_id);
					$('#uri_domainInfo_insert').val('');
					$('#serviceCode_domainInfo_insert').val('');
				}
			},
			update: function(obj){
				var company_id = $('#company_id').val();
				var seq = $(obj).closest('tr').find('#seq_uriInfo').val();
				var uri = $(obj).closest('tr').find('#uri_uriInfo').val();
				var service_code = $(obj).closest('tr').find('#service_code_uriInfo').val();
				var params = {
					"company_id": company_id,
					"service_code": service_code,
					"seq": seq,
					"uri": uri
				};
				
				if(!company_id){
					alert('회사를 선택해 주세요.')
					return;
				}else if(!uri){
					alert('URL을 입력해 주세요.')
					return;
				}else if(!service_code){
					alert('서비스 번호를 입력해 주세요.')
					return;
				}
				
				ajaxGet("/${map.loanId}/api/domainUriUpdate", params, root.uri.update_cb, params);
			},
			update_cb : function(response, data){
				if(response.result){
					root.domain.infoGet(data.company_id);
					alert('수정 되었습니다.')
				}
			},
			blind: function(obj){
				var company_id = $('#company_id').val();
				var seq = $(obj).closest('tr').find('#seq_uriInfo').val();
				var uri = $(obj).closest('tr').find('#uri_uriInfo').val();
				var service_code = $(obj).closest('tr').find('#service_code_uriInfo').val();
				
				var params = {
					"company_id": company_id,
					"service_code": service_code,
					"seq": seq,
					"uri": uri
				};
				ajaxGet("/${map.loanId}/api/domainUriDelete", params, root.uri.blind_cb, params);
			},
			blind_cb : function(response, data){
				if(response.result){
					root.domain.infoGet(data.company_id);
					root.domain.listGet(data.company_id);
					root.domain.selectBox_view(data.company_id);
				}
			}
		}
	}
}());
