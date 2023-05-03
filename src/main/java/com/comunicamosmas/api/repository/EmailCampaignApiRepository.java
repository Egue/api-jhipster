package com.comunicamosmas.api.repository;

import com.comunicamosmas.api.domain.EmailCampaignApi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmailCampaignApi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailCampaignApiRepository extends JpaRepository<EmailCampaignApi, Long> {}
