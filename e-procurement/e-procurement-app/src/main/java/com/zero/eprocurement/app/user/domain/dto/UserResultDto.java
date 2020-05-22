package com.zero.eprocurement.app.user.domain.dto;

import com.zero.eprocurement.app.common.core.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户结果dto")
public class UserResultDto extends BaseEntity {

	@ApiModelProperty(value = "序列号")
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private String id;

	@ApiModelProperty(value = "用户姓名")
	private String name;

	@ApiModelProperty(value = "用户年龄")
	private String age;

}
