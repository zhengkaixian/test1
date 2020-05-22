package com.zero.eprocurement.app.common.core;

public class ResponseUtil {
	/**
	 * @Title: genSuccess
	 * @Description: 生成响应成功(带正文)的结果
	 * @param data
	 * @param message
	 * @return
	 */
	public static <T> ResponseResult<T> genSuccess(T data, String message) {
		return getResponseBody(data, message,true,200);
	}
	
	/**
	 * @Title: genError
	 * @Description: 生成响应失败(带code)的结果
	 * @param code
	 * @param message
	 * @return
	 */
	public static <T> ResponseResult<T> genError(int code, String message) {
		return  getResponseBody(null, message,false,code);
	}
	
	/**
	* @Title: getResponseBody
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param data
	* @param message
	* @return
	* @return ResponseResult<T> (这里用一句话描述返回结果说明)
	*/ 
	private static <T> ResponseResult<T> getResponseBody(T data, String message, boolean success,int code) {
		ResponseResult<T> responseBody = new ResponseResult<T>();
		responseBody.setSuccess(success);
		responseBody.setData(data);
		responseBody.setMsg(message);
		responseBody.setCode(code);
		return responseBody;
	}
}
