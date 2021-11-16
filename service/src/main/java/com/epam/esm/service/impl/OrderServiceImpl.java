package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInputDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceOrderMapper;
import com.epam.esm.mapper.ServicePageMapper;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final GiftCertificateDao certificateDao;
    private final UserDao userDao;
    private final ServiceOrderMapper mapper;
    private final ServicePageMapper pageMapper;


    @Transactional
    @Override
    public OrderDto create(OrderInputDto orderDto) {
        Order order = mapper.toEntity(orderDto);
        Optional<GiftCertificate> existingCertificate = certificateDao.findById(orderDto.getCertificateId());
        if (existingCertificate.isEmpty()) {
            throw new ResourceNotFoundException(orderDto.getCertificateId());
        }
        Optional<User> existingUser = userDao.findById(orderDto.getUserId());
        if (existingUser.isEmpty()) {
            throw new ResourceNotFoundException(orderDto.getUserId());
        }
        order.setUser(existingUser.get());
        order.setCertificateId(orderDto.getCertificateId());
        order.setPrice(orderDto.getPrice());
        order.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        Order addedOrder = orderDao.create(order);
        return mapper.toDto(addedOrder);
    }

    @Override
    public OrderDto findById(long id) {
        Optional<Order> foundOrderOptional = orderDao.findById(id);
        return foundOrderOptional
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<OrderDto> findByUserId(long userId, PageDto pageDto) {
        Page page = pageMapper.toEntity(pageDto);
        Optional<User> existingUser = userDao.findById(userId);
        if (existingUser.isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }
        List<Order> foundOrders = orderDao.findByUser(existingUser.get(), page);
        return foundOrders.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
