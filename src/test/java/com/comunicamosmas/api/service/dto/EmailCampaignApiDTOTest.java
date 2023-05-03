package com.comunicamosmas.api.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.comunicamosmas.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailCampaignApiDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailCampaignApiDTO.class);
        EmailCampaignApiDTO emailCampaignApiDTO1 = new EmailCampaignApiDTO();
        emailCampaignApiDTO1.setId(1L);
        EmailCampaignApiDTO emailCampaignApiDTO2 = new EmailCampaignApiDTO();
        assertThat(emailCampaignApiDTO1).isNotEqualTo(emailCampaignApiDTO2);
        emailCampaignApiDTO2.setId(emailCampaignApiDTO1.getId());
        assertThat(emailCampaignApiDTO1).isEqualTo(emailCampaignApiDTO2);
        emailCampaignApiDTO2.setId(2L);
        assertThat(emailCampaignApiDTO1).isNotEqualTo(emailCampaignApiDTO2);
        emailCampaignApiDTO1.setId(null);
        assertThat(emailCampaignApiDTO1).isNotEqualTo(emailCampaignApiDTO2);
    }
}
