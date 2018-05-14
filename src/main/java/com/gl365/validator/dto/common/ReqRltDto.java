package com.gl365.validator.dto.common;

public class ReqRltDto<T>{
	
	/**
	 * description ：客户的账户
	 */
	private String loginName;

	/**
	 * description ： 客户的密码
	 */
	private String pwd;
	
	/**
	 * description ： 客户选择的服务名
	 */
	private String serviceName;

	/**
	 * description : 客户根据不同的服务传入的参数
	 */
	private T param;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}
}
