package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Create order dto.
     *
     * @param orderDto the order dto
     * @return created order dto
     */
    OrderDto create(OrderDto orderDto);

    /**
     * Find by id order dto.
     *
     * @param id the id
     * @return founded order dto
     */
    OrderDto findById(long id);

    /**
     * Find by user id list.
     *
     * @param userId  the user id
     * @param pageDto the page dto
     * @return the list
     */
    List<OrderDto> findByUserId(long userId, PageDto pageDto);
}
