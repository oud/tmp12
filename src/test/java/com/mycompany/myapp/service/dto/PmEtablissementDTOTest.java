package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PmEtablissementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PmEtablissementDTO.class);
        PmEtablissementDTO pmEtablissementDTO1 = new PmEtablissementDTO();
        pmEtablissementDTO1.setId(1L);
        PmEtablissementDTO pmEtablissementDTO2 = new PmEtablissementDTO();
        assertThat(pmEtablissementDTO1).isNotEqualTo(pmEtablissementDTO2);
        pmEtablissementDTO2.setId(pmEtablissementDTO1.getId());
        assertThat(pmEtablissementDTO1).isEqualTo(pmEtablissementDTO2);
        pmEtablissementDTO2.setId(2L);
        assertThat(pmEtablissementDTO1).isNotEqualTo(pmEtablissementDTO2);
        pmEtablissementDTO1.setId(null);
        assertThat(pmEtablissementDTO1).isNotEqualTo(pmEtablissementDTO2);
    }
}
