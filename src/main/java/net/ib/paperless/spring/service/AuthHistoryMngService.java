package net.ib.paperless.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ib.paperless.spring.domain.AccountAuthHistoryInfo;
import net.ib.paperless.spring.domain.CompanyInfo;
import net.ib.paperless.spring.domain.CustomerChargeInfo;
import net.ib.paperless.spring.domain.CustomerServiceInfo;
import net.ib.paperless.spring.domain.DomainInfo;
import net.ib.paperless.spring.domain.FileUploadInfo;
import net.ib.paperless.spring.repository.AuthHistoryMngRepository;

@Service
public class AuthHistoryMngService {
	@Autowired 
	AuthHistoryMngRepository authHistoryMngRepository;
	
	public List<AccountAuthHistoryInfo> historyCountSelectList(Map params){
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
		
		return authHistoryMngRepository.historyCountSelectList(params);
	}
	
	public List<AccountAuthHistoryInfo> historySelectList(Map params){
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
		
		return authHistoryMngRepository.historySelectList(params);
	}
	
	public List<AccountAuthHistoryInfo> accountAuthHistoryMngSelectList(Map params){
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
		
		return authHistoryMngRepository.accountAuthHistorySelectList(params);
	}
	
	public List<AccountAuthHistoryInfo> accountAuthHistoryMngSelectListExcel(Map params){
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
		return authHistoryMngRepository.accountAuthHistorySelectListExcel(params);
	}
	
	//금결원
	public List<AccountAuthHistoryInfo> accountAuthHistoryMngSelectList2(Map params){
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
		
		return authHistoryMngRepository.accountAuthHistorySelectList2(params);
	}
	
	public List<AccountAuthHistoryInfo> accountAuthHistoryMngSelectListExcel2(Map params){
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
		return authHistoryMngRepository.accountAuthHistorySelectListExcel2(params);
	}
	
	
	public List<AccountAuthHistoryInfo> accountBankNameMngSelectList(Map params){
		return authHistoryMngRepository.accountBankNameSelectList(params);
	}
	
	public List<CustomerChargeInfo> customerChargeMngSelectList(Map params){
		return authHistoryMngRepository.customerChargeSelectList(params);
	}
	
	public List<CustomerChargeInfo> customerChargeBasicSelectList(Map params){
		return authHistoryMngRepository.customerChargeBasicSelectList(params);
	}
	
	public List<CustomerServiceInfo> customerServiceMngSelectList(Map params){
		return authHistoryMngRepository.customerServiceSelectList(params);
	}
	
	public int customerServiceMngUpdate(Map<String,Object> map){
		return authHistoryMngRepository.customerServiceUpdate(map);
	}
	
	public int customerServiceBasicInsert(Map params){
		return authHistoryMngRepository.customerServiceBasicInsert(params);
	}
	
	public List<CompanyInfo> companyInfoMngSelectList(Map params){
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
		return authHistoryMngRepository.companyInfoSelectList(params);
	}
	public CompanyInfo companyInfoMngOne(Map params){
		return authHistoryMngRepository.companyInfoOne(params);
	}
	public List<CustomerChargeInfo> companyInfoTitleSelect(Map params){
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
		return authHistoryMngRepository.companyInfoTitleSelect(params);
	}
	public List<DomainInfo> domainInfoSelect(Map params){
		return authHistoryMngRepository.domainInfoSelect(params);
	}
	public List<DomainInfo> domainListSelect(Map params){
		return authHistoryMngRepository.domainListSelect(params);
	}
	public int domainInsert(Map params){
		return authHistoryMngRepository.domainInsert(params);
	}
	public int domainUriInsert(Map params){
		return authHistoryMngRepository.domainUriInsert(params);
	}
	public List<DomainInfo> domainCheck(Map params){
		return authHistoryMngRepository.domainCheck(params);
	}
	public List<DomainInfo> domainUriCheck(Map params){
		return authHistoryMngRepository.domainUriCheck(params);
	}
	public List<DomainInfo> domainUriInsertCheck(Map params){
		return authHistoryMngRepository.domainUriInsertCheck(params);
	}
	public int domainUriCheckIsYes(Map<String,Object> map){
		return authHistoryMngRepository.domainUriCheckIsYes(map);
	}
	public int domainDelete(Map<String,Object> map){
		return authHistoryMngRepository.domainDelete(map);
	}
	public int domainUriUpdate(Map<String,Object> map){
		return authHistoryMngRepository.domainUriUpdate(map);
	}
	public int domainUriDelete(Map<String,Object> map){
		return authHistoryMngRepository.domainUriDelete(map);
	}
	public int domainUriUse(Map<String,Object> map){
		return authHistoryMngRepository.domainUriUse(map);
	}
	public int companyInfoUpdate(Map<String,Object> map){
		return authHistoryMngRepository.companyInfoUpdate(map);
	}
	
	public int customerServiceSlideInsert(Map<String,Object> map){
		return authHistoryMngRepository.customerServiceSlideInsert(map);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<AccountAuthHistoryInfo> historySelect(Map params){
		return authHistoryMngRepository.historySelect(params);
	}
	
	public List<AccountAuthHistoryInfo> historyPublicSelect(Map params){
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
		
		return authHistoryMngRepository.historyPublicSelect(params);
	}
	
	public List<AccountAuthHistoryInfo> historyPublicTotalCountSelect(Map params){
		return authHistoryMngRepository.historyPublicTotalCountSelect(params);
	}
	
	public AccountAuthHistoryInfo historyPublicCountSelect(Map params){
		return authHistoryMngRepository.historyPublicCountSelect(params);
	}
	
	public List<FileUploadInfo> fileUploadSelect(Map params){
		return authHistoryMngRepository.fileUploadSelect(params);
	}
}