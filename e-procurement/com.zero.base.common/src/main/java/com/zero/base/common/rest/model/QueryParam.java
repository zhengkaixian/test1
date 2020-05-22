package com.zero.base.common.rest.model;

import java.io.Serializable;

import javax.validation.Valid;

import io.swagger.annotations.ApiModelProperty;

public class QueryParam<T> implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -9194240925819714451L;

	/**
	 * @fieldName: param
	 * @fieldType: T
	 * @Description: 查询参数
	 */
	@ApiModelProperty(value = "具体参数")
	@Valid
	private T param;

	/**
	 * @fieldName: pageSize
	 * @fieldType: int
	 * @Description: 每页显示条数
	 */
	@ApiModelProperty(value = "每页的数量")
	private int pageSize;

	/**
	 * @fieldName: pageNum
	 * @fieldType: int
	 * @Description: 页数
	 */
	@ApiModelProperty(value = "当前页")
	private int pageNum;

	public QueryParam() {
	}

	public QueryParam(int pageNum, int pageSize, T param) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.param = param;
	}

	@Override
	public String toString() {
		return "QueryParam [param=" + param + ", pageSize=" + pageSize + ", pageNum=" + pageNum + "]";
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
