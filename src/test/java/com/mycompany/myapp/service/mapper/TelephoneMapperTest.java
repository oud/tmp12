package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.TelephoneAsserts.*;
import static com.mycompany.myapp.domain.TelephoneTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TelephoneMapperTest {

    private TelephoneMapper telephoneMapper;

    @BeforeEach
    void setUp() {
        telephoneMapper = new TelephoneMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTelephoneSample1();
        var actual = telephoneMapper.toEntity(telephoneMapper.toDto(expected));
        assertTelephoneAllPropertiesEquals(expected, actual);
    }
}
