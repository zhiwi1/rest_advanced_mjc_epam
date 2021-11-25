package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInputDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.expression.HasPermissionToFindByUserId;
import com.epam.esm.hateoas.LinkMapperFacade;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping("/v3/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private static final int MIN_ID_VALUE = 1;
    private final OrderService orderService;
    private final LinkMapperFacade linkMapper;


    /**
     * Find by id order dto.
     *
     * @param id the id
     * @return the order dto
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public OrderDto findById(@PathVariable @Min(MIN_ID_VALUE) long id) {
        OrderDto orderDto = orderService.findById(id);
        linkMapper.mapLinks(orderDto);
        return orderDto;

    }

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @param page   the number of page
     * @param size   the size
     * @return the list with orderDto
     */
    @GetMapping("/users/{userId}")

    @HasPermissionToFindByUserId
    public List<OrderDto> findByUserId(@PathVariable @Min(MIN_ID_VALUE) Long userId,
                                       @RequestParam(required = false, defaultValue = "0") @Range(min = 0) int page,
                                       @RequestParam(required = false, defaultValue = "5") @Range(min = 0) int size) {
        PageDto pageDto = new PageDto(page, size);
        List<OrderDto> orderDtos = orderService.findByUserId(userId, pageDto);
        orderDtos.forEach(linkMapper::mapLinks);
        return orderDtos;
    }

    /**
     * Create order dto.
     *
     * @param orderInputDto the order dto
     * @return the created order dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody @Valid OrderInputDto orderInputDto, Principal principal) {
        var principalToken = (KeycloakAuthenticationToken) principal;
        String nameOfUser = principalToken.getAccount().getKeycloakSecurityContext().getToken().getPreferredUsername();
        orderInputDto.setNameOfUser(nameOfUser);
        OrderDto createdOrderDto = orderService.create(orderInputDto);
        linkMapper.mapLinks(createdOrderDto);
        return createdOrderDto;
    }
}