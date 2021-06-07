<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>굿페이퍼 비대면 서비스 관리 어드민</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>

	<script type="text/javascript" src="/static/include/js/ng-table-excel-export.js"></script>
	<script type="text/javascript">
	function viewPage(){
		meritz.history.view();
	}
	
	var meritz;
	var companyManage;
	var layerManage;
	var serviceManage;
	
	$(document).ready(function(){
		meritz = new Meritz;
		companyManage = new CompanyManage;
		layerManage = new LayerManage;
		serviceManage = new ServiceManage;
		
		meritz.view();
		
	});
	
	$(document).on('change', '#pageSize', function(){
		meritz.history.view();
	});
	
	$(document).on('change', '#year_select', function(){
		$("#pageNo").val('1');
		meritz.charge.init();
	});
	
	$(document).on('change', '#month_select', function(){
		$("#pageNo").val('1');
		meritz.charge.init();
	});
	/* 
	$(document).on('click', '#excelExport', function(){
		excelExport(this, '#lst_table01Export', 'lst_table01Export', 'table');
	});
	 */
	$(document).on('click', '#excelExport', function(){
		var excelExport = this;
		
		var typeNumber = $('#company_id').val();
		var pageSize = $("#pageSize option:selected").val();
		var pageNo = $("#pageNo").val();
		
		var year = $('#year_select').val();
		var month = $('#month_select').val();
		
		var params = {
			"pageNo": 1, 
			"pageSize": 100000000,
			"sdate":year+""+month,
			"edate":year+""+month,
			"typeNumber":typeNumber
		};
		
		ajaxGet("/${map.loanId}/api/meritz/selectUsedList", params, function(response){
			var stateNo = 0;
			var innerHtml = "";
			
			if (response.result) {
				var listLen = response.list.length;
				var historyPublicTable = document.createElement('table');
				var colgroup = $('#lst_table01Export colgroup').html();
				var thead = $('#lst_table01Export thead').html();
				var tbody = "";
				var inClass = "class='border_right'";
				var inStyle = "style='text-align:center;'";
				
				for(var i = 0; i<listLen; i++){
					stateNo = listLen - i;
					
					var v = response.list[i];
					if(!v.param1){v.param1 = '-';}
					if(!v.param2){v.param2 = '-';}
					if(!v.param3){v.param3 = '-';}
					if(!v.param4){v.param4 = '-';}
					if(!v.eform_id){v.eform_id = '-';}
					
					tbody += 
					"<tr>"+
						"<td class='border_right' "+inStyle+">"+stateNo       +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.type_name        +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.seq             +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.progress_type    +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.service_type     +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.contract_code    +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.customer_code    +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.progress_status  +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.uuids            +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.eform_id         +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.param1           +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.param2           +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.param3           +"</td>"+
						"<td class='border_right' "+inStyle+">"+v.param4           +"</td>"+
						"<td style='text-align:center; mso-number-format:\"yyyy-mm-dd hh:mm:ss\";'>"+v.reg_date         +"</td>"+
					"</tr></tbody>";
				}
				innerHtml += "<colgroup>" + colgroup + "</colgroup>";
				innerHtml += "<thead>"+ thead + "</thead>";
				innerHtml += "<tbody>"+ tbody + "</tbody>";
				
				$(historyPublicTable).html(innerHtml).after(function(){
					ngTableExcelExport.excel(excelExport, historyPublicTable, $('#historyPublicTitleText').text()+"_"+(new Date().toLocaleString()));
				});
			}
		}, params);
	});
	
	$(document).on('click', '.checkbox', function(){
		$("#pageNo").val('1');
		meritz.topArea.checkbox(this);
	});
	
	$(document).on('click', 'span.company_name', function(){
		$("#pageNo").val('1');
		var company_id = $(this).next().val();
		$('#company_id').val(company_id);
		meritz.history.getCount(meritz.history.countView);
	});
	
	</script>
	</head>
	<body>
		<div id="wrap">
			<!-- header -->
			<%@ include file="/WEB-INF/views/include_web/header_top.jsp"%>
				<!-- hidden -->
				<input type="hidden" id="pageNo" value="1" />
				<input type="hidden" id="inquiry_realname_count" value="0" />
				<input type="hidden" id="inquiry_transfer_count" value="0" />
				<input type="hidden" id="company_id" value="meritz" />
			<!-- //header -->
		
			<!-- container -->
			<div id="container">
				<!-- sub menu -->
				<%@ include file="/WEB-INF/views/include_web/sub_menu.jsp"%>
				<!-- sub search -->
				<br>
				<div class="top_area clearbox">
					<div class="lf_box">
						<strong class="sub_title">이용기간 조회</strong>
					</div>
				</div>
				<div class="lst_table01 option_table">
					<table class="table center">
						<caption>정산내역 옵션</caption>
		                <colgroup>
			                <col style="width:10%;">
			                <col style="width:auto;">
		                </colgroup>
						<tbody>
							<tr>
								<th>이용기간</th>
								<td class="left">
									<select id="year_select" class="select"></select>
									<select id="month_select" class="select"></select>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<br><br>
				<!-- //lst_table_middle_title -->
				<div class="top_area clearbox" id="lst_table_middle_title">
					<div class="lf_box">
						<strong class="sub_title">전체 이용 내역</strong>
						<br>
					</div>
				</div>
				<!-- lst_table_middle -->
				<div class="lst_table01" id="lst_table_middle">
					<table class="table center">
		                <caption>계좌 인증 내역</caption>
		                <colgroup>
		                	<col style="width: 34px;">
		                	<col style="width: 200px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                </colgroup>
		                <thead>
							<tr>
								<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
								<th>No</th>
								<th>사용 인증명</th>
								<th>사용 건수</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>-</td>
								<td>-</td>
								<td>-</td>
							</tr>
						</tbody>
					</table>
				</div>
				<br><br>
				<!-- top btn -->
				<div class="top_area clearbox">
					<div class="lf_box">
						<strong class="sub_title" id="historyPublicTitleText">이용내역상세</strong>
						<br>
						<strong class="countTxt">총 0건의 결과가 검색되었습니다.</strong>
						
						<select name="pageSize" id="pageSize" class="select02" style="width:92px;">
							<option value="20">20개</option>
							<option value="50">50개</option>
							<option value="100">100개</option>
							<option value="1000000000">전체결과</option>
						</select>
						<!-- <a class="btn_st_normal" id="excelExport"><span>엑셀 파일 다운로드</span></a> -->
						<button class="btn_st_normal" id="excelExport" download="전체계좌인증내역.xls">다운로드</button>
					</div>
					<div class="rf_box">
						
					</div>
				</div>
				<!-- contents -->
				<div class="contents">
					<!-- lst_table01 -->
					<div class="lst_table01" id="lst_table_history" style="overflow-y: auto; overflow-x: auto; box-shadow: 0 0.46875rem 2.1875rem rgba(4,9,20,0.03), 0 0.9375rem 1.40625rem rgba(4,9,20,0.03), 0 0.25rem 0.53125rem rgba(4,9,20,0.05), 0 0.125rem 0.1875rem rgba(4,9,20,0.03);">
						<table class="table center" id="lst_table01Export" style="min-width: 1460px;">
			                <caption>계좌 인증 내역</caption>
			                <colgroup>
			                	<col style="width:60px;">
			                	<col style="width:120px;">
			                	<col style="width:60px;">
			                	<col style="width:100px;">
			                	<col style="width:75px;">
			                	<col style="width:100px;">
			                	<col style="width:100px;">
			                	<col style="width:80px;">
			                	<col style="width:260px;">
			                	<col style="width:60px;">
			                	<col style="width:70px;">
			                	<col style="width:70px;">
			                	<col style="width:70px;">
			                	<col style="width:70px;">
			                	<col style="width:165px;">
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th class="border_right">순번</th>
									<th class="border_right">인증정보</th>
									<th class="border_right">고객ID</th>
									<th class="border_right">PROGRESS타입</th>
									<th class="border_right">서비스타입</th>
									<th class="border_right">계약코드</th>
									<th class="border_right">고객코드</th>
									<th class="border_right">계약상태(1-5)</th>
									<th class="border_right">고객일련번호</th>
									<th class="border_right">서식정보(1-5)</th>
									<th class="border_right">파라메터1</th>
									<th class="border_right">파라메터2</th>
									<th class="border_right">파라메터3</th>
									<th class="border_right">파라메터4</th>
<!-- 									<th style="width: 55px;">업로드파일개수</th> -->
									<th>날짜</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
									<td>-</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- //lst_table01 -->
		
					<!-- paging -->
					<div class="paging" id="paging"></div>
					<!-- //paging -->
					
				</div>
			</div>
			<br>
			<!-- //container -->
			<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
		</div>
	</body>
</html>