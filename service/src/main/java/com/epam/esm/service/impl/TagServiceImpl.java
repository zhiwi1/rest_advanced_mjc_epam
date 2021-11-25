package com.epam.esm.service.impl;

import com.epam.esm.dao.datajpa.DataGiftCertificateDao;
import com.epam.esm.dao.datajpa.DataTagDao;
import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceTagMapper;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final DataTagDao tagDao;
    private final DataGiftCertificateDao giftCertificateDao;
    private final ServiceTagMapper tagMapper;


    @Transactional
    @Override
    public TagDto create(TagCreateDto tagCreateDto) {
        isTagDublicate(tagCreateDto);
        Tag tag = tagMapper.toEntity(tagCreateDto);
        Tag insertedTag = tagDao.save(tag);
        return tagMapper.toDto(insertedTag);
    }

    @Override
    public List<TagDto> findAll(PageDto pageDto) {
        Pageable pageRequest = PageRequest.of(pageDto.getNumber(), pageDto.getSize());
        List<Tag> foundTags = tagDao.findAll(pageRequest).getContent();
        return foundTags.stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto create(TagDto value) {
        throw new UnsupportedOperationException("Method is not supported by current implementation");
    }

    @Override
    public TagDto findById(Long id) {
        Optional<Tag> foundTagOptional = tagDao.findById(id);
        return foundTagOptional.map(tagMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        isTagExists(id);
        tagDao.deleteById(id);
    }


    @Transactional
    @Override
    public void attachTag(CertificateTagDto certificateTagDto) {
        Optional<Tag> tag = tagDao.findById(certificateTagDto.getTagId());
        if (tag.isEmpty()) {
            throw new ResourceNotFoundException(certificateTagDto.getTagId());
        }
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(certificateTagDto.getCertificateId());
        if (giftCertificate.isEmpty()) {
            throw new ResourceNotFoundException(certificateTagDto.getCertificateId());
        }
        tagDao.attachTag(tag.get(), giftCertificate.get());
    }

    @Override
    public TagDto findMostPopularTagWithHighestCostOfAllOrders() {

        Optional<Tag> foundTagOptional = tagDao.findMostPopularOfUser();
        return foundTagOptional.map(tagMapper::toDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void isTagDublicate(TagCreateDto tagCreateDto) {
        Optional<Tag> existingTagOptional = tagDao.findByName(tagCreateDto.getName());
        if (existingTagOptional.isPresent()) {
            throw new DublicateResourceException(tagCreateDto.getName());
        }
    }

    private void isTagExists(long id) {
        Optional<Tag> tag = tagDao.findById(id);
        if (tag.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
    }
}
