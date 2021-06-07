package net.ib.paperless.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ib.paperless.spring.domain.EformAttach;
import net.ib.paperless.spring.domain.LoanInfo;
import net.ib.paperless.spring.domain.NoticeInfo;
import net.ib.paperless.spring.domain.ProdMng;
import net.ib.paperless.spring.repository.AdminNoticeRepository;

@Service
public class AdminNoticeService {
	@Autowired 
	AdminNoticeRepository adminNoticeRepository;
	
	public List<NoticeInfo> noticeSelectList(Map params){
		int pageSize = 0;
		int pageNo = 0;
		int pagePoint = 0;
		
		if(params.get("pageSize") == null) pageSize = 20;
		else pageSize = Integer.parseInt(params.get("pageSize").toString());
		
		if(params.get("pageNo") == null) pageNo = 1;
		else pageNo = Integer.parseInt(params.get("pageNo").toString());
		
		pagePoint = (pageNo - 1) * pageSize;
		
		params.put("pageSize", pageSize);
		params.put("pageNo", pageNo);
		params.put("pagePoint", pagePoint);
		//System.out.println("params : " + params);
		return adminNoticeRepository.noticeSelectList(params);
	}
	
	public NoticeInfo noticeSelectOne(int seq){
		return adminNoticeRepository.noticeSelectOne(seq);
	}
	
	public int noticeUpdate(Map params){
		return adminNoticeRepository.noticeUpdate(params);
	}
	
	public int noticeInsert(Map params){
		return adminNoticeRepository.noticeInsert(params);
	}
	
	public int noticeDelete(Map params){
		return adminNoticeRepository.noticeDelete(params);
	}


}