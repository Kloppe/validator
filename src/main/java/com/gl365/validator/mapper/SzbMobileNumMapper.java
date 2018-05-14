package com.gl365.validator.mapper;

import org.apache.ibatis.annotations.Param;

import com.gl365.validator.model.SzbMobileNum;

public interface SzbMobileNumMapper {

    int insertSelective(SzbMobileNum record);

    SzbMobileNum selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SzbMobileNum record);

    SzbMobileNum selective(@Param("mobileNum")String mobileNum, @Param("cardId")String cardId, @Param("userName")String userName);

}