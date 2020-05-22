package com.zero.base.common.core;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 * Entity基类
 * 
 */
public class BaseEntity implements Serializable {

	@ApiModelProperty(value = "序列号", name = "searchValue")
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "创建者", name = "createBy")
	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", name = "createTime")
	private Date createTime;

	@ApiModelProperty(value = "更新者", name = "updateBy")
	private String updateBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间", name = "updateTime")
	private Date updateTime;

	@ApiModelProperty(value = "备注", name = "remark")
	private String remark;

	@ApiModelProperty(value = "请求参数", name = "remark")
	private Map<String, Object> params;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
