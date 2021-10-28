package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.hateoas.LinkMapperFacade;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v2/certificates")
@RequiredArgsConstructor
@Validated
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final LinkMapperFacade linkMapper;

    @GetMapping
    public List<GiftCertificateDto> findGiftCertificates(@Valid @RequestBody GiftCertificateQueryParamDto giftCertificateQueryParametersDto,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        return giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto, pageDto);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@Range(min = 0) @PathVariable Long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findById(id);
        linkMapper.mapLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GiftCertificateDto create(@Valid @RequestBody GiftCertificateInputDto giftCertificate) {
        return giftCertificateService.create(giftCertificate);
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto update(@PathVariable @Range(min = 0) Long id, @Valid @RequestBody GiftCertificateInputDto giftCertificate) {
        return giftCertificateService.update(id, giftCertificate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Range(min = 0) Long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

