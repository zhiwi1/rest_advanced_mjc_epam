package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

public interface UserService extends BaseService<UserDto,Long>{
    UserDto createIfNotExist(UserDto userDto);
}
