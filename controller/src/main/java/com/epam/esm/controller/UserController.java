package com.epam.esm.controller;


import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.LinkMapperFacade;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/v2/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private static final int MIN_ID_VALUE =1;
    private final UserService userService;
    private final LinkMapperFacade linkMapper;

    /**
     * Find all list.
     *
     * @param page the number of page
     * @param size the size
     * @return the list of user's dto
     */
    @GetMapping
    public List<UserDto> findAll(@RequestParam(required = false, defaultValue = "1") @Range(min = 1) int page,
                                 @RequestParam(required = false, defaultValue = "5") @Range(min = 1) int size) {
        PageDto pageDto = new PageDto(page, size);
        List<UserDto> userDtoList = userService.findAll(pageDto);
        userDtoList.forEach(linkMapper::mapLinks);
        return userDtoList;
    }

    /**
     * Find by id user dto.
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable @Min(MIN_ID_VALUE) Long id) {
        UserDto userDto = userService.findById(id);
        linkMapper.mapLinks(userDto);
        return userDto;
    }


}
