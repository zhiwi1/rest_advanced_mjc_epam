package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderDto create(OrderDto orderDto) {

//       Optional<GiftCertificate> foundGiftCertificateDto
//                = giftCertificateDao.findById(orderDto.getGiftCertificateId());
//        Optional<User> user =userDao.findById(orderDto.getUserId());
        Order order = modelMapper.map(orderDto, Order.class);
        order.setUserId(orderDto.getUserId());
        order.setGiftCertificateId(orderDto.getGiftCertificateId());
        order.setPrice(orderDto.getPrice());
        order.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        Order addedOrder = orderDao.create(order);
        System.out.println(addedOrder);
        return modelMapper.map(addedOrder, OrderDto.class);
    }

    @Override
    public OrderDto findById(long id)  {
        Optional<Order> foundOrderOptional = orderDao.findById(id);
        return foundOrderOptional
                .map(foundOrder -> modelMapper.map(foundOrder, OrderDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<OrderDto> findByUserId(long userId, PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        List<Order> foundOrders = orderDao.findByUserId(userId, page);
        return foundOrders.stream()
                .map(foundOrder -> modelMapper.map(foundOrder, OrderDto.class))
                .collect(Collectors.toList());
    }
}
