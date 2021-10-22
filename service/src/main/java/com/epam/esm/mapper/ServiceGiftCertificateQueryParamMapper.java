package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQueryParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceGiftCertificateQueryParamMapper {
    private final ModelMapper modelMapper;
    public GiftCertificateQueryParam toEntity(GiftCertificateQueryParamDto dto){
     return    modelMapper.map(dto, GiftCertificateQueryParam.class);

    }
}
