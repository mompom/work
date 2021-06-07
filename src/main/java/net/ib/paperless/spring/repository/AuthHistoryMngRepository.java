package net.ib.paperless.spring.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import net.ib.paperless.spring.domain.AccountAuthHistoryInfo;
import net.ib.paperless.spring.domain.CompanyInfo;
import net.ib.paperless.spring.domain.CustomerChargeInfo;
import net.ib.paperless.spring.domain.CustomerServiceInfo;
import net.ib.paperless.spring.domain.DomainInfo;
import net.ib.paperless.spring.domain.FileUploadInfo;

@Repository
public class AuthHistoryMngRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<AccountAuthHistoryInfo> historyCountSelectList(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.historyCountSelectList",map);
	}
	
	public List<AccountAuthHistoryInfo> historySelectList(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.historySelectList",map);
	}
	
	public List<AccountAuthHistoryInfo> accountAuthHistorySelectList(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.accountAuthHistorySelectList",map);
	}
	public List<AccountAuthHistoryInfo> accountAuthHistorySelectListExcel(Map map) {
		//System.out.println(map.get("pageSize"));
		List<AccountAuthHistoryInfo> aa =  sqlSession.selectList("authHistoryMngMapper.accountAuthHistorySelectListExcel",map);
		//System.out.println(map.get("pageSize"));
		return aa;
		
	}
	
	//금결원
	public List<AccountAuthHistoryInfo> accountAuthHistorySelectList2(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.accountAuthHistorySelectList2",map);
	}
	public List<AccountAuthHistoryInfo> accountAuthHistorySelectListExcel2(Map map) {
		//System.out.println(map.get("pageSize"));
		List<AccountAuthHistoryInfo> aa =  sqlSession.selectList("authHistoryMngMapper.accountAuthHistorySelectListExcel2",map);
		//System.out.println(map.get("pageSize"));
		return aa;
		
	}
	
	public List<AccountAuthHistoryInfo> accountBankNameSelectList(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.accountBankNameSelectList",map);
	}
	
	public List<CustomerChargeInfo> customerChargeBasicSelectList(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.customerChargeBasicSelectList",map);
	}
	
	public List<CustomerChargeInfo> customerChargeSelectList(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.customerChargeSelectList",map);
	}
	
	public List<CustomerServiceInfo> customerServiceSelectList(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.customerServiceSelectList",map);
	}
	
	public int customerServiceUpdate(Map map) {
		return sqlSession.update("authHistoryMngMapper.customerServiceUpdate",map);
	}
	public int customerServiceBasicInsert(Map map) {
		return sqlSession.insert("authHistoryMngMapper.customerServiceBasicInsert",map);
	}
	
	public List<CompanyInfo> companyInfoSelectList(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.companyInfoSelectList",map);
	}
	public CompanyInfo companyInfoOne(Map map) {
		return sqlSession.selectOne("authHistoryMngMapper.companyInfoOne",map);
	}
	public List<CustomerChargeInfo> companyInfoTitleSelect(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.companyInfoTitleSelect",map);
	}
	public List<DomainInfo> domainInfoSelect(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.domainInfoSelect",map);
	}
	public List<DomainInfo> domainListSelect(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.domainListSelect",map);
	}
	public int domainInsert(Map map) {
		return sqlSession.insert("authHistoryMngMapper.domainInsert",map);
	}
	public int domainUriInsert(Map map) {
		return sqlSession.insert("authHistoryMngMapper.domainUriInsert",map);
	}
	public List<DomainInfo> domainCheck(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.domainCheck",map);
	}
	public List<DomainInfo> domainUriCheck(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.domainUriCheck",map);
	}
	public List<DomainInfo> domainUriInsertCheck(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.domainUriInsertCheck",map);
	}
	public int domainUriCheckIsYes(Map map) {
		return sqlSession.update("authHistoryMngMapper.domainUriCheckIsYes",map);
	}
	public int domainDelete(Map map) {
		return sqlSession.update("authHistoryMngMapper.domainDelete",map);
	}
	public int domainUriUpdate(Map map) {
		return sqlSession.update("authHistoryMngMapper.domainUriUpdate",map);
	}
	public int domainUriDelete(Map map) {
		return sqlSession.update("authHistoryMngMapper.domainUriDelete",map);
	}
	public int domainUriUse(Map map) {
		return sqlSession.update("authHistoryMngMapper.domainUriUse",map);
	}
	public int companyInfoUpdate(Map map) {
		return sqlSession.update("authHistoryMngMapper.companyInfoUpdate",map);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int customerServiceSlideInsert(Map map){
		int rst = 0;
		try {
			sqlSession.delete("authHistoryMngMapper.customerServiceSlideDelete",map);
			rst = sqlSession.insert("authHistoryMngMapper.customerServiceSlideInsert",map);
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return rst;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<AccountAuthHistoryInfo> historySelect(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.historySelect",map);
	}
	
	public List<AccountAuthHistoryInfo> historyPublicSelect(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.historyPublicSelect",map);
	}
	
	public AccountAuthHistoryInfo historyPublicCountSelect(Map map) {
		return sqlSession.selectOne("authHistoryMngMapper.historyPublicCountSelect",map);
	}
	
	public List<AccountAuthHistoryInfo> historyPublicTotalCountSelect(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.historyPublicTotalCountSelect",map);
	}
	
	public List<FileUploadInfo> fileUploadSelect(Map map) {
		return sqlSession.selectList("authHistoryMngMapper.fileUploadSelect",map);
	}
}