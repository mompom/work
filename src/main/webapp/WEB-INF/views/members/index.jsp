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
	function viewPage(){
		//view() or getData();
	}
	
	var index;
	
	$(document).ready(function(){
		index = new Index;
		
	});
	
	
	
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
				<div class="sub_topsearch"></div>
				<div class="contents"></div>
			</div>
			<!-- //container -->
			
			<!-- footer -->
			<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
			<!-- //footer -->
		</div>
	</body>
</html>