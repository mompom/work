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
	</style>
	
	<script type="text/javascript">
	$(document).ready(function(){
		getViewAjax();
		
		$("#customer_service_save_btn").bind("click", function(){
			var rst = $("#lst_table_middle tbody tr").each(function(k,v){
				var seq = $(this).find(".seq").val();
				var use_yn = $(this).find("#radio_"+seq+"_y").prop('checked');
				if(use_yn){
					use_yn = 'y';
				}else{
					use_yn = 'n';
				}
				var customer_service_name = $(this).find(".customer_service_name").val();
				var customer_service_name = $(this).find(".customer_service_name").val();
				var customer_service_comment = $(this).find(".customer_service_comment").val();
				var params = {
					"seq": seq,
					"use_yn": use_yn,
					"customer_service_name" : customer_service_name,
					"customer_service_comment" : customer_service_comment
				};
				var getType="json";
				var contType="application/json; charset=UTF-8";
				$.ajax({
					url: "/${map.loanId}/api/customerServiceUpdate",
					type: "POST",
					data: JSON.stringify( params),
					dataType: getType,
					contentType : contType,
					cache: false,
					success: function(response) {
						if (response.result) {
							location.reload();
							
						} else {
							alert("실패");
						}
					},
					fail: function() {
						alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
					}
				});
			});
			alert("저장 되었습니다.");
		});
	});
	
	$(document).on('keyup', '.customer_charge_change_ev',function(){
		var charge_val = $(this).closest('tr').find('.charge').val();
		var discount_val = $(this).closest('tr').find('.discount').val();
		
		$(this).closest('tr').find('.rst').text(Number(charge_val) - Number(discount_val));
	});
	
	
	function getViewAjax(){
		var params = {
		};
		$.ajax({
			url: "/${map.loanId}/api/customerServiceSelectList",
			type: "POST",
			data: JSON.stringify( params),
			dataType: "json",
			contentType : "application/json; charset=UTF-8",
			cache: false,
			success: function(response) {
				if (response.result) {
					$("#lst_table_middle tbody").empty();
					$(response.list).each(function(k,v){
						var checked_y='';
						var checked_n='';
						if(v.use_yn == 'y'){
							checked_y = "checked='checked'";
						}else{
							checked_n = "checked='checked'";
						}
						$("#lst_table_middle tbody").append(
							"<tr>"+
								"<td class='width_150'>"+
									"<div class='pure-radiobutton'>"+
										"<input id='radio_"+v.seq+"_y' name='radio_"+v.seq+"' type='radio' class='radio' "+checked_y+">"+
										"<label for='radio_"+v.seq+"_y'>Yes</label>"+
										"<input id='radio_"+v.seq+"_n' name='radio_"+v.seq+"' type='radio' class='radio' "+checked_n+">"+
										"<label for='radio_"+v.seq+"_n'>No</label>"+
										"<input type='hidden' class='seq' value='"+v.seq+"'>"+
									"</div>"+
								"</td>"+
								"<td class='al width_300'><input type='text' class='customer_service_name' value='"+v.customer_service_name+"'>("+v.service_name+")</td>"+
								"<td class='al'><input type='text' class='customer_service_comment width_max left' value='"+v.customer_service_comment+"''></td>"+
							"</tr>"
						);
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
						<!-- <li>
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
						</li> -->
					</ul>
				</div>
		
				
				<!-- top btn -->
				<div class="top_area clearbox">
					<div class="lf_box">
						<p class="txt">서비스관리내 정보는 굿페이퍼서비스에 직/간접적으로 반영됩니다.</p>
						<br>
						<p class="txt">철저한 관리를 부탁드리며, 저장 시 만전을 기해주시기 바랍니다.</p>
					</div>
					<div class="rf_box">
					</div>
				</div>
				<!-- contents -->
				<div class="contents">
					
					<!-- lst_table01 -->
					<div class="lst_table01" id="lst_table_middle">
						<table class="table"border="0" cellspacing="0" cellpadding="0">
			                <caption>계좌 인증 내역</caption>
			                <colgroup>
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th>사용여부</th>
									<th>서비스명</th>
									<th>비고</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<!-- //lst_table01 -->
				</div>
				<div class="bottom_area clearbox">
					<div class="cf_box">
						<a type="button" class="btn_st_bold bt01" id="customer_service_save_btn" href="#"><span>저장</span></a>
					</div>
				</div>
			</div>
			<!-- //container -->
			<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
		</div>
	</body>
</html>