package net.ib.paperless.spring.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clipsoft.org.json.simple.parser.JSONParser;
import com.clipsoft.org.json.simple.parser.ParseException;

import net.ib.paperless.spring.domain.EformAttach;
import net.ib.paperless.spring.domain.LoanInfo;
import net.ib.paperless.spring.domain.ProdMng;
import net.ib.paperless.spring.repository.AdminMngRepository;
import net.ib.paperless.spring.security.PasswordEncoding;

@Service
public class AdminMngService {
	@Autowired 
	AdminMngRepository adminMngRepository;

    @Autowired
    AuthenticationService authenticationService;
    
	public int newPasswordUpdate(Map params){
		int rst = 0;
		UserDetails ud = authenticationService.loadUserByUsername(params.get("id").toString());
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);

		String now_pw = params.get("now_pw").toString();
		String new_pw = passwordEncoding.encode(params.get("new_pw").toString());
		
		if (passwordEncoding.matches(now_pw, ud.getPassword())) {
			params.put("new_pw", new_pw);
			rst = adminMngRepository.newPasswordUpdate(params);
		}
		
		
		return rst;
	}
	
	public List<ProdMng> prodMngSelectList(Map params){
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
		//System.out.println("params : " + params);
		return adminMngRepository.prodMngSelectList(params);
	}

	public ProdMng prodMngSelectOne(int seq){
		return adminMngRepository.prodMngSelectOne(seq);
	}
	
	public List<EformAttach> eformAttachSelectList(int seq){
		return adminMngRepository.eformAttachSelectList(seq);
	}
	
	public int eformAttachInsert(Map map){
		return adminMngRepository.eformAttachInsert(map);
	}
	
	public List<LoanInfo> loanInfoSelectList(){
		return adminMngRepository.loanInfoSelectList();
	}
	
	public int eformInfoInsert(Map map){

		map.put("attach_type_count", 0);
		map.put("seq", 0);
		adminMngRepository.eformInfoInsert(map);
		//System.out.println("map  :  " + map);
		int seq = Integer.parseInt(map.get("seq").toString());
		if(seq > 0){
			if(map.get("eform_attachs") != null){
				List<String> eformAttachs = (List<String>) map.get("eform_attachs");
				//System.out.println("efromAttachs : " + eformAttachs);
				boolean boo = true;
				if(!eformAttachs.isEmpty()){
					//foreach(;)
					for(String s : eformAttachs){
						Map<String,Object> nMap = new HashMap<String,Object>();
						nMap.put("eform_id", seq);
						nMap.put("name", s);
						nMap.put("eform_path", "");
						//System.out.println("nMap: " + nMap);
						if(adminMngRepository.eformAttachInsert(nMap) <= 0){
							boo = false;
							break;
						}
					}
					if(boo)
						return 1;
					else
						return 0;
				}else
					return 0;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
		//return adminMngRepository.eformInfoInsert(map);
	}
	

	
	public int eformInfoInsertAsMultipart(Map map){

		String[] eformAttachsArray = map.get("eform_attachs").toString().split(",");
		List<String> eformAttachs = new ArrayList(Arrays.asList(eformAttachsArray));
		map.put("attach_type_count", eformAttachs.size());
		map.put("seq", 0);
		adminMngRepository.eformInfoInsert(map);
		//System.out.println("map  :  " + map);
		int seq = Integer.parseInt(map.get("seq").toString());
		if(seq > 0){
			if(eformAttachs.size() > 0){
				//System.out.println("efromAttachs : " + eformAttachs);
				boolean boo = true;
				if(!eformAttachs.isEmpty()){
					//foreach(;)
					for(String s : eformAttachs){
						Map<String,Object> nMap = new HashMap<String,Object>();
						nMap.put("eform_id", seq);
						nMap.put("name", s);
						nMap.put("eform_path", "");
						//System.out.println("nMap: " + nMap);
						if(adminMngRepository.eformAttachInsert(nMap) <= 0){
							boo = false;
							break;
						}
					}
					if(boo)
						return 1;
					else
						return 0;
				}else
					return 0;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
		//return adminMngRepository.eformInfoInsert(map);
	}

	public int eformInfoUpdateAsMultipart(Map map) throws ParseException{

		adminMngRepository.eformInfoUpdate(map);
		
		String eformAttachsJson = (String) map.get("eform_attachs");
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(eformAttachsJson);
		List<Object> list = (List<Object>) obj;
		if(list.size() > 0){
			boolean boo = true;
			for(Object s : list){
				Map<String,Object> o = (Map<String, Object>) s;
				o.put("eform_path", "");
				if(adminMngRepository.eformAttachUpdate(o) <= 0){
					boo = false;
					break;
				}
			}
			
			if(boo) return 1;
			else return 0;
		}else{return 0;}
	}
	
	public int eformDelete(HashMap<String,Object> params){
		//System.out.println("params : " + params.get("eformDel"));
		List<String> list = (List<String>)params.get("eformDel");
		//System.out.println("list : " + list);
		for(String i : list){
			adminMngRepository.eformInfoDelete(Integer.parseInt(i));
			adminMngRepository.eformAttachDelete(Integer.parseInt(i));
		}
		return 1;
	}
	
	public int companyUpdate(Map params){
		return adminMngRepository.companyUpdate(params);
	}
	
	public int companyInsert(Map params){
		return adminMngRepository.companyInsert(params);
	}
	
	public int companyDelete(Map params){
		return adminMngRepository.companyDelete(params);
	}
	
	public int userUpdate(Map params){
		return adminMngRepository.userUpdate(params);
	}
	
	public int userInsert(Map params){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);

		String passwd = passwordEncoding.encode("ib123!@#");
		
		params.put("passwd", passwd);
		return adminMngRepository.userInsert(params);
	}
	
	public int userDelete(Map params){
		return adminMngRepository.userDelete(params);
	}
}