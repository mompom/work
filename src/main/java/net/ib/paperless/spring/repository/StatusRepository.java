package net.ib.paperless.spring.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ib.paperless.spring.domain.AdminInfo;
import net.ib.paperless.spring.domain.EformAttach;
import net.ib.paperless.spring.domain.EformInfo;
import net.ib.paperless.spring.domain.EformUserData;
import net.ib.paperless.spring.domain.LoanInfo;
import net.ib.paperless.spring.domain.Progress;
import net.ib.paperless.spring.domain.ProgressStatus;
import net.ib.paperless.spring.domain.Status;

@Repository
public class StatusRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<Status> counselSelectList(Map map) {
		return sqlSession.selectList("statusMapper.counselSelectList",map);
	}
	
	public Status counselSelectOne(String progressId) {
		return sqlSession.selectOne("statusMapper.counselSelectOne",progressId);
	}

	public int progressUpdate(Map map) {
		return sqlSession.update("statusMapper.progressUpdate",map);
	}
	
	public List<Status> statusCodeSelectList(){
		return sqlSession.selectList("statusMapper.statusCodeSelectList");
	}
	
	public List<Status> progressNoteSelectList(String progressId){
		return sqlSession.selectList("statusMapper.progressNoteSelectList",progressId);
	}
	
	public int progressNoteInsert(Map map){
		return sqlSession.insert("statusMapper.progressNoteInsert",map);
	}
	
	public int progressStatusUpdate(Map map){
		return sqlSession.update("statusMapper.progressStatusUpdate",map);
	}
	
	public List<EformInfo> eformInfoSelectList(String load_id){
		return sqlSession.selectList("statusMapper.eformInfoSelectList",load_id);
	}
	
	public List<EformAttach> eformAttachSelectList(String progressId){
		return sqlSession.selectList("statusMapper.eformAttachSelectList",progressId);
	}
	
	public LoanInfo loanInfoSelectOne(String loanId){
		return sqlSession.selectOne("statusMapper.loanInfoSelectOne",loanId);
	}

	public int progressInsert(Progress progress){
		return sqlSession.insert("statusMapper.progressInsert",progress);
	}
	
	public int progressStatusInsert(ProgressStatus progressStatus){
		return sqlSession.insert("statusMapper.insertProgressStatus",progressStatus);
	}
	
	public int insertEformUserData(Map<String , Object> params){
		return sqlSession.insert("statusMapper.insertEformUserData",params);
	}
	
	public int editEformUserData(Map<String , Object> params){
		return sqlSession.update("statusMapper.editEformUserData",params);
	}
	
	public EformUserData eformUserDataSelectOne(String progressId) {
		return sqlSession.selectOne("statusMapper.eformUserDataSelectOne",progressId);
	}
	
	public int attachProgressInsert(Map map) {
		return sqlSession.insert("statusMapper.attachProgressInsert",map);
	}

	public List<AdminInfo> adminInfoSelectList(Map map) {
		return sqlSession.selectList("statusMapper.adminInfoSelectList",map);
	}
	
	public int adminInfoUpdate(Map<String , Object> params){
		return sqlSession.update("statusMapper.adminInfoUpdate",params);
	}

	public int adminInfoInsert(Map<String , Object> params){
		return sqlSession.insert("statusMapper.adminInfoInsert",params);
	}
	
	public int adminInfoSelectCnt(String id) {
		return sqlSession.selectOne("statusMapper.adminInfoSelectCnt",id);
	}
}