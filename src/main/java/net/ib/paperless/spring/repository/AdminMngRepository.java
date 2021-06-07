package net.ib.paperless.spring.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ib.paperless.spring.domain.EformAttach;
import net.ib.paperless.spring.domain.LoanInfo;
import net.ib.paperless.spring.domain.ProdMng;

@Repository
public class AdminMngRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int newPasswordUpdate(Map map) {
		return sqlSession.update("adminMngMapper.newPasswordUpdate",map);
	}
	
	public List<ProdMng> prodMngSelectList(Map map) {
		return sqlSession.selectList("adminMngMapper.prodMngSelectList",map);
	}

	public ProdMng prodMngSelectOne(int seq) {
		return sqlSession.selectOne("adminMngMapper.prodMngSelectOne",seq);
	}
	
	public List<EformAttach> eformAttachSelectList(int seq) {
		return sqlSession.selectList("adminMngMapper.eformAttachSelectList",seq);
	}
	
	public int eformAttachInsert(Map map) {
		return sqlSession.insert("adminMngMapper.eformAttachInsert",map);
	}
	
	public List<LoanInfo> loanInfoSelectList(){
		return sqlSession.selectList("adminMngMapper.loanInfoSelectList");
	}
	
	public int eformInfoInsert(Map map){
		return sqlSession.insert("adminMngMapper.eformInfoInsert",map);
	}
	
	public int eformInfoUpdate(Map map){
		return sqlSession.update("adminMngMapper.eformInfoUpdate",map);
	}
	
	public int eformAttachUpdate(Map map){
		return sqlSession.update("adminMngMapper.eformAttachUpdate",map);
	}
	
	public int eformInfoDelete(int i){
		return sqlSession.delete("adminMngMapper.eformInfoDelete",i);
	}

	public int eformAttachDelete(int i){
		return sqlSession.delete("adminMngMapper.eformAttachDelete",i);
	}
	
	public int companyUpdate(Map map) {
		return sqlSession.update("adminMngMapper.companyUpdate",map);
	}
	
	public int companyInsert(Map map) {
		return sqlSession.insert("adminMngMapper.companyInsert",map);
	}
	
	public int companyDelete(Map map) {
		return sqlSession.delete("adminMngMapper.companyDelete",map);
	}
	
	public int userUpdate(Map map) {
		return sqlSession.update("adminMngMapper.userUpdate",map);
	}
	
	public int userInsert(Map map) {
		return sqlSession.insert("adminMngMapper.userInsert",map);
	}
	
	public int userDelete(Map map) {
		return sqlSession.delete("adminMngMapper.userDelete",map);
	}
}