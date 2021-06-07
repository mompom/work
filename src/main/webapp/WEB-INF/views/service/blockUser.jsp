<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>굿페이퍼 비대면 서비스 관리 어드민</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
	<script type="text/javascript" src="/static/include/js/page/common/bank-common.js"></script>
	
<script type="text/javascript">
var blockUser_mng;
var bank_mng;
var checkList = new Array();

$(document).ready(function(){
	blockUser_mng = new BlockUser_mng;
	blockUser_mng.view();
	
	bank_mng = new Bank_mng;
	bank_mng.view('y', function(response){
		$('.insert_account_bank_name option').remove();
		$(response.list).each(function(k,v){
			$('.insert_account_bank_name').append(
					"<option value="+v.bank_code+">"+v.bank_name+"</option>"
			);
		});
		
	});
	
	$(document).on('click', '#all_select_checkbox', function(e){
		var isChecked = $(this).is(":checked") == true ? true : false;
		if(isChecked){
			checkList = new Array();
		}
		
		$("input:checkbox[name='prod_01']").each(function(e){
			$(this).prop('checked', isChecked);
			changeDeleteList($(this).val());
		});
	});
	 
	$(document).on("click", ".check_seq", function(e){
		changeDeleteList($(this).val());
	});
	
	$(document).on("click", ".cursor_pointer", function(e){
		var note = $(this).find('input').val();
		console.log(note);
		
		if(note == 'null' || note == ''){
			return;
		}
		$('.content_note').text(note);
		note_layer_open();
	});
});	

function changeDeleteList(seq){
	var isIndex = checkList.indexOf(seq);
	if(isIndex == -1){
		checkList.push(seq);
	}
	else{
		checkList.splice(isIndex,1);
	}
}

function add(){
	blockUser_mng.add();
}

function remove(){
	blockUser_mng.remove();
}

function search(){
	var searchWord = $(".search_word").val();
	blockUser_mng.view();
}

function viewPage(){
	blockUser_mng.view();
}

function insert_layer_close(){
	$(".add_user_layer").attr("style","display:none;");
}

function insert_layer_open(){
	$(".add_user_layer").attr("style","display:block;");
}

function note_layer_close(){
	$(".note_layer").attr("style","display:none;");
}

function note_layer_open(){
	$(".note_layer").attr("style","display:block;");
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
					<li><button type="button" class="btn_st_normal bt01" onclick="javascript:remove()"><span>삭제</span></button></li>
					<li><button type="button" class="btn_st_normal bt01" onclick="javascript:insert_layer_open()"><span>등록</span></button></li>
					<li>
						<select name="" id="" class="select01 search_option2" style="width:153px;">
							<option value="holder_name">이름</option>
							<option value="holder_number">계좌번호</option>
							<option value="bank_name">은행</option>
						</select>
					</li>
					<li class="input_search"><input class="search_word" type="text" name="" placeholder="검색어를 입력해 주세요."></li>
					<li><button type="button" class="btn_st_normal bt01" onclick="javascript:search()"><span>검색</span></button></li>
				</ul>
			</div>
	
			<!-- contents -->
			<div class="contents">
				<!-- lst_table01 -->
				<div class="lst_table01" id="lst_table01">
					<table id="historyInnerTable" class="table center">
		                <caption>계좌 인증 내역</caption>
		                <colgroup>
		                </colgroup>
		                <thead>
							<tr>
								<th class="border_right num"><input type="checkbox" name="company_all" id="all_select_checkbox"></th>
								<th class="border_right">순번</th>
								<th class="border_right">유저키</th>
								<th class="border_right">이름</th>
								<th class="border_right">계좌번호</th>
								<th class="border_right">은행코드</th>
								<th class="border_right">은행</th>
								<th class="border_right">노트</th>
								<th class="border_right">날짜</th>
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
	
	
	<!-- layerpopup 차단 등록 -->
	<div class="layerpop_wrap add_user_layer" style="display:none;">
		<div class="layerpop_mask"></div>
		<div class="layerpop" style="width:500px; top:200px;">
			<div class="layer_content">
				<!-- wrt_table01 -->
				<div class="wrt_table01">
					<form>
						<div class="insert_user_wrap">차단등록</div>
						<div class="insert_user_content_wrap">
							<div class="insert_user_content">계좌번호
								<input type="text" name=""  class="insert_account_holder_number" placeholder="계좌번호를 입력하세요.">
							</div>
							<div class="insert_user_content">이름
								<input type="text" name=""  class="insert_account_holder_name" placeholder="이름을 입력하세요.">
							</div>
							<!-- <div class="insert_user_content">은행코드
								<input type="text" name=""  class="insert_account_holder_code" placeholder="은행코드를 입력하세요">
							</div> -->
							<div class="insert_user_content">은행
								<!-- <input type="text" name=""  class="insert_account_bank_name" placeholder="은행명을 입력하세요."> -->
								<select name="" id="" class="select01 insert_account_bank_name">
									<option value="bank_name">은행명</option>
								</select>
							</div>
							<div class="insert_user_content">노트
								<input type="text" name=""  class="insert_note" placeholder="차단사유를 입력하세요.">
							</div>
						</div>
					</form>
				</div>
				<!-- //wrt_table01 -->
				<hr>
				<div class="layer_btnbox center">
					<button type="button" class="btn_st_normal bt03" onclick="javascript:add()"><span>등록</span></button>
					<button type="button" class="btn_st_normal bt03" onclick="javascript:insert_layer_close()"><span>취소</span></button>
				</div>
			</div>
	
		</div>
	</div>
	<!-- //layerpopup 차단 등록 -->
	
	<div class="layerpop_wrap note_layer" style="display:none;">
		<div class="layerpop_mask"></div>
		<div class="layerpop" style="width:500px; top:200px;">
			<div class="layer_content">
				<!-- wrt_table01 -->
				<div class="wrt_table01">
					<form>
						<div class="note_wrap">차단사유</div>
						<div class="content_wrap">
							<div class="content">
								<span class="content_note"></span>
							</div>
						</div>
					</form>
				</div>
				<!-- //wrt_table01 -->
				<hr>
				<div class="layer_btnbox center">
					<button type="button" class="btn_st_normal bt03" onclick="javascript:note_layer_close()"><span>취소</span></button>
				</div>
			</div>
	
		</div>
	</div>
</body>
</html>