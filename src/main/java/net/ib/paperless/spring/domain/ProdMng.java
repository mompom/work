package net.ib.paperless.spring.domain;

import java.util.List;

public class ProdMng{
	private int seq;
	private String loan_id;
	private int admin_id;
	private String id;
	private String name;
	private String eform_path;
	private int attach_use_yn;
	private int attach_type_count;
	private String reg_date;
	private String modify_date;
	private int totalCnt;
	private String loan_name;
	private List<EformAttach> eformAttach;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}
	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
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
	public String getEform_path() {
		return eform_path;
	}
	public void setEform_path(String eform_path) {
		this.eform_path = eform_path;
	}
	public int getAttach_use_yn() {
		return attach_use_yn;
	}
	public void setAttach_use_yn(int attach_use_yn) {
		this.attach_use_yn = attach_use_yn;
	}
	public int getAttach_type_count() {
		return attach_type_count;
	}
	public void setAttach_type_count(int attach_type_count) {
		this.attach_type_count = attach_type_count;
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
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getLoan_name() {
		return loan_name;
	}
	public void setLoan_name(String loan_name) {
		this.loan_name = loan_name;
	}
	public List<EformAttach> getEformAttach() {
		return eformAttach;
	}
	public void setEformAttach(List<EformAttach> eformAttach) {
		this.eformAttach = eformAttach;
	}
	
	
}