<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>굿페이퍼 비대면 서비스 관리 어드민</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
	
	<style type="text/css">
		/* .lst_table01 table thead th{padding:5px 0px; color:#010101; font-weight:bold; text-align:center; border-bottom:1px solid #cacbcb;}
		.lst_table01 table tbody tr td{padding:5px 0px; color:#424242; text-align:center; border-bottom:1px solid #dddddd; background:#fff;} */
	</style>
	
	<script type="text/javascript">

	function viewPage(){
		accountAuthHistory.history.view();
	}
	
	var accountAuthHistory;
	var companyManage;
	var layerManage;
	var serviceManage;
	
	$(document).ready(function(){
		accountAuthHistory = new AccountAuthHistory;
		companyManage = new CompanyManage;
		layerManage = new LayerManage;
		serviceManage = new ServiceManage;
		
		accountAuthHistory.view();
		
	});
	
	$(document).on('change', '#pageSize', function(){
		accountAuthHistory.history.view();
	});
	
	$(document).on('change', '#year_select', function(){
		$("#pageNo").val('1');
		accountAuthHistory.history.view();
	});
	
	$(document).on('change', '#month_select', function(){
		$("#pageNo").val('1');
		accountAuthHistory.history.view();
	});
	
	$(document).on('change', '#defaultSearchCompany', function(){
		$("#pageNo").val('1');
		var company_id = $(this).children("option:selected").attr('id');
		$('#company_id').val(company_id);
		accountAuthHistory.history.view();
	});

	$(document).on('click', '#excelExport', function(){
		excelExport(this, '#tblExport', 'tblExport', 'table');
	});
	
	$(document).on('click', '.checkbox', function(){
		$("#pageNo").val('1');
		accountAuthHistory.topArea.checkbox(this);
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
				<input type="hidden" id="company_id" value="" />
			<!-- //header -->
		
			<!-- container -->
			<div id="container">
				<!-- sub menu -->
				<%@ include file="/WEB-INF/views/include_web/sub_menu.jsp"%>
				<!-- sub search -->
				<br>
				<div class="top_area clearbox" id="lst_table_middle_title">
					<div class="lf_box">
						<strong class="sub_title">이용기간 조회</strong>
					</div>
				</div>
				<div class="lst_table01 option_table">
					<table class="table center">
						<caption>정신내역 옵션</caption>
		                <colgroup>
			                <col style="width:10%;">
			                <col style="width:auto;">
		                </colgroup>
						<tbody>
							<tr>
								<th>
								업체선택
								</th>
								<td class="left">
									<select id="defaultSearchCompany" class="select width_202">
										<option value="0">선택</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>이용기간</th>
								<td class="left">
									<select id="year_select" class="select"></select>
									<select id="month_select" class="select"></select>
									<!-- <button type="button" class="btn_st_normal bt01" id="service_add_btn"><span>검색</span></button> -->
								</td>
							</tr>
							<tr>
								<th>
									서비스
								</th>
								<td class="left">
									<div class="pure-checkbox" style="display: inline-block;">
			                            <input id="allCheck" name="allCheck" type="checkbox" class="checkbox" style="position:absolute; left:-10000%;" checked="checked">
                        				<label for="allCheck">전체</label>
			                        </div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<br><br>
				<!-- //lst_table_middle_title -->
				<div class="top_area clearbox" id="lst_table_middle_title">
					<div class="lf_box">
						<strong class="sub_title">정산내역</strong>
						<br>
						<strong class="txt">단위:원, VAT별도</strong>
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
								<th>서비스명</th>
								<th>기준단가</th>
								<th>할인액</th>
								<th>적용단가</th>
								<th>사용건수</th>
								<th>성공건수</th>
								<th>합계</th>
								<th>비고</th>
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
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>합계금액</td>
								<td class="monthly_use_sum">0</td>
								<td>기본 요금은 포함하지 않습니다.</td>
								
							</tr>
						</tfoot>
					</table>
				</div>
				<br><br>
				<!-- top btn -->
				<div class="top_area clearbox">
					<div class="lf_box">
						<strong class="sub_title">이용내역상세</strong>
						<br>
						<strong class="countTxt">검색 결과가 없습니다.</strong>
						
						<select name="pageSize" id="pageSize" class="select02" style="width:92px;">
							<option value="20">20개</option>
							<option value="50">50개</option>
							<option value="100">100개</option>
							<option value="1000000000">전체결과</option>
						</select>
						<!-- <a class="btn_st_normal" id="excelExport"><span>엑셀 파일 다운로드</span></a> -->
					</div>
					<div class="rf_box">
						
					</div>
				</div>
				<!-- contents -->
				<div class="contents">
					<!-- lst_table01 -->
					<div class="lst_table01" id="lst_table_history" style="overflow-x: auto; overflow-y: auto; max-height: 760px;">
						<table class="table center">
			                <caption>계좌 인증 내역</caption>
			                <colgroup>
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th style="width: 30px;">순번</th>
									<th style="width: 48px;">서비스</th>
									<th style="width: 48px;">성공여부</th>
									<th style="width: 72px;">일시</th>
								</tr>
							</thead>
							<tbody>
								<tr>
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
					<br>
					<!-- for Excel -->
					<div class="lst_table01_hidden" style="display: none;">
						<table id="tblExport" class="table">
			                <thead>
								<tr>
									<th>순번</th>
									<th>요청번호</th>
									<th>고객명</th>
									<th>은행명</th>
									<th>계좌번호</th>
									<th>실명확인성공여부</th>
									<th>실명확인 요청일시</th>
									<th>소액이체성공여부</th>
									<th>소액이체 요청일시</th>
									<th>인증완료여부</th>
									<th>인증완료일시</th>
									<th>금결원 결과 메시지</th>
									<th>개별은행 결과 메시지</th>
									<th>은행명</th>
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
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- //container -->
			<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
		</div>
	</body>
</html>