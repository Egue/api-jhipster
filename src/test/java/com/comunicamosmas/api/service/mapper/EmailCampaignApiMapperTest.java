package com.comunicamosmas.api.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailCampaignApiMapperTest {

    private EmailCampaignApiMapper emailCampaignApiMapper;

    @BeforeEach
    public void setUp() {
        emailCampaignApiMapper = new EmailCampaignApiMapperImpl();
    }
}
