package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfiguration;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServiceConfiguration.class)
class OrderServiceImplTest {
    @MockBean
    private OrderDao orderDao;
    @MockBean
    private UserService userService;
    @MockBean
    private GiftCertificateService giftCertificateService;
    @Autowired
    private OrderService orderService;
    private static Order order1;
    private static Order order2;
    private static OrderDto orderDto1;
    private static OrderDto orderDto2;
    private static OrderDto orderDto3;
    private static OrderDto orderDto4;
    private static GiftCertificateDto giftCertificateDto1;
    private static UserDto userDto1;
    private static PageDto pageDto1;
    private static PageDto pageDto2;

    @BeforeAll
    static void setUp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        order1 = Order.builder()
                .id(1L)
                .createDate(zonedDateTime)
                .price(new BigDecimal("100"))
                .userId(1L)
                .certificateId(2L)
                .build();
        order2 = Order.builder()
                .id(1L)
                .createDate(zonedDateTime)
                .price(new BigDecimal("1000"))
                .userId(1L)
                .certificateId(2L)
                .build();
        orderDto1 = OrderDto.builder()
                .id(1L)
                .createDate(zonedDateTime)
                .price(new BigDecimal("100"))
                .userId(1L)
                .certificateId(2L)
                .build();
        orderDto2 = OrderDto.builder()
                .userId(1L)
                .certificateId(2L)
                .build();
        orderDto3 = OrderDto.builder()
                .id(1L)
                .createDate(zonedDateTime)
                .price(new BigDecimal("1000"))
                .userId(1L)
                .certificateId(2L)
                .build();
        orderDto4 = OrderDto.builder()
                .id(-1L)
                .userId(1L)
                .certificateId(2L)
                .build();
        giftCertificateDto1 = GiftCertificateDto.builder()
                .name("Cinema")
                .description("Best cinema in the city")
                .price(new BigDecimal(1000))
                .duration(5)
                .createDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 12, 12, 0, 0),ZoneId.systemDefault()))
                .lastUpdateDate(ZonedDateTime.of(LocalDateTime.of(2020, 12, 13, 12, 0, 0),ZoneId.systemDefault()))
                .build();
        userDto1 = UserDto.builder()
                .id(1L)
                .name("Oleg")
                .build();
        pageDto1 = PageDto.builder()
                .number(1)
                .size(5)
                .build();
        pageDto2 = PageDto.builder()
                .number(-3)
                .size(3)
                .build();
    }

    @AfterAll
    static void tearDown() {
        order1 = null;
        order2 = null;
        orderDto1 = null;
        orderDto2 = null;
        orderDto3 = null;
        orderDto4 = null;
        giftCertificateDto1 = null;
        userDto1 = null;
        pageDto1 = null;
        pageDto2 = null;
    }

    @Test
    void addOrderCorrectDataShouldReturnOrderDtoTest() {
        when(giftCertificateService.findById(any(long.class))).thenReturn(giftCertificateDto1);
        when(userService.findById(any(long.class))).thenReturn(userDto1);
        when(orderDao.create(any(Order.class))).thenReturn(order2);
        OrderDto actual = orderService.create(orderDto2);
        assertEquals(orderDto3, actual);
    }

    @Test
    void addOrderCorrectDataShouldThrowExceptionTest() {
        when(giftCertificateService.findById(any(long.class))).thenReturn(giftCertificateDto1);
        when(userService.findById(any(long.class))).thenReturn(userDto1);
        when(orderDao.create(any(Order.class))).thenReturn(order2);

        assertDoesNotThrow( () -> orderService.create(orderDto4));
    }

    @Test
    void findOrderByIdCorrectDataShouldReturnOrderDtoTest() {
        long id = 1;
        when(orderDao.findById(any(long.class))).thenReturn(Optional.of(order1));
        OrderDto actual = orderService.findById(id);
        assertEquals(orderDto1, actual);
    }

   @ParameterizedTest
   @ValueSource(longs = {1,2,3,4,1000000})
    void findOrderByIdCorrectDataShouldThrowExceptionTest(long id) {
        when(orderDao.findById(any(long.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.findById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1,-2,-3,-4,-1000000})
    void findOrderByIdIncorrectDataShouldThrowExceptionTest(long id) {
        when(orderDao.findById(any(long.class))).thenReturn(Optional.of(order1));
        assertDoesNotThrow( () -> orderService.findById(id));
    }

    @Test
    void findOrdersByUserIdCorrectDataShouldReturnListOfOrderDtoTest() {
        int expected = 2;
        long userId = 1;
        when(orderDao.findByUserId(any(long.class), any(Page.class))).thenReturn(Arrays.asList(order1, order2));
        List<OrderDto> actual = orderService.findByUserId(userId, pageDto1);
        assertEquals(expected, actual.size());
    }

    @Test
    void findOrdersByUserIdIncorrectDataShouldThrowExceptionTest() {
        long userId = 1;
        when(orderDao.findByUserId(any(long.class), any(Page.class))).thenReturn(Arrays.asList(order1, order2));
        assertDoesNotThrow( () -> orderService.findByUserId(userId, pageDto2));
    }
}
