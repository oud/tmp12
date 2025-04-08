package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MiseEnGestionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MiseEnGestionDTO.class);
        MiseEnGestionDTO miseEnGestionDTO1 = new MiseEnGestionDTO();
        miseEnGestionDTO1.setId(1L);
        MiseEnGestionDTO miseEnGestionDTO2 = new MiseEnGestionDTO();
        assertThat(miseEnGestionDTO1).isNotEqualTo(miseEnGestionDTO2);
        miseEnGestionDTO2.setId(miseEnGestionDTO1.getId());
        assertThat(miseEnGestionDTO1).isEqualTo(miseEnGestionDTO2);
        miseEnGestionDTO2.setId(2L);
        assertThat(miseEnGestionDTO1).isNotEqualTo(miseEnGestionDTO2);
        miseEnGestionDTO1.setId(null);
        assertThat(miseEnGestionDTO1).isNotEqualTo(miseEnGestionDTO2);
    }
}
