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
	$("#searchBtn").bind("click",function(){getViewAjax();});
	
	$("#eform_attach_insert_btn").bind("click",function(){
		var method="POST";
		var requestUrl="/${map.loanId}/api/loanInfoSelectList";
		var params = {};
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
				if(response.result){
					$("#eform_attach_insert_loan_info").empty();
					$("#eform_attach_insert_loan_info").append("<option value=''>회사선택</option>");
					$(response.list).each(function(k,v){
						$("#eform_attach_insert_loan_info").append("<option value=\""+v.id+"\">"+v.name+"</option>");
						console.log("--------------");
					});
					//$("#eform_attach_insert_layer")
				}else{
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
		$("#eform_attach_insert_layer").attr("style","display:block;");
	});
	$("#eform_attach_insert_close").bind("click",function(){
		$("#eform_attach_insert_layer").attr("style","display:none;");
	});
	$("#eform_attach_detail_close").bind("click",function(){
		$("#eform_attach_detail_layer").attr("style","display:none;");
		
	});
	$("#eform_attach_update_close").bind("click",function(){
		$("#eform_attach_update_layer").attr("style","display:none;");
	});
	
	$("#eform_attach_insert_insert_btn").bind("click",function(){
		$("#eform_attach_insert_layer").find("ul").append("<li><input type=\"text\" style=\"width:100%;\" /></li>");
	});
	
	$("#eform_attach_insert_submit").bind("click",function(){
		var loan_id = $("#eform_attach_insert_loan_info option:selected").val()//회사
		if(loan_id == ""){
			alert("회사를 선택해 주십시오.");
			return false;
		}
		var name = $("#eform_attach_insert_name").val();
		if(name == ""){
			alert("상품명을 입력해 주십시오.");
			return false;
		}
		var eform_attachs = new Array();
		$("#eform_attach_insert_layer").find("ul").find("input").each(function(){
			if($(this).val() != "")
				eform_attachs.push($(this).val());
		});
		
		if(eform_attachs.length<= 0 ){
			alert("추가서류 등록이 필요합니다.");
			return false;
		}

		var formData = new FormData($("#eform_attach_insert_form")[0]);
		formData.append("loan_id",loan_id);
		formData.append("name",name);
		formData.append("eform_attachs",eform_attachs); 

		$.ajax({
			url: "/${map.loanId}/api/eformInfoInsert",
			type: "POST",
		    enctype: 'multipart/form-data',
			data: formData,
			processData: false,
		    contentType: false,
			cache: false,
			success: function(response) {
				if(response.result){
					$("#eform_attach_insert_loan_info").empty();
					$("#eform_attach_insert_loan_info").append("<option value=''>회사선택</option>");
					$(response.list).each(function(k,v){
						$("#eform_attach_insert_loan_info").append("<option value=\""+v.id+"\">"+v.name+"</option>");
					});
				}
				alert(response.message);
				if(response.url != "")
					window.location.href=response.url;
				$("#eform_attach_insert_close").trigger("click");
				getViewAjax();
			},
			fail: function() {
				alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
			}
		}); 
	}); 
	
	$("#eform_attach_update_submit").bind("click",function(){
		var name = $("#eform_attach_update_name").val();
		var eform_attachs = new Array();
		$("#eform_attach_update_layer").find("ul").find("input").each(function(k,v){
			var jObj = new Object();
			jObj.seq = $(v).attr("data-seq");
			jObj.name = $(v).val();
			eform_attachs.push(jObj);
		});
		
		var formData = new FormData($("#eform_attach_update_form")[0]);
		formData.append("name",name);
		formData.append("eform_attachs",JSON.stringify(eform_attachs));
		formData.append("seq",$("#eform_attach_update_name").attr("data-seq"));
		
		$.ajax({
			url: "/${map.loanId}/api/eformInfoUpdate",
			type: "POST",
		    enctype: 'multipart/form-data',
			data: formData,
			processData: false,
		    contentType: false,
			cache: false,
			success: function(response) {
				if(response.result){
					$("#eform_attach_insert_loan_info").empty();
					$("#eform_attach_insert_loan_info").append("<option value=''>회사선택</option>");
					$(response.list).each(function(k,v){
						$("#eform_attach_insert_loan_info").append("<option value=\""+v.id+"\">"+v.name+"</option>");
					});
				}
				alert(response.message);
				if(response.url != "")
					window.location.href=response.url;
				$("#eform_attach_update_close").trigger("click");
				getViewAjax();
			},
			fail: function() {
				alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
			}
		}); 
	});
	
	$("#prod_all").click(function(){
		if($("#prod_all").prop("checked")) {
			$("input[type=checkbox]").prop("checked",true);
		} else {  
			$("input[type=checkbox]").prop("checked",false); 
		}
	});
	
	$("#eform_attach_delete_btn").click(function(){
		if(confirm("정말 삭제하시겠습니까?")){
			var eformDel = new Array();
			$("input[name=prod_01]").each(function(){
				if($(this).prop("checked") == true)
					eformDel.push($(this).val());
			});
			
			var params = {"eformDel": eformDel};
			
			$.ajax({
				url: "/${map.loanId}/api/eformDel",
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
});


function getViewAjax(){
	var pageNo = $("#pageNo").val();
	var searchText = $("#searchText").val();
	var searchType = $("#searchType option:selected").val();
	var totalCnt = 0;
	var params = {
		"pageNo": pageNo, 
		"pageSize": pageSize,
		"searchText":searchText,
		"searchType":searchType
	};
	$.ajax({
		url: "/${map.loanId}/api/prodMngSelectList",
		type: "POST",
		data: JSON.stringify( params),
		dataType: "json",
		contentType : "application/json; charset=UTF-8",
		cache: false,
		success: function(response) {
			var pageBlock = 10;
			var pageNo = $("#pageNo").val();
			var stateNo = (pageNo-1) * pageSize;
			if (response.result) {
				$(".lst_table01 tbody").empty();
				$(response.list).each(function(k,v){
					totalCnt = v.totalCnt;
					if(stateNo % 2 == 0){
						$(".lst_table01 tbody").append("<tr>");
					}else{
						$(".lst_table01 tbody").append("<tr class=\"bg\">");
					}
					var fileArr = v.eform_path.split("\\");
					
					$(".lst_table01 tbody").append(
							"<td><input type=\"checkbox\" name=\"prod_01\" value=\""+v.seq+"\"></td>"+
							"<td class=\"al\">"+v.loan_name+"</td>"+
							"<td class=\"al\"><a href=\"javascript:eform_attach_detail_fn('"+v.seq+"')\">"+v.name+"</a></td>"+
							"<td>D0001</td>"+
							"<td>"+fileArr[fileArr.length-1]+"</td>"+
							"<td>"+v.attach_type_count+"개</td>"+
							"<td>"+v.admin_id+"</td>"+
							"<td>"+v.reg_date+"</td>"+
							"<td>-</td>"+
							"<td><a href=\"javascript:eform_attach_update_fn('"+v.seq+"')\">수정</a></td>"+
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

function eform_attach_detail_fn(seq){
	var method="POST";
	var requestUrl="/${map.loanId}/api/prodMngSelectOne";
	var params = {
		"seq": seq
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
			if (response.result) {
				var map = response.map;
				var eformAttachHtml = "";
				$(response.map.eformAttach).each(function(k,v){
					eformAttachHtml += "<li>"+v.name+"</li>";
				});
				$("#eform_attach_detail tbody td").eq(0).text(map.loan_name);
				$("#eform_attach_detail tbody td").eq(1).text(map.name);
				$("#eform_attach_detail tbody td").eq(2).text("-");
				$("#eform_attach_detail tbody td").eq(3).html("<ol class=\"dt_doc_lst\">"+eformAttachHtml+"</ol>");
				$("#eform_attach_detail tbody td").eq(4).text(map.reg_date);
				$("#eform_attach_detail tbody td").eq(5).text(map.modify_date);
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
	
	$("#eform_attach_detail_layer").attr("style","display:block;");
}

function eform_attach_update_fn(seq){
	var method="POST";
	var requestUrl="/${map.loanId}/api/prodMngSelectOne";
	var params = {
		"seq": seq
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
			if (response.result) {
				var map = response.map;

				var fileArr = map.eform_path.split("\\");
				var eformAttachHtml = "";
				$(response.map.eformAttach).each(function(k,v){
					eformAttachHtml += "<li><input type=\"text\" id=\""+v.seq+"\" value=\""+v.name+"\" data-seq=\""+v.seq+"\" style=\"width:100%;\"/></li>";
				});
				//eformAttachHtml += "<li><input type=\"text\" id=\"eform_attach_update_attach_text\" data-seq=\""+seq+"\" style=\"width:100%;\"/></li>";
				console.log(eformAttachHtml);
				$("#eform_attach_update tbody td").eq(0).text(map.loan_name);
				$("#eform_attach_update_name").val(map.name);
				$("#eform_attach_update_name").attr("data-seq",seq);
				$("#eform_attach_update").find("ul").empty();
				$("#eform_attach_update").find("ul").append(eformAttachHtml);
				$("#eform_attach_update_layer tbody").find("tr").eq(2).find("td").find("div").eq(0).empty();
				$("#eform_attach_update_layer tbody").find("tr").eq(2).find("td").find("div").eq(0).text("현재파일:"+fileArr[fileArr.length-1]);
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
	$("#eform_attach_update_layer").attr("style","display:block;");
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
					<select name="" id="searchType" class="select01" style="width:153px;">
						<option value="loan">회사명</option>
						<option value="name">상품명</option>
					</select>
				</li>
				<li class="input_search"><input type="text" id="searchText" name="" placeholder="검색어를 입력하세요"></li>
				<li><button type="button" class="btn_st bt01" id="searchBtn"><span>검색</span></button></li>
			</ul>
		</div>

		<!-- top btn -->
		<div class="top_area clearbox">
			<div class="lf_box">
				<button type="button" class="btn_st bt01" id="eform_attach_delete_btn"><span>삭제</span></button>
			</div>
			<div class="rf_box">
				<button type="button" class="btn_st bt01" id="eform_attach_insert_btn"><span>상품 등록</span></button>
			</div> 
		</div>

		<!-- contents -->
		<div class="contents">
			<!-- lst_table01 -->
			<div class="lst_table01">
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>상품(문서)관리 리스트</caption>
	                <colgroup>
		                <col style="width:6%;">
		                <col style="width:13%;">
		                <col style="width:auto;">
		                <col style="width:9%;">
		                <col style="width:9%;">
		                <col style="width:8%;">
		                <col style="width:10%;">
		                <col style="width:10%;">
		                <col style="width:10%;">
		                <col style="width:7%;">
	                </colgroup>
	                <thead>
						<tr>
							<th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th>
							<th>회사명</th>
							<th>상품명</th>
							<th>상품코드</th>
							<th>전자문서</th>
							<th>추가서류</th>
							<th>아이디</th>
							<th>등록일</th>
							<th>수정일</th>
							<th>수정</th>
						</tr>
					</thead>
					<tbody></tbody>
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


<!-- layerpopup 상품정보 등록 -->
<div class="layerpop_wrap" id="eform_attach_insert_layer" style="display:none;">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width:590px; top:150px;">
		<div class="layer_rdbox">
			<div class="layer_lt"></div>
			<div class="layer_rt"></div>
		</div>
		<div class="layer_content">
			<div class="layer_tit">상품정보 등록</div>
			
			<!-- wrt_table01 -->
			<div class="wrt_table01">
				<form id="eform_attach_insert_form" enctype="multipart/form-data">
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>상품정보 등록</caption>
	                <colgroup>
		                <col style="width:25%;">
		                <col style="width:auto;">
	                </colgroup>
					<tbody>
						<tr>
							<th>회사</th>
							<td>
								<select name="eform_attach_insert_loan_info" id="eform_attach_insert_loan_info" style="width:150px;">
									<option value="">회사선택</option>
									<option value="">ABC캐피탈</option>
									<option value="">DEF금융</option>
									<option value="">GHI캐피탈</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>상품명</th>
							<td><input type="text" name="eform_attach_insert_name" id="eform_attach_insert_name" style="width:100%;" placeholder="상품명을 입력하세요."></td>
						</tr>
						<tr>
							<th>전자문서<br>서식파일</th>
							<td>
								<div>
									<input type="file" name="eform_attach_insert_file" id="eform_attach_insert_file" style="width:300px;" >
									<!-- <button type="button" class="btn_st bt01"><span>파일찾기</span></button> -->
								</div>
								<p class="stxt01">※모바일 웹에서 고객의 계약 진행에 필요한 서류를 등록합니다.<br>
								 파일 선택 시 해당 금융상품 서류가 맞는지 반드시 확인하세요.</p>
							</td>
						</tr>
						<tr>
							<th>추가 서류<br>(APP 촬영)</th>
							<td class="np">
								<ul class="add_doc_lst">
									<li><input type="text" name="" style="width:100%;" placeholder="전용 APP 촬영 후 제출할 서류명을 입력하세요. Ex)신분증 앞면" ></li>
								</ul>
								<button type="button" class="add_doc_btn" id="eform_attach_insert_insert_btn">+ 서류입력 추가하기</button>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- //wrt_table01 -->

			<div class="layer_btnbox">
				<button type="button" class="btn_st bt03" id="eform_attach_insert_submit"><span>등록</span></button>
				<button type="button" class="btn_st bt03" id="eform_attach_insert_close"><span>취소</span></button>
			</div>
		</div>

		<div class="layer_rdbox">
			<div class="layer_lb"></div>
			<div class="layer_rb"></div>
		</div>
	</div>
</div>
<!-- //layerpopup 상품정보 등록 -->

<!-- layerpopup 상품정보 상세조회 -->
<div class="layerpop_wrap" style="display:none;" id="eform_attach_detail_layer">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width:590px; top:200px;">
		<div class="layer_rdbox">
			<div class="layer_lt"></div>
			<div class="layer_rt"></div>
		</div>
		<div class="layer_content">
			<div class="layer_tit">상품정보 상세조회</div>
			
			<!-- wrt_table01 -->
			<div class="wrt_table01" id="eform_attach_detail">
				<form>
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>상품정보 상세조회</caption>
	                <colgroup>
		                <col style="width:25%;">
		                <col style="width:auto;">
	                </colgroup>
					<tbody>
						<tr>
							<th>회사</th>
							<td>ABC캐피탈</td>
						</tr>
						<tr>
							<th>상품명</th>
							<td>개인신용대출</td>
						</tr>
						<tr>
							<th>상품코드</th>
							<td>A0001</td>
						</tr>
						<tr>
							<th>전자문서<br>서식파일</th>
							<td>Loan1.xxx</td>
						</tr>
						<tr>
							<th>추가서류</th>
							<td>
								<ol class="dt_doc_lst">
									<li>신분증 앞면</li>
									<li>신분증 뒷면</li>
									<li>등기부 등본</li>
									<li>계약서</li>
								</ol>
							</td>
						</tr>
						<tr>
							<th>등록일</th>
							<td>2016.11.13</td>
						</tr>
						<tr>
							<th>수정일</th>
							<td>2016.11.24</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- //wrt_table01 -->

			<div class="layer_btnbox">
				<button type="button" class="btn_st bt03" id="eform_attach_detail_close"><span>확인</span></button>
			</div>
		</div>

		<div class="layer_rdbox">
			<div class="layer_lb"></div>
			<div class="layer_rb"></div>
		</div>
	</div>
</div>
<!-- //layerpopup 상품정보 상세조회 -->


<!-- layerpopup 상품정보 수정 -->
<div class="layerpop_wrap" style="display:none;" id="eform_attach_update_layer">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width:590px; top:150px;">
		<div class="layer_rdbox">
			<div class="layer_lt"></div>
			<div class="layer_rt"></div>
		</div>
		<div class="layer_content">
			<div class="layer_tit">상품정보 수정</div>
			
			<!-- wrt_table01 -->
			<div class="wrt_table01" id="eform_attach_update">
				<form id="eform_attach_update_form" enctype="multipart/form-data">
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>상품정보 수정</caption>
	                <colgroup>
		                <col style="width:25%;">
		                <col style="width:auto;">
	                </colgroup>
					<tbody>
						<tr>
							<th>회사</th>
							<td></td>
						</tr>
						<tr>
							<th>상품명</th>
							<td><input type="text" name="" id="eform_attach_update_name" data-seq="" style="width:100%;" value="개인신용대출"></td>
						</tr>
						<tr>
							<th>전자문서<br>서식파일</th>
							<td>
								<div style="margin:0 0 10px;">현재 파일 : Loan1.xxx</div>
								<div>
									<input type="file" name="eform_attach_update_file" id="eform_attach_update_file" style="width:300px;" >
									<button type="button" class="btn_st bt01"><span>파일찾기</span></button>
								</div>
								<p class="stxt01">※모바일 웹에서 고객의 계약 진행에 필요한 서류를 등록합니다.<br>
								 파일 선택 시 해당 금융상품 서류가 맞는지 반드시 확인하세요.</p>
							</td>
						</tr>
						<tr>
							<th>추가 서류<br>(APP 촬영)</th>
							<td class="np">
								<ul class="add_doc_lst">
									<!-- <li><input type="text" name="" style="width:100%;" value="신분증 앞면" ></li>
									<li><input type="text" name="" style="width:100%;" value="신분증 뒷면" ></li>
									<li><input type="text" name="" style="width:100%;" value="등기부등본" ></li>
									<li><input type="text" name="" style="width:100%;" value="계약서" ></li>
									<li><input type="text" name="" style="width:100%;" placeholder="전용 APP 촬영 후 제출할 서류명을 입력하세요. Ex)기타 서류" ></li> -->
								</ul>
								<!-- <button type="button" class="add_doc_btn" id="eform_attach_update_insert_btn">+ 서류입력 추가하기</button> -->
							</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- //wrt_table01 -->

			<div class="layer_btnbox">
				<button type="button" class="btn_st bt03" id="eform_attach_update_submit"><span>수정</span></button>
				<button type="button" class="btn_st bt03" id="eform_attach_update_close"><span>취소</span></button>
			</div>
		</div>

		<div class="layer_rdbox">
			<div class="layer_lb"></div>
			<div class="layer_rb"></div>
		</div>
	</div>
</div>
<!-- //layerpopup 상품정보 수정 -->

</body>
</html>