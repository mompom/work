package net.ib.paperless.spring.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.ib.paperless.spring.domain.AccountAuthHistoryInfo;
import net.ib.paperless.spring.domain.CompanyInfo;
import net.ib.paperless.spring.domain.EformUserData;
import net.ib.paperless.spring.domain.NoticeInfo;
import net.ib.paperless.spring.domain.ProdMng;
import net.ib.paperless.spring.domain.Status;

public class ApiResponse<T> extends LinkedHashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;
	
	private int result;
	private String message;
	private int totalItems;
	private String resultType;	
	private int pageNo;
	private int itemSize;

    public ApiResponse() {

    }
    
    public ApiResponse(boolean result, String message , int totalItems , String resultType ) {
    	this.put("result", result);
    	this.put("message", message);
    	this.put("totalItems", totalItems);
    	this.put("resultType", resultType);
    }

    public void setResult(boolean result) {
    	this.put("result", result);
    }

    public void setMessage(String message){
    	this.put("message", message);
    }
    
    public void setTotalItems(int totalItems){
    	this.put("totalItems", totalItems);
    }
    
    public void setResultType(String resultType){
    	this.put("resultType", resultType);
    }
    
    /*public void setList(List<Object> list ) {
    	this.put("list", list);
    }*/
    
    public void setList(List<?> list ) {
    	this.put("list", list);
    }

    public void setMap(Status status ) {
    	this.put("map", status);
    }

    public void setMap(Map<String, Object> map ) {
    	this.put("map", map);
    }

    public void setString(String str) {
    	this.put("string", str);
    }
    
    public void setPageNo(int pageNo) {
    	this.put("pageNo", pageNo);
    }
    
    public void setItemSize(int itemSize) {
    	this.put("itemSize", itemSize);
    }

	public void setMap(EformUserData eformUserData) {
    	this.put("map", eformUserData);
	}

	public void setMap(ProdMng prodMng) {
    	this.put("map", prodMng);
		
	}
	
	public void setMap(NoticeInfo notice) {
    	this.put("map", notice);
	}
	
	public void setMap(CompanyInfo company) {
    	this.put("map", company);
	}
	
	public void setUrl(String url) {
    	this.put("url", url);
	}
	
	public void setMap(AccountAuthHistoryInfo accountAuthHistoryInfo) {
    	this.put("map", accountAuthHistoryInfo);
	}
}
