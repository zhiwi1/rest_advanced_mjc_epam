package com.epam.esm.dao.impl;

import com.epam.esm.config.DatabaseConfig;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DatabaseConfig.class)
@Transactional
class OrderDaoImplTest {
    private final OrderDao orderDao;


    @Autowired
    public OrderDaoImplTest(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Test
    void addCorrectDataShouldReturnOrderTest() {
        Order order = Order.builder()
                .createDate(ZonedDateTime.now())
                .price(new BigDecimal("100"))
                .userId(1L)
                .certificateId(2L)
                .build();
        Order actual = orderDao.create(order);
        assertNotNull(actual);
    }

    @Test
    void addCorrectDataShouldSetIdTest() {
        long expected = 5;
        Order order = Order.builder()
                .createDate(ZonedDateTime.now())
                .price(new BigDecimal("100"))
                .userId(1L)
                .certificateId(2L)
                .build();
        orderDao.create(order);
        assertEquals(expected, order.getId());
    }

    @Test
    void addCorrectDataShouldThrowExceptionTest() {
        Order order = Order.builder()
                .id(1L)
                .createDate(ZonedDateTime.now())
                .price(new BigDecimal("100"))
                .userId(1L)
                .certificateId(2L)
                .build();
        assertThrows(InvalidDataAccessApiUsageException.class, () -> orderDao.create(order));
    }

    @Test
    void addIncorrectDataShouldThrowExceptionTest() {
        Order order = Order.builder()
                .createDate(ZonedDateTime.now())
                .price(new BigDecimal("100000000000"))
                .userId(1L)
                .certificateId(2L)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> orderDao.create(order));
    }


    @Test
    void findByIdCorrectDataShouldReturnOrderOptionalTest() {
        Order order = Order.builder()
                .id(1L)
                .createDate(ZonedDateTime.of(LocalDateTime.of(2019, 1, 1, 21, 0, 0), ZoneId.systemDefault()))
                .price(new BigDecimal("1000"))
                .userId(1L)
                .certificateId(2L)
                .build();
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

        List<Order> actual = orderDao.findByUserId(userId, page);
        assertEquals(expected, actual.size());
    }

    @Test
    void findByUserIdCorrectDataShouldReturnEmptyListTest() {
        long userId = 1;
        Page page = Page.builder()
                .number(3)
                .size(3)
                .build();
        List<Order> actual = orderDao.findByUserId(userId, page);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findByUserIdIncorrectDataShouldThrowExceptionTest() {
        long userId = 1;
       Page page = Page.builder()
                .number(-3)
                .size(3)
                .build();
        assertThrows(InvalidDataAccessApiUsageException.class, () -> orderDao.findByUserId(userId, page));
    }
}
