package com.zero.eprocurement.app.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zero.eprocurement.app.common.core.ResponseResult;
import com.zero.eprocurement.app.common.core.ResponseUtil;
import com.zero.eprocurement.app.user.domain.dto.UserQueryDto;
import com.zero.eprocurement.app.user.domain.dto.UserResultDto;
import com.zero.eprocurement.app.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户控制层")
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/queryUserList", method = RequestMethod.POST)
	@ApiOperation(value = "查询用户信息列表")
	public @ResponseBody ResponseResult<List<UserResultDto>> queryUserList(@RequestBody UserQueryDto userQueryDto) {
		List<UserResultDto> userResultDtos = userService.queryUserList(userQueryDto);
		return ResponseUtil.genSuccess(userResultDtos, "查询用户信息成功!");
	}

}
