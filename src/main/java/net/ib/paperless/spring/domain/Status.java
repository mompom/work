package net.ib.paperless.spring.domain;

public class Status {
	private int seq;
	private String id;
	private String loan_id;
	private String eform_id;
	private String user_name;
	private String tel_number;
	private String require_amount;
	private String admin_id;
	private String request_time;
	private String call_start_time;
	private String call_end_time;
	private String reg_date;
	private String modify_date;
	private int progress_status;
	private String user_key;
	private int inquiry_credit_rate;
	private int totalCnt;
	private String sdate;
	private String edate;
	private String status_name;
	private String body = "";
	
	//progress_status
	private String progress_id;
	private int call_yn;
	private String call_chk_date;
	private int eform_yn;
	private String eform_complete_date;
	private int attach_yn;
	private String attach_complete_date;
	private int account_transfer_yn;
	private String account_transfer_date;
	private int account_verify_yn;
	private String account_verify_date;
	private int identity_chk_yn;
	private String identity_chk_date;
	
	private String account_holder_name;
	private String account_holder_number;
	private String account_yn;
	private String account_regdate;
	
	//eform_info
	private String name;
	
	private String bank="";
	private String account="";
	private String jumin="";
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}
	public String getEform_id() {
		return eform_id;
	}
	public void setEform_id(String eform_id) {
		this.eform_id = eform_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTel_number() {
		return tel_number;
	}
	public void setTel_number(String tel_number) {
		this.tel_number = tel_number;
	}
	public String getRequire_amount() {
		return require_amount;
	}
	public void setRequire_amount(String require_amount) {
		this.require_amount = require_amount;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getRequest_time() {
		return request_time;
	}
	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}
	public String getCall_start_time() {
		return call_start_time;
	}
	public void setCall_start_time(String call_start_time) {
		this.call_start_time = call_start_time;
	}
	public String getCall_end_time() {
		return call_end_time;
	}
	public void setCall_end_time(String call_end_time) {
		this.call_end_time = call_end_time;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getModify_date() {
		return modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}
	public int getProgress_status() {
		return progress_status;
	}
	public void setProgress_status(int progress_status) {
		this.progress_status = progress_status;
	}
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	public int getInquiry_credit_rate() {
		return inquiry_credit_rate;
	}
	public void setInquiry_credit_rate(int inquiry_credit_rate) {
		this.inquiry_credit_rate = inquiry_credit_rate;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getProgress_id() {
		return progress_id;
	}
	public void setProgress_id(String progress_id) {
		this.progress_id = progress_id;
	}
	public int getCall_yn() {
		return call_yn;
	}
	public void setCall_yn(int call_yn) {
		this.call_yn = call_yn;
	}
	public String getCall_chk_date() {
		return call_chk_date;
	}
	public void setCall_chk_date(String call_chk_date) {
		this.call_chk_date = call_chk_date;
	}
	public int getEform_yn() {
		return eform_yn;
	}
	public void setEform_yn(int eform_yn) {
		this.eform_yn = eform_yn;
	}
	public String getEform_complete_date() {
		return eform_complete_date;
	}
	public void setEform_complete_date(String eform_complete_date) {
		this.eform_complete_date = eform_complete_date;
	}
	public int getAttach_yn() {
		return attach_yn;
	}
	public void setAttach_yn(int attach_yn) {
		this.attach_yn = attach_yn;
	}
	public String getAttach_complete_date() {
		return attach_complete_date;
	}
	public void setAttach_complete_date(String attach_complete_date) {
		this.attach_complete_date = attach_complete_date;
	}
	public int getAccount_transfer_yn() {
		return account_transfer_yn;
	}
	public void setAccount_transfer_yn(int account_transfer_yn) {
		this.account_transfer_yn = account_transfer_yn;
	}
	public String getAccount_transfer_date() {
		return account_transfer_date;
	}
	public void setAccount_transfer_date(String account_transfer_date) {
		this.account_transfer_date = account_transfer_date;
	}
	public int getAccount_verify_yn() {
		return account_verify_yn;
	}
	public void setAccount_verify_yn(int account_verify_yn) {
		this.account_verify_yn = account_verify_yn;
	}
	public String getAccount_verify_date() {
		return account_verify_date;
	}
	public void setAccount_verify_date(String account_verify_date) {
		this.account_verify_date = account_verify_date;
	}
	public int getIdentity_chk_yn() {
		return identity_chk_yn;
	}
	public void setIdentity_chk_yn(int identity_chk_yn) {
		this.identity_chk_yn = identity_chk_yn;
	}
	public String getIdentity_chk_date() {
		return identity_chk_date;
	}
	public void setIdentity_chk_date(String identity_chk_date) {
		this.identity_chk_date = identity_chk_date;
	}
	public String getAccount_holder_name() {
		return account_holder_name;
	}
	public void setAccount_holder_name(String account_holder_name) {
		this.account_holder_name = account_holder_name;
	}
	public String getAccount_holder_number() {
		return account_holder_number;
	}
	public void setAccount_holder_number(String account_holder_number) {
		this.account_holder_number = account_holder_number;
	}
	public String getAccount_yn() {
		return account_yn;
	}
	public void setAccount_yn(String account_yn) {
		this.account_yn = account_yn;
	}
	public String getAccount_regdate() {
		return account_regdate;
	}
	public void setAccount_regdate(String account_regdate) {
		this.account_regdate = account_regdate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getJumin() {
		return jumin;
	}
	public void setJumin(String jumin) {
		this.jumin = jumin;
	}
	
	
	
	
}