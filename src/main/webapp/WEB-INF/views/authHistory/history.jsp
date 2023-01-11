<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>굿페이퍼 비대면 서비스 관리 어드민</title>
	
	<%@ include file="/WEB-INF/views/include_web/header_src.jsp"%>
	
	<script type="text/javascript" src="/static/include/js/Chart.bundle.js"></script>
	<script type="text/javascript" src="/static/include/js/Chart.bundle.utils.js"></script>
	<script type="text/javascript" src="/static/include/js/ng-table-excel-export.js"></script>
	<script type="text/javascript">

	function updateChart(obj, barChartData){
		var color = Chart.helpers.color;
		barChartData.labels = [];
		barChartData.datasets = [/* {
			label: '실명인증 사용건수',
			data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
			backgroundColor: color('#8fc050').alpha(0.5).rgbString(),
			borderColor: window.chartColors.black,
			borderWidth: 0
		}, */{
			label: '실명인증 성공건수',
			data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
			backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
// 			backgroundColor: color('#ff8438').alpha(0.5).rgbString(),
			borderColor: window.chartColors.black,
			borderWidth: 0
		},/* {
			label: '계좌인증 사용건수',
			data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
			backgroundColor: color('#8fc050').alpha(0.5).rgbString(),
			borderColor: window.chartColors.black,
			borderWidth: 0
		}, */{
			label: '계좌인증 성공건수',
			data: [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
			backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
			borderColor: window.chartColors.black,
			borderWidth: 0
		}];
		$('#lst_table_middle tbody tr').each(function(k,v){
			barChartData.datasets[0].data[k] = Number($(this).find('td:eq(5)').text());
			barChartData.datasets[1].data[k] = Number($(this).find('td:eq(8)').text());
			/* barChartData.datasets[2].data[k] = Number($(this).find('td:eq(7)').text());
			barChartData.datasets[3].data[k] = Number($(this).find('td:eq(8)').text()); */
			barChartData.labels[k] = $(this).find('td:eq(1)').text();
		});
		window.myBar.update();
	}
	
	function initChart(){
		var barChartData = {};
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
					display: false,
					text: '계좌인증내역 통계'
				},
				scales: {
			        xAxes: [{
			            barThickness : 30
			        }]
			    }
			}
		});
		return barChartData;
	}
	
	function viewPage(){
		accountAuthHistory.history.view();
	}
	
	var accountAuthHistory;
	var companyManage;
	var layerManage;
	var serviceManage;
	
	var chartData;
	
	$(document).ready(function(){
		chartData = initChart();
		
		accountAuthHistory = new AccountAuthHistory;
		companyManage = new CompanyManage;
		layerManage = new LayerManage;
		serviceManage = new ServiceManage;
		
		var level = "<c:out value='${map.admin_level}'/>";
		accountAuthHistory.view(level);
		
		$('#historyPublic').scroll(function(e){
			$('#historyPublicTitle').scrollLeft($(this).scrollLeft());
		});
		
	});
	
	$(document).on('change', '#pageSize', function(){
		$("#pageNo").val('1');
		accountAuthHistory.history.view();
	});
	
	$(document).on('change', '#year_select', function(){
		$("#pageNo").val('1');

		var company_id = $('#defaultSearchCompany').children("option:selected").attr('id');
		$('#company_id').val(company_id);
		accountAuthHistory.history.view();
		accountAuthHistory.charge.view();
	});
	
	$(document).on('change', '#month_select', function(){
		$("#pageNo").val('1');
		
		var company_id = $('#defaultSearchCompany').children("option:selected").attr('id');
		$('#company_id').val(company_id);
		accountAuthHistory.history.view();
		accountAuthHistory.charge.view();
	});
	
	$(document).on('change', '#defaultSearchCompany', function(){
		$("#pageNo").val('1');
		var company_id = $(this).children("option:selected").attr('id');
		$('#company_id').val(company_id);
		accountAuthHistory.history.view();
		accountAuthHistory.charge.view();
	});

	function test(list, st, ed, index){
		if(ed > list.length){
			ed = list.length;
		}
		let csvContent = "";
		var rows = [];
		rows[0] = ['순번', '업체명', '요청번호', '고객명', '계좌번호', '은행명', '실명확인여부', '실명확인요청일시', '입금이체성공여부', '입금이체요청일시', '인증완료여부' , '인증완료일시', '사용플랫폼', '금결원결과메시지', '개별은행결과메시지', '은행명'];
		for(var i = st; i<ed; i++){
			var v = list[i];
			stateNo = list.length - i;
			rows[i+1] = [
				stateNo,
				v.company_name,
				"=\""+v.req_code+"\"",
				v.account_holder_name,
				"=\""+v.account_holder_number+"\"",
				v.account_bank_name,
				v.inquiry_realname_yn,
				v.inquiry_realname_date,
				v.inquiry_transfer_deposit_yn,
				v.inquiry_transfer_deposit_date,
				v.authcode_confirm_yn,
				v.authcode_confirm_date,
				v.using_platform,
				v.rsp_message,
				v.bank_rsp_message,
				v.bank_rsp_name
			];
			
		}
		rows.forEach(function(rowArray){
		   let row = rowArray.join(",");
		   csvContent += row + "\r\n";
		}); 
		
		var encodedUri = encodeURI(csvContent);
		var link = document.createElement("a");
		link.setAttribute("href", 'data:text/csv;charset=utf-8,\uFEFF'+encodedUri);
		link.setAttribute("download", $('#historyPublicTitleText').text() + "(" + index + ").csv");
		document.body.appendChild(link); 

		link.click();
		link.remove();
	}
	
	$(document).on('click', '#excelExport', function(){
		var excelExport = this;
		
		var company_id = $('#company_id').val();
		var pageSize = $("#pageSize option:selected").val();
		var pageNo = $("#pageNo").val();
		
		var year = $('#year_select').val();
		var month = $('#month_select').val();
		
		var searchOption1 = $("#search_option1").val();
		var searchOption2 = $(".search_option2").val();
		var searchOption3 = $(".search_option3").val();
		var searchWord = $(".search_word").val();
		
		console.log(pageSize);
		var params = {
			"pageNo": 1, 
			"pageSize": 100000000,
			"sdate":year+""+month,
			"edate":year+""+month,
			"company_id":company_id,
			"searchOption1" : searchOption1,
			"searchOption2" : searchOption2,
			"searchOption3" : searchOption3,
			"searchWord" : searchWord
		};
		console.log(params)
		$('body').loading();
		ajaxGet("/${map.loanId}/api/historyPublicSelect", params, function(response){
			
			var stateNo = 0;
			var innerHtml = "";
			var cnt = 0;
			/* if (response.result) {
				var listLen = response.list.length;
				var a = listLen/8000;
				var b = 8000;
				for(var i = 0; i<=a; i++){
					test(response.list, b*i, b*(i+1), i);
				}
			}
			$('body').loading('stop');
			 */
			 var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
			 
			if (response.result) {
				var list = response.list;
				let csvContent = "";
				var rows = [];
				rows[0] = ['순번', '업체명', '요청번호', '고객명', '계좌번호', '은행명', '실명확인여부', '실명확인요청일시', '입금이체성공여부', '입금이체요청일시', '인증완료여부' , '인증완료일시', '사용플랫폼', '금결원결과메시지', '개별은행결과메시지', '은행명'];
				for(var i = 0; i<list.length; i++){
					var v = list[i];
					stateNo = list.length - i;
					rows[i+1] = [
						stateNo,
						v.company_name,
						"=\""+v.req_code+"\"",
						v.account_holder_name,
						"=\""+v.account_holder_number+"\"",
						v.account_bank_name,
						v.inquiry_realname_yn,
						v.inquiry_realname_date,
						v.inquiry_transfer_deposit_yn,
						v.inquiry_transfer_deposit_date,
						v.authcode_confirm_yn,
						v.authcode_confirm_date,
						v.using_platform,
						v.rsp_message.replace(/\n/g,'').replace(/\,/g,'_').replace(/\/t/g,' '),
						v.bank_rsp_message,
						v.bank_rsp_name
					];
					
				}
				rows.forEach(function(rowArray){
				   let row = rowArray.join(",");
				   csvContent += row + "\r\n";
				}); 
				
				var today = new Date();
				var dd = String(today.getDate()).padStart(2, '0');
				var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
				var yyyy = today.getFullYear();

				today = yyyy+mm+dd;
				
				var fileName = $('#historyPublicTitleText').text() + "_"+today+".csv";
				var csvData = csvContent;
		        var blob = new Blob([ '\uFEFF' + csvData ], {
		            type : "application/csv;charset=utf-8;"
		        });

		        if (window.navigator.msSaveBlob) {
		            // FOR IE BROWSER
		            navigator.msSaveBlob(blob, fileName);
		        } else {
		            // FOR OTHER BROWSERS
		            var link = document.createElement("a");
		            var csvUrl = URL.createObjectURL(blob);
		            link.href = csvUrl;
		            link.style = "visibility:hidden";
		            link.download = fileName;
		            document.body.appendChild(link);
		            link.click();
		            document.body.removeChild(link);
		        }
		        
		        /* 
				var encodedUri = encodeURI(csvContent);
				var link = document.createElement("a");
				link.setAttribute("href", 'data:text/csv;charset=utf-8,\uFEFF'+encodedUri);
				link.setAttribute("download", $('#historyPublicTitleText').text() + ".csv");
				document.body.appendChild(link); 
	
				link.click();
				link.remove(); */
				$('body').loading('stop');
			}
		}, params); 
		/*
		ajaxGet("/${map.loanId}/api/historyPublicSelect", params, function(response){
			var stateNo = 0;
			var innerHtml = "";
			
			if (response.result) {
				var listLen = response.list.length;
				var historyPublicTable = document.createElement('table');
				var colgroup = $('#historyTable colgroup').html();
				var thead = $('#historyTable thead').html();
				var tbody = "";
				var inClass = "class='border_right'";
				var inStyle = "style='text-align:center;'";
				for(var i = 0; i<listLen; i++){
					var v = response.list[i];
					stateNo = listLen - i;
					tbody += 
					"<tr>"+
						"<td "+inClass+" "+inStyle+">"+stateNo+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.company_name+"</td>"+
						"<td "+inClass+" style='text-align:center; mso-number-format:\"###\";'>"+v.req_code+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.account_holder_name+"</td>"+
						"<td "+inClass+" style='text-align:center; mso-number-format:\"###\";'>"+v.account_holder_number+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.account_bank_name+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.inquiry_realname_yn+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.inquiry_realname_date+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.inquiry_transfer_deposit_yn+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.inquiry_transfer_deposit_date+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.authcode_confirm_yn+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.authcode_confirm_date+"</td>"+
						
						"<td "+inClass+" "+inStyle+">"+v.using_platform+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.rsp_message+"</td>"+
						"<td "+inClass+" "+inStyle+">"+v.bank_rsp_message+"</td>"+
						"<td "+inStyle+">"+v.bank_rsp_name+"</td>"+
					"</tr></tbody>";
				}
				innerHtml += "<colgroup>" + colgroup + "</colgroup>";
				innerHtml += "<thead>"+ thead + "</thead>";
				innerHtml += "<tbody>"+ tbody + "</tbody>";
				
				$(historyPublicTable).html(innerHtml).after(function(){
					ngTableExcelExport.excel(excelExport, historyPublicTable, $('#historyPublicTitleText').text()+"_"+(new Date().toLocaleString()));
				});
			}
		}, params); 
			 */
	});
	
	$(document).on('click', '.checkbox', function(){
		$("#pageNo").val('1');
		accountAuthHistory.topArea.checkbox(this);
	});
	
	$(document).on('click', 'span.company_name, span.company_all', function(){
		var company_id = $(this).next().val();
		$("#pageNo").val('1');
		$('#company_id').val(company_id);
		$('#historyPublicTitleText').text('계좌인증내역_'+$(this).text());
		accountAuthHistory.history.getCount(accountAuthHistory.history.countView);
		//accountAuthHistory.history.getData(accountAuthHistory.history.dataView);
	});
	
	$(document).on('click', '#lst_chart_display', function(){
		if($("#lst_chart").css("display") != "none") {
			$('#lst_chart').slideUp(500);
		}else if($("#lst_chart").css("display") == "none"){
			$('#lst_chart').slideDown(500);
		}
		
	});
	
	$(document).on('click', '#expend', function(){
		if($('#container').width() > 1024){
			$('#container').animate({
				width: '1024px'
			}, {duration: 1000,
				easing: "easeOutExpo"
				}, function(){
				
			});
		}else{
			$('#container').animate({
				width: '99.9%'
			}, {duration: 1000,
				easing: "easeOutExpo"
				}, function(){
				
			});
		}
	});
	
	function search(){
		
		var searchWord = $(".search_word").val();
		/*
		if(searchWord == "")
		{
			alert('검색어를 입력해 주세요.')
			return;
		}
		*/
		viewPage();
	}
	</script>
	</head>
	<body>
		<div id="wrap">
			<!-- header -->
			<%@ include file="/WEB-INF/views/include_web/header_top.jsp"%>
				<!-- hidden -->
				<input type="hidden" id="pageNo" value="1" />
				<input type="hidden" id="inquiry_realname_count" value="0" />
				<input type="hidden" id="inquiry_transfer_count" value="0" />
				<input type="hidden" id="company_id" value="" />
			<!-- //header -->
		
			<!-- container -->
			<div id="container">
				<!-- sub menu -->
				<%@ include file="/WEB-INF/views/include_web/sub_menu.jsp"%>
				<!-- sub search -->
				<br>
				<div class="top_area clearbox" id="lst_table_middle_title">
					<div class="lf_box">
						<strong class="sub_title">이용기간 조회</strong>
					</div>
				</div>
				<div class="lst_table01 option_table">
					<table class="table center">
						<caption>정신내역 옵션</caption>
		                <colgroup>
			                <col style="width:10%;">
			                <col style="width:auto;">
		                </colgroup>
						<tbody>
							<tr>
								<th>
								업체선택
								</th>
								<td class="left">
									<select id="defaultSearchCompany" class="select width_202">
										<c:if test="${map.admin_level eq 3 }">
											<option value="-">선택</option>
										</c:if>
										<c:if test="${map.admin_level ne 3 }">
											<option value="">전체</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<th>이용기간</th>
								<td class="left">
									<select id="year_select" class="select"></select>
									<select id="month_select" class="select"></select>
									<!-- <button type="button" class="btn_st_normal bt01" id="service_add_btn"><span>검색</span></button> -->
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<br><br>
				
				<!-- //chart st -->
				<div class="top_area clearbox">
					<div class="lf_box">
						<strong class="sub_title cursor_pointer" id="lst_chart_display">계좌인증 통계그래프</strong>
					</div>
				</div>
				<div class="center" id="lst_chart" style="width:100%; overflow: auto; margin: 0px; background-color: #fff; box-shadow: 0 0.46875rem 2.1875rem rgba(4,9,20,0.03), 0 0.9375rem 1.40625rem rgba(4,9,20,0.03), 0 0.25rem 0.53125rem rgba(4,9,20,0.05), 0 0.125rem 0.1875rem rgba(4,9,20,0.03);">
					<canvas id="chart"></canvas>
				</div>
				<br><br>
				<!-- //chart ed -->
						
				<!-- //lst_table_middle_title -->
				<div class="top_area clearbox" id="lst_table_middle_title">
					<div class="lf_box">
						<strong class="sub_title">계좌인증 고객사별</strong>
						<br>
						<strong class="txt">사용플랫폼 : nh (농협API) , op (금결원 오픈플랫폼)</strong>
					</div>
				</div>
				<!-- lst_table_middle -->
				<div class="lst_table01" id="lst_table_middle">
					<table class="table center">
		                <caption>계좌 인증 내역</caption>
		                <colgroup>
		                	<col style="width: 34px;">
		                	<col style="width: 200px;">
		                	<col style="width: 200px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                	<col style="width: 100px;">
		                </colgroup>
		                <thead>
							<tr>
								<!-- th class="num"><input type="checkbox" name="prod_all" id="prod_all"></th-->
								<th class="border_right">고객사ID</th>
								<th class="border_right">고객사명</th>
								<th class="border_right">서비스명</th>
								<th class="border_right">사용플랫폼</th>
								<th class="border_right">실명인증<br>사용건수</th>
								<th class="border_right">실명인증<br>성공건수</th>
								<th class="border_right">실명인증<br>실패건수</th>
								<th class="border_right">계좌인증<br>사용건수</th>
								<th class="border_right">계좌인증<br>성공건수</th>
								<th>계좌인증<br>실패건수</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
							</tr>
						</tbody>												
						<tfoot>
							<tr>
								<td class=""></td>
								<td class=""><span class="cursor_pointer company_all" style="color:#00b752;">전체</span><input id="company_all_input" type="hidden" value=""></td>
								<td class="border_right"></td>
								<td class="border_right">합계</td>
								<td class="border_right inquiry_realname_sum">0</td>
								<td class="border_right inquiry_realname_sum_y" style="color:blue;">0</td>
								<td class="border_right inquiry_realname_sum_n" style="color:black;">0</td>
								<td class="border_right inquiry_transfer_sum">0</td>
								<td class="border_right inquiry_transfer_sum_y" style="color:blue;">0</td>
								<td class="inquiry_transfer_sum_n" style="color:black;">0</td>								
							</tr>
						</tfoot>
					</table>
				</div>
				
				<br><br>
				
				<!-- top btn -->
				<div class="top_area clearbox">
					<div class="lf_box">
						<strong class="sub_title" id="historyPublicTitleText">계좌인증내역_전체</strong>
						<br>
						<strong class="countTxt"></strong>
						<span style="width:100px;display:inline-block;text-align:right">보여질 페이지 수 : </span>
						<select name="pageSize" id="pageSize" class="select02" style="width:92px;">
							<option value="20">20개</option>
							<option value="50">50개</option>
							<option value="100">100개</option>
							<option value="1000000000">전체결과</option>
						</select>
						<span style="width:100px;display:inline-block;text-align:right">확인여부 : </span>
						<select name="" id="" class="select01 search_option3" style="width:120px;">
							<option value="all">전체</option> 
							<option value="y">성공</option>
							<option value="n">실패</option>
						</select>
						<select name="" id="" class="select01 search_option2" style="display:none;width:153px;">
							<option value="">이름</option>
