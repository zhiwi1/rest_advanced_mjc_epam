package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.ServiceTagMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceConfiguration.class)
class TagServiceImplTest {
    @MockBean
    private TagDao tagDao;
    @MockBean
    private GiftCertificateDao certificateDao;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ServiceTagMapper tagMapper;
    @Autowired
    private TagService tagService;

    public static Object[][] createTags() {
        return new Object[][]{
                {new Tag("ab"), new TagCreateDto("ab"), new TagDto(1, "ab")},
                {new Tag("bc"), new TagCreateDto("bc"), new TagDto(1, "bc")},
                {new Tag("cd"), new TagCreateDto("cd"), new TagDto(1, "cd")}
        };
    }

    public static Object[][] createTagsAndDto() {
        return new Object[][]{
                {new Tag( "ab"), new TagDto(1, "ab")},
                {new Tag( "bc"), new TagDto(5, "bc")}
        };
    }

    @ParameterizedTest
    @MethodSource("createTags")
    void shouldReturnTagDtoWhenCreateTest(Tag tag1, TagCreateDto tagCreateDto, TagDto tagDto1) {
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagDao.create(any(Tag.class))).thenReturn(tag1);
        TagDto actual = tagService.create(tagCreateDto);
        TagDto expected = tagDto1;
        assertEquals(expected.getName(), actual.getName());
    }

    @ParameterizedTest
    @MethodSource("createTags")
    void shouldThrowExceptionWhenAddTagTest(Tag tag, TagCreateDto tagCreateDto) {
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.ofNullable(tag));
        assertThrows(DublicateResourceException.class, () -> tagService.create(tagCreateDto));
    }

    @ParameterizedTest
    @MethodSource("createTags")
    void shouldReturnListOfTagDtoWhenFindAllTest(Tag tag) {
        int expectedSize = 2;
        PageDto page = PageDto.builder().number(1).size(9).build();
        when(tagDao.findAll(any(Page.class))).thenReturn(Arrays.asList(tag, tag));
        List<TagDto> actual = tagService.findAll(page);
        assertEquals(expectedSize, actual.size());
    }

    @ParameterizedTest
    @MethodSource("createTagsAndDto")
    void shouldReturnTagDtoWhenFindByIdTest(Tag tag, TagDto tagDto) {
        long id=tagDto.getId();
        tag.setId(id);
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag));
        TagDto actual = tagService.findById(id);
        TagDto expected = tagDto;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 23, 10000000, -123213})
    void shouldThrowExceptionWhenFindByIdTest(long id) {
        when(tagDao.findById(any(long.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1213, 23, 10000000})
    void shouldNotThrowExceptionWhenDeleteTagTest(long id) {
        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(new Tag( "hello")));
        doNothing().when(tagDao).delete(any(long.class));
        assertDoesNotThrow(() -> tagService.delete(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1213, 23, 10000000, -123213})
    void shouldThrowExceptionWhenDeleteTest(long id) {
        doNothing().when(tagDao).delete(any(long.class));
        assertThrows(ResourceNotFoundException.class, () -> tagService.delete(id));
    }
}
