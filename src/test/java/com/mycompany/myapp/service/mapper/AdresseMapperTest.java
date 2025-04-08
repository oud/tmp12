package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.AdresseAsserts.*;
import static com.mycompany.myapp.domain.AdresseTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdresseMapperTest {

    private AdresseMapper adresseMapper;

    @BeforeEach
    void setUp() {
        adresseMapper = new AdresseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdresseSample1();
        var actual = adresseMapper.toEntity(adresseMapper.toDto(expected));
        assertAdresseAllPropertiesEquals(expected, actual);
    }
}
