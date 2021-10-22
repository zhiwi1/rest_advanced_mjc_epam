//package com.epam.esm.service.impl;
//
//import com.epam.esm.config.ServiceConfiguration;
//import com.epam.esm.dao.GiftCertificateDao;
//import com.epam.esm.dao.TagDao;
//import com.epam.esm.dao.UserDao;
//import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
//import com.epam.esm.dao.impl.TagDaoImpl;
//import com.epam.esm.dto.PageDto;
//import com.epam.esm.dto.TagCreateDto;
//import com.epam.esm.dto.TagDto;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exception.DublicateResourceException;
//import com.epam.esm.exception.ResourceNotFoundException;
//import com.epam.esm.mapper.ServicePageMapper;
//import com.epam.esm.mapper.ServiceTagMapper;
//import com.epam.esm.service.TagService;
//import com.epam.esm.service.UserService;
//import com.epam.esm.service.impl.TagServiceImpl;
//import com.epam.esm.util.Page;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.ValueSource;
//
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = ServiceConfiguration.class)
//class TagServiceImplTest {
//    @MockBean
//    private TagDao tagDao;
////    @MockBean
////    private GiftCertificateDao certificateDao;
////    @Autowired
////    private ModelMapper modelMapper;
////    @Autowired
////    private ServiceTagMapper tagMapper;
//    @Autowired
//    private  TagService tagService;
////    @Autowired
////    private ServicePageMapper pageMapper;
//
//    public static Object[][] createTags() {
//        return new Object[][]{
//                {new Tag(1L, "ab"), new TagCreateDto("ab"), new TagDto(1, "ab")},
//                {new Tag(1L, "bc"), new TagCreateDto("bc"), new TagDto(1, "bc")},
//                {new Tag(1L, "cd"), new TagCreateDto("cd"), new TagDto(1, "cd")}
//        };
//    }
//
//
//    public static Object[][] createDoubleTags() {
//        return new Object[][]{
//                {new Tag(1L, "ab"), new TagDto(1, "ab"), new TagDto(2, "ab")},
//                {new Tag(1L, "abcd"), new TagDto(1, "abcd"), new TagDto(2, "absa")},
//                {new Tag(1L, "cjlaskdd"), new TagDto(1, "cjlaskdd"), new TagDto(2, "asdasda")}
//        };
//    }
//
//    @ParameterizedTest
//    @MethodSource("createTags")
//    void shouldReturnTagDtoWhenCreateTest(Tag tag1, TagCreateDto tagCreateDto, TagDto tagDto1) {
//        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
//        when(tagDao.create(any(Tag.class))).thenReturn(tag1);
//        TagDto actual = tagService.create(tagCreateDto);
//        TagDto expected = tagDto1;
//        assertEquals(expected, actual);
//    }
//
//    @ParameterizedTest
//    @MethodSource("createTags")
//    void shouldThrowExceptionWhenAddTagTest(Tag tag, TagCreateDto tagCreateDto) {
//        when(tagDao.findByName(any(String.class))).thenReturn(Optional.ofNullable(tag));
//        assertThrows(DublicateResourceException.class, () -> tagService.create(tagCreateDto));
//    }
//
//    @ParameterizedTest
//    @MethodSource("createTags")
//    void shouldReturnListOfTagDtoWhenFindAllTest(Tag tag) {
//        int expectedSize = 2;
//        when(tagDao.findAll(any(Page.class))).thenReturn(Arrays.asList(tag, tag));
//        List<TagDto> actual = tagService.findAll(new PageDto(1, 20));
//        assertEquals(expectedSize, actual.size());
//    }
//
//    @ParameterizedTest
//    @MethodSource("createDoubleTags")
//    void shouldReturnTagDtoWhenFindByIdTest(Tag tag, TagDto tagDto) {
//        long id = tag.getId();
//        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(tag));
//        TagDto actual = tagService.findById(id);
//        TagDto expected = tagDto;
//        assertEquals(expected, actual);
//    }
//
//    @ParameterizedTest
//    @ValueSource(longs = {1, 23, 10000000, -123213})
//    void shouldThrowExceptionWhenFindByIdTest(long id) {
//        when(tagDao.findById(any(long.class))).thenReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(id));
//    }
//
//    @ParameterizedTest
//    @ValueSource(longs = {1213, 23, 10000000})
//    void shouldNotThrowExceptionWhenDeleteTagTest(long id) {
//        when(tagDao.findById(any(long.class))).thenReturn(Optional.of(new Tag(1L, "hello")));
//        doNothing().when(tagDao).delete(any(long.class));
//        assertDoesNotThrow(() -> tagService.delete(id));
//    }
//
//    @ParameterizedTest
//    @ValueSource(longs = {1213, 23, 10000000, -123213})
//    void shouldThrowExceptionWhenDeleteTest(long id) {
//        doNothing().when(tagDao).delete(any(long.class));
//        assertThrows(ResourceNotFoundException.class, () -> tagService.delete(id));
//    }
//
////    @ParameterizedTest
////    @MethodSource("createDoubleTags")
////    void shouldReturnListOfTagDtoWhenFindTagsByGiftCertificateIdCheckSizeTest(Tag tag, Tag tag1) {
////        int expected = 2;
////        long giftCertificateId = 1;
////        when(tagDao.findByCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag, tag1));
////        Set<TagDto> actual = tagService.findTagsByGiftCertificateId(giftCertificateId);
////        assertEquals(expected, actual.size());
////    }
//
////    @ParameterizedTest
////    @MethodSource("createDoubleTags")
////    void shouldReturnListOfTagDtoWhenFindTagsByGiftCertificateIdTest(Tag tag, Tag tag1, TagDto tagDto, TagDto tagDto1) {
////        when(tagDao.findByCertificateId(any(Long.class))).thenReturn(Arrays.asList(tag, tag1));
////        Set<TagDto> expected = new HashSet<>();
////        expected.add(tagDto);
////        expected.add(tagDto1);
////        long id = 1L;
////        Set<TagDto> actual = tagService.findTagsByGiftCertificateId(id);
////        assertEquals(expected, actual);
////    }
//}
