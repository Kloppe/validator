package com.gl365.validator.dto.common;

import java.io.Serializable;

import com.gl365.validator.common.JsonUtils;
import com.gl365.validator.common.ResultCodeEnum;

/**
 * < 基础响应 >
 * 
 * 包含常规请求响应的参数
 * 
 * @author hui.li 2017年4月12日 - 下午1:22:21
 * @Since 1.0
 */
public class ResultDto<T> implements Serializable {

	private static final long serialVersionUID = 5598379493227689413L;

	/**
	 * result : 响应码
	 */
	private String result;

	/**
	 * description ： 响应描述
	 */
	private String description;

	/**
	 * data : 结果数据
	 */
	private T data;

	public ResultDto() {

	}

	public ResultDto(T data) {
		this.data = data;
	}

	public ResultDto(String result, String msg, T data) {
		this.result = result;
		this.description = msg;
		this.data = data;
	}

	public ResultDto(ResultCodeEnum.System source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}
	
	public ResultDto(ResultCodeEnum.System source, String message, T data) {
		this.result = source.getCode();
		this.description = message;
		this.data = data;
	}
	
	public ResultDto(ResultCodeEnum.System source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}
	
	public ResultDto(ResultCodeEnum.Validator source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}
	
	public ResultDto(ResultCodeEnum.Validator source, T data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}
	
	public ResultDto(ResultCodeEnum.Validator source, String message, T data) {
		this.result = source.getCode();
		this.description = message;
		this.data = data;
	}
	

	public static ResultDto<?> result(ResultDto<?> source, Object data) {
		ResultDto<?> result = new ResultDto<>(data);
		result.setResult(source.getResult());
		result.setDescription(source.getDescription());
		return result;
	}

	/***
	 * 
	 * System
	 */
	public static ResultDto<?> errorResult() {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
		result.setDescription(ResultCodeEnum.System.SYSTEM_ERROR.getMsg());
		return result;
	}

	public static ResultDto<?> result(ResultCodeEnum.System source) {
		ResultDto<?> result = new ResultDto<>();
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}

	public static ResultDto<?> result(ResultCodeEnum.System source, Object data) {
		ResultDto<?> result = new ResultDto<>(data);
		result.setResult(source.getCode());
		result.setDescription(source.getMsg());
		return result;
	}

	public static ResultDto<?> result(ResultCodeEnum.System source, String message, Object data) {
		ResultDto<?> result = new ResultDto<>(data);
		result.setResult(source.getCode());
		result.setDescription(message);
		return result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}

}
