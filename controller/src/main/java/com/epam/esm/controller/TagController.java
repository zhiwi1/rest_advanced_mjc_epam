package com.epam.esm.controller;

import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.LinkMapperFacade;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


/**
 * The type Tag controller.
 */
@RestController
@RequestMapping("/v2/tags")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TagController {
    private static final int MIN_ID_VALUE = 1;
    private final TagService tagService;
    private final LinkMapperFacade linkMapper;

    /**
     * Find all with pagination list.
     *
     * @param page the page
     * @param size the size
     * @return the list
     */
    @GetMapping
    public List<TagDto> findAll(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        List<TagDto> tagDtoList = tagService.findAll(pageDto);
        linkMapper.mapLinks(tagDtoList);
        return tagDtoList;
    }

    /**
     * Find by id tag dto.
     *
     * @param id the id
     * @return the tag dto
     */
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable @Min(MIN_ID_VALUE) Long id, Principal principal) {
        var principalToken = (KeycloakAuthenticationToken) principal;
        Optional.ofNullable(principalToken).ifPresent(
                token -> log.info(token.getAccount().getKeycloakSecurityContext().getToken().getEmail())
        );

        TagDto tagDto = tagService.findById(id);
        linkMapper.mapLinks(tagDto);
        return tagDto;
    }

    /**
     * Create tag dto.
     *
     * @param tagCreateDto the tag create dto
     * @return the created tag dto
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public TagDto create(@Valid @RequestBody TagCreateDto tagCreateDto) {
        TagDto tagDto = tagService.create(tagCreateDto);
        linkMapper.mapLinks(tagDto);
        return tagDto;
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> delete(@PathVariable @Min(MIN_ID_VALUE) Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Attach tag to gift certificate.
     *
     * @param certificateTagDto the certificate tag dto
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/attach")
//    @HasPermissionAdmin
    //todo @HasPermission()
    //users 1)local login password 2)google 3)github
    //google users github users 2 tables
    //or директория два столбца id, name keycloak directories
    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public void attachTag(@Valid @RequestBody CertificateTagDto certificateTagDto) {
        tagService.attachTag(certificateTagDto);
    }

    /**
     * Find most popular tag with highest cost of all orders tag dto.
     *
     * @return the tag dto
     */
    @GetMapping("/popular")
    @PreAuthorize("hasRole('admin')")
    public TagDto findMostPopularTagWithHighestCostOfAllOrders() {
        TagDto tagDto = tagService.findMostPopularTagWithHighestCostOfAllOrders();
        linkMapper.mapLinks(tagDto);
        return tagDto;
    }

}

