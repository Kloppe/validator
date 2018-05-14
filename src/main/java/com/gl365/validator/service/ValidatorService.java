package com.gl365.validator.service;

import com.gl365.validator.dto.common.ResultDto;

public interface ValidatorService {

	public ResultDto<Boolean> validIdCard(String idCard, String name) throws Exception ;

	public ResultDto<Boolean> validMobile(String mobile, String idCard, String name) throws Exception ;

	public ResultDto<Boolean> validBankCard(String accountNo, String idCard, String name) throws Exception ;

}
