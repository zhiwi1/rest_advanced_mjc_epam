package com.epam.esm.service.impl;

import com.epam.esm.dao.datajpa.DataUserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceUserMapper;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ServiceUserMapper userMapper;
    private final DataUserDao userDao;

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException(
                String.format("Can't execute delete(%d).This operation not supported in this implementation", id
                )
        );
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> user = userDao.findById(id);
        return user.map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));

    }

    @Override
    public List<UserDto> findAll(PageDto pageDto) {
        Pageable pageRequest = PageRequest.of(pageDto.getNumber(), pageDto.getSize());
        List<User> foundUsers = userDao.findAll(pageRequest).getContent();
        return foundUsers.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserDto value) {
        throw new UnsupportedOperationException("Method is not supported by current implementation");
    }

    @Override
    @Transactional
    public UserDto createIfNotExist(UserDto userDto) {
        Optional<User> optional = userDao.findByName(userDto.getName());
        System.out.println(optional);
        System.out.println( optional.get().getId());
        if (optional.isEmpty()) {
            return userMapper.toDto(userDao.save(userMapper.toEntity(userDto)));
        }
        return userMapper.toDto(optional.get());

    }

}
