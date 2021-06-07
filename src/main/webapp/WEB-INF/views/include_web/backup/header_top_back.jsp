<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="header">
	<div class="top_cont">
		<h1><a href="#">ABC캐피탈 <span>비대면 대출 신청 서비스 어드민</span></a></h1>
		<div class="top_util">
			<form action="${base}/logout" method="post">
				<span class="user">${map.level_name} ${map.admin_name}님이 로그인했습니다.</span>
				<button type="submit" class="btn_st bt01"><span>LOGOUT</span></button>
			</form>
		</div>
		<div class="gnb">
			<ul>
				<c:if test="${map.admin_level == 9}">
					<li <c:if test="${map.topmenu == 'status'}">class="on"</c:if>><a href="/${map.loanId}/status/loan">진행 현황</a></li>
					<li <c:if test="${map.topmenu == 'stats'}">class="on"</c:if>><a href="/${map.loanId}/stats/id">통계</a></li>
					<li <c:if test="${map.topmenu == 'user_mng'}">class="on"</c:if>><a href="/${map.loanId}/user_mng/user_mng">사용자 관리</a></li>
					<li <c:if test="${map.topmenu == 'admin_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin_mng/prod_mng">운영 관리</a></li>
				</c:if>
				<c:if test="${map.admin_level == 1}">
					<li <c:if test="${map.topmenu == 'admin_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin_mng/prod_mng">운영관리</a></li>
					<li <c:if test="${map.topmenu == 'authHistory_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistory">정산관리</a></li>
					<li <c:if test="${map.topmenu == 'confirm_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/domainManage">서비스관리</a></li>
					<li <c:if test="${map.topmenu == 'confirm_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/domainManage">내역조회</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</div>