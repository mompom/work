package net.ib.paperless.spring.domain;

public class ProgressStatus extends Progress {
	private int seq;
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
	private String account_vefiry_date;
	private int identity_chk_yn;
	private String identity_chk_date;
	private int status_seq;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
	public String getAccount_vefiry_date() {
		return account_vefiry_date;
	}
	public void setAccount_vefiry_date(String account_vefiry_date) {
		this.account_vefiry_date = account_vefiry_date;
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
	public int getStatus_seq() {
		return status_seq;
	}
	public void setStatus_seq(int status_seq) {
		this.status_seq = status_seq;
	}
	
}