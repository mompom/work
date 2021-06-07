package net.ib.paperless.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ib.paperless.spring.common.UUIDs;
import net.ib.paperless.spring.domain.BlockUserInfo;
import net.ib.paperless.spring.repository.BlockUserRepository;

@Service
public class BlockUserService {
	@Autowired 
	BlockUserRepository blockUserRepository;
	
	public List<BlockUserInfo> blockUserSelect(Map params){
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
		return blockUserRepository.blockUserSelect(params);
	}
	
	public BlockUserInfo blockUserSelectOne(int seq){
		return blockUserRepository.blockUserSelectOne(seq);
	}
	
	public int blockUserUpdate(Map params){
		return blockUserRepository.blockUserUpdate(params);
	}
	
	public int blockUserInsert(Map params){
		String account_holder_number = params.get("account_holder_number").toString();
		String account_holder_code = params.get("account_holder_code").toString();
		String account_holder_name = params.get("account_holder_name").toString();
		
		String user_key_combine = account_holder_number + "^" + account_holder_code + "^" + account_holder_name;
		String user_key = UUIDs.createNameUUID(user_key_combine.getBytes()).toString();
		
		params.put("user_key", user_key);
		return blockUserRepository.blockUserInsert(params);
	}
	
	public int blockUserDelete(Map params){
		return blockUserRepository.blockUserDelete(params);
	}


}