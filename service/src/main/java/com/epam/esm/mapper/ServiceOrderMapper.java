package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInputDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Entity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServiceOrderMapper {
    private final ModelMapper modelMapper;

    public Order toEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public OrderDto toDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        if (order.getCertificates() != null) {
            List<Long> idsOfCertificates = (order.getCertificates().stream()
                    .map(Entity::getId).collect(Collectors.toList()));
            orderDto.setCertificateId(idsOfCertificates);
        }
        return orderDto;
    }

    public Order toEntity(OrderInputDto giftCertificateCreateDto) {
        return modelMapper.map(giftCertificateCreateDto, Order.class);
    }
}
