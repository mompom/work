package net.ib.paperless.spring.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ib.paperless.spring.domain.BlockUserInfo;

@Repository
public class BlockUserRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BlockUserInfo> blockUserSelect(Map map) {
		return sqlSession.selectList("blockUserMapper.blockUserSelect",map);
	}
	
	public BlockUserInfo blockUserSelectOne(int seq) {
		return sqlSession.selectOne("blockUserMapper.blockUserSelectOne",seq);
	}
	
	public int blockUserUpdate(Map map) {
		return sqlSession.update("blockUserMapper.blockUserUpdate",map);
	}
	
	public int blockUserInsert(Map map) {
		return sqlSession.update("blockUserMapper.blockUserInsert",map);
	}
	
	public int blockUserDelete(Map map) {
		return sqlSession.delete("blockUserMapper.blockUserDelete",map);
	}
	
}