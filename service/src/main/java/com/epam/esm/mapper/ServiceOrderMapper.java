package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInputDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceOrderMapper {
    private final ModelMapper modelMapper;

    public Order toEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public OrderDto toDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    public Order toEntity(OrderInputDto giftCertificateCreateDto) {
        return modelMapper.map(giftCertificateCreateDto, Order.class);
    }
}
