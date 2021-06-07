$(document).ready(function(){
	$("#fast_check").bind("click",function(){
		$("#call_start_time option").eq(0).attr("selected","selected");
		$("#call_end_time option").eq(23).attr("selected","selected");
	});
	
	$("#faseBtn").bind("click",function(){
		if(validation()){
			$.blockUI({ 
				/*css: {
		            border: 'none', 
		            padding: '15px', 
		            backgroundColor: '#000', 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px', 
		            opacity: .5, 
		            color: '#fff' 
	        	}*/
				message :
					"<div class='loading_box' style='display:block;'><div class='mask_area'></div>"+
					"<div class='loading_cont'><img src='/static/img/loading_1.gif'/></div>"
			}); 
	        setTimeout($.unblockUI, 2000); 
			//fast_app();
		}
	});
	
	$('.header_menu>.gnb>ul>li, .navigation_warp').hover(
		function(){
			$('.navigation_warp').show();
			
			if($(this).context.localName == 'li'){
				$('.navigation_warp').css('left', $(this).offset().left+50);
				$('.navigation_list').hide();//css('display', 'none');
				if($(this).attr('id')=='admin_mng'){
					$('#admin_mng_nav').show();
				}else if($(this).attr('id')=='authHistory_mng'){
					$('#authHistory_mng_nav').show();
				}else if($(this).attr('id')=='confirm_mng'){
					$('#service_mng_nav').show();
				}else if($(this).attr('id')=='result_mng'){
					$('#result_mng_nav').show();
				}else if($(this).attr('id')=='members_mng'){
					$('#members_mng_nav').show();
				}
				
			}
			
		},function(){
			if($(this).context.localName != 'li'){
				$('.navigation_list').hide();//css('display', 'none');
			}
			$('.navigation_warp').hide();//css('display', 'none');
		}
	);
	
	
	
	
	
	$(document).on('click', '.expend', function(){
		var get_expend = localStorage.getItem('expend');
		if(get_expend == 1){
			$('#container').animate({
				width: '1025px'
			}, {duration: 500,
				easing: "easeOutExpo"
			});
			$('.expend').animate({
				backgroundColor: 'transparent'
			});
			localStorage.setItem('expend', 0);
		}else{
			$('#container').animate({
				width: '99.0%'
			}, {duration: 500,
				easing: "easeOutExpo"
			});
			$('.expend').animate({
				backgroundColor: '#8fc050'
			});
			localStorage.setItem('expend', 1);
		}
	});
	setExpendOption();
	
	
	
	
	$(document).on('click', '#pass_confirm_btn', function(){
		//$('.new_password_layerpop').css('display', 'none');
		

		var captcha_check = IsRecapchaValid(widId);
		console.log('password update' + captcha_check);
		
		var now_pw = $('#now_pw').val();
		var new_pw = $('#new_pw').val();
		var conf_pw = $('#conf_pw').val();
		console.log(now_pw);
		
		var pw_alert = function(pw){
			$(pw).animate({borderColor: 'red'}, {duration: 300, easing: "easeOutExpo"})
			.animate({borderColor: '#dbdbdb'}, {duration: 300, easing: "easeOutExpo"});
			
			$('#pass_confirm_btn').animate({backgroundColor: 'red'}, {duration: 300, easing: "easeOutExpo"})
			.animate({backgroundColor: '#4dc453'}, {duration: 300, easing: "easeOutExpo"});
		}
		
		if(!now_pw){
			pw_alert('#now_pw');
			return;
		}
		if(!new_pw){
			pw_alert('#new_pw');
			return;
		}
		if(!conf_pw){
			pw_alert('#conf_pw');
			return;
		}
		if(new_pw != conf_pw){
			pw_alert('#new_pw');
			pw_alert('#conf_pw');
			return;
		}
		/*if(captcha_check != true){
			pw_alert();
			return;
		}*/
		
		var params = {
				'now_pw' : now_pw,
				'new_pw' : new_pw,
				'conf_pw' : conf_pw
			};
			
			ajaxGet("/${map.loanId}/api/newPasswordUpdate", params, function(response){
				console.log(response);
				if (response.result) {
					alert('비밀번호가 변경되었습니다.');
					$('.new_password_layerpop').css('display', 'none');
					$('#now_pw').val('');
					$('#new_pw').val('');
					$('#conf_pw').val('');
				}else{
					pw_alert('#now_pw');
				}
			}, params);
		});
	});
	
	$(document).on('click', '#pass_cancel_btn', function(){
		$('.new_password_layerpop').css('display', 'none');
		$('#now_pw').val('');
		$('#new_pw').val('');
		$('#conf_pw').val('');
	});
	
	//캡차
	$(document).on('click', '.password_update', function(){
		$('#now_pw').val('');
		$('#new_pw').val('');
		$('#conf_pw').val('');
		$('.new_password_layerpop').css('display','block');
});


