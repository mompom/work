<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>기본형 계좌인증</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
	
	<style type="text/css">
		/* .lst_table01 table thead th{padding:5px 0px; color:#010101; font-weight:bold; text-align:center; border-bottom:1px solid #cacbcb;}
		.lst_table01 table tbody tr td{padding:5px 0px; color:#424242; text-align:center; border-bottom:1px solid #dddddd; background:#fff;} */
	</style>
	
	<script type="text/javascript">
	$(document).ready(function(){
		setDefaultDate($("#defaultSearchDate option:selected").val());
		excelExport('#excelExport', '#tblExport', 'tblExport', 'table');
		getViewAjax();
		
		$("#defaultSearchDate").bind("change",function(){
			setDefaultDate($("#defaultSearchDate option:selected").val());	
			getViewAjax();
		});
		$("#pageSize").bind("change", function(){
			getViewAjax();
		});
		$("#sdate").bind("change", function(){
			$("#defaultSearchDate").val(0).prop("selected", true);
			$(".select01").find("strong").text("직접입력");
			getViewAjax();
		});
		$("#edate").bind("change", function(){
			$("#defaultSearchDate").val(0);
			getViewAjax();
		});
	});
	
	function getViewAjax(){
		
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
		};
		$.ajax({
			url: "/${map.loanId}/api/accountAuthHistorySelectList",
			type: "POST",
			data: JSON.stringify( params),
			dataType: "json",
			contentType : "application/json; charset=UTF-8",
			cache: false,
			success: function(response) {
				var pageBlock = 10;
				var pageSize = $("#pageSize option:selected").val();
				var pageNo = $("#pageNo").val();
				//var stateNo = (pageNo-1) * pageSize;
				var stateNo = response.list[0].totalCnt - ((pageNo-1) * pageSize); 
				console.log(response.list);
				if (response.result) {
					$(".lst_table01 tbody").empty();
					$(response.list).each(function(k,v){
						var rsp_message = '-';
						var bank_rsp_message = '-';
						
						totalCnt = v.totalCnt;
						v.rsp_message != null?v.rsp_message : v.rsp_message = '-';
						v.bank_rsp_message != null?v.bank_rsp_message : v.bank_rsp_message = '-';
						v.bank_rsp_name != null?v.bank_rsp_name : v.bank_rsp_name = '-';
						
						if(v.rsp_message.length > 15){
							rsp_message = v.rsp_message.substring(0, 14).concat('...');
						}
						if(v.bank_rsp_message.length > 15){
							bank_rsp_message = v.bank_rsp_message.substring(0, 14).concat('...');
						}
						
						$(".lst_table01 tbody").append(
							"<tr>"+
								"<td>"+stateNo+"</td>"+
								"<td>"+v.req_code+"</td>"+
								"<td>"+v.account_holder_name+"</td>"+
								"<td>"+v.account_bank_name+"</td>"+
								"<td>"+v.account_holder_number+"</td>"+
								"<td>"+v.r+"</td>"+
								"<td>"+v.inquiry_realname_date+"</td>"+
								"<td>"+v.q+"</td>"+
								"<td>"+v.inquiry_transfer_deposit_date+"</td>"+
								"<td>"+v.s+"</td>"+
								"<td>"+v.authcode_confirm_date+"</td>"+
								"<td class='tooltip'>"+(v.rsp_message.length>15?("<a href='#'>"+rsp_message+"</a><span class='tooltiptext'>"+v.rsp_message+"</span>"):rsp_message)+"</td>"+
								"<td class='tooltip'>"+(v.bank_rsp_message.length>15?("<a href='#'>"+bank_rsp_message+"</a><span class='tooltiptext'>"+v.bank_rsp_message+"</span>"):bank_rsp_message)+"</td>"+
								"<td>"+v.bank_rsp_name+"</td>"+
							"</tr>"
						);
						stateNo = stateNo - 1;
					});
					//카운트
					$(".txt").text("총 "+totalCnt+"건의 결과가 검색되었습니다.");
					//페이징 세팅
					var html = setPaging(pageNo , pageSize , pageBlock , totalCnt);
					
					$("#paging").empty();
					$("#paging").append(html);
					
					getExcelAjax();
				} else {
					if(response.message != "")
						alert(response.message);
					if(response.url != "")
						window.location.href=response.url;
				}
			},
			fail: function() {
				alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
			}
		});
	}
	
	function getExcelAjax(){
		var sdate = $("#sdate").val() + " 00:00:00";
		var edate = $("#edate").val() + " 23:59:59";
		var params = {
			"sdate":sdate,
			"edate":edate,
		};
		$.ajax({
			url: "/${map.loanId}/api/accountAuthHistorySelectListExcel",
			type: "POST",
			data: JSON.stringify( params),
			dataType: "json",
			contentType : "application/json; charset=UTF-8",
			cache: false,
			success: function(response) {
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
								"<td style='text-align:center;'>"+v.r+"</td>"+
								"<td>"+v.inquiry_realname_date+"</td>"+
								"<td style='text-align:center;'>"+v.q+"</td>"+
								"<td>"+v.inquiry_transfer_deposit_date+"</td>"+
								"<td style='text-align:center;'>"+v.s+"</td>"+
								"<td>"+v.authcode_confirm_date+"</td>"+
								"<td>"+v.rsp_message+"</td>"+
								"<td>"+v.bank_rsp_message+"</td>"+
								"<td>"+v.bank_rsp_name+"</td>"+
							"</tr>"
						);
						stateNo = stateNo + 1;
					});
				} else {
					if(response.message != "")
						alert(response.message);
					if(response.url != "")
						window.location.href=response.url;
				}
			},
			fail: function() {
				alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
			}
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
			<!-- //header -->
		
			<!-- container -->
			<div id="container">
				<!-- sub menu -->
				<%@ include file="/WEB-INF/views/include_web/sub_menu.jsp"%>
				<!-- sub search -->
				<div class="sub_topsearch">
					<ul class="clearbox">
						<li>
							<select name="" id="defaultSearchCompany" class="select">
								<option value="0">전체</option>
								<option value="0">인포뱅크</option>
								<option value="0">인포나라</option>
								<option value="0">인포인포</option>
							</select>
						</li>
						<li>
							<select name="" id="defaultSearchDate" class="select01">
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
						</li>
				</ul>
				</div>
		
				<!-- top btn -->
				<div class="top_area clearbox">
					<div class="lf_box">
						<strong class="txt"></strong>
					</div>
					<div class="rf_box">
						<select name="pageSize" id="pageSize" class="select02" style="width:92px;">
							<option value="20">20개</option>
							<option value="50">50개</option>
							<option value="100">100개</option>
							<option value="1000000000">전체결과</option>
						</select>
						<a type="button" class="btn_st_normal bt01" id="excelExport" href=""><span>엑셀 파일 다운로드</span></a>
					</div> 
				</div>
				<!-- contents -->
				<div class="contents">
					<!-- lst_table01 -->
					<div class="lst_table01">
						<table class="table"border="0" cellspacing="0" cellpadding="0">
			                <caption>계좌 인증 내역</caption>
			                <colgroup>
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th style="width: 30px;">순번</th>
									<th style="width: 48px;">요청번호</th>
									<th style="width: 48px;">고객명</th>
									<th style="width: 72px;">은행명</th>
									<th style="width: 96px;">계좌번호</th>
									<th style="width: 30px;">실명<br>확인</th>
									<th style="width: 115px;">실명확인<br>요청일시</th>
									<th style="width: 30px;">소액<br>이체</th>
									<th style="width: 115px;">소액이체<br>요청일시</th>
									<th style="width: 30px;">인증<br>완료</th>
									<th style="width: 115px;">인증<br>완료일시</th>
									<th style="width: 150px;">금결원<br>결과 메시지</th>
									<th style="width: 150px;">개별은행<br>결과 메시지</th>
									<th style="width: 61px;">은행명</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<!-- //lst_table01 -->
		
					<!-- paging -->
					<div class="paging" id="paging"></div>
					<!-- //paging -->
					
					<!-- for Excel -->
					<div class="lst_table01_hidden" style="display: none;">
						<table id="tblExport" class="table"border="0" cellspacing="0" cellpadding="0">
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
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- //container -->
			<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
		</div>
	</body>
</html>