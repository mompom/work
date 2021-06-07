package test;


import static org.assertj.core.api.Assertions.setRemoveAssertJRelatedElementsFromStackTrace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import net.minidev.json.JSONObject;

public class OpenPlatformTest {

	/******************************************************************************************
	 ** Static 변수 정의
	 ******************************************************************************************/
	static String apiKey = "l7xx4ec42a5d79934d218f707a0f69a8fd12";
	static String apiSecret = "1157dea490e144498bc14380e7b95a03";
	
	static String postMethod = "POST";
	static String getMethod = "GET";
	
	static String host = "https://testapi.open-platform.or.kr";
	static String getAccessTokenUrl = "/oauth/2.0/token";
	static String getRealNameUrl = "/v1.0/inquiry/real_name";
	static String getBankStatusUrl = "/bank/status";
	static String setTransderDepositUrl= "/transfer/deposit2";
	
	static String accessToken = "71822347-33ad-4f3e-a677-f3779d27fb0e";
	
	
    public enum httpType { FormType, JsonType}

	
	public static void main(String args[]) {
		//String response = getAccessToken();
		String response = setTransderDeposit("1111", "출금인", "0004", "80880204109783", "김영수", "입금인");
		System.out.println("result = "+response);
	}
	
	/**
	 * 현재 시간을 조회한다.
	 * @return 현재시간(yyyymmddhhmmss)
	 */
	public static String getDateTime(){
		long time = System.currentTimeMillis(); 

		SimpleDateFormat dayTime = new SimpleDateFormat("yyyymmddhhmmss");

		String dateTime = dayTime.format(new Date(time));

		return dateTime;

	}
	
	/**
	 * 해당 계좌로 송금한다.
	 * @param passPhrase 			이용기관 입금이체용 암호문구 (1111)
	 * @param outputText			출금계좌 인자내역(출금내역에 나오는 이름?)
	 * @param bankCode				은행코드
	 * @param accountNumber			입금계좌번호
	 * @param accountHolderName		입금계좌 예금주명
	 * @param inputText				입금계좌 인자내역(입금내역에 나오는 이름?)
	 * @return						처리결과(jsonString)
	 */
	public static String setTransderDeposit(String passPhrase, String outputText, String bankCode, String accountNumber, String accountHolderName, String inputText)
	{
		HashMap<String, String> list = new HashMap<>();
		
		list.put("tran_no","1");
		list.put("bank_code_std",bankCode);
		list.put("account_num",accountNumber);
		list.put("account_holder_name",accountHolderName);
		list.put("print_content",inputText);
		list.put("tran_amt","1");
		
		HashMap<String, Object> param = new HashMap<>();
		
		param.put("wd_pass_phrase",passPhrase);
		param.put("wd_print_content",outputText);
		param.put("tran_dtime",getDateTime());
		param.put("req_cnt","1");
		param.put("req_list",list);
		
		return httpUrlConnection(httpType.JsonType, true, setTransderDepositUrl,postMethod, param);
	}
	
	/**
	 * 실효 은행 리스트를 조회한다.
	 * @return	조회결과(jsonString)
	 */
	public static String getBankStatus(){
		
		return httpUrlConnection(httpType.JsonType, true,  getBankStatusUrl, getMethod, null);
	}
	
	/**
	 * 해당 계좌의 실명인증 사실여부를 조회한다.
	 * @param bankCode		은행코드
	 * @param accountNumber	계좌번호
	 * @param birthDay		생년월일(yyyymmdd)
	 * @return				조회결과(jsonString)
	 */
	public static String getRealName(String bankCode, String accountNumber, String birthDay){
		
		HashMap<String, Object> params = new HashMap<>();
		
		params.put("bank_code_std",bankCode);
		params.put("account_num",accountNumber);
		params.put("account_holder_info",birthDay);
		params.put("tran_dtime",getDateTime());

		return httpUrlConnection(httpType.JsonType, true,  getRealNameUrl,postMethod, params);
	}
	
	/**
	 * accessToken을 조회한다.
	 * @return	조회결과(jsonString)
	 */
	public static String getAccessToken(){

		HashMap<String, Object> params = new HashMap<>();
		
		params.put("grant_type","client_credentials");
		params.put("scope","oob");
		params.put("client_id",apiKey);
		params.put("client_secret",apiSecret);
		
		return httpUrlConnection(httpType.FormType, false, getAccessTokenUrl,postMethod, params);
		
	}
	
	/**
	 * httpRequest function
	 * @param type 		content-type (formType or jsonType)
	 * @param isHeader	accessToke 포함 여부
	 * @param url		host를 제외한 url
	 * @param method	http method(post or get)
	 * @param body		hashMap<string,string> parameter
	 * @return			resopnse to jsonString
	 */
	public static String httpUrlConnection(httpType type, Boolean isHeader, String url, String method, HashMap<String, Object> body)
	{
		
		HttpsURLConnection httpsURLConnection = null;
		StringBuffer param = new StringBuffer();
		String result = "";
		String contentType = "";
		
	
		if(type == httpType.FormType)
		{
			if(body != null){
				int i = 1;
				Set<String> set = body.keySet();
				Iterator<String> iterator = set.iterator();
				while(iterator.hasNext())
				{
					String key = iterator.next();
					param.append(key).append("=").append(body.get(key));
					if(i != body.size())
					{
						param.append("&");
						i++;
					}
				}
			}
			contentType = "application/x-www-form-urlencoded; charset=UTF-8";
		}
		else if(type == httpType.JsonType)
		{
			if(body != null)
			{
				JSONObject jsonObject = new JSONObject(body);
				param.append(jsonObject.toJSONString());
			}
			contentType = "application/json; charset=UTF-8";
		}
			
		
        try {
			URL httpUrl = new URL(host+url);
			httpsURLConnection = (HttpsURLConnection) httpUrl.openConnection();
			httpsURLConnection.setRequestMethod(method);
			
			httpsURLConnection.setRequestProperty("Content-type",contentType);
			httpsURLConnection.setUseCaches(false);
			if(method == postMethod) httpsURLConnection.setDoOutput(true);
			httpsURLConnection.setDoInput(true);
			if(isHeader) httpsURLConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
			
			if(body != null)
			{
				OutputStream outputStream = httpsURLConnection.getOutputStream();
				outputStream.write(param.toString().getBytes("UTF-8") );
				outputStream.flush();
				outputStream.close();
			}
			httpsURLConnection.connect();  
			httpsURLConnection.setInstanceFollowRedirects(true);  
			   
			String temp = null;
			   
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
			while((temp = bufferedReader.readLine()) != null){
				result+= temp + "\n";
			}
			System.out.println("requestUrl  = "+host+url);
			System.out.println("method = " + method);
			System.out.println("contentType = "+contentType);
			System.out.println("param = "+param);


        } catch (Exception e) {

             e.printStackTrace();

        } finally {
             if (httpsURLConnection != null) {
            	 httpsURLConnection.disconnect();
             }
        }
        
		return result;
        
	}
}
