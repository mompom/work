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
//삭제 등록시 confirm 추가
//table 칼럼 고정
//company update 구현
//company info 구현
var company_mng;
var checkList = new Array();

$(document).ready(function(){
	company_mng = new Company_mng;
	company_mng.view();
	$('#all_select_checkbox').click(function(){
		var isChecked = $(this).is(":checked") == true ? true : false;
		
		if(isChecked)
			checkList = new Array();	
		
		$("input:checkbox[name='prod_01']").each(function(e){
			$(this).prop('checked', isChecked);
			changeDeleteList($(this).val());
		});
	});
	
	 
	$('.delete_company').click(function(){
		alert('지원하지않는 기능입니다.');
		return;
		
		if(checkList.length == 0)
		{
			alert("삭제하실 회사를 선택해 주세요.");
			return;
		}
		
		var str_checkList = "";

		for(var i = 0; i < checkList.length; i++)
		{
			if(i != 0)
				str_checkList = str_checkList+",";
			
			str_checkList = str_checkList+checkList[i];
		}

		var method="POST";
		var requestUrl="/${map.loanId}/api/companyDelete";
		var params = {"check_list":str_checkList};
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
				if(response.result == true){
					location.reload();
				}
				else
				{
					alert('삭제에 실패 했습니다.')
				}
			},
			fail: function() {
				alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
			}
		});

	});
	
	$(document).on("click", ".check_seq", function(e){
		changeDeleteList($(this).val());
	});
	
	//getSelectBox("search_option1");
});	

function getSelectBox(arrt_name){
	
	if("${map.admin_level}" != 1)
	{	
		$('.search_select_box').attr("style","display:none;");
		$('.add_notice_select_box').attr("style","display:none;");
		$("#"+arrt_name).append("<option value='${map.loanId}' selected='selected'>${map.loanId}</option>");
		$("."+arrt_name).attr("disable","disable");
		$("."+arrt_name).find('strong').append('${map.loanId}');
		return;
	}
	
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
				$("#"+arrt_name).empty();
				$("#"+arrt_name).append("<option value=''>회사선택</option>");
				$("."+arrt_name).find('strong').append('회사선택');
				$(response.list).each(function(k,v){
					$("#"+arrt_name).append("<option value=\""+v.id+"\">"+v.name+"</option>");
				});
			}
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
}

function changeDeleteList(seq){
	var isIndex = checkList.indexOf(seq);
	if(isIndex == -1)
	{
		checkList.push(seq);
	}
	else
	{
		checkList.splice(isIndex,1);
	}
	
}

function add_company_layer_open()
{
	$(".add_company_layer").attr("style","display:block;");
}

function company_modify_layer_close(){
	var editable = $('.modify_name').attr('readonly');
	if(editable != 'readonly'){
		$('.modify_name').attr('readonly', 'readonly');
		$('.modify_employee_name').attr('readonly', 'readonly');
		$('.modify_employee_tel').attr('readonly', 'readonly');
		$('.modify_employee_phone').attr('readonly', 'readonly');
		$('.modify_employee_email').attr('readonly', 'readonly');
		
		$('.modify_company_wrap').css('background-color', '#4dc453');
		$('#update_confirm_btn').css('background-color', '#4dc453');
		$('#update_cancel_btn').css('background-color', '#4dc453');
		
		$('#update_confirm_btn').text('수정');
		$('#update_cancel_btn').text('닫기');
		return;
	}
	$(".company_modify_layer").attr("style","display:none;");
}

function add_company_layer_close()
{
	$(".add_company_layer").attr("style","display:none;");
}

function insert_company()
{
	var name = $('.insert_name').val();
	var id = $('.insert_id').val();
	var employee_name = $('.insert_employee_name').val();
	var employee_tel = $('.insert_employee_tel').val();
	var employee_phone = $('.insert_employee_phone').val();
	var employee_email = $('.insert_employee_email').val();

	if(name == "")
	{
		alert('회사명을 입력해 주세요.')
		return;
	}
	else if(id == "")
	{
		alert('회사코드를 입력해 주세요.')
		return;
	}
	
	var method="POST";
	
	var requestUrl="/${map.loanId}/api/companyInsert";
	var params = {
		"name" : name,
		"id" : id,
		"employee_name" : employee_name,
		"employee_tel" : employee_tel,
		"employee_phone" : employee_phone,
		"employee_email" : employee_email,
		
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
				location.reload();
			} else {
				alert("실패");
			}
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
	
}

