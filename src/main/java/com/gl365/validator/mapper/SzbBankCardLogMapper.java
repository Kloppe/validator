package com.gl365.validator.mapper;

import com.gl365.validator.model.SzbBankCardLog;

public interface SzbBankCardLogMapper {

	int insertSelective(SzbBankCardLog record);

	SzbBankCardLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SzbBankCardLog record);

}