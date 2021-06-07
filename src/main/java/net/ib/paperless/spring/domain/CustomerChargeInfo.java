package net.ib.paperless.spring.domain;

public class CustomerChargeInfo {
	private int seq;
	private int service_seq;
	private String company_id;
	private String customer_service_name;
	private String service_name;
	private int service_type;
	private int customer_service_type;
	private int monthly_use;
	private int charge;
	private int discount;
	private int start_year_date;
	private int start_month_date;
	private int end_year_date;
	private int end_month_date;
	private String start_date;
	private String end_date;
	private String reg_date;
	private String use_yn;
	private String endless_yn;
	private String view_yn;
	
	private int customer_service_seq;
	private int totalCnt;
	
	public int getCustomer_service_seq() {
		return customer_service_seq;
	}
	public void setCustomer_service_seq(int customer_service_seq) {
		this.customer_service_seq = customer_service_seq;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getStart_year_date() {
		return start_year_date;
	}
	public void setStart_year_date(int start_year_date) {
		this.start_year_date = start_year_date;
	}
	public int getStart_month_date() {
		return start_month_date;
	}
	public void setStart_month_date(int start_month_date) {
		this.start_month_date = start_month_date;
	}
	public int getEnd_year_date() {
		return end_year_date;
	}
	public void setEnd_year_date(int end_year_date) {
		this.end_year_date = end_year_date;
	}
	public int getEnd_month_date() {
		return end_month_date;
	}
	public void setEnd_month_date(int end_month_date) {
		this.end_month_date = end_month_date;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getService_seq() {
		return service_seq;
	}
	public void setService_seq(int service_seq) {
		this.service_seq = service_seq;
	}
	public int getService_type() {
		return service_type;
	}
	public void setService_type(int service_type) {
		this.service_type = service_type;
	}
	public int getCustomer_service_type() {
		return customer_service_type;
	}
	public void setCustomer_service_type(int customer_service_type) {
		this.customer_service_type = customer_service_type;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getCustomer_service_name() {
		return customer_service_name;
	}
	public void setCustomer_service_name(String customer_service_name) {
		this.customer_service_name = customer_service_name;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public int getMonthly_use() {
		return monthly_use;
	}
	public void setMonthly_use(int monthly_use) {
		this.monthly_use = monthly_use;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getEndless_yn() {
		return endless_yn;
	}
	public void setEndless_yn(String endlees_yn) {
		this.endless_yn = endlees_yn;
	}
	public String getView_yn() {
		return view_yn;
	}
	public void setView_yn(String view_yn) {
		this.view_yn = view_yn;
	}
}
