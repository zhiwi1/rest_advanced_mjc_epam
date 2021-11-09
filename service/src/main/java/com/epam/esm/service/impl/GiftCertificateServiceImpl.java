package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.datajpa.DataGiftCertificateDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceGiftCertificateMapper;
import com.epam.esm.mapper.ServiceGiftCertificateQueryParamMapper;
import com.epam.esm.mapper.ServicePageMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateQueryParam;
import com.epam.esm.util.GiftCertificateSpecification;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final DataGiftCertificateDao giftCertificateDao;
    private final ServiceGiftCertificateMapper certificateMapper;
    private final ServiceGiftCertificateQueryParamMapper queryParamMapper;

    @Transactional
    @Override
    public GiftCertificateDto create(GiftCertificateInputDto giftCertificateInputDto) {
        isCertificateDublicate(giftCertificateInputDto);
        GiftCertificate certificate = certificateMapper.toEntity(giftCertificateInputDto);
        GiftCertificate addedCertificate = giftCertificateDao.save(certificate);
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
    public GiftCertificateDto create(GiftCertificateDto value) {
        return null;
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
            GiftCertificateQueryParamDto params, PageDto pageDto) {
        Pageable page = PageRequest.of(pageDto.getNumber(), pageDto.getSize());
        Specification<GiftCertificate> specification = GiftCertificateSpecification.nameLike(params.getName())
                .and(GiftCertificateSpecification.descriptionLike(params.getDescription()))
                .and(GiftCertificateSpecification.findByTags(params.getTagNames()))
                .and(GiftCertificateSpecification.havingOrderAndSort(params.getOrderType(), params.getSortType()));

        List<GiftCertificate> foundGiftCertificates =
                giftCertificateDao.findAll(specification, page).getContent();
        return foundGiftCertificates.stream().
                map(certificateMapper::toDto).
                collect(Collectors.toList());
    }

    @Transactional
    @Override
    public GiftCertificateDto update(Long id, GiftCertificateInputDto giftCertificateDto) {
        GiftCertificateDto foundGiftCertificateDto = findById(id);
        updateFields(foundGiftCertificateDto, giftCertificateDto);
        GiftCertificate foundGiftCertificate = certificateMapper.toEntity(foundGiftCertificateDto);
        GiftCertificate updatedGiftCertificate = giftCertificateDao.save(foundGiftCertificate);
        return certificateMapper.toDto(updatedGiftCertificate);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        isCertificateExists(id);
        giftCertificateDao.deleteById(id);
    }


    private void updateFields(GiftCertificateDto foundCertificate, GiftCertificateInputDto receivedGiftCertificate) {
        Optional.ofNullable(receivedGiftCertificate.getName()).ifPresent(foundCertificate::setName);
        Optional.ofNullable(receivedGiftCertificate.getDescription()).ifPresent(foundCertificate::setDescription);
        Optional.ofNullable(receivedGiftCertificate.getPrice()).ifPresent(foundCertificate::setPrice);
        Optional.of(receivedGiftCertificate.getDuration()).filter(duration -> duration != 0).ifPresent(foundCertificate::setDuration);
    }

    private void isCertificateDublicate(GiftCertificateInputDto certificateInputDto) {
        Optional<GiftCertificate> existingCertificateOptional = giftCertificateDao.findByName(certificateInputDto.getName());
        if (existingCertificateOptional.isPresent()) {
            throw new DublicateResourceException(certificateInputDto.getName());
        }
    }

    private void isCertificateExists(long id) {
        Optional<GiftCertificate> certificate = giftCertificateDao.findById(id);
        if (certificate.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
    }
}

