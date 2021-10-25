package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.hateoas.LinkMapper;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
@Validated
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final LinkMapper linkMapper;

    @GetMapping
    public List<GiftCertificateDto> findGiftCertificates(@Valid @RequestBody GiftCertificateQueryParamDto giftCertificateQueryParametersDto,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        return giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto, pageDto);
    }
//todo 409, 404 find by id
    @GetMapping("/{id:\\d+}")
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

    @PatchMapping("/{id:\\d+}")
    public GiftCertificateDto update(@PathVariable @Range(min = 0) Long id, @Valid @RequestBody GiftCertificateInputDto giftCertificate) {
        return giftCertificateService.update(id, giftCertificate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable @Range(min = 0) Long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

