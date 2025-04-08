package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.PmEtablissementAsserts.*;
import static com.mycompany.myapp.domain.PmEtablissementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PmEtablissementMapperTest {

    private PmEtablissementMapper pmEtablissementMapper;

    @BeforeEach
    void setUp() {
        pmEtablissementMapper = new PmEtablissementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPmEtablissementSample1();
        var actual = pmEtablissementMapper.toEntity(pmEtablissementMapper.toDto(expected));
        assertPmEtablissementAllPropertiesEquals(expected, actual);
    }
}
