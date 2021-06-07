package net.ib.paperless.spring.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ib.paperless.spring.domain.EformAttach;
import net.ib.paperless.spring.domain.LoanInfo;
import net.ib.paperless.spring.domain.NoticeInfo;
import net.ib.paperless.spring.domain.ProdMng;

@Repository
public class AdminNoticeRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<NoticeInfo> noticeSelectList(Map map) {
		return sqlSession.selectList("adminNoticeMapper.noticeSelectList",map);
	}
	
	public NoticeInfo noticeSelectOne(int seq) {
		return sqlSession.selectOne("adminNoticeMapper.noticeSelectOne",seq);
	}
	
	public int noticeUpdate(Map map) {
		return sqlSession.update("adminNoticeMapper.noticeUpdate",map);
	}
	
	public int noticeInsert(Map map) {
		return sqlSession.update("adminNoticeMapper.noticeInsert",map);
	}
	
	public int noticeDelete(Map map) {
		return sqlSession.delete("adminNoticeMapper.noticeDelete",map);
	}
	
}