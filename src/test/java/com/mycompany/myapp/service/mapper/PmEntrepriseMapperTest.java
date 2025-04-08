package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.PmEntrepriseAsserts.*;
import static com.mycompany.myapp.domain.PmEntrepriseTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PmEntrepriseMapperTest {

    private PmEntrepriseMapper pmEntrepriseMapper;

    @BeforeEach
    void setUp() {
        pmEntrepriseMapper = new PmEntrepriseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPmEntrepriseSample1();
        var actual = pmEntrepriseMapper.toEntity(pmEntrepriseMapper.toDto(expected));
        assertPmEntrepriseAllPropertiesEquals(expected, actual);
    }
}
