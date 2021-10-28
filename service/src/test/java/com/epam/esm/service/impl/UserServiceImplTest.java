package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfiguration;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServiceConfiguration.class)
class UserServiceImplTest {

    @MockBean
    private UserDao userDao;
    @Autowired
    private UserService userService;


    private static PageDto pageDto1;
    private static PageDto pageDto2;

    @BeforeAll
    static void setUp() {
        pageDto1 = PageDto.builder()
                .number(1)
                .size(5)
                .build();
        pageDto2 = PageDto.builder()
                .number(-3)
                .size(3)
                .build();
    }

    public static Object[][] createUsers() {
        return new Object[][]{
                {new User( "ab"), new User( "ab")},
                {new User( "Ivan"), new User( "Oleg")},
                {new User( "aaaa"), new User( "bbbb")}
        };
    }

    @ParameterizedTest
    @MethodSource("createUsers")
    void findAllUsersCorrectDataShouldReturnListOfUserDtoTest(User user1, User user2) {
        int expected = 2;
        when(userDao.findAll(any(Page.class))).thenReturn(Arrays.asList(user1, user2));
        List<UserDto> actual = userService.findAll(pageDto1);
        assertEquals(expected, actual.size());
    }

    @ParameterizedTest
    @MethodSource("createUsers")
    void findAllUsersIncorrectDataShouldThrowExceptionTest(User user1, User user2) {
        when(userDao.findAll(any(Page.class))).thenReturn(Arrays.asList(user1, user2));
        assertDoesNotThrow(() -> userService.findAll(pageDto2));
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 10, 111123123})
    void findUserByIdCorrectDataShouldReturnUserDtoTest(long id) {
        User user = new User( "Ivan");
        user.setId(1);
        UserDto expected = new UserDto(1L, "Ivan");
        when(userDao.findById(any(long.class))).thenReturn(Optional.of(user));
        UserDto actual = userService.findById(id);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 10, 111123123})
    void findUserByIdCorrectDataShouldThrowExceptionTest(long id) {
        when(userDao.findById(any(long.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, -10, -111123123})
    void findUserByIdIncorrectDataShouldThrowExceptionTest(long id) {
        User user = new User( "Ivan");
        user.setId(1);
        when(userDao.findById(any(long.class))).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.findById(id));
    }

}
