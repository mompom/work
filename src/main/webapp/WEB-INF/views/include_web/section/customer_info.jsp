<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- lst_table01 -->
<br>
<div class="top_area clearbox" id="lst_table_urlInfo_title">
	<div class="lf_box">
		<strong class="sub_title">회사 조회</strong>
	</div>
	<div class="rf_box">
	</div> 
	<div class="clearbox"></div>
</div>
<div class="lst_table01 option_table" id="lst_table_companyInfo">
	<table class="table center">
              <caption>고객 정보</caption>
              <colgroup>
              	<col width="10%">
              	<col width="30%">
              	<col width="60%">
              </colgroup>
              <thead>
		</thead>
		<tbody>
			<tr>
				<th class="subject">회사명</th>
				<td><input type='text' class="long disabled" id="company_name" value="" readonly="readonly"></td>
				<td class="al"><button type="button" class="btn_st_std bt01" id="companyList_select_btn"><span>조회</span></button></td>
			</tr>
			<tr>
				<th class="subject">성명</th>
				<td><input type='text' class="long disabled" id="employee_name" value=""></td>
				<td class="al"></td>
			</tr>
			<tr>
				<th class="subject">사무실</th>
				<td><input type='text' class="long disabled" id="employee_tel" value=""></td>
				<td class="al"></td>
			</tr>
			<tr>
				<th class="subject">연락처</th>
				<td><input type='text' class="long disabled" id="employee_phone" value=""></td>
				<td class="al">"-" 없이 숫자로 입력</td>
			</tr>
			<tr>
				<th class="subject">이메일</th>
				<td><input type='text' class="long disabled" id="employee_email" value=""></td>
				<td class="al">예 : name@korea.com</td>
			</tr>
			<tr>
				<th class="subject">확인</th>
				<td>
					<div class="pure-radiobutton">
                        <button type="button" id="companyInfoUpdate_btn" class="btn_st_std bt01 disabled"><span>수정</span></button>
                        <button type="button" id="companyInfoReset_btn" class="btn_st_std bt01 disabled"><span>취소</span></button>
                    </div>
				</td>
				<td class="al"><span id="service_insert_company_feedback" class="text_alert hide">저장을 완료하였습니다.</span></td>
			</tr>
		</tbody>
	</table>
</div>