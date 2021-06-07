package net.ib.paperless.spring.repository;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import net.ib.paperless.spring.domain.User;

@Repository
public class UserRepository {

    /// 메소드 이름을 규칙에 맞게만해 주어도 Spring Data JPA 가 알아서 implementation을 제공한다.
  /*  public List<User> findAllByOrderByUserNoAsc();
    User findByUserNameIgnoreCase(String username);
    User findByUserIDIgnoreCase(String userid);*/

	@Autowired
	//@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	/* DB Select */
	public User findByIdIgnoreCase(String id) {
		return sqlSession.selectOne("userMapper.findByIdIgnoreCase" , id);
	}
	
	public HashMap<String, Object> userLevelSelectOne(String id) {
		return sqlSession.selectOne("userMapper.userLevelSelectOne" , id);
	}
}