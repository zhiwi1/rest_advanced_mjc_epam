package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.hateoas.LinkMapper;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v2/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;
    private final LinkMapper linkMapper;

    @GetMapping("/{id:\\d+}")
    public OrderDto findById(@PathVariable @Range(min = 1) long id) {
        OrderDto orderDto = orderService.findById(id);
        linkMapper.mapLinks(orderDto);
        return orderDto;

    }

    @GetMapping("/users/{userId:\\d+}")
    public List<OrderDto> findByUserId(@PathVariable @Range(min = 1) long userId,
                                       @RequestParam(required = false, defaultValue = "1") @Range(min = 0) int page,
                                       @RequestParam(required = false, defaultValue = "5") @Range(min = 0) int size) {
        PageDto pageDto = new PageDto(page, size);
        List<OrderDto> orderDtos = orderService.findByUserId(userId, pageDto);
        orderDtos.forEach(linkMapper::mapLinks);
        return orderDtos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody @Valid OrderDto orderDto) {
        OrderDto createdOrderDto = orderService.create(orderDto);
        linkMapper.mapLinks(createdOrderDto);
        return createdOrderDto;
    }
}