<!-- 							<option value="">계좌</option> -->
						</select>
						<span style="width:100px;display:inline-block;text-align:right">고객명 : </span>
						<input class="search_word" type="text" name="" placeholder="검색어를 입력해 주세요.">
						
						<button type="button" class="btn_st_normal" onclick="javascript:search()">검색</button>
						<!-- <a class="btn_st_normal" id="excelExport"><span>엑셀 파일 다운로드</span></a> -->
						<button class="btn_st_normal" id="excelExport" download="전체계좌인증내역.xls">다운로드</button>
					</div>
					<div class="rf_box">
						
					</div>
				</div>
				<!-- contents -->
				<div class="contents">
					<!-- lst_table01 -->
					<div id="historyPublic" class="lst_table01" style="overflow-x: auto; overflow-y: auto; /*max-height: 760px; */ box-shadow: 0 0.46875rem 2.1875rem rgba(4,9,20,0.03), 0 0.9375rem 1.40625rem rgba(4,9,20,0.03), 0 0.25rem 0.53125rem rgba(4,9,20,0.05), 0 0.125rem 0.1875rem rgba(4,9,20,0.03);">
					<table id="historyTable" class="table center" style="width: 2360px;">
					<colgroup>
	                	<col style="width:60px;">
	                	<col style="width:95px; max_width:95px;">
	                	<col style="width:285px;">
	                	<col style="width:95px;">
	                	<col style="width:180px;">
	                	<col style="width:100px;">
	                	<col style="width:60px;">
	                	<col style="width:195px;">
	                	<col style="width:60px;">
	                	<col style="width:195px;">
	                	<col style="width:60px;">
	                	<col style="width:195px;">
	                	<col style="width:60px;">
	                	<col style="width:285px;">
	                	<col style="width:285px;">
	                	<col style="width:150px;">
	                </colgroup>
	                <thead>
						<tr style="border-bottom:1px solid #dbdbdb;">
							<th class="border_right">순번</th>
							<th class="border_right">업체명</th>
							<th class="border_right">요청번호</th>
							<th class="border_right">고객명</th>
							<th class="border_right">계좌번호</th>
							<th class="border_right">은행명</th>
							<th class="border_right">실명확인<br>여부</th>
							<th class="border_right">실명확인<br>요청일시</th>
							<th class="border_right">입금이체<br>성공여부</th>
							<th class="border_right">입금이체<br>요청일시</th>
							<th class="border_right">인증완료<br>여부</th>
							<th class="border_right">인증완료<br>일시</th>
							<th class="border_right">사용<br>플랫폼</th>
							<th class="border_right">금결원<br>결과메시지</th>
							<th class="border_right">개별은행<br>결과메시지</th>
							<th>은행명</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					</table>
					</div>
					<!-- //lst_table01 -->
				</div>
				<!-- paging -->
				<div class="paging" id="paging"></div>
				<!-- //paging -->
				
				<br><br>
				
				<!-- top btn -->
			</div>
			<!-- //container -->
			
			<%@ include file="/WEB-INF/views/include_web/footer.jsp"%>
		</div>
	</body>
</html>