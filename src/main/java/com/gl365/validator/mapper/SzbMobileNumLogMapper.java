package com.gl365.validator.mapper;

import com.gl365.validator.model.SzbMobileNumLog;

public interface SzbMobileNumLogMapper {

    int insertSelective(SzbMobileNumLog record);

    SzbMobileNumLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SzbMobileNumLog record);

}