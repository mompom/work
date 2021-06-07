package net.ib.paperless.spring.domain;

public class BlockUserInfo {
	private int seq;
	private String user_key;
	private String account_holder_name;
	private String account_holder_number;
	private String account_holder_code;
	private String account_bank_name;
	private String reg_date;
	private String note;
	
	private int totalCnt;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
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
	public String getAccount_holder_code() {
		return account_holder_code;
	}
	public void setAccount_holder_code(String account_holder_code) {
		this.account_holder_code = account_holder_code;
	}
	public String getAccount_bank_name() {
		return account_bank_name;
	}
	public void setAccount_bank_name(String account_bank_name) {
		this.account_bank_name = account_bank_name;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}