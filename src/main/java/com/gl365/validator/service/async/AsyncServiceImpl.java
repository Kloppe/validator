package com.gl365.validator.service.async;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.gl365.validator.common.JsonUtils;
import com.gl365.validator.dto.common.ReqRltDto;
import com.gl365.validator.dto.common.ResRltDto;
import com.gl365.validator.dto.validatorDto.BankCardDto;
import com.gl365.validator.dto.validatorDto.IdCardDto;
import com.gl365.validator.dto.validatorDto.MobileDto;
import com.gl365.validator.mapper.SzbBankCardLogMapper;
import com.gl365.validator.mapper.SzbBankCardMapper;
import com.gl365.validator.mapper.SzbIdCardLogMapper;
import com.gl365.validator.mapper.SzbIdCardMapper;
import com.gl365.validator.mapper.SzbMobileNumLogMapper;
import com.gl365.validator.mapper.SzbMobileNumMapper;
import com.gl365.validator.model.SzbBankCard;
import com.gl365.validator.model.SzbBankCardLog;
import com.gl365.validator.model.SzbIdCard;
import com.gl365.validator.model.SzbIdCardLog;
import com.gl365.validator.model.SzbMobileNum;
import com.gl365.validator.model.SzbMobileNumLog;

@Component
public class AsyncServiceImpl {
	
	private static final Logger LOG = LoggerFactory.getLogger(AsyncServiceImpl.class);
	
	public static final String VALIDA_TRUE_RLT_1 = "1";//验证一致
	
	public static final String VALIDA_FALSE_RLT_2 = "2";////验证不一致
	
	public static final String VALIDA_FALSE_RLT_3 = "3";//无法验证
	
	@Autowired
	private SzbIdCardLogMapper szbIdCardLogMapper;
	
	@Autowired
	private SzbIdCardMapper szbIdCardMapper;
	
	@Autowired
	private SzbMobileNumLogMapper szbMobileNumLogMapper;
	
	@Autowired
	private SzbMobileNumMapper szbMobileNumMapper;
	
	@Autowired
	private SzbBankCardLogMapper szbBankCardLogMapper;
	
	@Autowired
	private SzbBankCardMapper szbBankCardMapper;
	
	@Async
	public void validIdCard(ReqRltDto<IdCardDto> req, String url, String returnJson){
		LOG.info("Async validIdCard begin,req={},url={},returnJson={}",JsonUtils.toJsonString(req),url,returnJson);
		try{
			String reqJson = JsonUtils.toJsonString(req);
			IdCardDto idCardDto = req.getParam();
			ResRltDto result = JsonUtils.toBean(returnJson, ResRltDto.class);
			if(null == result){
				result = new ResRltDto();
			}
			StringBuffer sb = new StringBuffer("");
			sb.append("url = ")
			  .append(url)
			  .append(" ,reqJson = ")
			  .append(reqJson);
			LocalDateTime time = LocalDateTime.now();
			
			SzbIdCardLog record = new SzbIdCardLog();
			record.setUserName(idCardDto.getName());
			record.setCardId(idCardDto.getIdCard());
			record.setReqContent(sb.toString());
			record.setResContent(returnJson);
			record.setResCode(result.getRESULT());
			record.setResMsg(result.getMESSAGE());
			record.setRemark(result.getGuid());
			record.setCreatedBy(999999L);
			record.setCreatedDate(time);
			record.setUpdatedBy(999999L);
			record.setUpdatedDate(time);
			record.setDeletedFlag("N");
			int rltNum = szbIdCardLogMapper.insertSelective(record);
			LOG.info("Async validIdCard end,rlt={}",rltNum);
			
			//如果返回明确结果为一致或者不一致存到另一张表,下次验证直接调用自己的表
			if (VALIDA_TRUE_RLT_1.equals(result.getRESULT()) || VALIDA_FALSE_RLT_2.equals(result.getRESULT())
					|| VALIDA_FALSE_RLT_3.equals(result.getRESULT())){
				SzbIdCard target = new SzbIdCard();
				BeanUtils.copyProperties(record, target);
				int realRltNum = szbIdCardMapper.insertSelective(target);
				LOG.info("Async validIdCard real info written in our database,rlt={}",realRltNum);
			}
		}catch(Exception e){
			LOG.error("Async validIdCard ===> AsyncServiceImpl.validIdCard exception,e:{}",e);
		}
	}
	
