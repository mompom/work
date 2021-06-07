<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>비대면 대출 신청 서비스</title>
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
<script type="text/javascript">
var pageSize = 20;

$(document).ready(function(){
	getViewAjax();
	$("#admin_info_delete_btn").click(function(){
		if(confirm("정말 삭제하시겠습니까?")){
			var adminInfoDel = new Array();
			$("input[name=user_01]").each(function(){
				if($(this).prop("checked") == true)
					adminInfoDel.push($(this).val());
			});
			
			var params = {"adminInfoDel": adminInfoDel};
			
			$.ajax({
				url: "/${map.loanId}/api/adminInfoDel",
				type: "POST",
				data: JSON.stringify(params),
				dataType: "json",
				contentType : "application/json; charset=UTF-8",
				cache: false,
				success: function(response) {
					alert(response.message);
					if(response.url != "")
						window.location.href=response.url;
					getViewAjax();
				},
				fail: function() {
					alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
				}
			});
		}
	});
	
	$("#admin_info_update_close").bind("click",function(){
		$("#admin_info_update_layer").attr("style","display:none;");		
	});

	$("#user_all").click(function(){
		if($("#user_all").prop("checked")) {
			$("input[type=checkbox]").prop("checked",true);
		} else {  
			$("input[type=checkbox]").prop("checked",false); 
		}
	});
	
	$("#admin_info_update_submit").bind("click",function(){
		var seq = $("#admin_info_update_seq").val();
		var level = $("#admin_info_update_level option:selected").val();//권한
		var name = $("#admin_info_update_name").val();//이름
		var loan_id = $("#admin_info_update_loan_id option:selected").val();//회사
		var team  = $("#admin_info_update_team").val();//부서
		var tel_number = $("#admin_info_update_tel_number").val();//연락처
		
		//admin_info Update
		var method="POST";
		var requestUrl="/${map.loanId}/api/adminInfoUpdate";
		var params = {
			"seq": seq,
			"level": level,
			"name": name,
			"loan_id": loan_id,
			"team": team,
			"tel_number": tel_number
		};
		var getType="json";
		var contType="application/json; charset=UTF-8";
		$.ajax({
			url: requestUrl,
			type: method,
			data: JSON.stringify( params),
			dataType: getType,
			contentType : contType,
			cache: false,
			success: function(response) {
				if(response.message != "")
					alert(response.message);
				if(response.url != "")
					window.location.href=response.url;
				$("#admin_info_update_close").trigger("click");
				getViewAjax();
			},
			fail: function() {
				alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
			}
		});
	});
	
	$("#admin_info_insert_button").bind("click",function(){
		$("#admin_info_insert_layer").attr("style","display:block;");
		
	});
	
	$("#idChkButton").bind("click",function(){
		var id = $("#admin_info_insert_id").val();
		if(id==""){
			alert("아이디를 입력해 주십시오.");	
			return;
		}
		//admin_info Update
		var method="POST";
		var requestUrl="/${map.loanId}/api/adminInfoIdChk";
		var params = {
			"id": id
		};
		var getType="json";
		var contType="application/json; charset=UTF-8";
		$.ajax({
			url: requestUrl,
			type: method,
			data: JSON.stringify( params),
			dataType: getType,
			contentType : contType,
			cache: false,
			success: function(response) {
				if(response.message != "")
					alert(response.message);
				if(response.url != "")
					window.location.href=response.url;
				if(!response.result){
					$("#admin_info_insert_id").val("");
					$("#admin_info_insert_id").focus();
				}else{
					$("#idChkButton").attr("data-flag", "1");					
				}				
			},
			fail: function() {
				alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
			}
		});
	});
	
	$("#admin_info_insert_close").bind("click",function(){
		$("#admin_info_insert_layer").attr("style","display:none;");
	});
	
	$("#admin_info_insert_submit").bind("click",function(){
		if($("#idChkButton").attr("data-flag")==0){
			alert("중복확인을 하십시오.");
			return;
		}
		var passwd = $("#admin_info_insert_passwd").val();
		var level = $("#admin_info_insert_level option:selected").val();
		var name = $("#admin_info_insert_name").val();
		var loan_id = $("#admin_info_insert_loan_id option:selected").val();
		var team = $("#team").val();
		var tel_number = $("#tel_number").val();
		var id = $("#admin_info_insert_id").val();
		if(name == ""){
			alert("이름을 입력해 주십시오.");
			return;
		}
		
		if(passwd == ""){
			alert("비밀번호를 입력해 주십시오.");
			return;
		}
		
		if(team == ""){
			alert("부서를 입력해 주십시오.");
			return;
		}
		
		if(tel_number == ""){
			alert("전화번호를 입력해 주십시오.");
			return;
		}

		//admin_info Update
		var method="POST";
		var requestUrl="/${map.loanId}/api/adminInfoInsert";
		var params = {
			"id":id,
			"passwd":passwd,
			"level": level,
			"name": name,
			"loan_id": loan_id,	
			"team": team,	
			"tel_number": tel_number	
		};
		var getType="json";
		var contType="application/json; charset=UTF-8";
		$.ajax({
			url: requestUrl,
			type: method,
			data: JSON.stringify( params),
			dataType: getType,
			contentType : contType,
			cache: false,
			success: function(response) {
				if(response.message != "")
					alert(response.message);	
				if(response.url != "")
					window.location.href=response.url;
				$("#admin_info_insert_close").trigger("click");
				getViewAjax();
			},
			fail: function() {
				alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
			}
		});
	});
});

