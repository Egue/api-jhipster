package com.comunicamosmas.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.comunicamosmas.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailCampaignApiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailCampaignApi.class);
        EmailCampaignApi emailCampaignApi1 = new EmailCampaignApi();
        emailCampaignApi1.setId(1L);
        EmailCampaignApi emailCampaignApi2 = new EmailCampaignApi();
        emailCampaignApi2.setId(emailCampaignApi1.getId());
        assertThat(emailCampaignApi1).isEqualTo(emailCampaignApi2);
        emailCampaignApi2.setId(2L);
        assertThat(emailCampaignApi1).isNotEqualTo(emailCampaignApi2);
        emailCampaignApi1.setId(null);
        assertThat(emailCampaignApi1).isNotEqualTo(emailCampaignApi2);
    }
}
