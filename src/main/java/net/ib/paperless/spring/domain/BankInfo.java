package net.ib.paperless.spring.domain;

public class BankInfo {
	private String bank_code;
	private String bank_name;
	private String major_yn;
	private String available_yn;
	private String type;
	private String available_op;
	private int totalCnt;
	
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getMajor_yn() {
		return major_yn;
	}
	public void setMajor_yn(String major_yn) {
		this.major_yn = major_yn;
	}
	public String getAvailable_yn() {
		return available_yn;
	}
	public void setAvailable_yn(String available_yn) {
		this.available_yn = available_yn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAvailable_op() {
		return available_op;
	}
	public void setAvailable_op(String available_op) {
		this.available_op = available_op;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
}