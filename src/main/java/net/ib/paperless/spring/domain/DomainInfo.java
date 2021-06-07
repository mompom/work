package net.ib.paperless.spring.domain;

public class DomainInfo {
	int seq; 
	String company_id;
	String domain;
	String uri;
	String service_code;
	String confirm_yn;
	String use_yn;
	String reg_date;
	int service_type;
	String service_name;
	String domain_code;
	int domain_cnt;
	
	
	public String getDomain_code() {
		return domain_code;
	}
	public void setDomain_code(String domain_code) {
		this.domain_code = domain_code;
	}
	public int getDomain_cnt() {
		return domain_cnt;
	}
	public void setDomain_cnt(int domain_cnt) {
		this.domain_cnt = domain_cnt;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getConfirm_yn() {
		return confirm_yn;
	}
	public void setConfirm_yn(String confirm_yn) {
		this.confirm_yn = confirm_yn;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getService_type() {
		return service_type;
	}
	public void setService_type(int service_type) {
		this.service_type = service_type;
	}
}
