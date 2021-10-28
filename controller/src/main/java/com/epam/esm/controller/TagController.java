package com.epam.esm.controller;

import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.LinkMapperFacade;
import com.epam.esm.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping("/v2/tags")
@RequiredArgsConstructor
@Validated
public class TagController {
    private final TagService tagService;
    private final LinkMapperFacade linkMapper;

    @GetMapping
    //todo validation pagination
    public List<TagDto> findAll(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        List<TagDto> tagDtoList = tagService.findAll(pageDto);
        linkMapper.mapLinks(tagDtoList);
        return tagDtoList;
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable @Min(0) Long id) {
        TagDto tagDto = tagService.findById(id);
        linkMapper.mapLinks(tagDto);
        return tagDto;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@Valid @RequestBody TagCreateDto tagCreateDto) {
        TagDto tagDto = tagService.create(tagCreateDto);
        linkMapper.mapLinks(tagDto);
        return tagDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable @Min(0) Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/attach")
    public void attachTag(@Valid @RequestBody CertificateTagDto certificateTagDto) {
        tagService.attachTag(certificateTagDto);
    }

    @GetMapping("/popular")
    public TagDto findMostPopularTagWithHighestCostOfAllOrders() {
        TagDto tagDto = tagService.findMostPopularTagWithHighestCostOfAllOrders();
        linkMapper.mapLinks(tagDto);
        return tagDto;
    }

}

