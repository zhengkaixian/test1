package com.zero.eprocurement.app.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zero.eprocurement.app.user.domain.dto.UserQueryDto;
import com.zero.eprocurement.app.user.domain.dto.UserResultDto;
import com.zero.eprocurement.app.user.mapper.UserMapper;
import com.zero.eprocurement.app.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<UserResultDto> queryUserList(UserQueryDto userQueryDto) {
		return userMapper.queryUserList(userQueryDto);
	}

}
