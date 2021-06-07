<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>굿페이퍼 비대면 서비스 관리 어드민</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
	
	<script type="text/javascript">

	function viewPage(){
		companyManage.company.listGet('paging');
	}
	
	var domainManage;
	var companyManage;
	var layerManage;
	
	$(document).ready(function(){
		domainManage = new DomainManage;
		companyManage = new CompanyManage;
		layerManage = new LayerManage;
		
		$('.disabled').attr('disabled','true');
	});
	
	$(document).on('click', '#companyList_select_btn', function(){
		layerManage.layer.open(".companyList_select_layer");
		companyManage.company.listGet('paging');
	});
	
	$(document).on('click', '#companyInfoSelectList_search_btn', function(){
		$("#pageNo").val('1');
		companyManage.company.listGet('paging');
	});
	
	$(document).on('click', '#companyInfoUpdate_btn', function(){
		if(confirm("고객 정보를 수정하시겠습니까?")) {
			companyManage.company.infoUpdate();
	    } else {
	        return false;
	    }
	});

	$(document).on('click', '#companyInfoReset_btn', function(){
		companyManage.company.reset();
	});
	
	$(document).on('click', '.companyInfoName', function(){
		var seq = $(this).attr('id');
		companyManage.company.infoGet(seq, function(){
			$('#companyInfoSelectList_searchWord').val('');
			$("#pageNo").val('1');
			var company_id = $('#company_id').val();
			domainManage.domain.listGet(company_id);
			domainManage.domain.selectBox_view(company_id);
			domainManage.domain.infoGet(company_id);
		});
		layerManage.layer.close('.companyList_select_layer');
	});
	
	$(document).on('click', '#domain_insert_btn', function(){
		domainManage.domain.check(this);
	});
	
	$(document).on('click', '#domain_delete_btn', function(){
		if(confirm("도메인을 사용중지 하시겠습니까?")) {
			domainManage.domain.blind(this);
	    } else {
	        return false;
	    }
	});
	
	$(document).on('click', '#domainUri_insert_btn', function(){
		domainManage.uri.check(this);
		//domainUriInsert(this);
	});
	
	$(document).on('click', '#uri_update_btn', function(){
		var uri = $(this).closest('tr').find('.uri_uriInfo').val();
		var service_code = $(this).closest('tr').find('.service_code_uriInfo').val();
		if(!uri){
			alert('URL을 입력해 주세요.')
			return;
		}else if(!service_code){
			alert('URL을 입력해 주세요.')
			return;
		}
		if(confirm("URL을 수정 하시겠습니까?")) {
			domainManage.uri.update(this);
	    } else {
	        return false;
	    }
	});
	
	$(document).on('click', '#uri_delete_btn', function(){
		if(confirm("URL을 삭제 하시겠습니까?")) {
	        domainManage.uri.blind(this);
	    } else {
	        return false;
	    }
		
	});
	
	$(document).on('click', '#uri_use_btn', function(){
		if(confirm("URL을 사용하시겠습니까?")) {
	        domainManage.uri.use(this);
	    } else {
	        return false;
	    }
		
	});
	
	$(document).on('change', '#domainList_select', function(){
		var service_name = $(this).children("option:selected").val();
		var service_type = $(this).children("option:selected").attr('id');
		$('#domainServiceName').text(service_name);
		$('#domainServiceName').append("<input type='hidden' id='domainServiceType' value='"+service_type+"'>");
	});	
		
	
	function layer_close(){
		layerManage.layer.close('.companyList_select_layer', function(){
			$('#companyInfoSelectList_searchWord').val('');
		});
	}
	</script>
	</head>
	<body>
		<div id="wrap">
			<!-- header -->
			<%@ include file="/WEB-INF/views/include_web/header_top.jsp"%>
				<!-- hidden -->
				<input type="hidden" id="pageNo" value="1" />
				<input type="hidden" id="company_id" value="" />
				<input type="hidden" id="company_seq" value="" />
			<!-- //header -->
		
			<!-- container -->
			<div id="container">
				<!-- sub menu -->
				<%@ include file="/WEB-INF/views/include_web/sub_menu.jsp"%>
				<!-- sub search -->
				<div class="sub_topsearch">
					
					<!-- <strong class="sub_txt">홈</strong>
					<span> > </span>
					<strong class="sub_txt">서비스관리</strong>
					<span> > </span>
					<strong class="sub_title" style="font-size: 30px;">서비스 등록</strong>
					<span> - </span>
					<strong class="sub_txt">서비스관리</strong>
					<span> - </span>
					<strong class="sub_txt">고객(요금)관리</strong> -->
					
				</div>
		
				<!-- top btn -->
				
				<!-- contents -->
				<div class="contents">
					<%@ include file="/WEB-INF/views/include_web/section/customer_info.jsp"%>
					<br><br>
					
					<!-- //lst_table_middle_title -->
					<div class="top_area clearbox" id="lst_table_domainInfo_title">
						<div class="lf_box">
							<strong class="sub_title">도메인 관리</strong>
							<br>
							<strong class="sub_txt">도메인 사용중지 시 하위 URL주소들의 사용이 불가능 합니다. 사용중지 시 만전을 기해주시기 바랍니다.</strong>
						</div>
						<div class="rf_box">
						</div> 
						<div class="clearbox"></div>
					</div>
					
					<!-- lst_table_middle -->
					<div class="lst_table01" id="lst_table_domainInfo">
						<table class="table center" cellspacing="0" cellpadding="0">
			                <caption>도메인 관리</caption>
			                <colgroup>
			                	<col width="5%">
			                	<col width="55%">
			                	<col width="30%">
			                	<col width="10%">
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th class="width_217 border_right">도메인<br>순번</th>
									<th class="width_217 border_right">도메인</th>
									<th class="width_217 border_right">서비스 종류</th>
									<th class="width_217">확인</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="border_right">-</td>
									<td class="border_right">-</td>
									<td class="border_right">-</td>
									<td>-</td>
								</tr>
							</tbody>
						</table>
					</div>
					<br><br>
					<!-- //lst_table_middle -->
					
					
					<!-- //lst_table_middle_title -->
					<div class="top_area clearbox" id="lst_table_domainInsert_title">
						<div class="lf_box">
							<strong class="sub_title">도메인 등록</strong>
							<br>
							<strong class="sub_txt">서비스 요청 도메인주소와 등록된 도메인주소가 불일치 하는 경우 서비스가 제한됩니다.</strong>
						</div>
						<div class="rf_box">
						</div> 
						<div class="clearbox"></div>
					</div>
					
					<!-- lst_table_middle -->
					<div class="lst_table01" id="lst_table_domainInsert">
						<table class="table center" cellspacing="0" cellpadding="0">
			                <caption>도메인 등록</caption>
			                <colgroup>
			                	<col width="12%">
			                	<col width="32%">
			                	<col width="32%">
			                	<col width="16%">
			                	<col width="8%">
			                </colgroup>
			                <thead>
								<tr>
									<th class='border_right'>이용서비스</th>
									<!-- <th class='border_right'>프로토콜</th> -->
									<th class='border_right'>도메인</th>
									<th class='border_right'>URL</th>
									<th class='border_right'>서비스 코드</th>
									<th>확인</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										<select name="" id="service_select" class="select01 width_max disabled">
											<option value="">서비스선택</option>
											<option value="1">기본형 계좌인증</option>
											<option value="2">통합형 계좌인증</option>
											<option value="3">농협 계좌인증</option>
											<option value="4">계좌 실명확인</option>
											<option value="5">파일 스캔</option>
										</select>
									</td>
									<!-- <td>
										<select name="" id="protocol_select" class="select01 width_max disabled">
											<option value="">프로토콜선택</option>
											<option value="http://">http://</option>
											<option value="https://">https://</option>
											<option value="ftp://">ftp://</option>
										</select>
									</td> -->
									<td>
										<input type='text' id="domain_input_text" class="width_max disabled" value="" placeholder="ex) https://goodpaper.co.kr">
									</td>
									<td>
										<input type='text' id="uri_input_text" class="width_max disabled" value="" placeholder="ex) /index.html">
									</td>
									<td>
										<input type='text' id="serviceCode_input_text" class="width_max disabled" value="" placeholder="ex) goodpaper01">
									</td>
									<td>
										<button type="button" id="domain_insert_btn" class="btn_st_insert bt01 width_max"><span>등록</span></button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<br><br>
					<!-- //lst_table_middle -->
					
					<!-- //lst_table_middle_title -->
					<div class="top_area clearbox" id="lst_table_urlInfo_title">
						<div class="lf_box">
							<strong class="sub_title">URL 관리</strong>
						</div>
						<div class="rf_box">
						</div> 
						<div class="clearbox"></div>
					</div>
					
					<!-- lst_table_middle -->
					<div class="lst_table01" id="lst_table_urlInfo">
						<table class="table center" cellspacing="0" cellpadding="0">
			                <caption>URL 관리</caption>
			                <colgroup>
			                	<col width="5%">
			                	<col width="31%">
			                	<col width="31%">
			                	<col width="15%">
			                	<col width="10%">
			                	<col width="8%">
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th class="border_right">URL<br>순번</th>
									<th class="border_right">도메인/서비스  종류</th>
									<th class="border_right">URL 주소</th>
									<th class="border_right">서비스<br>코드</th>
									<th class="border_right"">등록일자</th>
									<th>확인</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="border_right">-</td>
									<td class="border_right">-</td>
									<td class="border_right">-</td>
									<td class="border_right">-</td>
									<td class="border_right">-</td>
									<td>-</td>
								</tr>
							</tbody>
						</table>
					</div>
					<br>
					<br>
					<!-- //lst_table_middle -->
					
					<!-- //lst_table_middle_title -->
					<div class="top_area clearbox" id="lst_table_domainUriInsert_title">
						<div class="lf_box">
							<strong class="sub_title">URL 등록</strong>
						</div>
						<div class="rf_box">
						</div> 
						<div class="clearbox"></div>
					</div>
					<!-- lst_table_middle -->
					<div class="lst_table01" id="lst_table_domainUriInsert">
						<table class="table center" cellspacing="0" cellpadding="0">
			                <caption>URL 등록</caption>
			                <colgroup>
			                	<col width="22%">
			                	<col width="17%">
			                	<col width="40%">
			                	<col width="15%">
			                	<col width="6%">
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th class="width_217 border_right">도메인</th>
									<th class="width_217 border_right">서비스 종류</th>
									<th class="width_217 border_right">URL 주소</th>
									<th class="width_217 border_right">서비스 코드</th>
									<th class="width_217">확인</th>
								</tr>
								<tr>
									<td>
										<select id="domainList_select" class="select01 width_max disabled"></select>
									</td>
									<td id="domainServiceName">
										-
									</td>
									<td>
										<input id='uri_domainInfo_insert' type='text' class='width_max disabled' value='' placeholder='ex) /admin/index.jsp'/>
									</td>
									<td>
										<input id='serviceCode_domainInfo_insert' type='text' class='width_max disabled' value=''/>
									</td>
									<td>
										<button type='button' id='domainUri_insert_btn' class='btn_st_insert bt01 width_max'><span>등록</span></button>
									</td>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					
					<!-- //lst_table_middle -->
					
				</div>
				<!-- //contents -->
				
				<!-- bottom_area -->
				<div class="bottom_area clearbox" id="bottom_area">
					<div class="cf_box">
						<!-- <button type="button" class="btn_st_bold bt01"><span>저장</span></button> -->
					</div>
				</div>
				<!-- //bottom_area -->
				
			</div>
			<br>
			<!-- //container -->
			<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
		</div>
		<!-- //wrap -->
		
		<!-- layerpopup 상호명 조회 -->
		<div class="layerpop_wrap companyList_select_layer" style="display:none;">
			<div class="layerpop_mask"></div>
			<div class="layerpop" style="width:500px; top:200px;">
				<div class="layer_content" style="padding: 15px 15px 0 15px;">
					<!-- lst_table01_companyList -->
					<div class="lst_table01 center" id="lst_table01_companyList">
						<table  cellspacing="0" cellpadding="0">
			                <caption>상호명 조회</caption>
			                <colgroup>
				                <col style="width:10%;">
				                <col style="width:auto;">
				                <col style="width:10%;">
			                </colgroup>
			                <thead>
			                	<tr class="add_notice_select_box">
									<th class="border_right">No</th>
									<th>상호명(가나다순)</th>
									<th>
										<!-- <img alt="" src="/static/img/btn_layer_close.png" height="30px;" width="30px;" style="cursor: pointer;" onclick="javascript:layer_close();"> -->
									</th>
								</tr>
							<tbody>
							</tbody>
						</table>
					</div>
					<!-- //lst_table01_companyList -->
					<!-- paging -->
					<div class="paging" id="paging"></div>
					<!-- //paging -->
					<div class="layer_btnbox center">
						<input type="text" id="companyInfoSelectList_searchWord" placeholder="상호명을 입력하세요." style="width: 100%; padding: 5px 0; margin: 0 0 5px 0;">
						<button type="button" class="btn_st_std bt03" id="companyInfoSelectList_search_btn"><span>검색</span></button>
						<button type="button" class="btn_st_std bt03" onclick="javascript:layer_close()"><span>닫기</span></button>
					</div>
				</div>
			</div>
		</div>
		<!-- //layerpopup 상호명 조회 -->
	</body>
</html>