package com.gl365.validator.mapper;

import org.apache.ibatis.annotations.Param;

import com.gl365.validator.model.SzbBankCard;

public interface SzbBankCardMapper {

    int insertSelective(SzbBankCard record);

    SzbBankCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SzbBankCard record);

    SzbBankCard selective(@Param("bankId")String bankId, @Param("cardId")String cardId, @Param("userName")String userName);

}