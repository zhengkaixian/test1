package com.zero.eprocurement.app.user.service;

import java.util.List;

import com.zero.eprocurement.app.user.domain.dto.UserQueryDto;
import com.zero.eprocurement.app.user.domain.dto.UserResultDto;

public interface UserService {
	
	/**
	 * 查询用户列表信息
	 * @param userQueryDto
	 * @return
	 */
	List<UserResultDto> queryUserList(UserQueryDto userQueryDto);
	
}
