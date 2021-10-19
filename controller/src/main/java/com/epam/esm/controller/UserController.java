package com.epam.esm.controller;


import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.LinkMapper;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final LinkMapper linkMapper;

    @GetMapping
    public List<UserDto> findAll(@RequestParam(required = false, defaultValue = "1")  @Range(min = 1) int page,
                                 @RequestParam(required = false, defaultValue = "5")  @Range(min = 1) int size) {
        PageDto pageDto = new PageDto(page, size);
        List<UserDto> userDtoList = userService.findAll(pageDto);
        userDtoList.forEach(linkMapper::mapLinks);
        return userDtoList;
    }

    @GetMapping("/{id:\\d+}")
    public UserDto findById(@PathVariable @Range(min = 0) Long id) {
        UserDto userDto = userService.findById(id);
        linkMapper.mapLinks(userDto);
        return userDto;
    }


}
