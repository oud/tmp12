package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.MiseEnGestionAsserts.*;
import static com.mycompany.myapp.domain.MiseEnGestionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MiseEnGestionMapperTest {

    private MiseEnGestionMapper miseEnGestionMapper;

    @BeforeEach
    void setUp() {
        miseEnGestionMapper = new MiseEnGestionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMiseEnGestionSample1();
        var actual = miseEnGestionMapper.toEntity(miseEnGestionMapper.toDto(expected));
        assertMiseEnGestionAllPropertiesEquals(expected, actual);
    }
}
