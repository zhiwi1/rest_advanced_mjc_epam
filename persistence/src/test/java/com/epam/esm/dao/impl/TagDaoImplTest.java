package com.epam.esm.dao.impl;

import com.epam.esm.config.DatabaseConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DatabaseConfig.class)
@Transactional
class TagDaoImplTest {
    @Autowired
    private TagDao tagDao;


    public static Object[][] createTags() {
        return new Object[][]{
                {new Tag("1")},
                {new Tag("2")},
                {new Tag("3")}
        };
    }

    public static Object[][] createTagsTogether() {
        return new Object[][]{
                {new Tag("1"), new Tag("2"), new Tag("3")},
                {new Tag(""), new Tag("2 das"), new Tag("3")},
                {new Tag("1"), new Tag("dsa ds2"), new Tag("3ccz")}
        };
    }

    @ParameterizedTest
    @MethodSource("createTags")
    void shouldReturnTagTest(Tag tag) {
        Tag actual = tagDao.create(tag);
        assertNotNull(actual);
    }


    @ParameterizedTest
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @MethodSource("createTags")
    void addCorrectDataShouldSetIdTest(Tag tag) {
        long expected = 5;
        tagDao.create(tag);
        assertEquals(expected, tag.getId());
    }

    @ParameterizedTest
    @MethodSource("createTagsTogether")
    void shouldReturnListOfTagsWhenFindAllTest(Tag tag1, Tag tag2, Tag tag3) {
        long expected = 7;
        tagDao.create(tag1);
        tagDao.create(tag2);
        tagDao.create(tag3);
        List<Tag> actual = tagDao.findAll(new Page(1, 8));
        assertEquals(expected, actual.size());
    }

    @Test
    void shouldReturnTagOptionalWhenFindByIdTest() {
        Tag tag = new Tag( "work");
        tag.setId(3);
        Optional<Tag> actual = tagDao.findById(3L);
        assertEquals(Optional.of(tag), actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void shouldReturnEmptyOptionalWhenFindByIdTest(long id) {
        Optional<Tag> actual = tagDao.findById(id);
        assertFalse(actual.isPresent());
    }

    @Test
    void removeCorrectDataShouldNotThrowExceptionTest() {
        long id = 4;
        assertDoesNotThrow(() -> tagDao.delete(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void shouldNotThrowExceptionWhenRemoveCorrectDataTest(long id) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> tagDao.delete(id));
    }


    @ParameterizedTest
    @MethodSource("createTags")
    void shouldReturnTagOptionalWhenFindByNameTest(Tag expected) {
        String name = expected.getName();
        tagDao.create(expected);
        Optional<Tag> actual = tagDao.findByName(name);
        assertEquals(Optional.of(expected), actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"WORK", "SDAAS", "    1"})
    void shouldReturnEmptyOptionalFindByNameCorrectDataTest(String name) {
        Optional<Tag> actual = tagDao.findByName(name);
        assertFalse(actual.isPresent());
    }

    @Test
    void shouldDoesNotThrowExceptionTest() {
        Tag tag = new Tag("work");
        GiftCertificate certificate = new GiftCertificate( "a", "b", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>());
        assertDoesNotThrow(() -> tagDao.attachTag(tag, certificate));

    }
}