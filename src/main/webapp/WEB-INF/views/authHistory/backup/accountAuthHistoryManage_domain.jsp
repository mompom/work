<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>인증내역 관리</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
	
	<style type="text/css">
		.lst_table01 table thead th{padding:5px 0px; color:#010101; font-weight:bold; text-align:center; border:1px solid #cacbcb; background:#f1f7fc;}
		.lst_table01 table tbody tr td{padding:5px 0px; color:#424242; text-align:center; border:1px solid #dddddd; background:#fff;}
	</style>
	
	<script type="text/javascript">
	$(document).ready(function(){
		setBindEvents();
		excelExport('#excelExport', '#tblExport', 'tblExport', 'table');
		getViewAjax();
	});
	
	function setBindEvents(){
		var func_getViewAjax = function(){
			getViewAjax();
		}
		/* 검색버튼 
		$("#searchBtn").bind("click", func_getViewAjax); 
		*/
		$("#pageSize").bind("change", func_getViewAjax);
		$("#sdate").bind("change", func_getViewAjax);
		$("#edate").bind("change", func_getViewAjax);
		$("#searchType").bind("change", func_getViewAjax);
		$("#searchDateType").bind("change", func_getViewAjax);
		$("#searchText").bind("keyup", func_getViewAjax);
		$("#defaultSearchDate").bind("change", func_getViewAjax);
	}
	
	function getViewAjax(){
		setDefaultDate($("#defaultSearchDate option:selected").val());
		var pageSize = $("#pageSize option:selected").val();
		var pageNo = $("#pageNo").val();
		var sdate = $("#sdate").val() + " 00:00:00";
		var edate = $("#edate").val() + " 23:59:59";
		var searchText = $("#searchText").val();
		var searchType = $("#searchType option:selected").val();
		var searchDateType = $("#searchDateType option:selected").val();
		var totalCnt = 0;
		var params = {
			"pageNo": pageNo, 
			"pageSize": pageSize,
			"sdate":sdate,
			"edate":edate,
			"searchText":searchText,
			"searchType":searchType,
			"searchDateType":searchDateType
		};
		$.ajax({
			url: "/${map.loanId}/api/AccountAuthHistorySelectList",
			type: "POST",
			data: JSON.stringify( params),
			dataType: "json",
			contentType : "application/json; charset=UTF-8",
			cache: false,
			success: function(response) {
				var pageBlock = 10;
				var pageSize = $("#pageSize option:selected").val();
				var pageNo = $("#pageNo").val();
				var stateNo = (pageNo-1) * pageSize;
				
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
								"<td>"+(stateNo+1)+"</td>"+
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
								"<td class='tooltip'>"+(v.rsp_message.length>15?("<a href=''>"+rsp_message+"</a><span class='tooltiptext'>"+v.rsp_message+"</span>"):rsp_message)+"</td>"+
								"<td class='tooltip'>"+(v.bank_rsp_message.length>15?("<a href=''>"+bank_rsp_message+"</a><span class='tooltiptext'>"+v.bank_rsp_message+"</span>"):bank_rsp_message)+"</td>"+
								"<td>"+v.bank_rsp_name+"</td>"+
							"</tr>"
						);
						stateNo = stateNo + 1;
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
		var pageSize = $("#pageSize option:selected").val();
		var pageNo = $("#pageNo").val();
		var sdate = $("#sdate").val() + " 00:00:00";
		var edate = $("#edate").val() + " 23:59:59";
		var searchText = $("#searchText").val();
		var searchType = $("#searchType option:selected").val();
		var searchDateType = $("#searchDateType option:selected").val();
		var totalCnt = 0;
		var params = {
			"pageNo": pageNo, 
			"pageSize": pageSize,
			"sdate":sdate,
			"edate":edate,
			"searchText":searchText,
			"searchType":searchType,
			"searchDateType":searchDateType
		};
		$.ajax({
			url: "/${map.loanId}/api/AccountAuthHistorySelectList",
			type: "POST",
			data: JSON.stringify( params),
			dataType: "json",
			contentType : "application/json; charset=UTF-8",
			cache: false,
			success: function(response) {
				var pageBlock = 10;
				var pageSize = $("#pageSize option:selected").val();
				var pageNo = $("#pageNo").val();
				var stateNo = (pageNo-1) * pageSize;
				
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
						<!-- li class="input_search"><input type="text" id="searchText" name="" placeholder="검색어를 입력하세요"></li-->
						<li>
							<select name="" id="defaultSearchDate" class="select01">
								<option value="7">최근 7일</option>
								<option value="14">최근 14일</option>
								<option value="30">최근 1개월</option>
								<option value="365">최근 1년</option>
								<option value="">직접입력</option>
							</select>
						</li>
						<!-- li>
							<select name="" id="searchDateType" class="select01" style="width:153px;">
								<option value="realName_searchDate">실명확인 요청일시</option>
								<option value="transfer_searchDate">소액이체 요청일시</option>
								<option value="confirm_searchDate">인증완료 요청일시</option>
							</select>
						</li-->
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
						<a type="button" class="" id="" href="/${map.loanId}/admin/accountAuthHistoryManage_date"><span>일자 별</span></a>
						/
						<a type="button" class="" id="" href="/${map.loanId}/admin/accountAuthHistoryManage_domain"><span>도메인 별</span></a>
					</div>
					<div class="rf_box">
						<select name="pageSize" id="pageSize" class="select02" style="width:92px;">
							<option value="20">20개</option>
							<option value="50">50개</option>
							<option value="100">100개</option>
							<option value="1000000000">전체결과</option>
						</select>
						<!-- <a type="button" class="btn_st bt01" id="excelExport" href=""><span>엑셀 파일 다운로드</span></a> -->
					</div> 
				</div>
				<!-- contents -->
				<div class="contents">
					<!-- lst_table01 -->
					<div>
						<div class="cboth tableTit">
							<h5 class="domainMtableTitle">도메인 별</h5>
						</div>
						<table class="tableType01 domainMtable alignC" width="100%">
			                <caption>계좌 인증 내역</caption>
			                <colgroup>
			                	<col width="10%" />
								<col width="5%"  />
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th rowspan="2">도메인주소</th>
									<th rowspan="2">순번</th>
									<th colspan="3">휴대폰</th>
									<th colspan="3">신용카드</th>
									<th colspan="3">공인인증서</th>
									<th colspan="2">SMS/LMS</th>
								</tr>
								<tr>
									<th>시도</th>
									<th>성공</th>
									<th>최종<br>성공</th>
									<th>시도</th>
									<th>성공</th>
									<th>최종<br>성공</th>
									<th>시도</th>
									<th>성공</th>
									<th>최종<br>성공</th>
									<th>발송<br>성공</th>
									<th>발송<br>실패</th>
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
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td>전체</td>
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
							</tfoot>
						</table>
					</div>
					<!-- //lst_table01 -->
		
					<!-- paging -->
					<div class="paging" id="paging"></div>
					<!-- //paging -->
				</div>
		
			</div>
			<!-- //container -->
		
		</div>
	</body>
</html>