package com.gl365.validator.mapper;

import org.apache.ibatis.annotations.Param;

import com.gl365.validator.model.SzbIdCard;

public interface SzbIdCardMapper {

    int insertSelective(SzbIdCard record);

    SzbIdCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SzbIdCard record);

    SzbIdCard selective(@Param("cardId")String cardId, @Param("userName")String userName);

}