/* 
* 
*/
function getViewAjax(){
	var searchText = $("#searchText").val();
	var pageNo = $("#pageNo").val();
	var totalCnt = 0;
	var level = $("#level option:selected").val();
	var method="POST";
	var requestUrl="/${map.loanId}/api/adminInfoSelectList";
	var params = {
		"pageNo": pageNo, 
		"pageSize": pageSize,
		"level":level,
		"searchText":searchText
	};
	var getType="json";
	var contType="application/json; charset=UTF-8";
	//ajaxGetData(method , requestUrl , params ,getType ,contType , setView);
	$.ajax({
		url: requestUrl,
		type: method,
		data: JSON.stringify( params),
		dataType: getType,
		contentType : contType,
		cache: false,
		success: function(response) {
			var pageBlock = 10;
			var pageNo = $("#pageNo").val();
			var stateNo = (pageNo-1) * pageSize;
			if (response.result) {
				$(".lst_table01 tbody").empty();
				$(response.list).each(function(k,v){
					totalCnt = v.totalCnt;
					$(".lst_table01 tbody").append(
						"<tr>"+
							"<td><input type=\"checkbox\" name=\"user_01\" value=\""+v.seq+"\"></td>"+
							"<td><a href=\"javascript:admin_info_update('"+v.seq+"','"+v.id+"','"+v.name+"','"+v.loan_id+"','"+v.team+"','"+v.tel_number+"')\">"+v.id+"</a></td>"+
							"<td>"+v.name+"</td>"+
							"<td>"+v.level_name+"</td>"+
							"<td>"+v.team+"</td>"+
							"<td>"+v.tel_number+"</td>"+
							"<td>"+v.reg_date+"</td>"+
							"<td>"+v.modify_date+"</td>"+
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

function admin_info_update(seq,id,name,loan_id,team,tel_number){
	$("#admin_info_update_seq").val(seq);
	$("#admin_info_update_layer").attr("style","display:block;");
	$("#admin_info_update_loan_id").find("option").each(function(k,v){
		if($(v).val() == loan_id){
			$(this).attr("selected","selected");
			//$("#admin_info_update_loan_id").parent().find("span").text($(this).text);
		}
	});
	$("#admin_info_update_id").text(id);
	$("#admin_info_update_name").val(name);
	$("#admin_info_update_team").val(team);
	$("#admin_info_update_tel_number").val(tel_number);

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
		
		<!-- sub search -->
		<div class="sub_topsearch">
			<ul class="clearbox">
				<li>
					<select name="" id="level" class="select01" style="width:153px;">
						<option value="">권한 전체</option>
						<option value="2">관리자</option>
						<option value="3">상담사</option>
					</select>
				</li>
				<li class="input_search"><input type="text" name="" id="searchText" placeholder="검색어를 입력하세요"></li>
				<li><button type="button" class="btn_st bt01" id="admin_info_search_btn"><span>검색</span></button></li>
			</ul>
		</div>

		<!-- top btn -->
		<div class="top_area clearbox">
			<div class="lf_box">
				<button type="button" class="btn_st bt01" id="admin_info_delete_btn"><span>삭제</span></button>
			</div>
			<div class="rf_box">
				<button type="button" class="btn_st bt01" id="admin_info_insert_button"><span>사용자 등록</span></button>
			</div> 
		</div>

		<!-- contents -->
		<div class="contents">
			<!-- lst_table01 -->
			<div class="lst_table01">
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>사용자 관리 리스트</caption>
	                <colgroup>
		                <col style="width:6%;">
		                <col style="width:auto;">
		                <col style="width:15%;">
		                <col style="width:13%;">
		                <col style="width:15%;">
		                <col style="width:12%;">
		                <col style="width:12%;">
		                <col style="width:12%;">
	                </colgroup>
	                <thead>
						<tr>
							<th class="num"><input type="checkbox" name="user_all" id="user_all"></th>
							<th>아이디</th>
							<th>이름</th>
							<th>권한</th>
							<th>소속</th>
							<th>연락처</th>
							<th>등록일</th>
							<th>수정일</th>
						</tr>
					</thead>
					<tbody>
						<!-- <tr>
							<td><input type="checkbox" name="user_01"></td>
							<td><a href="#">admin</a></td>
							<td>관리자</td>
							<td>어드민</td>
							<td>마케팅 2팀</td>
							<td>02-1234-5678</td>
							<td>2016.11.14</td>
							<td>2016.11.20</td>
						</tr> -->
					</tbody>
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


<!-- layerpopup 사용자 등록 -->
<div class="layerpop_wrap" id="admin_info_insert_layer" style="display:none;">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width:360px; top:200px;">
		<div class="layer_rdbox">
			<div class="layer_lt"></div>
			<div class="layer_rt"></div>
		</div>
		<div class="layer_content">
			<div class="layer_tit">사용자 등록</div>
			
			<!-- wrt_table01 -->
			<div class="wrt_table01">
				<form>
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>사용자 등록</caption>
	                <colgroup>
		                <col style="width:25%;">
		                <col style="width:auto;">
	                </colgroup>
					<tbody>
						<tr>
							<th>권한</th>
							<td>
								<select name="" id="admin_info_insert_level" style="width:130px;">
									<option value="2">어드민</option>
									<option value="3">상담원</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>아이디</th>
							<td>
								<div>
									<input type="text" name="" id="admin_info_insert_id" style="width:130px;">
									<button type="button" class="btn_st bt01" id="idChkButton" data-flag="0"><span>중복확인</span></button>
								</div>
								<div>아이디는 영문과 숫자를 혼합해야 하며,<br> 최소 6자에서 최대 18자입니다.</div>
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><input type="text" name="" id="admin_info_insert_passwd" style="width:100%;"></td>
						</tr>
						<tr>
							<th>이름</th>
							<td><input type="text" name="" id="admin_info_insert_name" style="width:100%;"></td>
						</tr>
						<tr>
							<th>회사</th>
							<td>
								<select name="" id="admin_info_insert_loan_id" style="width:130px;">
									<option value="scv">scv</option>
									<option value="sanwa">산와머니</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>부서</th>
							<td><input type="text" name="" id="admin_info_insert_team" style="width:100%;"></td>
						</tr>
						<tr>
							<th>연락처</th>
							<td><input type="text" name="" id="admin_info_insert_tel_number" style="width:100%;"></td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- //wrt_table01 -->

			<div class="layer_btnbox">
				<button type="button" class="btn_st bt03" id="admin_info_insert_submit"><span>등록</span></button>
				<button type="button" class="btn_st bt03" id="admin_info_insert_close"><span>취소</span></button>
			</div>
		</div>

		<div class="layer_rdbox">
			<div class="layer_lb"></div>
			<div class="layer_rb"></div>
		</div>
	</div>
</div>
<!-- //layerpopup 사용자 등록 -->

<!-- layerpopup 사용자 수정 -->
<div class="layerpop_wrap" id="admin_info_update_layer" style="display:none;">
	<input type="hidden" id="admin_info_update_seq"/>
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width:360px; top:200px;">
		<div class="layer_rdbox">
			<div class="layer_lt"></div>
			<div class="layer_rt"></div>
		</div>
		<div class="layer_content">
			<div class="layer_tit">사용자 수정</div>
			
			<!-- wrt_table01 -->
			<div class="wrt_table01">
				<form>
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>사용자 수정</caption>
	                <colgroup>
		                <col style="width:25%;">
		                <col style="width:auto;">
	                </colgroup>
					<tbody>
						<tr>
							<th>권한</th>
							<td>
								<select name="" id="admin_info_update_level" style="width:130px;">
									<option value="3">상담원</option>
									<option value="2">어드민</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>아이디</th>
							<td id="admin_info_update_id"></td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><button type="button" class="btn_st bt01"><span>초기화</span></button></td>
						</tr>
						<tr>
							<th>이름</th>
							<td><input type="text" name="" id="admin_info_update_name" style="width:100%;"></td>
						</tr>
						<tr>
							<th>회사</th>
							<td>
								<select name="" id="admin_info_update_loan_id" style="width:130px;">
									<option value="scv">scv</option>
									<option value="sanwa">산와머니</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>부서</th>
							<td><input type="text" name="" id="admin_info_update_team" style="width:100%;"></td>
						</tr>
						<tr>
							<th>연락처</th>
							<td><input type="text" name="" id="admin_info_update_tel_number" style="width:100%;"></td>
						</tr>
						<tr>
							<th>등록일</th>
							<td id="admin_info_update_reg_date">2016.11.13</td>
						</tr>
						<tr>
							<th>수정일</th>
							<td id="admin_info_update_modify_date">2016.11.20</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- //wrt_table01 -->

			<div class="layer_btnbox">
				<button type="button" class="btn_st bt03" id="admin_info_update_submit"><span>수정</span></button>
				<button type="button" class="btn_st bt03" id="admin_info_update_close"><span>취소</span></button>
			</div>
		</div>

		<div class="layer_rdbox">
			<div class="layer_lb"></div>
			<div class="layer_rb"></div>
		</div>
	</div>
</div>
<!-- //layerpopup 사용자 수정 -->

</body>
</html>