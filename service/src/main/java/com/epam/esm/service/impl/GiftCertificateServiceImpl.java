package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceGiftCertificateMapper;
import com.epam.esm.mapper.ServiceGiftCertificateQueryParamMapper;
import com.epam.esm.mapper.ServicePageMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateQueryParam;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final ServiceGiftCertificateMapper certificateMapper;
    private final ServicePageMapper pageMapper;
    private final ServiceGiftCertificateQueryParamMapper queryParamMapper;

    @Transactional
    @Override
    public GiftCertificateDto create(GiftCertificateInputDto giftCertificateInputDto) {
        Optional<GiftCertificate> existingCertificate = giftCertificateDao.findByName(giftCertificateInputDto.getName());
        if (existingCertificate.isPresent()) {
            throw new DublicateResourceException(giftCertificateInputDto.getName());
        }
        GiftCertificate certificate = certificateMapper.toEntity(giftCertificateInputDto);
        GiftCertificate addedCertificate = giftCertificateDao.create(certificate);
        return certificateMapper.toDto(addedCertificate);
    }

    @Override
    public List<GiftCertificateDto> findAll(PageDto pageDto) {
        throw new UnsupportedOperationException(
                String.format("Can't execute findAll(%s)This operation not supported in this implementation",
                        pageDto.toString()
                )
        );
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
        return foundGiftCertificate
                .map(certificateMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }


    @Override
    public List<GiftCertificateDto> findGiftCertificates(
            GiftCertificateQueryParamDto giftCertificateQueryParametersDto, PageDto pageDto) {
        Page page = pageMapper.toEntity(pageDto);
        GiftCertificateQueryParam queryParam = queryParamMapper.toEntity(giftCertificateQueryParametersDto);
        List<GiftCertificate> foundGiftCertificates =
                giftCertificateDao.findByQueryParameters(queryParam, page);
        return foundGiftCertificates.stream()
                .map(certificateMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public GiftCertificateDto update(Long id, GiftCertificateInputDto giftCertificateDto) {
        GiftCertificateDto foundGiftCertificateDto = findById(id);
        updateFields(foundGiftCertificateDto, giftCertificateDto);
        GiftCertificate foundGiftCertificate = certificateMapper.toEntity(foundGiftCertificateDto);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(foundGiftCertificate);
        return certificateMapper.toDto(updatedGiftCertificate);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Optional<GiftCertificate> certificate = giftCertificateDao.findById(id);
        if (certificate.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        giftCertificateDao.delete(id);
    }


    private void updateFields(GiftCertificateDto foundCertificate, GiftCertificateInputDto receivedGiftCertificate) {
        Optional.ofNullable(receivedGiftCertificate.getName()).ifPresent(foundCertificate::setName);
        Optional.ofNullable(receivedGiftCertificate.getDescription()).ifPresent(foundCertificate::setDescription);
        Optional.ofNullable(receivedGiftCertificate.getPrice()).ifPresent(foundCertificate::setPrice);
        Optional.of(receivedGiftCertificate.getDuration()).filter(duration -> duration != 0).ifPresent(foundCertificate::setDuration);
    }
}

