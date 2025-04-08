package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PmEntrepriseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PmEntrepriseDTO.class);
        PmEntrepriseDTO pmEntrepriseDTO1 = new PmEntrepriseDTO();
        pmEntrepriseDTO1.setId(1L);
        PmEntrepriseDTO pmEntrepriseDTO2 = new PmEntrepriseDTO();
        assertThat(pmEntrepriseDTO1).isNotEqualTo(pmEntrepriseDTO2);
        pmEntrepriseDTO2.setId(pmEntrepriseDTO1.getId());
        assertThat(pmEntrepriseDTO1).isEqualTo(pmEntrepriseDTO2);
        pmEntrepriseDTO2.setId(2L);
        assertThat(pmEntrepriseDTO1).isNotEqualTo(pmEntrepriseDTO2);
        pmEntrepriseDTO1.setId(null);
        assertThat(pmEntrepriseDTO1).isNotEqualTo(pmEntrepriseDTO2);
    }
}
