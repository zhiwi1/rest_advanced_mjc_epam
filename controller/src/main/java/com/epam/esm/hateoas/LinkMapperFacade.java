package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
//import com.epam.esm.controller.UserController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.mapper.ServiceGiftCertificateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class LinkMapperFacade {
    private final ServiceGiftCertificateMapper certificateMapper;

    public void mapLinks(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).findById(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).delete(tagDto.getId())).withRel("delete"));
        tagDto.add(linkTo(methodOn(TagController.class).create(new TagCreateDto(tagDto.getName()))).withRel("create"));
    }

    public void mapLinks(List<TagDto> tags) {
        tags.forEach(this::mapLinks);
    }

    public void mapLinks(Set<TagDto> tags) {
        tags.forEach(this::mapLinks);
    }

    public void mapLinks(GiftCertificateDto certificateDto) {
        certificateDto.add(
                linkTo(methodOn(GiftCertificateController.class)
                        .findById(certificateDto.getId()))
                        .withSelfRel()
        );
        Optional.ofNullable(certificateDto.getTags()).ifPresent(this::mapLinks);
        certificateDto.add(
                (linkTo(methodOn(GiftCertificateController.class)
                        .create(certificateMapper.toDto(certificateDto)))
                        .withRel("create"))
        );
        certificateDto.add(
                (linkTo(
                        methodOn(GiftCertificateController.class)
                                .update(certificateDto.getId(), certificateMapper.toDto(certificateDto)))
                        .withRel("update"))
        );

        certificateDto.add(
                (linkTo(
                        methodOn(GiftCertificateController.class)
                                .delete(certificateDto.getId()))
                        .withRel("delete"))
        );
    }


    public void mapLinks(UserDto userDto) {
        userDto.add(
                linkTo(
                        methodOn(UserController.class)
                                .findById(userDto.getId())).withSelfRel()
        );

    }
    public void mapLinks(OrderDto orderDto) {
        orderDto.add(
                linkTo(methodOn(OrderController.class).findById(orderDto.getId()))
                        .withSelfRel()
        );
    }
}
