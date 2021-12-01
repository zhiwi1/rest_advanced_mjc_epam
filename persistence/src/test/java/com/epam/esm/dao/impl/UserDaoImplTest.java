//package com.epam.esm.dao.impl;
//
//
//import com.epam.esm.config.TestDatabaseConfig;
//import com.epam.esm.dao.UserDao;
//import com.epam.esm.entity.User;
//import com.epam.esm.util.Page;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Deprecated(since = "version 3")
//@SpringBootTest(classes = TestDatabaseConfig.class)
//class UserDaoImplTest {
//
//    private final UserDao userDao;
//
//    @Autowired
//    public UserDaoImplTest(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    public static Object[][] createUsers() {
//        return new Object[][]{
//                {new User("Oleg"), 1L},
//                {new User("Gleb"), 2L},
//                {new User("Ivan"), 3L}
//        };
//    }
//
//    @Test
//    void findAllCorrectDataShouldReturnListOfUsersTest() {
//        long expected = 3;
//        Page page1 = Page.builder()
//                .number(1)
//                .size(5)
//                .build();
//        List<User> actual = userDao.findAll(page1);
//        assertEquals(expected, actual.size());
//    }
//
//    @Test
//    void findAllCorrectDataShouldReturnEmptyListTest() {
//        Page page2 = Page.builder()
//                .number(3)
//                .size(3)
//                .build();
//        List<User> actual = userDao.findAll(page2);
//        assertTrue(actual.isEmpty());
//    }
//
//    @Test
//    void findAllIncorrectDataShouldThrowExceptionTest() {
//        Page page3 = Page.builder()
//                .number(-3)
//                .size(3)
//                .build();
//        assertThrows(IllegalArgumentException.class, () -> userDao.findAll(page3));
//    }
//
//    @ParameterizedTest
//    @MethodSource("createUsers")
//    void findByIdCorrectDataShouldReturnUserOptionalTest(User user, Long id) {
//        Optional<User> actual = userDao.findById(id);
//        assertEquals(Optional.of(user), actual);
//    }
//
//    @ParameterizedTest
//    @ValueSource(longs = {100L, Long.MAX_VALUE, 2123213321L})
//    void findByIdCorrectDataShouldReturnEmptyOptionalTest(long id) {
//        Optional<User> actual = userDao.findById(id);
//        assertFalse(actual.isPresent());
//    }
//
//
//}