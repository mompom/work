package net.ib.paperless.spring.domain;

import java.util.List;

public class AccountAuthHistoryInfo {
	private int seq;
	private String company_id;
	private String company_name;
	private String req_code;
	private String account_holder_name;
	private String account_bank_code;
	private String account_bank_name;
	private String account_holder_number;
	
	private String rsp_message;
	private String bank_rsp_message;
	private String bank_rsp_name;
	private char replace_yn;
	private String account_type;
	private String account_ssn_number;
	private String account_sex;
	
	private int customer_service_seq;
	private int service_type;
	private String service_name;
	private String service_code;
	private String customer_service_name;
	private String inquiry_yn;
	private String inquiry_date;
	
	private char inquiry_realname_yn;
	private String inquiry_realname_date;
	
	private char inquiry_transfer_deposit_yn;
	private String inquiry_transfer_deposit_date;
	
	private char authcode_confirm_yn;
	private String authcode_confirm_date;
	
	private char jumin_realname_yn;
	private String jumin_realname_date;
	
	private String using_platform;
	
	private int totalCnt;
	private int bsCnt;
	private int opCnt;
	private int nhCnt;
	private int inquiry_count;
	private int inquiry_total_count;
	private int inquiry_realname_count;
	private int inquiry_realname_count_y;
	private int inquiry_realname_count_n;
	private int inquiry_transfer_count;
	private int inquiry_transfer_count_y;
	private int inquiry_transfer_count_n;
	
	
	public int getInquiry_total_count() {
		return inquiry_total_count;
	}
	public void setInquiry_total_count(int inquiry_total_count) {
		this.inquiry_total_count = inquiry_total_count;
	}
	public int getInquiry_realname_count_y() {
		return inquiry_realname_count_y;
	}
	public void setInquiry_realname_count_y(int inquiry_realname_count_y) {
		this.inquiry_realname_count_y = inquiry_realname_count_y;
	}
	public int getInquiry_realname_count_n() {
		return inquiry_realname_count_n;
	}
	public void setInquiry_realname_count_n(int inquiry_realname_count_n) {
		this.inquiry_realname_count_n = inquiry_realname_count_n;
	}
	public int getInquiry_transfer_count_y() {
		return inquiry_transfer_count_y;
	}
	public void setInquiry_transfer_count_y(int inquiry_transfer_count_y) {
		this.inquiry_transfer_count_y = inquiry_transfer_count_y;
	}
	public int getInquiry_transfer_count_n() {
		return inquiry_transfer_count_n;
	}
	public void setInquiry_transfer_count_n(int inquiry_transfer_count_n) {
		this.inquiry_transfer_count_n = inquiry_transfer_count_n;
	}
	public String getBank_rsp_name() {
		return bank_rsp_name;
	}
	public void setBank_rsp_name(String bank_rsp_name) {
		this.bank_rsp_name = bank_rsp_name;
	}
	public char getReplace_yn() {
		return replace_yn;
	}
	public void setReplace_yn(char replace_yn) {
		this.replace_yn = replace_yn;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getAccount_ssn_number() {
		return account_ssn_number;
	}
	public void setAccount_ssn_number(String account_ssn_number) {
		this.account_ssn_number = account_ssn_number;
	}
	public String getAccount_sex() {
		return account_sex;
	}
	public void setAccount_sex(String account_sex) {
		this.account_sex = account_sex;
	}
	public int getService_type() {
		return service_type;
	}
	public void setService_type(int service_type) {
		this.service_type = service_type;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public int getBsCnt() {
		return bsCnt;
	}
	public void setBsCnt(int bsCnt) {
		this.bsCnt = bsCnt;
	}
	public int getOpCnt() {
		return opCnt;
	}
	public void setOpCnt(int opCnt) {
		this.opCnt = opCnt;
	}
	public int getNhCnt() {
		return nhCnt;
	}
	public void setNhCnt(int nhCnt) {
		this.nhCnt = nhCnt;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public char getInquiry_realname_yn() {
		return inquiry_realname_yn;
	}
	public void setInquiry_realname_yn(char inquiry_realname_yn) {
		this.inquiry_realname_yn = inquiry_realname_yn;
	}
	public char getInquiry_transfer_deposit_yn() {
		return inquiry_transfer_deposit_yn;
	}
	public void setInquiry_transfer_deposit_yn(char inquiry_transfer_deposit_yn) {
		this.inquiry_transfer_deposit_yn = inquiry_transfer_deposit_yn;
	}
	public char getAuthcode_confirm_yn() {
		return authcode_confirm_yn;
	}
	public void setAuthcode_confirm_yn(char authcode_confirm_yn) {
		this.authcode_confirm_yn = authcode_confirm_yn;
	}
	public char getJumin_realname_yn() {
		return jumin_realname_yn;
	}
	public void setJumin_realname_yn(char jumin_realname_yn) {
		this.jumin_realname_yn = jumin_realname_yn;
	}
	public String getJumin_realname_date() {
		return jumin_realname_date;
	}
	public void setJumin_realname_date(String jumin_realname_date) {
		this.jumin_realname_date = jumin_realname_date;
	}
	public String getUsing_platform() {
		return using_platform;
	}
	public void setUsing_platform(String using_platform) {
		this.using_platform = using_platform;
	}
	public int getInquiry_count() {
		return inquiry_count;
	}
	public void setInquiry_count(int inquiry_count) {
		this.inquiry_count = inquiry_count;
	}
	public int getInquiry_realname_count() {
		return inquiry_realname_count;
	}
	public void setInquiry_realname_count(int inquiry_realname_count) {
		this.inquiry_realname_count = inquiry_realname_count;
	}
	public int getInquiry_transfer_count() {
		return inquiry_transfer_count;
	}
	public void setInquiry_transfer_count(int inquiry_transfer_count) {
		this.inquiry_transfer_count = inquiry_transfer_count;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getReq_code() {
		return req_code;
	}
	public void setReq_code(String req_code) {
		this.req_code = req_code;
	}
	public String getAccount_holder_name() {
		return account_holder_name;
	}
	public void setAccount_holder_name(String account_holder_name) {
		this.account_holder_name = account_holder_name;
	}
	public String getAccount_bank_code() {
		return account_bank_code;
	}
	public void setAccount_bank_code(String account_bank_code) {
		this.account_bank_code = account_bank_code;
	}
	public String getAccount_bank_name() {
		return account_bank_name;
	}
	public void setAccount_bank_name(String account_bank_name) {
		this.account_bank_name = account_bank_name;
	}
	public String getAccount_holder_number() {
		return account_holder_number;
	}
	public void setAccount_holder_number(String account_holder_number) {
		this.account_holder_number = account_holder_number;
	}
	public String getInquiry_realname_date() {
		return inquiry_realname_date;
	}
	public void setInquiry_realname_date(String inquiry_realname_date) {
		this.inquiry_realname_date = inquiry_realname_date;
	}
	public String getInquiry_transfer_deposit_date() {
		return inquiry_transfer_deposit_date;
	}
	public void setInquiry_transfer_deposit_date(String inquiry_transfer_deposit_date) {
		this.inquiry_transfer_deposit_date = inquiry_transfer_deposit_date;
	}
	public String getAuthcode_confirm_date() {
		return authcode_confirm_date;
	}
	public void setAuthcode_confirm_date(String authcode_confirm_date) {
		this.authcode_confirm_date = authcode_confirm_date;
	}
	public String getRsp_message() {
		return rsp_message;
	}
	public void setRsp_message(String rsp_message) {
		this.rsp_message = rsp_message;
	}
	public String getBank_rsp_message() {
		return bank_rsp_message;
	}
	public void setBank_rsp_message(String bank_rsp_message) {
		this.bank_rsp_message = bank_rsp_message;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getCustomer_service_seq() {
		return customer_service_seq;
	}
	public void setCustomer_service_seq(int customer_service_seq) {
		this.customer_service_seq = customer_service_seq;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getCustomer_service_name() {
		return customer_service_name;
	}
	public void setCustomer_service_name(String customer_service_name) {
		this.customer_service_name = customer_service_name;
	}
	public String getInquiry_yn() {
		return inquiry_yn;
	}
	public void setInquiry_yn(String inquiry_yn) {
		this.inquiry_yn = inquiry_yn;
	}
	public String getInquiry_date() {
		return inquiry_date;
	}
	public void setInquiry_date(String inquiry_date) {
		this.inquiry_date = inquiry_date;
	}
}