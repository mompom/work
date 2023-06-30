<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div id="header">
	<div class="header_top">
		<div class="top_cont">
			<!-- <h1 class="top_logo">굿페이퍼</h1> -->
			<h1><a href="#">굿페이퍼 <span>비대면 서비스 관리 어드민</span></a></h1>
			<div class="top_util">
				<form action="${base}/logout" method="post">
					<span class="user_name">${map.admin_name}</span>
					<span class="user">(${map.level_name})</span>
					
					<span class="space_cst">ㅣ</span>
					<button type="button" class="btn_st_bd password_update"><span>Password</span></button> 
					<span class="space_cst">ㅣ</span>
					<button type="submit" class="btn_st_bd logout_bt"><span>Logout</span></button>
					<span class="space_cst">ㅣ</span>
					<button type="button" class="btn_st_bd expend"><span>Option</span></button>
				</form>
			</div>
		</div>
	</div>
	<div class="header_menu">
		<div class="gnb">
			<ul>
				<c:if test="${map.admin_level == 1}">
					<li id="admin_mng" <c:if test="${map.topmenu == 'admin_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/notice">운영관리</a></li>
					<li id="authHistory_mng" <c:if test="${map.topmenu == 'authHistory_mng'}">class="on"</c:if>><a href="/${map.loanId}/authHistory/calculate">정산관리</a></li>
					<li id="confirm_mng" <c:if test="${map.topmenu == 'confirm_mng'}">class="on"</c:if>><a href="/${map.loanId}/service/domainManage">서비스관리</a></li>
					<%-- <li id="result_mng" <c:if test="${map.topmenu == 'result_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistoryManage_date">내역조회</a></li> --%>
					<li id="members_mng" <c:if test="${map.topmenu == 'members_mng'}">class="on"</c:if>><a href="/${map.loanId}/members/meritz">회원사전용</a></li>
				</c:if>
				<c:if test="${map.admin_level == 2}">
					<li id="admin_mng" <c:if test="${map.topmenu == 'admin_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/notice">운영관리</a></li>
					<li id="authHistory_mng" <c:if test="${map.topmenu == 'authHistory_mng'}">class="on"</c:if>><a href="/${map.loanId}/authHistory/calculate">정산관리</a></li>
					<%-- <li id="result_mng" <c:if test="${map.topmenu == 'result_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistoryManage_date">내역조회</a></li> --%>
					<li id="members_mng" <c:if test="${map.topmenu == 'members_mng'}">class="on"</c:if>><a href="/${map.loanId}/members/meritz">회원사전용</a></li>
				</c:if>
			</ul>
		</div>
		<div class="navigation_warp">
			<div class='tri'></div>
			<div class="navigation_list" id="admin_mng_nav">
				<ul>
					<li <c:if test="${map.submenu == 'notice'}">class="on"</c:if>><a href="/${map.loanId}/admin/notice">공지사항</a></li>
					<c:if test="${map.admin_level == 1}">
						<li <c:if test="${map.submenu == 'company_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/company_mng">회사 관리</a></li>
						<li <c:if test="${map.submenu == 'company_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/user_mng">계정 관리</a></li>
					</c:if>
				</ul>
			</div>
			<div class="navigation_list" id="authHistory_mng_nav">
				<ul>
					<li <c:if test="${map.submenu == 'calculate'}">class="on"</c:if>><a href="/${map.loanId}/authHistory/calculate">인증내역 정산</a></li>
					<li <c:if test="${map.submenu == 'history'}">class="on"</c:if>><a href="/${map.loanId}/authHistory/history">인증내역 조회</a></li>
				</ul>
			</div>
			<div class="navigation_list" id="service_mng_nav">
				<ul>
					<c:if test="${map.admin_level == 1}">
						<li <c:if test="${map.submenu == 'domainManage'}">class="on"</c:if>><a href="/${map.loanId}/service/domainManage">서비스 등록</a></li>
					</c:if>
					<li <c:if test="${map.submenu == 'serviceManage'}">class="on"</c:if>><a href="/${map.loanId}/service/serviceManage">서비스 관리</a></li>
					<li <c:if test="${map.submenu == 'customerChargeManage'}">class="on"</c:if>><a href="/${map.loanId}/service/customerChargeManage">고객(요금)관리</a></li>
					<li <c:if test="${map.submenu == 'blockUser'}">class="on"</c:if>><a href="/${map.loanId}/service/blockUser">차단 관리</a></li>
				</ul>
			</div>
			<div class="navigation_list" id="result_mng_nav">
				<ul>
					<li <c:if test="${map.submenu == 'accountAuthHistoryManage'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistoryManage_date">인증내역 관리(X)</a></li>
					<li <c:if test="${map.submenu == 'accountAuthHistoryCheck'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistoryCheck">인증내역 조회(X)</a></li>		
				</ul>
			</div>
			<div class="navigation_list" id="members_mng_nav">
				<ul>
					<li <c:if test="${map.submenu == 'meritz'}">class="on"</c:if>><a href="/${map.loanId}/members/meritz">메리츠</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>


<div class="layerpop_wrap new_password_layerpop" style="display: none;">
	<div class="layerpop_mask"></div>
	<div class="layerpop" style="width: 330px; top:200px;">
		<div class="layer_content">
			<!-- wrt_table01 -->
			<div class="wrt_table01">
				<form>
					<input class="pass_seq" type="hidden" name="" value="">
					<div class="pass_title_wrap">
	<!-- 					<input class="pass_title" type="text" name="" value="공지 제목을 입력하세요."> -->
						<div class="pass_title" contenteditable="false">로그인 비밀번호 변경</div>
					</div>
					
					<div class="pass_body_wrap">
						<div class="pass_body" contenteditable="false">
							<input type="password" class="pass_input" id="now_pw" placeholder="현재 비밀번호" value="">
							<input type="password" class="pass_input" id="new_pw" placeholder="새 비밀번호" value="">
							<input type="password" class="pass_input" id="conf_pw" placeholder="새 비밀번호 확인" value="">
						</div>
						<br>
						<p id="pass_ment">비밀번호는 영문, 숫자, 특수문자를 포함하여 8자리 이상으로 설정해 주세요.</p>
						<br>
						<div id="recapchaWidget" class="g-recaptcha"></div>
					</div>
				</form>
			</div>
			<!-- //wrt_table01 -->
			<hr>
			<div class="layer_btnbox center">
				<button type="button" class="btn_st_normal bt03" id="pass_confirm_btn"><span>수정</span></button>
				<button type="button" class="btn_st_normal bt03" id="pass_cancel_btn"><span>닫기</span></button>
			</div>
		</div>

	</div>
</div>