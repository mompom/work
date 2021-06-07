<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>인증내역 관리</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
	
	<style type="text/css">
		.lst_table01 table thead th{padding:5px 0px; color:#010101; font-weight:bold; text-align:center; border:1px solid #cacbcb; background:#f1f7fc;}
		.lst_table01 table tbody tr td{padding:5px 0px; color:#424242; text-align:center; border:1px solid #dddddd; background:#fff;}
		.dataset_td, .datasetSum_td{
			color:blue;
			cursor: pointer;
			font-weight: bold;
		}
	</style>
	
	<script type="text/javascript" src="/static/include/js/Chart.bundle.js"></script>
	<script type="text/javascript" src="/static/include/js/Chart.bundle.utils.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		setDefaultDate($("#defaultSearchDate option:selected").val());
		//setBindEvents();
		//getViewAjax();
		
		//Chart ST
		var chartData = initChart();
		
		$(".dataset_td, .datasetSum_td").bind("click", function(){
			var obj = $(this);
			updateChart(obj, chartData);
		});
		//Chart ED
		$("#defaultSearchDate").bind("change",function(){
			setDefaultDate($("#defaultSearchDate option:selected").val());	
			getViewAjax();
		});
		$("#pageSize").bind("change", function(){
			getViewAjax();
		});
		$("#sdate").bind("change", function(){
			$("#defaultSearchDate").val(0).prop("selected", true);
			$(".select01").find("strong").text("직접입력");
			getViewAjax();
		});
		$("#edate").bind("change", function(){
			$("#defaultSearchDate").val(0);
			getViewAjax();
		});
	});
	
	function updateChart(obj, chartData){
		var date = obj.text();
		var currTag = obj.next().next();
		var index = 0;
			for(var j = 0; j<chartData.datasets[index].data.length; j++){
				for(var i = 0 ; i<chartData.datasets.length; i++){
					if(currTag.is('td')){
						chartData.datasets[i].data[j]=currTag.text();
						currTag = currTag.next();
					};
				}
				index=0;
			}
		window.myBar.options.title.text = date + " 요청건수 : " + obj.next().text();
		window.myBar.update();
	}
	
	function initChart(){
		var color = Chart.helpers.color;
		var barChartData = {
			labels: ['휴대폰', '신용카드', '공인인증서', 'SMS/LMS'],
			datasets: [{
				label: '시도',
				backgroundColor: color(window.chartColors.red).alpha(1).rgbString(),
				borderColor: window.chartColors.red,
				borderWidth: 1,
				data: [
					10,
					30,
					25,
					42
				]
			}, {
				label: '성공',
				backgroundColor: color(window.chartColors.blue).alpha(1).rgbString(),
				borderColor: window.chartColors.blue,
				borderWidth: 1,
				data: [
					8,
					20,
					20,
					40
				]
			}, {
				label: '실패',
				backgroundColor: color(window.chartColors.yellow).alpha(1).rgbString(),
				borderColor: window.chartColors.yellow,
				borderWidth: 1,
				data: [
					8,
					20,
					20,
					2
				]
			}]

		};
		
		var ctx = document.getElementById('chart').getContext('2d');
		window.myBar = new Chart(ctx, {
			type: 'bar',
			//type: 'line',
			data: barChartData,
			options: {
				responsive: true,
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: '일자별 전체 통계'
				}
			}
		});
		
		var obj = $('.datasetSum_td');
		updateChart(obj, barChartData);
		return barChartData;
	}
	
	function setBindEvents(){
		var func_getViewAjax = function(){
			getViewAjax();
		}
		/* 검색버튼 
		$("#searchBtn").bind("click", func_getViewAjax); 
		*/
		$("#pageSize").bind("change", func_getViewAjax);
		$("#sdate").bind("change", func_getViewAjax);
		$("#edate").bind("change", func_getViewAjax);
		$("#searchType").bind("change", func_getViewAjax);
		$("#searchDateType").bind("change", func_getViewAjax);
		$("#searchText").bind("keyup", func_getViewAjax);
		$("#defaultSearchDate").bind("change", func_getViewAjax);
	}
	
	function getViewAjax(){
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
				<div class="sub_topsearch">
					<ul class="clearbox">
						<!-- li class="input_search"><input type="text" id="searchText" name="" placeholder="검색어를 입력하세요"></li-->
						<li>
							<select name="" id="defaultSearchDate" class="select01">
								<option value="7">최근 7일</option>
								<option value="14">최근 14일</option>
								<option value="30">최근 1개월</option>
								<option value="365">최근 1년</option>
								<option value="0">직접입력</option>
							</select>
						</li>
						<!-- li>
							<select name="" id="searchDateType" class="select01" style="width:153px;">
								<option value="realName_searchDate">실명확인 요청일시</option>
								<option value="transfer_searchDate">소액이체 요청일시</option>
								<option value="confirm_searchDate">인증완료 요청일시</option>
							</select>
						</li-->
						<li>
							<span class="sch_data"><input type="text" class="datepicker" id="sdate"></span>
							~
							<span class="sch_data"><input type="text" class="datepicker" id="edate"></span>
						</li>
					</ul>
				</div>
		
				<!-- top btn -->
				<div class="top_area clearbox">
					<div class="lf_box">
						<a type="button" class="" id="" href="/${map.loanId}/admin/accountAuthHistoryManage_date"><span>일자 별</span></a>
						/
						<a type="button" class="" id="" href="/${map.loanId}/admin/accountAuthHistoryManage_domain"><span>도메인 별</span></a>
					</div>
					<div class="rf_box">
						<select name="pageSize" id="pageSize" class="select02" style="width:92px;">
							<option value="20">20개</option>
							<option value="50">50개</option>
							<option value="100">100개</option>
							<option value="1000000000">전체결과</option>
						</select>
						<!-- <a type="button" class="btn_st bt01" id="excelExport" href=""><span>엑셀 파일 다운로드</span></a> -->
					</div> 
				</div>
				<!-- contents -->
				<div class="contents">
					<!-- lst_table01 -->
					<div>	
						<div>
						
						<!-- //chart st -->
							<div id="container" style="width: 50%;">
								<canvas id="chart"></canvas>
							</div>
							
						<!-- //chart ed -->
						</div>
						<div class="cboth tableTit">
							<h5 class="domainMtableTitle">일자 별</h5>
						</div>
						<table class="tableType01 domainMtable alignC" width="100%">
			                <caption>계좌 인증 내역</caption>
			                <colgroup>
			                	<col width="10%" />
								<col width="5%"  />
			                </colgroup>
			                <thead>
								<tr>
									<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
									<th rowspan="2">일자</th>
									<th rowspan="2">요청 <br>건수</th>
									<th colspan="3">휴대폰</th>
									<th colspan="3">신용카드</th>
									<th colspan="3">공인인증서</th>
									<th colspan="3">SMS/LMS</th>
								</tr>
								<tr>
									<th>시도</th>
									<th>성공</th>
									<th>최종<br>성공</th>
									<th>시도</th>
									<th>성공</th>
									<th>최종<br>성공</th>
									<th>시도</th>
									<th>성공</th>
									<th>최종<br>성공</th>
									<th>시도</th>
									<th>발송<br>성공</th>
									<th>발송<br>실패</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="dataset_td">2018-05-07</td>
									<td>50</td>
									<td>15</td>
									<td>15</td>
									<td>15</td>
									<td>30</td>
									<td>30</td>
									<td>30</td>
									<td>5</td>
									<td>5</td>
									<td>5</td>
									<td>0</td>
									<td>0</td>
									<td>0</td>
								</tr>
								<tr>
									<td class="dataset_td">2018-05-08</td>
									<td>107</td>
									<td>10</td>
									<td>8</td>
									<td>8</td>
									<td>30</td>
									<td>20</td>
									<td>20</td>
									<td>25</td>
									<td>20</td>
									<td>20</td>
									<td>42</td>
									<td>40</td>
									<td>2</td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td class="datasetSum_td">전체</td>
									<td>157</td>
									<td>25</td>
									<td>23</td>
									<td>23</td>
									<td>60</td>
									<td>50</td>
									<td>50</td>
									<td>30</td>
									<td>25</td>
									<td>25</td>
									<td>42</td>
									<td>40</td>
									<td>2</td>
								</tr>
							</tfoot>
						</table>
					</div>
					<!-- //lst_table01 -->
		
					<!-- paging -->
					<div class="paging" id="paging"></div>
					<!-- //paging -->
				</div>
		
			</div>
			<!-- //container -->
		
		</div>
	</body>
</html>