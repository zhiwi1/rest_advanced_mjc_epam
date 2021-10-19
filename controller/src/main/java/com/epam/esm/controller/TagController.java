package com.epam.esm.controller;

import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.LinkMapper;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Validated
@RequestMapping("/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final LinkMapper linkMapper;

    @GetMapping
    public List<TagDto> findAll(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "5") int size) {
        PageDto pageDto = new PageDto(page, size);
        List<TagDto> tagDtoList = tagService.findAll(pageDto);
        linkMapper.mapLinks(tagDtoList);
        return tagDtoList;
    }

    @GetMapping("/{id:\\d+}")
    public TagDto findById(@PathVariable @Range(min = 0) Long id) {
        TagDto tagDto = tagService.findById(id);
        linkMapper.mapLinks(tagDto);
        return tagDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@Valid @RequestBody TagCreateDto tagCreateDto) {
        TagDto tagDto = tagService.create(tagCreateDto);
        linkMapper.mapLinks(tagDto);
        return tagDto;
    }

    @DeleteMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable @Range(min = 0) Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/attach")
    public void attachTag(@Valid @RequestBody CertificateTagDto certificateTagDto) {
        tagService.attachTag(certificateTagDto);
    }

}

