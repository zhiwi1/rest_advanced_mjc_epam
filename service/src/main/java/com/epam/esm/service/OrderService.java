package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;

import java.util.List;

public interface OrderService {
    OrderDto create(OrderDto orderDto);

    OrderDto findById(long id);

    List<OrderDto> findByUserId(long userId, PageDto pageDto);
}
