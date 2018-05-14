package com.gl365.validator.service.impl;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.validator.common.HttpsUtil;
import com.gl365.validator.common.JsonUtils;
import com.gl365.validator.common.ResultCodeEnum;
import com.gl365.validator.dto.common.ReqRltDto;
import com.gl365.validator.dto.common.ResRltDto;
import com.gl365.validator.dto.common.ResultDto;
import com.gl365.validator.dto.validatorDto.BankCardDto;
import com.gl365.validator.dto.validatorDto.IdCardDto;
import com.gl365.validator.dto.validatorDto.MobileDto;
import com.gl365.validator.mapper.SzbBankCardMapper;
import com.gl365.validator.mapper.SzbIdCardMapper;
import com.gl365.validator.mapper.SzbMobileNumMapper;
import com.gl365.validator.model.SzbBankCard;
import com.gl365.validator.model.SzbIdCard;
import com.gl365.validator.model.SzbMobileNum;
import com.gl365.validator.service.ValidatorService;
import com.gl365.validator.service.async.AsyncServiceImpl;

@Service
public class ValidatorServiceImpl implements ValidatorService {

	private static final Logger LOG = LoggerFactory.getLogger(ValidatorServiceImpl.class);
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Autowired
	private SzbIdCardMapper szbIdCardMapper;
	
	@Autowired
	private SzbMobileNumMapper szbMobileNumMapper;
	
	@Autowired
	private SzbBankCardMapper szbBankCardMapper;
	
