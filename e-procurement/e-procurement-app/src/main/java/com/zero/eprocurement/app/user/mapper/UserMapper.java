package com.zero.eprocurement.app.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zero.eprocurement.app.user.domain.dto.UserQueryDto;
import com.zero.eprocurement.app.user.domain.dto.UserResultDto;

@Mapper
public interface UserMapper {
	
	/**
	 * 查询用户列表信息
	 * @param userQueryDto
	 * @return
	 */
	List<UserResultDto> queryUserList(UserQueryDto userQueryDto);
	
}