	@Async
	public void validMobile(ReqRltDto<MobileDto> req,String url, String returnJson){
		LOG.info("Async validMobile begin,req={},url={},returnJson={}",JsonUtils.toJsonString(req),url,returnJson);
		try{
			String reqJson = JsonUtils.toJsonString(req);
			MobileDto mobileDto = req.getParam();
			ResRltDto result = JsonUtils.toBean(returnJson, ResRltDto.class);
			if(null == result){
				result = new ResRltDto();
			}
			StringBuffer sb = new StringBuffer("");
			sb.append("url = ")
			  .append(url)
			  .append(" ,reqJson = ")
			  .append(reqJson);
			LocalDateTime time = LocalDateTime.now();
			SzbMobileNumLog record = new SzbMobileNumLog();
			record.setUserName(mobileDto.getName());
			record.setCardId(mobileDto.getIdCard());
			record.setMobileNum(mobileDto.getMobile());
			record.setReqContent(sb.toString());
			record.setResContent(returnJson);
			record.setResCode(result.getRESULT());
			record.setResMsg(result.getMESSAGE());
			record.setRemark(result.getGuid());
			record.setCreatedBy(999999L);
			record.setCreatedDate(time);
			record.setUpdatedBy(999999L);
			record.setUpdatedDate(time);
			record.setDeletedFlag("N");
			int rltNum = szbMobileNumLogMapper.insertSelective(record);
			LOG.info("Async validMobile end,rlt={}",rltNum);
			
			//如果返回明确结果为一致或者不一致存到另一张表,下次验证直接调用自己的表
			if (VALIDA_TRUE_RLT_1.equals(result.getRESULT()) || VALIDA_FALSE_RLT_2.equals(result.getRESULT())
					|| VALIDA_FALSE_RLT_3.equals(result.getRESULT())){
				SzbMobileNum target = new SzbMobileNum();
				BeanUtils.copyProperties(record, target);
				int realRltNum = szbMobileNumMapper.insertSelective(target);
				LOG.info("Async validMobile real info written in our database,rlt={}",realRltNum);
			}
		}catch(Exception e){
			LOG.error("Async validMobile ===> AsyncServiceImpl.validIdCard exception,e:{}",e);
		}
	}
	
	@Async
	public void validBankCard(ReqRltDto<BankCardDto> req,String url, String returnJson){
		LOG.info("Async validBankCard begin,req={},url={},returnJson={}",JsonUtils.toJsonString(req),url,returnJson);
		try{
			String reqJson = JsonUtils.toJsonString(req);
			BankCardDto bankCardDto = req.getParam();
			ResRltDto result = JsonUtils.toBean(returnJson, ResRltDto.class);
			if(null == result){
				result = new ResRltDto();
			}
			StringBuffer sb = new StringBuffer("");
			sb.append("url = ")
			  .append(url)
			  .append(" ,reqJson = ")
			  .append(reqJson);
			LocalDateTime time = LocalDateTime.now();
			
			SzbBankCardLog record = new SzbBankCardLog();
			record.setUserName(bankCardDto.getName());
			record.setBankId(bankCardDto.getAccountNo());
			record.setCardId(bankCardDto.getIdCard());
			record.setReqContent(sb.toString());
			record.setResContent(returnJson);
			record.setResCode(result.getRESULT());
			record.setResMsg(result.getMESSAGE());
			record.setRemark(result.getGuid());
			record.setCreatedBy(999999L);
			record.setCreatedDate(time);
			record.setUpdatedBy(999999L);
			record.setUpdatedDate(time);
			record.setDeletedFlag("N");
			int rltNum = szbBankCardLogMapper.insertSelective(record);
			LOG.info("Async validBankCard end,rlt={}",rltNum);
			
			//如果返回明确结果为一致或者不一致存到另一张表,下次验证直接调用自己的表
			if(VALIDA_TRUE_RLT_1.equals(result.getRESULT()) || VALIDA_FALSE_RLT_2.equals(result.getRESULT())){
				SzbBankCard target = new SzbBankCard();
				BeanUtils.copyProperties(record, target);
				int realRltNum = szbBankCardMapper.insertSelective(target);
				LOG.info("Async validBankCard real info written in our database,rlt={}",realRltNum);
			}
		}catch(Exception e){
			LOG.error("Async validBankCard ===> AsyncServiceImpl.validIdCard exception,e:{}",e);
		}
	}
	
}
