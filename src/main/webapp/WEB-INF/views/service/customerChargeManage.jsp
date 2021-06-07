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
	
	var customerChargeManage;
	var companyManage;
	var layerManage;
	
	$(document).ready(function(){
		$('.disabled').attr('disabled','true');
		
		customerChargeManage = new CustomerChargeManage;
		companyManage = new CompanyManage;
		layerManage = new LayerManage;
	});

	$(document).on('click', '#lst_table_basic_add', function(){
		layerManage.layer.open('.service_select_basic_layer');
		customerChargeManage.layerpop.basic.getData();
	});
	$(document).on('click', '#lst_table_basic_save', function(){
		if(confirm("이용서비스 기본 요금을 저장 하시겠습니까?")) {
			customerChargeManage.basic.insertData();
	    } else {
	        return false;
	    }
	});
	
	$(document).on('click', '#lst_table_slide_add', function(){
		customerChargeManage.add.slide();
	});
	
	$(document).on('click', '#lst_table_slide_save', function(){
		if(confirm("이용서비스 적용단가 관리를 저장 하시겠습니까?")) {
			customerChargeManage.slide.insertData();
	    } else {
	        return false;
	    }
		
	});
	
	$(document).on('click', '#companyList_select_btn', function(){
		$(".companyList_select_layer").attr("style","display:block;");
		companyManage.company.listGet('paging');
	});
	
	$(document).on('click', '#companyInfoSelectList_search_btn', function(){
		$("#pageNo").val('1');
		companyManage.company.listGet('paging');
	});
	
	$(document).on('keyup', '.customer_charge_change_ev',function(){
		var charge_val = $(this).closest('tr').find('.charge').val();
		var discount_val = $(this).closest('tr').find('.discount').val();
		charge_val = removeComma(charge_val);
		discount_val = removeComma(discount_val);
		var obj = (charge_val) - (discount_val);
		
		obj = numberWithCommas(obj);
		$(this).closest('tr').find('.rst').text(obj);
		
	});
	
	$(document).on('click', '.companyInfoName', function(){
		var seq = $(this).attr('id');
		companyManage.company.infoGet(seq, function(){
			$('#companyInfoSelectList_searchWord').val('');
			$("#pageNo").val('1');
			var company_id = $('#company_id').val();
			customerChargeManage.slide.init(company_id);
		});
		layerManage.layer.close('.companyList_select_layer');
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
	
	$(document).on("keypress", "input[type=text].number", function () {
	    if((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105) && (event.keyCode != 8) && (event.keyCode != 46)){
	    	event.returnValue = false;
	    }
	});
	
	$(document).on("keyup", "input[type=text].number", function () {
	    var obj = $(this);
	    numberWithCommasInput(obj);
	});
	
	$(document).on('change', '.lst_table_slide_title select', function(){
		customerChargeManage.add.select.validate(this);
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
				<input type="hidden" id="basic_seqId" value="1" />
			<!-- //header -->
		
			<!-- container -->
			<div id="container">
				<!-- sub menu -->
				<%@ include file="/WEB-INF/views/include_web/sub_menu.jsp"%>
				<!-- sub search -->
				<div class="sub_topsearch">
					<ul class="clearbox">
						<!-- <li>
							<select id="defaultSearchCompany" class="select">
								<option value="0">전체</option>
								<option value="0">인포뱅크</option>
								<option value="0">인포나라</option>
								<option value="0">인포인포</option>
							</select>
						</li>
						<li>
							<select id="defaultSearchDate" class="select01">
								<option value="7">최근 7일</option>
								<option value="14">최근 14일</option>
								<option value="30">최근 1개월</option>
								<option value="365">최근 1년</option>
								<option value="0">직접입력</option>
							</select>
						</li>
						<li>
							<span class="sch_data"><input type="text" class="datepicker" id="sdate"></span>
							~
							<span class="sch_data"><input type="text" class="datepicker" id="edate"></span>
						</li> -->
					</ul>
				</div>
		
				<!-- contents -->
				<div class="contents">
					<!-- 고객정보 -->
					<%@ include file="/WEB-INF/views/include_web/section/customer_info.jsp"%>
					<br><br>
					<!-- 기본요금관리 -->
					<div class="top_area clearbox">
						<div class="lf_box">
							<strong class="sub_title">기본요금 관리</strong>
							<br>
							<strong class="txt">이용서비스 및 기본요금 관리</strong>
							<button type="button" class="btn_st_100 bt01 disabled" id="lst_table_basic_add"><span>서비스추가</span></button>
							<button type="button" class="btn_st_100 bt01 disabled" id="lst_table_basic_save"><span>+ 저장</span></button>
							<span id="service_insert_basic_feedback" class="text_alert hide">저장을 완료하였습니다.</span>
						</div>
					</div>
					<div class="lst_table01 center" id="lst_table_basic">
						<table class="table"border="0" cellspacing="0" cellpadding="0">
							<thead>
								<tr>
									<th class="width_217">이용서비스</th>
									<th class="width_217">기본요금</th>
									<th class="width_217">확인</th>
								<tr>
							</thead>
						<tbody>
							<tr>
								<td>
									<span>-</span><input type="hidden" id="basic_service_seq" value="">
								</td>
								<td>
									<input type="text" class="short right number" id="" value="0">원
								</td>
								<td></td>
							</tr>
						</tbody>
						</table>
					</div>
					<!-- //기본요금관리 -->
					<br><br>
					<!-- 요금관리슬라이드 --> 
					<div class="top_area clearbox">
						<div class="lf_box">
							<strong class="sub_title">적용단가 관리</strong>
							<br>
							<strong class="txt">이용서비스 및 적용단가 관리</strong>
							<button type="button" class="btn_st_100 bt01 disabled" id="lst_table_slide_add"><span>+ 기간추가</span></button>
							<button type="button" class="btn_st_100 disabled" id="lst_table_slide_save" ><span>+ 저장</span></button>
							<span id="service_insert_slide_feedback" class="text_alert hide">저장을 완료하였습니다.</span>
						</div>
					</div>
					<div class="top_area clearbox lst_table_rm lst_table_slide_title" id="lst_table_slide_title">
						<div class="lf_box">
							<strong class="txt">이용기간 : </strong>
							<select id="start_year_date" class="start_year_date selectYearDate disabled">
							</select>
							<select id="start_month_date" class="start_month_date selectMonthDate disabled">
							</select>
							<span>~</span>
							<select id="end_year_date" class="end_year_date selectYearDate disabled">
							</select>
							<select id="end_month_date" class="end_month_date selectMonthDate disabled">
							</select>
							<!-- <div class="pure-checkbox" style="display: inline-block;">
								<input id="check" name="check" type="checkbox" class="checkbox" style="position:absolute; left:-10000%;">
								<label id="label" for="check">종료월없음</label>
							</div> -->
							<button type="button" class="btn_st_100 bt01 disabled" id="service_select_btn" ><span>+ 서비스선택</span></button>
							<button type="button" class="btn_st_100 bt01 disabled" id="service_delete_btn" ><span>- 기간삭제</span></button>
						</div>
					</div>
					<div class="lst_table01 lst_table_rm lst_table_slide" id="lst_table_slide">
						<input type="hidden" class="lst_table_slide_hidden" id="lst_table_slide_hidden" value="">
						<table class="table center"border="0" cellspacing="0" cellpadding="0">
							<thead>
								<tr>
									<th class="width_217">이용서비스</th>
									<th class="width_217">월이용건수</th>
									<th class="width_217">기준단가</th>
									<th class="width_217">할인액</th>
									<th class="width_217">적용단가</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
								</tr>
							</tbody>
						</table>
						<br>
					</div>
						<!-- //요금관리슬라이드 -->
				</div>
				
				<br>
			</div>
			<!-- //container -->
			<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
		</div>
		
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
						<button type="button" class="btn_st_normal bt03" id="companyInfoSelectList_search_btn"><span>검색</span></button>
						<button type="button" class="btn_st_normal bt03" onclick="javascript:layer_close()"><span>닫기</span></button>
					</div>
				</div>
		
			</div>
		</div>
		<!-- //layerpopup 상호명 조회 -->
		
		<!-- layerpopup 기본요금 서비스선택 -->
		<div class="layerpop_wrap service_select_basic_layer" style="display:none;">
			<div class="layerpop_mask"></div>
			<div class="layerpop" style="width:600px; top:200px;">
				<div class="layer_content" style="padding: 15px 15px 0 15px;">
					<div class="layer_tit">서비스 조회</div>
					
					<!-- lst_table01 -->
					<div class="lst_table01" id="lst_table_service_basic">
						<table class="table"border="0" cellspacing="0" cellpadding="0">
			                <caption>계좌 인증 내역</caption>
			                <colgroup>
				                <col style="width:5%;">
			                	<col style="width:auto;">
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th>선택</th>
									<th>서비스명</th>
									<th>비고</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<!-- //lst_table01 -->
					<div class="layer_btnbox center" id="lst_table_service_basic_bottom">
						<button type="button" class="btn_st_100 bt01" onclick="javascript:layerManage.layer.close('.service_select_basic_layer')"><span>닫기</span></button>
						<button type="button" class="btn_st_100 bt01" onclick="javascript:customerChargeManage.layerpop.basic.confirm()"><span>확인</span></button>
						<input type="hidden" id="seqId" value="">
					</div>
				</div>
			</div>
		</div>
		
		<!-- layerpopup 서비스선택 -->
		<div class="layerpop_wrap service_select_layer" >
			<div class="layerpop_mask"></div>
			<div class="layerpop" style="width:800px; top:200px;">
				<div class="layer_content" style="padding: 15px 15px 0 15px;">
					<div class="layer_tit">서비스 조회</div>
					
					<!-- lst_table01 -->
					<div class="lst_table01" id="lst_table_service">
						<table class="table"border="0" cellspacing="0" cellpadding="0">
			                <caption>계좌 인증 내역</caption>
			                <colgroup>
				                <col style="width:5%;">
			                	<col style="width:auto;">
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th>선택</th>
									<th>서비스명</th>
									<th>비고</th>
									<th>요금할인구간</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<!-- //lst_table01 -->
					<div class="layer_btnbox center" id="lst_table_service_bottom">
						<button type="button" class="btn_st_100 bt01" onclick="javascript:layerManage.layer.close('.service_select_layer')"><span>닫기</span></button>
						<button type="button" class="btn_st_100 bt01" onclick="javascript:customerChargeManage.layerpop.slide.confirm()"><span>확인</span></button>
						<input type="hidden" id="seqId" value="">
					</div>
				</div>
			</div>
		</div>
		<!-- //layerpopup 서비스선택 -->
	</body>
</html>