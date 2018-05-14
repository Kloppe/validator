package com.gl365.validator.mapper;

import com.gl365.validator.model.SzbIdCardLog;

public interface SzbIdCardLogMapper {

    int insertSelective(SzbIdCardLog record);

    SzbIdCardLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SzbIdCardLog record);

}