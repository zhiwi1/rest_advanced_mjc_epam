package com.epam.esm.service.impl;

import com.epam.esm.config.ServiceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.datajpa.DataGiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateInputDto;
import com.epam.esm.dto.GiftCertificateQueryParamDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DublicateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceConfiguration.class)
class CertificateServiceImplTest {
    @MockBean
    private DataGiftCertificateDao giftCertificateDao;
    @Autowired
    private GiftCertificateService giftCertificateService;

    public static Object[][] createGiftCertificatesAndDto() {
        var zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        return new Object[][]{
                {
                        new GiftCertificate("a", "b", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateDto(1, "a", "b", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},
                {
                        new GiftCertificate("2", "123", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateDto(1, "2", "123", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)}
        };
    }

    public static Object[][] createGiftCertificatesAndInputDto() {
        var zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        return new Object[][]{
                {
                        new GiftCertificate("a", "b", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateInputDto("a", "b", BigDecimal.ONE, 0),
                        new GiftCertificateDto(1, "a", "b", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},

                {
                        new GiftCertificate("2", "123", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateInputDto("2", "123", BigDecimal.ONE, 0),
                        new GiftCertificateDto(1, "2", "123", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},
        };
    }

    public static Object[][] createGiftCertificatesAndInputDtoForUpdate() {
        var zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        return new Object[][]{
                {
                        new GiftCertificate("ad", "bs", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificate("a", "b", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateInputDto("a", "b", BigDecimal.ONE, 0),
                        new GiftCertificateDto(1, "a", "b", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},

                {
                        new GiftCertificate("ad", "bs", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificate("2", "123", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateInputDto("2", "123", BigDecimal.ONE, 0),
                        new GiftCertificateDto(1, "2", "123", BigDecimal.ONE, 0, new HashSet<>(), zonedDateTime, zonedDateTime)},
        };
    }

    public static Object[][] createGiftCertificatesTogether() {
        var zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        return new Object[][]{
                {new GiftCertificate("a", "b", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificate("2", "123", BigDecimal.ONE, zonedDateTime, zonedDateTime, 0, new HashSet<>()),
                        new GiftCertificateQueryParamDto()
                }
        };
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndInputDto")
    void shouldReturnGiftCertificateDtoWhenAddGiftCertificateTest(GiftCertificate certificate, GiftCertificateInputDto certificateDto, GiftCertificateDto giftCertificateDto) {
        certificate.setId(giftCertificateDto.getId());
        when(giftCertificateDao.save(any(GiftCertificate.class))).thenReturn(certificate);
        when(giftCertificateDao.findByName(any())).thenReturn(Optional.empty());
        GiftCertificateDto actual = giftCertificateService.create(certificateDto);
        assertEquals(giftCertificateDto, actual);
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndInputDto")
    void shouldThrowExceptionWhenAddGiftCertificateTest(GiftCertificate certificate, GiftCertificateInputDto certificateDto) {
        when(giftCertificateDao.findByName(any(String.class))).thenReturn(Optional.of(certificate));
        when(giftCertificateDao.save(any(GiftCertificate.class))).thenReturn(certificate);
        assertThrows(DublicateResourceException.class,
                () -> giftCertificateService.create(certificateDto));
    }


    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndInputDtoForUpdate")
    void shouldReturnGiftCertificateDtoWhenUpdateGiftCertificateTest
            (GiftCertificate certificate0, GiftCertificate certificate, GiftCertificateInputDto certificateInputDto, GiftCertificateDto certificateDto) {
        certificate0.setId(certificateDto.getId());
        certificate.setId(certificateDto.getId());
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(certificate0));
        when(giftCertificateDao.save(any(GiftCertificate.class))).thenReturn(certificate);
        GiftCertificateDto actual = giftCertificateService.update(certificate0.getId(), certificateInputDto);
        assertEquals(certificateDto, actual);
    }


    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndInputDto")
    void shouldThrowExceptionWhenUpdateGiftCertificateIncorrectDataTest(GiftCertificate certificate, GiftCertificateInputDto certificateDto) {
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.empty());
        when(giftCertificateDao.save(any(GiftCertificate.class))).thenReturn(certificate);
        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.update(certificate.getId(), certificateDto));
    }

    @ParameterizedTest
    @MethodSource("createGiftCertificatesAndDto")
    void shouldNotThrowExceptionWhenRemoveGiftCertificateTest(GiftCertificate certificate) {
        long id = 1;
        doNothing().when(giftCertificateDao).delete(any(GiftCertificate.class));
        when(giftCertificateDao.findById(any(long.class))).thenReturn(Optional.of(certificate));
        assertDoesNotThrow(() -> giftCertificateService.delete(id));
    }

}