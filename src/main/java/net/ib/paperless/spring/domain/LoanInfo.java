package net.ib.paperless.spring.domain;

import lombok.Data;

public class LoanInfo{
	private String id;
	private String name;
	private String tel_number;
	private String call_center;
	private int min_amount;
	private int max_amount;
	private String reg_date;
	private int ftp_type;
	private String ftp_ip;
	private String ftp_pwd;
	private String ftp_base_pass;
	private String ftp_id;
	private int	ftp_port;
	
	public int getFtp_port() {
		return ftp_port;
	}
	public void setFtp_port(int ftp_port) {
		this.ftp_port = ftp_port;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel_number() {
		return tel_number;
	}
	public void setTel_number(String tel_number) {
		this.tel_number = tel_number;
	}
	public String getCall_center() {
		return call_center;
	}
	public void setCall_center(String call_center) {
		this.call_center = call_center;
	}
	public int getMin_amount() {
		return min_amount;
	}
	public void setMin_amount(int min_amount) {
		this.min_amount = min_amount;
	}
	public int getMax_amount() {
		return max_amount;
	}
	public void setMax_amount(int max_amount) {
		this.max_amount = max_amount;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getFtp_type() {
		return ftp_type;
	}
	public void setFtp_type(int ftp_type) {
		this.ftp_type = ftp_type;
	}
	public String getFtp_ip() {
		return ftp_ip;
	}
	public void setFtp_ip(String ftp_ip) {
		this.ftp_ip = ftp_ip;
	}
	public String getFtp_pwd() {
		return ftp_pwd;
	}
	public void setFtp_pwd(String ftp_pwd) {
		this.ftp_pwd = ftp_pwd;
	}
	public String getFtp_base_pass() {
		return ftp_base_pass;
	}
	public void setFtp_base_pass(String ftp_base_pass) {
		this.ftp_base_pass = ftp_base_pass;
	}
	public String getFtp_id() {
		return ftp_id;
	}
	public void setFtp_id(String ftp_id) {
		this.ftp_id = ftp_id;
	}
	
}