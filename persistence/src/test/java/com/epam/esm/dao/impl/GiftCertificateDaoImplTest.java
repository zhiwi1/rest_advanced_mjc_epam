package com.epam.esm.dao.impl;

import com.epam.esm.config.DatabaseConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DatabaseConfig.class)
@Transactional
class GiftCertificateDaoImplTest {

    private final GiftCertificateDao giftCertificateDao;


    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    public static Object[][] createGiftCertificates() {
        return new Object[][]{
                {new GiftCertificate( "a", "b", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>())},
                {new GiftCertificate( "2", "123", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>())},
                {new GiftCertificate( "3", "wow", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>())}
        };
    }

    public static Object[][] createGiftCertificatesTogether() {
        return new Object[][]{
                {new GiftCertificate( "a", "b", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>()),
                        new GiftCertificate( "2", "123", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>()),
                        new GiftCertificate( "3", "wow", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>())
                }};
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("createGiftCertificates")
    void shouldReturnTagWhenCreateTest(GiftCertificate certificate) {
        GiftCertificate giftCertificate = giftCertificateDao.create(certificate);
        assertNotNull(giftCertificate);
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificates")
    void shouldReturnThisGiftCertificateWhenCreateTest(GiftCertificate certificate) {
        GiftCertificate actual = giftCertificateDao.create(certificate);
        GiftCertificate expected = certificate;
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesTogether")
    void shouldReturnListOfTagsWhenFindAllTest(GiftCertificate giftCertificate1, GiftCertificate giftCertificate2, GiftCertificate giftCertificate3) {
        long expected = 7;
        giftCertificateDao.create(giftCertificate1);
        giftCertificateDao.create(giftCertificate2);
        giftCertificateDao.create(giftCertificate3);
        List<GiftCertificate> actual = giftCertificateDao.findAll(new Page(1, 8));
        assertEquals(expected, actual.size());
    }

    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void shouldReturnEmptyOptionalWhenFindByIdTest(long id) {
        Optional<GiftCertificate> actual = giftCertificateDao.findById(id);
        assertFalse(actual.isPresent());
    }


    @ParameterizedTest
    @ValueSource(longs = {100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void deleteIncorrectDataShouldThrowExceptionTest(long id) {
        assertThrows(InvalidDataAccessApiUsageException.class,() -> giftCertificateDao.delete(id));
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 100, 213, Long.MAX_VALUE, Long.MIN_VALUE})
    void shouldNotThrowExceptionDeleteCertificateCorrectDataTest() {
        long id = 1;
        assertDoesNotThrow(() -> giftCertificateDao.delete(id));
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificates")
    void shouldReturnListAddDeleteCertificateCorrectDataTest(GiftCertificate certificate) {
        giftCertificateDao.delete(1L);
        giftCertificateDao.delete(2L);
        giftCertificateDao.delete(3L);
        giftCertificateDao.delete(4L);
        List<GiftCertificate> expected = new ArrayList<>();
        List<GiftCertificate> actual = giftCertificateDao.findAll(new Page(1, 8));
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUpdatedCertificateTest() {
        GiftCertificate expected = new GiftCertificate( "a", "b", BigDecimal.ONE, ZonedDateTime.now(), ZonedDateTime.now(), 0, new HashSet<>());
        expected.setId(14);
        GiftCertificate actual = giftCertificateDao.update(expected);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificates")
    void updateCertificateCorrectDataShouldNotThrowExceptionTest(GiftCertificate giftCertificate) {
        assertDoesNotThrow(() -> giftCertificateDao.update(giftCertificate));
    }


}

