package com.epam.esm.service.impl;

import com.epam.esm.dao.datajpa.DataGiftCertificateDao;
import com.epam.esm.dao.datajpa.DataOrderDao;
import com.epam.esm.dao.datajpa.DataUserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInputDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceOrderMapper;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final DataOrderDao orderDao;
    private final DataGiftCertificateDao certificateDao;
    private final DataUserDao userDao;
    private final ServiceOrderMapper mapper;

    @Transactional
    @Override
    public OrderDto create(OrderInputDto orderDto) {
        Order order = mapper.toEntity(orderDto);
        Optional<User> existingUser = userDao.findById(orderDto.getUserId());
        if (existingUser.isEmpty()) {
            throw new ResourceNotFoundException(orderDto.getUserId());
        }
        order.setUser(existingUser.get());
        List<GiftCertificate> certificates = new ArrayList<>();

        Arrays.asList(orderDto.getCertificateId()).forEach(id -> {
            Optional<GiftCertificate> optional = certificateDao.findById(id);
            if (optional.isEmpty()) {
                throw new ResourceNotFoundException(orderDto.getUserId());
            }
            log.info(optional.get().toString());
            certificates.add(optional.get());
            orderDto.setPrice(orderDto.getPrice().add(optional.get().getPrice()));
        });
        order.setCertificates(certificates);
        order.setPrice(orderDto.getPrice());
        order.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        Order addedOrder = orderDao.save(order);
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
        Optional<User> existingUser = userDao.findById(userId);
        if (existingUser.isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }
        Pageable page = PageRequest.of(pageDto.getNumber(), pageDto.getSize());
        List<Order> foundOrders = orderDao.findAllByUser(existingUser.get(), page);
        return foundOrders.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
