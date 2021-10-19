package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServicePageMapper;
import com.epam.esm.mapper.ServiceUserMapper;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ServiceUserMapper userMapper;
    private final ServicePageMapper pageMapper;
    private final UserDao userDao;

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> user = userDao.findById(id);
        return user.map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));

    }

    @Override
    public List<UserDto> findAll(PageDto pageDto) {
        Page page = pageMapper.toEntity(pageDto);
        List<User> foundUsers = userDao.findAll(page);
        System.out.println(foundUsers);
        return foundUsers.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
