package com.zero.eprocurement.app.user.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户查询dto")
public class UserQueryDto {

	@ApiModelProperty(value = "用户姓名")
	private String name;

}
