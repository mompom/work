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
var user_mng;
var checkList = new Array();

$(document).ready(function(){
	user_mng = new User_mng;
	user_mng.view();
	$('#all_select_checkbox').click(function(){
		var isChecked = $(this).is(":checked") == true ? true : false;
		
		if(isChecked)
			checkList = new Array();	
		
		$("input:checkbox[name='prod_01']").each(function(e){
			$(this).prop('checked', isChecked);
			changeDeleteList($(this).val());
		});
	});
	
	 
	$('.delete_user').click(function(){
		if(checkList.length == 0)
		{
			alert("삭제하실 사용자를 선택해 주세요.");
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
		var requestUrl="/${map.loanId}/api/userDelete";
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
	getSelectBox("company_list_select");
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

function add_user_layer_open()
{
	$(".add_user_layer").attr("style","display:block;");
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

function insert_user()
{
	var loan_id = $('.loan_id').val();
	var level = $('.insert_level').val();
	var id = $('.insert_id').val();
	var name = $('.insert_name').val();
	var tel_number = $('.insert_phone').val();
	var team = $('.insert_team').val();
	var note = $('.insert_note').val();

	if(loan_id == ""){
		alert('회사를 선택해 주세요.')
		return;
	}else if(level == ""){
		alert('레벨을 선택해 주세요.')
		return;
	}else if(id == ""){
		alert('아이디를 입력해 주세요.')
		return;
	}else if(name == ""){
		alert('이름을 입력해 주세요.')
		return;
	}
	
	var method="POST";
	
	var requestUrl="/${map.loanId}/api/userInsert";
	var params = {
		"loan_id" : loan_id,
		"level" : level,
		"id" : id,
		"name" : name,
		"tel_number" : tel_number,
		"team" : team,
		"note" : note
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
				alert("이미 등록된 아이디 입니다.");
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
	user_mng.view();
}

function viewPage(){
	user_mng.view();
}

function add_user_layer_close()
{
	$(".add_user_layer").attr("style","display:none;");
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
					<li><button type="button" class="btn_st_normal bt01 delete_user"><span>사용정지</span></button></li>
					<li><button type="button" class="btn_st_normal bt01" onclick="javascript:add_user_layer_open()"><span>계정등록</span></button></li>
					<li>
						<select name="" id="" class="select01 search_option2" style="width:153px;">
							<option value="name">이름</option>
							<option value="id">아이디</option>
							<option value="loan_id">회사코드</option>
						</select>
					</li>
					<li class="input_search"><input class="search_word" type="text" name="" placeholder="검색어를 입력해 주세요."></li>
					<li><button type="button" class="btn_st_normal bt01" onclick="javascript:search()"><span>검색</span></button></li>
				</ul>
			</div>
	
			<!-- contents -->
			<div class="contents">
				<!-- lst_table01 -->
				<div class="lst_table01" id="lst_table01_company">
					<table id="historyInnerTable" class="table center">
		                <caption>계좌 인증 내역</caption>
		                <colgroup>
		                </colgroup>
		                <thead>
							<tr>
								<th class="border_right num"><input type="checkbox" name="company_all" id="all_select_checkbox"></th>
								<th class="border_right">순번</th>
								<th class="border_right">회사코드</th>
								<th class="border_right">아이디</th>
								<th class="border_right">레벨</th>
								<th class="border_right">이름</th>
								<th class="border_right">번호</th>
								<th class="border_right">팀</th>
								<th class="border_right">노트</th>
								<th class="border_right">등록일</th>
								<th>수정일</th>
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
	<div class="layerpop_wrap add_user_layer" style="display:none;">
		<div class="layerpop_mask"></div>
		<div class="layerpop" style="width:500px; top:200px;">
			<div class="layer_content">
				<!-- wrt_table01 -->
				<div class="wrt_table01">
					<form>
						<div class="insert_user_wrap">계정등록</div>
						<div class="insert_user_content_wrap">
							<div class="insert_user_content">회사명
								<select name="" id="company_list_select" class="loan_id">
									<option value="name">이름</option>
								</select>
							</div>
							<div class="insert_user_content">레벨
								<select name="" id="admin_level_list_select" class="insert_level">
									<option value="1">시스템관리자</option>
									<option value="2">정산관리자</option>
									<option value="3">고객사</option>
								</select>
							</div>
							<div class="insert_user_content">아이디
								<input type="text" name=""  class="insert_id" placeholder="아이디를 입력하세요.">
							</div>
							<div class="insert_user_content">이름
								<input type="text" name=""  class="insert_name" placeholder="이름을 입력하세요.">
							</div>
							<div class="insert_user_content">번호
								<input type="text" name=""  class="insert_phone" placeholder="ex) 010-1234-5678">
							</div>
							<div class="insert_user_content">팀
								<input type="text" name=""  class="insert_team" placeholder="팀을 입력하세요.">
							</div>
							<div class="insert_user_content">노트
								<input type="text" name=""  class="insert_note" placeholder="노트를 입력하세요.">
							</div>
						</div>
					</form>
				</div>
				<!-- //wrt_table01 -->
				<hr>
				<div class="layer_btnbox center">
					<button type="button" class="btn_st_normal bt03" onclick="javascript:insert_user()"><span>등록</span></button>
					<button type="button" class="btn_st_normal bt03" onclick="javascript:add_user_layer_close()"><span>취소</span></button>
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