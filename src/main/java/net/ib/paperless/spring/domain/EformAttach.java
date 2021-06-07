package net.ib.paperless.spring.domain;

public class EformAttach {
	private int seq;
	private int eform_id;
	private String id;
	private String name;
	private String eform_path;
	private int required_yn;
	private String reg_date;
	private int tap_seq;
	private int progress_status;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getEform_id() {
		return eform_id;
	}
	public void setEform_id(int eform_id) {
		this.eform_id = eform_id;
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
	public int getRequired_yn() {
		return required_yn;
	}
	public void setRequired_yn(int required_yn) {
		this.required_yn = required_yn;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getTap_seq() {
		return tap_seq;
	}
	public void setTap_seq(int tap_seq) {
		this.tap_seq = tap_seq;
	}
	public int getProgress_status() {
		return progress_status;
	}
	public void setProgress_status(int progress_status) {
		this.progress_status = progress_status;
	}
	
	
}