function company_detail_function(seq){

	var method="POST";
	var requestUrl="/${map.loanId}/api/companyInfoOne";
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
				var v = response.map;
				$('.modify_seq').val(v.seq);
				$('.modify_name').val(v.name);
				$('.modify_id').text(v.id);
				$('.modify_employee_name').val(v.employee_name);
				$('.modify_employee_tel').val(v.employee_tel);
				$('.modify_employee_phone').val(v.employee_phone);
				$('.modify_employee_email').val(v.employee_email);
			} else {
				alert("실패");
			}
			
			
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
	
	$(".company_modify_layer").attr("style","display:block;");
}

function update_company(){
	var seq = $('.modify_seq').val();
	var name = $('.modify_name').val();
	var employee_name = $('.modify_employee_name').val();
	var employee_tel = $('.modify_employee_tel').val();
	var employee_phone = $('.modify_employee_phone').val();
	var employee_email = $('.modify_employee_email').val();

	if(name == "")
	{
		alert('회사명을 입력해 주세요.')
		return;
	}
	
	var editable = $('.modify_name').attr('readonly');

	if(editable != 'readonly'){
		var update_confirm = confirm('수정 하시겠습니까?');
		if(!update_confirm){
			return;
		}
		
		$('.modify_name').attr('readonly', 'readonly');
		$('.modify_employee_name').attr('readonly', 'readonly');
		$('.modify_employee_tel').attr('readonly', 'readonly');
		$('.modify_employee_phone').attr('readonly', 'readonly');
		$('.modify_employee_email').attr('readonly', 'readonly');
		
		$('.modify_company_wrap').css('background-color', '#4dc453');
		$('#update_confirm_btn').css('background-color', '#4dc453');
		$('#update_cancel_btn').css('background-color', '#4dc453');
		
		$('#update_confirm_btn').text('수정');
		$('#update_cancel_btn').text('닫기');
	}else{
		$('.modify_name').removeAttr('readonly');
		$('.modify_employee_name').removeAttr('readonly');
		$('.modify_employee_tel').removeAttr('readonly');
		$('.modify_employee_phone').removeAttr('readonly');
		$('.modify_employee_email').removeAttr('readonly');
		
		$('.modify_company_wrap').css('background-color', '#F96');
		$('#update_confirm_btn').css('background-color', '#F96');
		$('#update_cancel_btn').css('background-color', '#F96');
		
		$('#update_confirm_btn').text('저장');
		$('#update_cancel_btn').text('취소');
		$('.modify_name').selectRange(name.length, name.length);
		return;
	}
	
	var method="POST";
	var requestUrl="/${map.loanId}/api/companyUpdate";
	var params = {
		"seq": seq,
		"name" : name,
		"employee_name" : employee_name,
		"employee_tel" : employee_tel,
		"employee_phone" : employee_phone,
		"employee_email" : employee_email,
		
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
				company_mng.view();
			} else {
				alert("실패");
			}
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
}

function search(){
	
	var searchWord = $(".search_word").val();
	
	if(searchWord == ""){
		alert('검색어를 입력해 주세요.')
		return;
	}
	company_mng.view();
}