var widId = "";
var onloadCallback = function ()
{
 widId = grecaptcha.render('recapchaWidget', {
 'sitekey':'6LcyWIwUAAAAAKJnyMCiXQpgwKkgUuM-RdkNELPk'
         });
};


function IsRecapchaValid(widId)
{
var res = grecaptcha.getResponse(widId);
console.log(res);
if (res == "" || res == undefined || res.length == 0)
   {
    return false;
   }
 return true;
}


function setExpendOption(){
	var get_expend = localStorage.getItem('expend');
	if(get_expend==1){
		$('#container').animate({
			width: '99.0%'
		}, {duration: 0,
			easing: "easeOutExpo"
		});
		$('.expend').animate({
			backgroundColor: '#8fc050'
		});
		localStorage.setItem('expend', 1);
	}
}

function validation(){
	if(!onlyHan("user_name" , "한글만 가능합니다.")){
		return false;
	}
	if(!checkVal("tel_number")){
		return false;
	}
	if(!checkVal("amount")){
		return false;
	}
	if(!$("#fast_check").prop("checked")){
		if($("#call_start_time option:selected").val() >= $("#call_end_time option:selected").val()){
			alert("희망상담시간을 확인해주십시오.");
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}

function fast_app(){
	//대출신청 저장
	//${map.loanId }
	var method="POST";
	var requestUrl="http://localhost:8080/api/setProgress";
	var params = {
		"user_name": $("#user_name").val(), 
		"loan_id": $("#loanId").val(), 
		"tel_number":$("#tel_number").val(), 
		"require_amount" : $("#amount").val(),
		"call_start_time":$("#call_start_time option:selected").val(), 
		"call_end_time":$("#call_end_time option:selected").val()
	};
	var getType="json";
	var contType="application/json; charset=UTF-8";
	ajaxGetData(method , requestUrl , params ,getType , contType , resultData);
}

function resultData (response) {
	if(response.message != "")
		alert(response.message);
	/*if(response.result){
		window.location.href="step01";
	}*/
}


/*
 * method : POST , GET
 * getType : HTML JSON XML
 * 
 */
function ajaxGetData(method , requestUrl , params ,getType ,contType , functionObj){
	$.ajax({
		url: requestUrl,
		type: method,
		data: JSON.stringify( params),
		dataType: getType,
		contentType : contType,
		cache: false,
		success: function(result) {
			if (functionObj) {
				functionObj(result);
			} else {
				return result;
			}
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
 }

function ajaxGetData_get(method , requestUrl , params ,getType ){

	$.ajax({
		url: requestUrl,
		type: method,
		data: params,
		dataType: getType,
		cache: false,
		success: function(result) {
			//alert(result.responseText);
			return result;
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
 }

function ajaxGet(requestUrl, params, functionObj, functionObj_params){
	
	$.ajax({
		url: requestUrl,
		type: "POST",
		data: JSON.stringify(params),
		dataType: "json",
		contentType : "application/json; charset=UTF-8",
		cache: false,
		success: function(result) {
			if (functionObj) {
				functionObj(result, functionObj_params);
			} else {
				return result;
			}
		},
		fail: function() {
			alert("서버와의 연결에 실패하였습니다\n잠시후 다시 시도해주세요");
		}
	});
}

//한글만입력
function onlyHan(id , msg)
{
	if(!checkVal(id)){
		return false;
	}
	
	regexp = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
	v = $("#"+id).val();
	if( regexp.test(v) ) {
		alert(msg);
		$("#"+id).val(v.replace(regexp,''));
		return false;
	}else{
		return true;
	}
}

//값 확인
function checkVal(id){
	if($("#"+id).val() == ""){
		alert("값을 입력해주세요.");
		return false;
	}else{
		return true;
	}
}


//숫자만 입력
function setNum(obj){
	val=$("#" + obj).val();
	re=/[^0-9]/gi;
	$("#" + obj).val(val.replace(re,""));
}

function setPaging(pageNo , pageSize , pageBlock , totalCnt){
	var html = "";
	if(totalCnt <= pageSize){
		html += "<a href='javascript:setPage(1)' class='first'><img class='double_arrow' src='/static/img/double-arrow-left.png' alt='처음' /></a>";
		html += "<a href='javascript:setPage(1)' class='prev'><img class='nav_arrow' src='/static/img/nav-arrow-left.png' alt='이전' /></a>";
		html += "<a class='on' href='javascript:setPage(1)' >1</a>";
		html += "<a href='javascript:setPage(1)' class='next'><img class='nav_arrow' src='/static/img/nav-arrow-right.png' alt='다음' /></a>";
		html += "<a href='javascript:setPage(1)' class='last'><img class='double_arrow' src='/static/img/double-arrow-right.png' alt='마지막' /></a>";
	}else{
		var lastPage = Math.ceil(totalCnt / pageSize);
		html += "<a href='javascript:setPage(1)' class='first'><img class='double_arrow' src='/static/img/double-arrow-left.png' alt='처음' /></a>";
		if(parseInt(pageNo) > 1)
			html += "<a href='javascript:setPage("+(parseInt(pageNo)-1)+")' class='prev'><img class='nav_arrow' src='/static/img/nav-arrow-left.png' alt='이전' /></a>";
		else
			html += "<a href='javascript:setPage(1)' class='prev'><img class='nav_arrow' src='/static/img/nav-arrow-left.png' alt='이전' /></a>";
		
		//for(i=1;i<=lastPage;i++){
		var st = pageNo;
		var ed = lastPage;
		
		pageBlock = 5;
		
		if((parseInt(pageNo)+pageBlock) > parseInt(lastPage)){
			ed = lastPage;
		}else{
			//ed = (parseInt(st)+ee);
			ed = (parseInt(pageNo) + pageBlock);
		}
		
		
		if((parseInt(pageNo)-pageBlock) < 1){
			st = 1;
		}else{
			st = (parseInt(pageNo) - pageBlock);
		}
		
		for(i=st;i<=ed;i++){
			if(i == pageNo)
				html += "<a href='javascript:setPage("+i+")' class='on'>"+i+"</a>";
			else
				html += "<a href='javascript:setPage("+i+")' class='off'>"+i+"</a>";
		}
		
		if(pageNo < lastPage)
			html += "<a href='javascript:setPage("+(parseInt(pageNo)+1)+")' class='next'><img class='nav_arrow' src='/static/img/nav-arrow-right.png' alt='다음' /></a>";
		else
			html += "<a href='javascript:setPage("+pageNo+")' class='next'><img class='nav_arrow' src='/static/img/nav-arrow-right.png' alt='다음' /></a>";
		
		html += "<a href='javascript:setPage("+lastPage+")' class='last'><img class='double_arrow' src='/static/img/double-arrow-right.png' alt='마지막' /></a>";
	}
	
	return html;	
}



function setPage(pageNo){
	$("#pageNo").val(pageNo);
	/*
	var pageSize = $("#pageSize option:selected").val();
	var pageNo = $("#pageNo").val();
	var pageBlock = 10;*/
	viewPage();
}


/*
 * 기본 날짜 지정
 */
function setDefaultDate(type){
	var today = new Date();
	var year = today.getFullYear();
	var month = today.getMonth()+1;
	var day = today.getDate();
	if(month < 10) month = "0"+month;
	if(day < 10) day = "0"+day;
	
	var yymmdd = year+"-"+month+"-"+day;

	var today2 = new Date(year,Number(month-1),Number(day)-type);
	var year2 = today2.getFullYear();
	var month2 = today2.getMonth()+1;
	var day2 = today2.getDate();
	if(month2 < 10) month2 = "0"+month2;
	if(day2 < 10) day2 = "0"+day2;
	
	var yymmdd2 = year2+"-"+month2+"-"+day2;
	$("#sdate").val(yymmdd2);
	$("#edate").val(yymmdd);

}