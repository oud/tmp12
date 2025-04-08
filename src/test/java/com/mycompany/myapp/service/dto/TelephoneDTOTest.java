package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelephoneDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelephoneDTO.class);
        TelephoneDTO telephoneDTO1 = new TelephoneDTO();
        telephoneDTO1.setId(1L);
        TelephoneDTO telephoneDTO2 = new TelephoneDTO();
        assertThat(telephoneDTO1).isNotEqualTo(telephoneDTO2);
        telephoneDTO2.setId(telephoneDTO1.getId());
        assertThat(telephoneDTO1).isEqualTo(telephoneDTO2);
        telephoneDTO2.setId(2L);
        assertThat(telephoneDTO1).isNotEqualTo(telephoneDTO2);
        telephoneDTO1.setId(null);
        assertThat(telephoneDTO1).isNotEqualTo(telephoneDTO2);
    }
}
