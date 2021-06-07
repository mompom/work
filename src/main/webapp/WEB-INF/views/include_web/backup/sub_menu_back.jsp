<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${map.topmenu == 'status'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'loan'}">class="on"</c:if>><a href="/${map.loanId}/status/loan">대출 업무</a></li>
		<li <c:if test="${map.submenu == 'counsel'}">class="on"</c:if>><a href="/${map.loanId}/status/counsel">상담 신청</a></li>
	</ul>
</div>
</c:if>
<c:if test="${map.topmenu == 'admin_mng'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'notice'}">class="on"</c:if>><a href="/${map.loanId}/admin_mng/notice">공지사항</a></li>
		<li <c:if test="${map.submenu == 'loan'}">class="on"</c:if>><a href="#">회사 관리</a></li>
		<li <c:if test="${map.submenu == 'prod_mng'}">class="on"</c:if>><a href="/${map.loanId}/admin_mng/prod_mng">상품(문서) 관리</a></li>
	</ul>
</div>
</c:if>

<!-- 18.04.26 김웅희 -->
<c:if test="${map.topmenu == 'authHistory_mng'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'accountAuthHistory'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistory">계좌 인증내역</a></li>
		<!-- li <c:if test="${map.submenu == 'temp1'}">class="on"</c:if>><a href="#">실명 인증 내역</a></li>
		<li <c:if test="${map.submenu == 'temp2'}">class="on"</c:if>><a href="#">계정 인증 내역</a></li-->
	</ul>
</div>
</c:if>

<c:if test="${map.topmenu == 'confirm_mng'}">
<div class="sub_menu">
	<ul>
		<li <c:if test="${map.submenu == 'domainManage'}">class="on"</c:if>><a href="/${map.loanId}/admin/domainManage">도메인 관리</a></li>
		<li <c:if test="${map.submenu == 'accountAuthHistoryManage'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistoryManage_date">인증내역 관리</a></li>
		<li <c:if test="${map.submenu == 'accountAuthHistoryCheck'}">class="on"</c:if>><a href="/${map.loanId}/admin/accountAuthHistoryCheck">인증내역 조회</a></li>
		
	</ul>
</div>
</c:if>