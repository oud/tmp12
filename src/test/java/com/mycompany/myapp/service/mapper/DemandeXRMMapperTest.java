package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.DemandeXRMAsserts.*;
import static com.mycompany.myapp.domain.DemandeXRMTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandeXRMMapperTest {

    private DemandeXRMMapper demandeXRMMapper;

    @BeforeEach
    void setUp() {
        demandeXRMMapper = new DemandeXRMMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDemandeXRMSample1();
        var actual = demandeXRMMapper.toEntity(demandeXRMMapper.toDto(expected));
        assertDemandeXRMAllPropertiesEquals(expected, actual);
    }
}