	/** 
	 * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173 
	 * **/  
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|7[37]|8[019])\\d{8}$)|(^1700\\d{7}$)";  
  
    /** 
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1707,1708,1709,175 
     * **/  
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[56]|8[56])\\d{8}$)|(^170[7-9]\\d{7}$)";  
  
    /** 
     * 中国移动号码格式验证 
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184 
     * ,187,188,147,178,1705 
     * **/ 
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)"; 
	
	//test  test 123456
    private static final String LOGIN_NAME = "geile";// 客户的账户
    private static final String LOGIN_PWD = "geile0614";// 客户密码
	
	// 2.1.1个人电子信息验证	服务名
    private static final String VALIDIDCARD_SERVICENAME = "IDNameCheck";
	// 2.1.1个人电子信息验证	生产环境 "https://www.miniscores.cn:8313/CreditFunc/v2.1/IDNameCheck";
	// 2.1.1个人电子信息验证	测试对接环境    "https://114.55.36.16:8090/CreditFunc/v2.1/IDNameCheck";
    private static final String VALIDIDCARD_URL = "https://www.miniscores.cn:8313/CreditFunc/v2.1/IDNameCheck";

	// 3.2.1银行卡三要素验证	服务名
    private static final String VALIDBANKCARD3_SERVICENAME = "NameIDCardAccountVerify";
	// 3.2.1银行卡三要素验证	生产环境  "https://www.miniscores.cn:8313/CreditFunc/v2.1/NameIDCardAccountVerify";
	// 3.2.1银行卡三要素验证	测试对接环境 "https://114.55.36.16:8090/CreditFunc/v2.1/NameIDCardAccountVerify";
    private static final String VALIDBANKCARD3_URL = "https://www.miniscores.cn:8313/CreditFunc/v2.1/NameIDCardAccountVerify";
	// 2.2.1银行卡四要素验证V3	服务名
	//	public static final String VALIDBANKCARD4_SERVICENAME = "NameIDCardPhoneAccountVerifyV3";
	// 2.2.1银行卡四要素验证V3	生产环境 
	//public static final String VALIDBANKCARD4_URL = "https://www.miniscores.cn:8313/CreditFunc/v2.1/NameIDCardPhoneAccountVerifyV3";
	
	// 3.1.1移动三要素实名认证	服务名
    private static final String CMCC_VALIDMOBILE_SERVICENAME = "IdNamePhoneCmccCheck";
	// 3.1.1移动三要素实名认证	生产环境 "https://www.miniscores.cn:8313/CreditFunc/v2.1/IdNamePhoneCmccCheck";
	// 3.1.1移动三要素实名认证	测试对接环境 "https://114.55.36.16:8090/CreditFunc/v2.1/IdNamePhoneCmccCheck";
    private static final String CMCC_VALIDMOBILE_URL = "https://www.miniscores.cn:8313/CreditFunc/v2.1/IdNamePhoneCmccCheck";
	
	// 3.1.2联通三要素实名认证	服务名
    private static final String CUCC_VALIDMOBILE_SERVICENAME = "IdNamePhoneUnicomCheck";
	// 3.1.2联通三要素实名认证	生产环境 "https://www.miniscores.cn:8313/CreditFunc/v2.1/IdNamePhoneUnicomCheck";
	// 3.1.2联通三要素实名认证	测试对接环境 "https://114.55.36.16:8090/CreditFunc/v2.1/IdNamePhoneUnicomCheck";
    private static final String CUCC_VALIDMOBILE_URL = "https://www.miniscores.cn:8313/CreditFunc/v2.1/IdNamePhoneUnicomCheck";
	
	// 3.1.3联通三要素实名认证	服务名
    private static final String CTCC_VALIDMOBILE_SERVICENAME = "IdNamePhoneTelecomCheck";
	// 3.1.3联通三要素实名认证	生产环境 "https://www.miniscores.cn:8313/CreditFunc/v2.1/IdNamePhoneTelecomCheck";
	// 3.1.3联通三要素实名认证	测试对接环境   "https://114.55.36.16:8090/CreditFunc/v2.1/IdNamePhoneTelecomCheck";
    private static final String CTCC_VALIDMOBILE_URL = "https://www.miniscores.cn:8313/CreditFunc/v2.1/IdNamePhoneTelecomCheck";
	
	@Override
	public ResultDto<Boolean> validIdCard(String idCard, String name) throws Exception {
		//查本地库,若有明确结果则直接返回
		SzbIdCard szbIdCard = szbIdCardMapper.selective(idCard, name);
		if (null != szbIdCard && (AsyncServiceImpl.VALIDA_TRUE_RLT_1.equals(szbIdCard.getResCode())
				|| AsyncServiceImpl.VALIDA_FALSE_RLT_2.equals(szbIdCard.getResCode())
				|| AsyncServiceImpl.VALIDA_FALSE_RLT_3.equals(szbIdCard.getResCode()))){
			return getRltByRltCode(szbIdCard.getResMsg(), szbIdCard.getResCode());
		}
		
		IdCardDto param = new IdCardDto();
    	param.setIdCard(idCard);
    	param.setName(name);
		ReqRltDto<IdCardDto> req = new ReqRltDto<IdCardDto>();
		req.setLoginName(LOGIN_NAME);
		req.setPwd(LOGIN_PWD);
		req.setServiceName(VALIDIDCARD_SERVICENAME);
		req.setParam(param);
		
		//调用第三方的返回结果
		String json = JsonUtils.toJsonString(req);
		String url = VALIDIDCARD_URL;
		LOG.info("validIdCard sendPost by third-party services begin,url={}.json={}",url,json);
		String jsonStr = HttpsUtil.post(url, json);
		LOG.info("validIdCard sendPost by third-party services end,jsonStr={}",jsonStr);
		
		//异步写数据库
		asyncServiceImpl.validIdCard(req, url, jsonStr);
		
		//对第三方的返回结果封装
		return returnRlt(jsonStr);
	}

	@Override
	public ResultDto<Boolean> validBankCard(String accountNo, String idCard, String name) throws Exception {
		//查本地库,若有明确结果则直接返回
		SzbBankCard szbBankCard = szbBankCardMapper.selective(accountNo, idCard, name);
		if(null != szbBankCard && (AsyncServiceImpl.VALIDA_TRUE_RLT_1.equals(szbBankCard.getResCode()) ||
		   AsyncServiceImpl.VALIDA_FALSE_RLT_2.equals(szbBankCard.getResCode())) ){
			return getRltByRltCode(szbBankCard.getResMsg(), szbBankCard.getResCode());
		}
		
		BankCardDto param = new BankCardDto();
		param.setAccountNo(accountNo);
    	param.setIdCard(idCard);
    	param.setName(name);
		ReqRltDto<BankCardDto> req = new ReqRltDto<BankCardDto>();
		req.setLoginName(LOGIN_NAME);
		req.setPwd(LOGIN_PWD);
		req.setServiceName(VALIDBANKCARD3_SERVICENAME);
		req.setParam(param);

		//调用第三方的返回结果
		String json = JsonUtils.toJsonString(req);
		String url = VALIDBANKCARD3_URL;
		LOG.info("validBankCard sendPost by third-party services begin,url={}.json={}",url,json);
		String jsonStr = HttpsUtil.post(url, json);
		LOG.info("validBankCard sendPost by third-party services end,jsonStr={}",jsonStr);
		
		//异步写数据库
		asyncServiceImpl.validBankCard(req, url, jsonStr);
				
		//对第三方的返回结果封装
		return returnRlt(jsonStr);
	}
	
	@Override
	public ResultDto<Boolean> validMobile(String mobile, String idCard, String name) throws Exception {
		//查本地库,若有明确结果则直接返回
		SzbMobileNum szbMobileNum = szbMobileNumMapper.selective(mobile, idCard, name);
		if (null != szbMobileNum && (AsyncServiceImpl.VALIDA_TRUE_RLT_1.equals(szbMobileNum.getResCode())
				|| AsyncServiceImpl.VALIDA_FALSE_RLT_2.equals(szbMobileNum.getResCode())
				|| AsyncServiceImpl.VALIDA_FALSE_RLT_3.equals(szbMobileNum.getResCode()))){
			return getRltByRltCode(szbMobileNum.getResMsg(), szbMobileNum.getResCode());
		}
		
		MobileDto param = new MobileDto();
    	param.setIdCard(idCard);
    	param.setName(name);
    	param.setMobile(mobile);
		ReqRltDto<MobileDto> req = new ReqRltDto<MobileDto>();
		req.setLoginName(LOGIN_NAME);
		req.setPwd(LOGIN_PWD);
		req.setParam(param);
		
		//调用第三方的返回结果
		String jsonStr = validateOperator(req, mobile);
		
		//对第三方的返回结果封装
		return returnRlt(jsonStr);
	}
	
	private ResultDto<Boolean> returnRlt(String jsonStr) {
		ResRltDto result = JsonUtils.toBean(jsonStr, ResRltDto.class);
		if(null == result){
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS,"无法验证",false);
		}
		String rltCode = result.getRESULT();
		String message = result.getMESSAGE();
		return getRltByRltCode(message, rltCode);
	}

	private ResultDto<Boolean> getRltByRltCode(String message, String rltCode) {
		ResultDto<Boolean> rlt = null;
		if("1".equals(rltCode)){
			LOG.info("validIdCard true ,msg={}",message);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS,"恭喜您,您已认证成功",true);
		}else if("2".equals(rltCode)){
			LOG.info("validIdCard false ,msg={}",message);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS,"认证失败,请检查输入信息",false);
		}else{
			LOG.info("validIdCard false ,msg={}",message);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS,"认证失败,请检查输入信息",false);
		}
		return rlt;
	}
	
	public String sendValidMobile(ReqRltDto<MobileDto> req, String serviceName, String url) throws Exception{
		req.setServiceName(serviceName);
		String json = JsonUtils.toJsonString(req);
		LOG.info("validMobile sendPost by third-party services begin,url={}.json={}",url,json);
		String jsonStr = HttpsUtil.post(url, json);
		LOG.info("validMobile sendPost by third-party services end,jsonStr={}",jsonStr);
		
		//异步写数据库
		asyncServiceImpl.validMobile(req, url, jsonStr);
		
		return jsonStr;
	}

	/**
	 * 根据运营商校验手机
	 * @param req
	 * @param mobile
	 * @return String
	 * @throws Exception
	 */
	public String validateOperator(ReqRltDto<MobileDto> req, String mobile) throws Exception {
		String isCmcc = isChinaMobilePhoneNum(mobile);
		if (!"ALL".equals(isCmcc)) {
			return sendValidMobile(req, CMCC_VALIDMOBILE_SERVICENAME, CMCC_VALIDMOBILE_URL);
		}
		String isCucc = isChinaUnicomPhoneNum(mobile);
		if (!"ALL".equals(isCucc)) {
			return sendValidMobile(req, CUCC_VALIDMOBILE_SERVICENAME, CUCC_VALIDMOBILE_URL);
		}
		String isCtcc = isChinaTelecomPhoneNum(mobile);
		if (!"ALL".equals(isCtcc)) {
			return sendValidMobile(req, CTCC_VALIDMOBILE_SERVICENAME, CTCC_VALIDMOBILE_URL);
		}
		return "";
	}
    /** 
     * 验证【移动】手机号码的格式 
     * @param str 校验手机字符串 
     * @return 返回CMCC(移动号码),否则为ALL(未确定运营商) 
     */  
    public String isChinaMobilePhoneNum(String str) {  
    	return  match(CHINA_MOBILE_PATTERN,str) ? "CMCC" : "ALL";  
    }  
    
    /** 
     * 验证【联通】手机号码的格式 
     * @param str 校验手机字符串 
     * @return 返回CUCC(联通号码),否则为ALL(未确定运营商) 
     */  
    public String isChinaUnicomPhoneNum(String str) {  
    	return  match(CHINA_UNICOM_PATTERN, str) ? "CUCC" : "ALL"; 
    }  
    
    /** 
     * 验证【电信】手机号码的格式 
     * @param str 校验手机字符串 
     * @return 返回CTCC(电信号码),否则为ALL(未确定运营商) 
     */ 
    public String isChinaTelecomPhoneNum(String str) {  
        return match(CHINA_TELECOM_PATTERN, str) ? "CTCC" : "ALL";  
    }  
      
    /** 
     * 匹配函数 
     * @param regex 
     * @param input 
     * @return 
     */  
    private boolean match(String regex, String input) {  
        return Pattern.matches(regex, input);  
    }  
}
