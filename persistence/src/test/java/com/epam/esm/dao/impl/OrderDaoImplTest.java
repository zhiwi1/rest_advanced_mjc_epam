package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Deprecated(since = "version 3")
@SpringBootTest(classes = TestDatabaseConfig.class)
@Transactional
class OrderDaoImplTest {
    private final OrderDao orderDao;


    @Autowired
    public OrderDaoImplTest(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Test
    void addCorrectDataShouldThrowExceptionReturnOrderTest() {
        Order order = Order.builder()
                .createDate(ZonedDateTime.now())
                .price(new BigDecimal("100"))
                .certificates(new ArrayList<>())
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> orderDao.create(order));
    }

    @Test
    void addIncorrectDataShouldThrowExcepTest() {
        Order order = Order.builder()
                .price(new BigDecimal("100"))
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> orderDao.create(order));
    }

    @Test
    void addIncorrect2DataShouldThrowExceptionTest() {
        Order order = Order.builder()
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> orderDao.create(order));
    }

    @Test
    void addIncorrectDataShouldThrowExceptionTest() {
        Order order = Order.builder()
                .createDate(ZonedDateTime.now())
                .price(new BigDecimal("100000000000"))
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> orderDao.create(order));
    }


    @Test
    void findByIdCorrectDataShouldReturnOrderOptionalTest() {
        Order order = Order.builder()
                .createDate(ZonedDateTime.of(LocalDateTime.of(2019, 1, 1, 21, 0, 0), ZoneId.systemDefault()))
                .price(new BigDecimal("1000"))
                .user(new User("Oleg"))
                .build();
        order.setId(1);
        long id = 1;
        Optional<Order> actual = orderDao.findById(id);
        assertEquals(Optional.of(order), actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnEmptyOptionalTest() {
        long id = 8;
        Optional<Order> actual = orderDao.findById(id);
        assertFalse(actual.isPresent());
    }

    @Test
    void findByUserIdCorrectDataShouldReturnListOfOrdersTest() {
        long userId = 1;
        long expected = 2;
        Page page = Page.builder()
                .number(1)
                .size(5)
                .build();
        User user = new User("Oleg");
        user.setId(userId);
        List<Order> actual = orderDao.findByUser(user, page);
        assertEquals(expected, actual.size());
    }

    @Test
    void findByUserIdCorrectDataShouldThrowExceptionTest() {
        User user = new User("ivan");
        Page page = Page.builder()
                .number(3)
                .size(3)
                .build();
        assertThrows(IllegalStateException.class, () -> orderDao.findByUser(user, page));

    }

    @Test
    void findByUserIdIncorrectDataShouldThrowExceptionTest() {
        long userId = 1;
        Page page = Page.builder()
                .number(-3)
                .size(3)
                .build();
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> orderDao.findByUser(user, page));
    }
}
