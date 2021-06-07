package net.ib.paperless.spring.domain;

import lombok.Data;

public class JsonFile {
	private String reg;
	private String account_holder_name;
	private String account_bank_name;
	private String name;
	private String tel;
	private String progress_id;
	private String account;
	private String account_holder_number;
	private String jumin;
	private String bank;
	private String account_yn;
	private String account_regdate;
	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}
	public String getAccount_holder_name() {
		return account_holder_name;
	}
	public void setAccount_holder_name(String account_holder_name) {
		this.account_holder_name = account_holder_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getProgress_id() {
		return progress_id;
	}
	public void setProgress_id(String progress_id) {
		this.progress_id = progress_id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccount_holder_number() {
		return account_holder_number;
	}
	public void setAccount_holder_number(String account_holder_number) {
		this.account_holder_number = account_holder_number;
	}
	public String getJumin() {
		return jumin;
	}
	public void setJumin(String jumin) {
		this.jumin = jumin;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
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
	public String getAccount_bank_name() {
		return account_bank_name;
	}
	public void setAccount_bank_name(String account_bank_name) {
		this.account_bank_name = account_bank_name;
	}
	
}