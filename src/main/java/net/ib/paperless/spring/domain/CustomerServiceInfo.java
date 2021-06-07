package net.ib.paperless.spring.domain;

public class CustomerServiceInfo {
	private int seq;
	private int service_type;
	private String service_name;
	private int customer_service_type;
	private String customer_service_name;
	private String customer_service_comment;
	private String use_yn;
	private int totalCnt;
	
	
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getService_type() {
		return service_type;
	}
	public void setService_type(int service_type) {
		this.service_type = service_type;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public int getCustomer_service_type() {
		return customer_service_type;
	}
	public void setCustomer_service_type(int customer_service_type) {
		this.customer_service_type = customer_service_type;
	}
	public String getCustomer_service_name() {
		return customer_service_name;
	}
	public void setCustomer_service_name(String customer_service_name) {
		this.customer_service_name = customer_service_name;
	}
	public String getCustomer_service_comment() {
		return customer_service_comment;
	}
	public void setCustomer_service_comment(String customer_service_comment) {
		this.customer_service_comment = customer_service_comment;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
}
