package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.hateoas.LinkMapperFacade;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
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
import javax.validation.constraints.Min;
import java.util.List;

/**
 * The type Gift certificate controller.
 */
@RestController
@RequestMapping("/v2/certificates")
@RequiredArgsConstructor
@Validated

public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final LinkMapperFacade linkMapper;
    private static final int MIN_ID_VALUE = 1;

    /**
     * Find gift certificates list.
     *
     * @param giftCertificateQueryParametersDto the gift certificate query parameters dto
     * @param page                              the page
     * @param size                              the size
     * @return the list with giftCertificateDto
     */
    @GetMapping
    public List<GiftCertificateDto> findGiftCertificates(@Valid @RequestBody GiftCertificateQueryParamDto giftCertificateQueryParametersDto,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        return giftCertificateService.findGiftCertificates(giftCertificateQueryParametersDto, pageDto);
    }

    /**
     * Find by id gift certificate dto.
     *
     * @param id the id
     * @return the gift certificate dto
     */
    @GetMapping("/{id}")
    public GiftCertificateDto findById(@Min(MIN_ID_VALUE) @PathVariable Long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findById(id);
        linkMapper.mapLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    /**
     * Create gift certificate dto.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate dto
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GiftCertificateDto create(@Valid @RequestBody GiftCertificateInputDto giftCertificate) {
        return giftCertificateService.create(giftCertificate);
    }

    /**
     * Update gift certificate dto.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the gift certificate dto
     */
    @PatchMapping("/{id}")
    public GiftCertificateDto update(@PathVariable @Min(MIN_ID_VALUE) Long id, @Valid @RequestBody GiftCertificateInputDto giftCertificate) {
        return giftCertificateService.update(id, giftCertificate);
    }

    /**
     * Delete .
     *
     * @param id the id
     * @return the response entity(void)
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(MIN_ID_VALUE) Long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

