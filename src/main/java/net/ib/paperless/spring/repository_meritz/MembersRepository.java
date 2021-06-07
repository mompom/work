package net.ib.paperless.spring.repository_meritz;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import net.ib.paperless.spring.domain.MembersMeritzInfo;
import net.ib.paperless.spring.domain.MembersMeritzTotalCntInfo;

@Repository
public class MembersRepository {
	@Autowired
	@Qualifier("sqlSessionTemplate2")
	private SqlSession sqlSession_meritz;
	
	public List<MembersMeritzTotalCntInfo> selectTotalUsedList(String sdate) {
		return sqlSession_meritz.selectList("membersMapper.selectTotalUsedList", sdate);
	}
	
	public List<MembersMeritzInfo> selectUsedList(Map map) {
		return sqlSession_meritz.selectList("membersMapper.selectUsedList", map);
	}
	
	public int countUsedList(Map map) {
		return sqlSession_meritz.selectOne("membersMapper.countUsedList", map);
	}
	
	
}