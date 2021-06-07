package net.ib.paperless.spring.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ib.paperless.spring.domain.BankInfo;

@Repository
public class BankRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BankInfo> bankSelect(Map map) {
		return sqlSession.selectList("bankMapper.bankSelect",map);
	}
	
	public BankInfo bankSelectOne(int seq) {
		return sqlSession.selectOne("bankMapper.bankSelectOne",seq);
	}
	
	public int bankUpdate(Map map) {
		return sqlSession.update("bankMapper.bankUpdate",map);
	}
	
	public int bankInsert(Map map) {
		return sqlSession.update("bankMapper.bankInsert",map);
	}
	
	public int bankDelete(Map map) {
		return sqlSession.delete("bankMapper.bankDelete",map);
	}
	
}