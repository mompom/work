<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${map.topmenu == 'admin_mng'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'notice'}">class="on"</c:if>><a href="/${map.loanId}/admin/notice">공지사항</a></li>
		<li <c:if test="${map.submenu == 'company_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/company_mng">회사 관리</a></li>
		<li <c:if test="${map.submenu == 'user_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin/user_mng">계정 관리</a></li>
	</ul>
</div>
</c:if>

<!-- 18.04.26 김웅희 -->
<c:if test="${map.topmenu == 'calculate_mng'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'calculate'}">class="on"</c:if>><a href="/${map.loanId}/authHistory/calculate">인증내역 정산</a></li>
		<li <c:if test="${map.submenu == 'history'}">class="on"</c:if>><a href="/${map.loanId}/authHistory/history">인증내역 조회</a></li>
	</ul>
</div>
</c:if>

<c:if test="${map.topmenu == 'service_mng'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'domainManage'}">class="on"</c:if>><a href="/${map.loanId}/service/domainManage">서비스 등록</a></li>
		<li <c:if test="${map.submenu == 'serviceManage'}">class="on"</c:if>><a href="/${map.loanId}/service/serviceManage">서비스 관리</a></li>
		<li <c:if test="${map.submenu == 'customerChargeManage'}">class="on"</c:if>><a href="/${map.loanId}/service/customerChargeManage">고객(요금)관리</a></li>
		<li <c:if test="${map.submenu == 'blockUser'}">class="on"</c:if>><a href="/${map.loanId}/service/blockUser">차단 관리</a></li>
	</ul>
</div>
</c:if>

<c:if test="${map.topmenu == 'result_mng'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'accountAuthHistoryManage'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistoryManage_date">인증내역 관리(X)</a></li>
		<li <c:if test="${map.submenu == 'accountAuthHistoryCheck'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistoryCheck">인증내역 조회(X)</a></li>
	</ul>
</div>
</c:if>

<c:if test="${map.topmenu == 'members_mng'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'members'}">class="on"</c:if>><a href="/${map.loanId}/members/members">회원사전용</a></li>
		<li <c:if test="${map.submenu == 'meritz'}">class="on"</c:if>><a href="/${map.loanId}/members/meritz">메리츠</a></li>
	</ul>
</div>
</c:if>