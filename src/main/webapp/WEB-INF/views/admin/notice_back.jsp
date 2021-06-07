<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>비대면 대출 신청 서비스</title>
<style type="text/css">
	img:hover{
		opacity: 0.5;
  			filter: alpha(opacity=50);
	}
</style>

<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>

<script type="text/javascript">
var pageSize = 20;
var checkList = new Array();

$(document).ready(function(){
	$(".insert_title").focusout(function(){
        var element = $(this);        
        if (!element.text().trim().length) {
            element.empty();
        }
    });
	
	$('#all_select_checkbox').click(function(){
		var isChecked = $(this).is(":checked") == true ? true : false;
		
		if(isChecked)
			checkList = new Array();	
		
		$("input:checkbox[name='prod_01']").each(function(e){
			$(this).prop('checked', isChecked);
			changeDeleteList($(this).val());
		});
	});
	
	$('.delete_notice').click(function(){
		if(checkList.length == 0)
		{
			alert("삭제하실 공시사항을 선택해 주세요.");
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
		var requestUrl="/${map.loanId}/api/noticeDelete";
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
	 
	getSelectBox("search_option1");
	getSelectBox("add_loanId_selectbox");
	getViewAjax();
});

function viewPage(){
	getViewAjax();
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

function getViewAjax(){
	
	var pageNo = $("#pageNo").val();
	var searchOption1 = $("#search_option1").val();
	var searchOption2 = $(".search_option2").val();
	var searchWord = $(".search_word").val();
	var totalCnt = 0;		
	var method="POST";
	var requestUrl="/${map.loanId}/api/noticeSelectList";
	var params = {
		"pageNo": pageNo, 
		"pageSize": pageSize,
		"searchOption1" : searchOption1,
		"searchOption2" : searchOption2,
		"searchWord" : searchWord
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
			console.log(response)
			var pageBlock = 10;
			var pageNo = $("#pageNo").val();
			var stateNo = (pageNo-1) * pageSize;
			if (response.result) {
				$(".lst_table01 tbody").empty();
				$(response.list).each(function(k,v){
					totalCnt = v.totalCnt;
					$(".lst_table01 tbody").append(
						"<tr>"+
							"<td class='center'><input type=\"checkbox\" class=\"check_seq\" name=\"prod_01\" value=\""+v.seq+"\"></td>"+
							"<td class='center'>"+v.name+"</td>"+
							"<td class='al' id=\""+v.seq+"\"><a href=\"javascript:notice_detail_function('"+v.seq+"')\">"+v.title+"</a></td>"+
							"<td class='center'>"+v.admin_id+"</td>"+
							"<td class='center'>"+v.reg_date+"</td>"+
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
				
				//체크박스 리스트 초기화
				checkList = new Array();
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
	
	if(searchWord == "")
	{
		alert('검색어를 입력해 주세요.')
		return;
	}
	getViewAjax();
}

function notice_detail_function(seq){

	var method="POST";
	var requestUrl="/${map.loanId}/api/noticeSelectOne";
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
				//여기이이이이
				console.log (map);
// 				$('.modify_title').val(map.title);
				$('.modify_title').text(map.title);
				$('.modify_body').text(map.body);
				$('.modify_seq').val(map.seq);
			} else {
				alert("실패");
			}
			
			
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
	
	$(".notice_modify_layer").attr("style","display:block;");
}

function insert_notice(){
	var title = $('.insert_title').text();
	var body = $('.insert_body').text();
	var loan_id = $('.add_loanId_selectbox').val();
	
	if(loan_id == "")	{
		alert('회사를 선택해 주세요.')
		return;
	}
	else if(title == "")	{
		alert('제목을 입력해 주세요.')
		return;
	}
	else if(body == "")	{
		alert('내용을 입력해 주세요.')
		return;
	}
	
	var insert_confirm = confirm('저장 하시겠습니까?');
	if(!insert_confirm){
		return;
	}
	
	var method="POST";
	
	var requestUrl="/${map.loanId}/api/noticeInsert";
	var params = {
		"title" : title,
		"body" : body,
		"loan_id": loan_id
		
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

function update_notice(){
	var title = $('.modify_title').text();
	var body = $('.modify_body').text();
	var seq = $('.modify_seq').val();
	var editable = $('.modify_title').attr('contentEditable');
	
	if(editable == 'true'){
		var update_confirm = confirm('수정 하시겠습니까?');
		if(!update_confirm){
			return;
		}
		
		$('.modify_title').attr('contentEditable', false)
		$('.modify_body').attr('contentEditable', false)
		$('#update_confirm_btn').css('background-color', '#4dc453');
		$('#update_cancel_btn').css('background-color', '#4dc453');
		$('.modify_title_wrap').css('background-color', '#4dc453');
		$('#update_confirm_btn').text('수정');
		$('#update_cancel_btn').text('닫기');
	}else{
		$('.modify_title').attr('contentEditable', true)
		$('.modify_body').attr('contentEditable', true)
		$('#update_confirm_btn').css('background-color', '#F96');
		$('#update_cancel_btn').css('background-color', '#F96');
		$('.modify_title_wrap').css('background-color', '#F96');
		$('#update_confirm_btn').text('저장');
		$('#update_cancel_btn').text('취소');
		$('.modify_body').focusEnd();
		return;
	}
	
	if(title.length > 45){
		alert("제목 45자 제한");
		return;
	}
	
	var method="POST";
	var requestUrl="/${map.loanId}/api/noticeUpdate";
	var params = {
		"seq": seq,
		"title" : title,
		"body" : body
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
				//alert('#'+seq+" a");
				
				//$('#'+seq+" a").innerHTML = title;
				//$(".notice_modify_layer").attr("style","display:none;");
				
				//location.reload();
			} else {
				alert("실패");
			}
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
}

function add_notice_layer_open(){
	$(".add_notice_layer").attr("style","display:block;");
}

function modify_layer_close(){
	var editable = $('.modify_title').attr('contentEditable');
	if(editable == 'true'){
		$('.modify_title').attr('contentEditable', false)
		$('.modify_body').attr('contentEditable', false)
		$('#update_confirm_btn').css('background-color', '#4dc453');
		$('#update_cancel_btn').css('background-color', '#4dc453');
		$('.modify_title_wrap').css('background-color', '#4dc453');
		$('#update_confirm_btn').text('수정');
		$('#update_cancel_btn').text('닫기');
		return;
	}
	$(".notice_modify_layer").attr("style","display:none;");
}

function add_notice_layer_close(){
	$(".add_notice_layer").attr("style","display:none;");
}

</script>
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
				<li class="search_select_box">
					<select name="" id="search_option1" class="select01 search_option1" style="width:153px;">
					</select>
				</li>
				<li>
					<select name="" id="" class="select01 search_option2" style="width:153px;">
						<option value="title">제목</option>
						<option value="body">내용</option>
					</select>
				</li>
				<li class="input_search"><input class="search_word" type="text" name="" placeholder="검색어를 입력해 주세요."></li>
				<li><button type="button" class="btn_st_normal bt01" onclick="javascript:search()"><span>검색</span></button></li>
			</ul>
		</div>

		<!-- top btn -->
		<div class="top_area clearbox">
			<div class="lf_box">
				<button type="button" class="btn_st_normal bt01 delete_notice"><span>삭제</span></button>
			</div>
			<div class="rf_box">
				<button type="button" class="btn_st_normal bt01" onclick="javascript:add_notice_layer_open()"><span>공지 등록</span></button>
			</div> 
		</div>

		<!-- contents -->
		<div class="contents">
			<!-- lst_table01 -->
			<div class="lst_table01">
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>공지사항 리스트</caption>
	                <colgroup>
		                <col style="width:6%;">
		                <col style="width:15%;">
		                <col style="width:auto;">
		                <col style="width:12%;">
		                <col style="width:12%;">
	                </colgroup>
	                <thead>
						<tr>
							<th class="num"><input type="checkbox" name="notice_all" id="all_select_checkbox"></th>
							<th>구분</th>
							<th>제목</th>
							<th>등록ID</th>
							<th>등록일</th>
						</tr>
					</thead>
					<tbody>
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
<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
</div>


<!-- layerpopup 공지사항 등록 -->
<div class="layerpop_wrap add_notice_layer" style="display:none;">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width: 70%; min-width: 500px; top:200px;">
		<div class="layer_content">
			<!-- wrt_table01 -->
			<div class="wrt_table01">
				<form>
					<div class="select_company_wrap">
						<select name="" id="add_loanId_selectbox" class="select01 add_loanId_selectbox" style="width:100%; font-size: 12px;"></select>
					</div>
					<hr style="border: 0px solid #eee; height: 1px; background-color: #eee; padding:0px; margin: 0px;">
					<div class="insert_title_wrap">
						<div class="insert_title" contenteditable= "true" placeholder="제목을 입력하세요."></div>
					</div>
					<div class="insert_body_wrap">
						<div class="insert_body" contenteditable= "true" placeholder="내용을 입력하세요."></div>
					</div>
				</form>
			</div>
			<!-- //wrt_table01 -->
			<hr>
			<div class="layer_btnbox center">
				<button type="button" class="btn_st_normal bt03" onclick="javascript:insert_notice()"><span>등록</span></button>
				<button type="button" class="btn_st_normal bt03" onclick="javascript:add_notice_layer_close()"><span>취소</span></button>
			</div>
		</div>
	</div>
</div>
<!-- //layerpopup 공지사항 등록 -->

<!-- layerpopup 공지사항 수정 -->
<div class="layerpop_wrap notice_modify_layer" style="display:none;">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width: 70%; min-width: 500px; top:200px;">
		<div class="layer_content">
			<!-- wrt_table01 -->
			<div class="wrt_table01">
				<form>
					<input class="modify_seq" type="hidden" name="" value="">
					<div class="modify_title_wrap">
	<!-- 					<input class="modify_title" type="text" name="" value="공지 제목을 입력하세요."> -->
						<p class="modify_title" contenteditable="false"></p>
					</div>
					<div class="modify_body_wrap">
						<p class="modify_body" contenteditable="false"></p>
					</div>
				</form>
			</div>
			<!-- //wrt_table01 -->
			<hr>
			<div class="layer_btnbox center">
				<button type="button" class="btn_st_normal bt03" id="update_confirm_btn" onclick="javascript:update_notice()"><span>수정</span></button>
				<button type="button" class="btn_st_normal bt03" id="update_cancel_btn"onclick="javascript:modify_layer_close()"><span>닫기</span></button>
			</div>
		</div>

	</div>
</div>
<!-- //layerpopup 공지사항 수정 -->

</body>
</html>