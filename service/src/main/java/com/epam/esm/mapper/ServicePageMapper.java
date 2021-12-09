package com.epam.esm.mapper;

import com.epam.esm.dto.PageDto;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @deprecated because project use datajpa since 3 version
 */

@Deprecated(since = "version 3")
@Component
@RequiredArgsConstructor
public class ServicePageMapper {
    private final ModelMapper modelMapper;

    public Page toEntity(PageDto pageDto) {
        return modelMapper.map(pageDto, Page.class);
    }

    public PageDto toDto(Page page) {
        return modelMapper.map(page, PageDto.class);
    }
}
