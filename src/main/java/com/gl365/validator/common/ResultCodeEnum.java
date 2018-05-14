package com.gl365.validator.common;

/**
 * api接口请求结果枚举 规则： 长度6位 |0000|00 前四位表示模块|后两位递增
 * 
 * 列如 系统类异常：0000** 用户类异常：1000**
 * 
 * @author dfs_519 2017年4月27日下午2:02:21
 */
public class ResultCodeEnum {

	public enum System {
		/**
		 * 系统保留100以下的错误码
		 */
		SUCCESS("000000", "成功"),

		PARAM_NULL("000002", "参数为空"),

		PARAM_ERROR("000003", "参数非法"),

		REQUEST_IS_NULL("000006", "错误请求"),

		SYSTEM_DATA_EXECEPTION("000008", "系统数据异常"),

		SYSTEM_TIME_OUT("000098", "请求频繁"),

		SYSTEM_ERROR("000099", "服务器错误，请稍后重试"),
		;

		private String code;

		private String msg;

		private System(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public enum Validator {
//		身份信息比对结果
//		1：一致
//		2 : 不一致
//		3：无法验证
//		-1：异常情况（可能原因为IP未注册，账号密码错误，详情参考调用接口返回的具体描述）
		
//		银行卡三要素验证结果
//		1：认证信息匹配
//		2：认证信息不匹配
//		3：无法验证
//		-1：异常情况（可能原因为IP未注册，账号密码错误，详情参考调用接口返回的具体描述）
		
//		姓名身份证号和手机号认证结果
//		1：一致
//		2：不一致
//		3：无记录
		VALIDATE_ERROR("500001", "验证繁忙,请稍后再试"),
		VALIDATE_AGAIN("500002", "请重试"),
		;
		
		private String code;

		private String msg;

		private Validator(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