function viewPage(){
	company_mng.view();
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
			<br>
			<div class="sub_topsearch">
				<ul class="top_area clearbox">
					<!-- <li class="search_select_box">
						<select name="" id="search_option1" class="select01 search_option1" style="width:153px;">
						</select>
					</li> -->
					<li><button type="button" class="btn_st_normal bt01 delete_company"><span>삭제</span></button></li>
					<li><button type="button" class="btn_st_normal bt01" onclick="javascript:add_company_layer_open()"><span>회사등록</span></button></li>
					<li>
						<select name="" id="" class="select01 search_option2" style="width:153px;">
							<option value="company_name">회사명</option>
						</select>
					</li>
					<li class="input_search"><input class="search_word" type="text" name="" placeholder="검색어를 입력해 주세요."></li>
					<li><button type="button" class="btn_st_normal bt01" onclick="javascript:search()"><span>검색</span></button></li>
					<li><strong class="sub_txt">회사 삭제 시 만전을 기해주시기 바랍니다.</strong></li>
				</ul>
			</div>
	
			<!-- contents -->
			<div class="contents">
				<!-- lst_table01 -->
				<div class="lst_table01" id="lst_table01_company">
					<table id="historyInnerTable" class="table center">
		                <thead>
							<tr>
								<th class="border_right num"><input type="checkbox" name="company_all" id="all_select_checkbox"></th>
								<th class="border_right">순번</th>
								<th class="border_right">회사코드</th>
								<th class="border_right" style="min-width: 250px;">회사명</th>
								<th class="border_right">관리자</th>
								<th>등록일</th>
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
		<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
	</div>
	
	
	<!-- layerpopup 회사 등록 -->
	<div class="layerpop_wrap add_company_layer" style="display:none;">
		<div class="layerpop_mask"></div>
		<div class="layerpop" style="width:500px; top:200px;">
			<div class="layer_content">
				<!-- wrt_table01 -->
				<div class="wrt_table01">
					<form>
						<div class="insert_company_wrap">회사등록</div>
						<div class="insert_company_content_wrap">
							<div class="insert_company_content">회사명
								<input type="text" name=""  class="insert_name" placeholder="회사명을 입력하세요.">
							</div>
							<div class="insert_company_content">회사코드
								<input type="text" name=""  class="insert_id" placeholder="회사코드를 입력하세요.">
							</div>
							<div class="insert_company_content">성명
								<input type="text" name=""  class="insert_employee_name" placeholder="관리자 성명을 입력하세요.">
							</div>
							<div class="insert_company_content">사무실
								<input type="text" name=""  class="insert_employee_tel" placeholder="ex) 02-123-4567">
							</div>
							<div class="insert_company_content">연락처
								<input type="text" name=""  class="insert_employee_phone" placeholder="ex) 010-1234-5678">
							</div>
							<div class="insert_company_content">이메일
								<input type="text" name=""  class="insert_employee_email" placeholder="ex) abc@infobank.net">
							</div>
						</div>
					</form>
				</div>
				<!-- //wrt_table01 -->
				<hr>
				<div class="layer_btnbox center">
					<button type="button" class="btn_st_normal bt03" onclick="javascript:insert_company()"><span>등록</span></button>
					<button type="button" class="btn_st_normal bt03" onclick="javascript:add_company_layer_close()"><span>취소</span></button>
				</div>
			</div>
	
		</div>
	</div>
	<!-- //layerpopup 회사 등록 -->
	
	<!-- layerpopup 회사 수정 -->
	<div class="layerpop_wrap company_modify_layer" style="display:none;">
		<div class="layerpop_mask"></div>
		<div class="layerpop" style="width:500px; top:200px;">
			<div class="layer_content">
				<!-- wrt_table01 -->
				<div class="wrt_table01">
					<form>
						<input class="modify_seq" type="hidden" name="" value="">
						<div class="modify_company_wrap">회사정보</div>
						<div class="modify_comapny_content_wrap">
							<div class="modify_company_content">회사명
								<input type="text" name=""  class="modify_name" readonly="readonly" placeholder="회사명을 입력하세요.">
							</div>
							<div class="modify_company_content">회사코드
								<div class="modify_id"></div>
							</div>
							<div class="modify_company_content">성명
								<input type="text" name=""  class="modify_employee_name" readonly="readonly" placeholder="관리자 성명을 입력하세요.">
							</div>
							<div class="modify_company_content">사무실
								<input type="text" name=""  class="modify_employee_tel" readonly="readonly" placeholder="ex) 02-123-4567">
							</div>
							<div class="modify_company_content">연락처
								<input type="text" name=""  class="modify_employee_phone" readonly="readonly" placeholder="ex) 010-1234-5678">
							</div>
							<div class="modify_company_content">이메일
								<input type="text" name=""  class="modify_employee_email" readonly="readonly" placeholder="ex) abc@infobank.net">
							</div>
						</div>
					</form>
				</div>
				<!-- //wrt_table01 -->
				<hr>
				<div class="layer_btnbox center">
					<button type="button" class="btn_st_normal bt03" id="update_confirm_btn" onclick="javascript:update_company()"><span>수정</span></button>
					<button type="button" class="btn_st_normal bt03" id="update_cancel_btn" onclick="javascript:company_modify_layer_close()"><span>닫기</span></button>
				</div>
			</div>
	
		</div>
	</div>
	<!-- //layerpopup 회사 수정 -->
</body>
</html>