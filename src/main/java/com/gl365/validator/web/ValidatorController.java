package com.gl365.validator.web;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.validator.common.JsonUtils;
import com.gl365.validator.common.ResultCodeEnum;
import com.gl365.validator.dto.common.ResultDto;
import com.gl365.validator.service.ValidatorService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ValidatorController {

	private static final Logger LOG = LoggerFactory.getLogger(ValidatorController.class);

	@Autowired
	private ValidatorService validatorService;
	
	@PostMapping("/validIdCard")
	@HystrixCommand(fallbackMethod = "validIdCardFallback")
	public Object validIdCard(@RequestParam("cardId") String cardId, @RequestParam("name") String name){
		LOG.info("validIdCard begin, reqParam:cardId={},name={}",cardId,name);
		ResultDto<Boolean> rlt = null;
		try {
			if(StringUtils.isBlank(cardId) || StringUtils.isBlank(name)){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = validatorService.validIdCard(cardId,name);
		} catch (Exception e) {
			LOG.error("validIdCard exception  ===> validatorService.validIdCard exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS,ResultCodeEnum.Validator.VALIDATE_ERROR.getMsg(),false);
		}
		LOG.info("validIdCard end,rlt={}",JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	@PostMapping("/validMobile")
	@HystrixCommand(fallbackMethod = "validMobileFallback")
	public Object validMobile(@RequestParam("mobileNum") String mobileNum, @RequestParam("idNum") String idNum, @RequestParam("name") String name){
		LOG.info("validMobile begin, reqParam:mobileNum={},idNum={},name={}",mobileNum,idNum,name);
		ResultDto<Boolean> rlt = null;
		try {
			if(StringUtils.isBlank(mobileNum) || StringUtils.isBlank(idNum) || StringUtils.isBlank(name)){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = validatorService.validMobile(mobileNum,idNum,name);
		} catch (Exception e) {
			LOG.error("validMobile exception  ===> validatorService.validMobile exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS,ResultCodeEnum.Validator.VALIDATE_ERROR.getMsg(),false);
		}
		LOG.info("validMobile end,rlt={}",JsonUtils.toJsonString(rlt));
		return rlt;
	}

	@PostMapping("/validBankCard")
	@HystrixCommand(fallbackMethod = "validBankCardFallback")
	public Object validBankCard(@RequestParam("bankId") String bankId, @RequestParam("cardId") String cardId, @RequestParam("name") String name) {
		LOG.info("validBankCard begin, reqParam:bankId={},cardId={},name={}",bankId,cardId,name);
		ResultDto<Boolean> rlt = null;
		try {
			if(StringUtils.isBlank(bankId) || StringUtils.isBlank(cardId) || StringUtils.isBlank(name)){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = validatorService.validBankCard(bankId,cardId,name);
		} catch (Exception e) {
			LOG.error("validBankCard exception  ===> validatorService.validBankCard exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS,ResultCodeEnum.Validator.VALIDATE_ERROR.getMsg(),false);
		}
		LOG.info("validBankCard end,rlt={}",JsonUtils.toJsonString(rlt));
		return rlt;
	}
	
	
	
	public Object validBankCardFallback(@RequestParam("bankId") String bankId, @RequestParam("cardId") String cardId, @RequestParam("name") String name) {
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS,ResultCodeEnum.Validator.VALIDATE_AGAIN.getMsg(),false);
	}
	public Object validMobileFallback(@RequestParam("mobileNum") String mobileNum, @RequestParam("idNum") String idNum, @RequestParam("name") String name){
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS,ResultCodeEnum.Validator.VALIDATE_AGAIN.getMsg(),false);
	}
	public Object validIdCardFallback(@RequestParam("cardId") String cardId, @RequestParam("name") String name){
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS,ResultCodeEnum.Validator.VALIDATE_AGAIN.getMsg(),false);
	}
}
