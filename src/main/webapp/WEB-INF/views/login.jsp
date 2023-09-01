<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String error = "";
    if (request.getParameter("error") != null) {
       error = request.getParameter("error");
    }
%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>굿페이퍼 비대면 서비스 관리 어드민</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
	
	<script type="text/javascript">
	$(document).ready(function(){

		var login_error = '<%=error%>';
		if(login_error !== ''){
			$('#txt').text(login_error).fadeOut(500).fadeIn(500).fadeOut(500).fadeIn(500);
		}

		$('#login_form').submit(function(event){
			
			var id = $('input[name=id]', '#login_form').val();
			var password = $('input[name=password]', '#login_form').val();

			if(!id){
				$('#txt').hide();
				$('#txt').text('아이디를 입력해 주세요.').fadeIn(500);
				return false;
			}else if(!password){
				$('#txt').hide();
				$('#txt').text('패스워드를 입력해 주세요.').fadeIn(500);
				return false;
			}else{
				return true;
			}
		});
	});
	
	</script>
</head>
<body>
<div id="login_wrap">
	<!-- header -->
	<div id="header">
		<div class="top_cont">
			<h1><a href="#">굿페이퍼 <span>비대면 서비스 관리 어드민</span></a></h1>
		</div>
	</div>
	<!-- //header -->
	
	<!-- container -->
	<div id="container">
		<div id="login_box_wrap">
			<form action="/login_process" id="login_form" method="post">
				<div class="login_box">
					<h2>굿페이퍼 관리자 페이지</h2>
					<br>
					<p>비대면 서비스 관리자 페이지입니다.</p>
					<div style="height:20px;"><p id="txt" class="text_alert">아이디/비밀번호를 입력 후 로그인 하세요.</p></div>
					<br>
					<ul>
						<li class="id"><input type="text" class="long" name="id" placeholder="아이디"></li>
						<li class="pw"><input type="password" class="long" name="password" placeholder="비밀번호"></li>
					</ul>
					<br>
					<div class="login_btn">
						<button type="submit" id="login_btn" class="btn_st_login bt03"><span>로그인</span></button>
					</div>
				</div>
			</form>-
		</div>
	</div>
    
	<!-- //container -->

</div>
<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
<!-- layerpopup 비밀번호 변경 -->
<div class="layerpop_wrap" style="display:none;">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width:590px; top:180px;">
		<div class="layer_rdbox">
			<div class="layer_lt"></div>
			<div class="layer_rt"></div>
		</div>
		<div class="layer_content">
			<div class="layer_tit">비밀번호 변경</div>
			
			<p class="stxt02" style="margin:0 0 15px;">개인정보 보호를 위해 3개월 마다 비밀번호 변경이 필요합니다.<br>
			지금 변경하지 않을 경우, ‘다음 로그인시 변경’을 선택하세요.</p>

			<!-- wrt_table01 -->
			<div class="wrt_table01">
				<form>
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>비밀번호 등록</caption>
	                <colgroup>
		                <col style="width:25%;">
		                <col style="width:auto;">
	                </colgroup>
					<tbody>
						<tr>
							<th>새 비밀번호 입력</th>
							<td><input type="text" name="" style="width:100%;" ></td>
						</tr>
						<tr>
							<th>새 비밀번호 확인</th>
							<td><input type="text" name="" style="width:100%;" ></td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- //wrt_table01 -->

			<p class="stxt02">※영문, 숫자, 특수문자를 조합해, 최소 8자 이상 18자리 이하로 등록하세요. </p>

			<div class="layer_btnbox">
				<button type="button" class="btn_st bt03"><span>비밀번호 변경</span></button>
				<button type="button" class="btn_st bt03"><span>다음 로그인 시 변경</span></button>
			</div>
		</div>

		<div class="layer_rdbox">
			<div class="layer_lb"></div>
			<div class="layer_rb"></div>
		</div>
	</div>
</div>
<!-- //layerpopup 비밀번호 변경 -->

<!-- layerpopup 비밀번호 등록 -->
<div class="layerpop_wrap" style="display:none;">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width:590px; top:180px;">
		<div class="layer_rdbox">
			<div class="layer_lt"></div>
			<div class="layer_rt"></div>
		</div>
		<div class="layer_content">
			<div class="layer_tit">비밀번호 등록</div>
			
			<p class="stxt02" style="margin:0 0 15px;">초기화 된 비밀번호로 로그인 하셨습니다.<br>
			새로운 비밀번호를 등록해야 서비스를 이용할 수 있습니다.</p>

			<!-- wrt_table01 -->
			<div class="wrt_table01">
				<form>
				<table border="0" cellspacing="0" cellpadding="0">
	                <caption>비밀번호 등록</caption>
	                <colgroup>
		                <col style="width:25%;">
		                <col style="width:auto;">
	                </colgroup>
					<tbody>
						<tr>
							<th>새 비밀번호 입력</th>
							<td><input type="text" name="" style="width:100%;" ></td>
						</tr>
						<tr>
							<th>새 비밀번호 확인</th>
							<td><input type="text" name="" style="width:100%;" ></td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!-- //wrt_table01 -->

			<p class="stxt02">※영문, 숫자, 특수문자를 조합해, 최소 8자 이상 18자리 이하로 등록하세요. </p>

			<div class="layer_btnbox">
				<button type="button" class="btn_st bt03"><span>비밀번호 변경</span></button>
			</div>
		</div>

		<div class="layer_rdbox">
			<div class="layer_lb"></div>
			<div class="layer_rb"></div>
		</div>
	</div>
</div>
<!-- //layerpopup 비밀번호 등록 -->

</body>
</html>