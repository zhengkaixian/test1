package com.zero.base.common.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description= "返回响应数据")
public class ResponseResult<T> {
	/**
	 * @fieldName: success
	 * @fieldType: boolean
	 * @Description: 操作是否成功
	 */
	@ApiModelProperty(value = "是否成功")
	private boolean success;
	/**
	 * @fieldName: mes
	 * @fieldType: String
	 * @Description: 操作结果信息
	 */
	@ApiModelProperty(value = "操作结果信息")
	private String msg;
	/**
	 * @fieldName: data
	 * @fieldType: T
	 * @Description: 返回对象
	 */
	@ApiModelProperty(value = "返回对象")
	private T data;

	/**
	 * @fieldName: code
	 * @fieldType: String
	 * @Description: 业务异常编码
	 */
	@ApiModelProperty(value = "业务异常编码")
	private int code = 200;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
