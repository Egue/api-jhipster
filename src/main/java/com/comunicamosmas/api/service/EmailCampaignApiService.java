package com.comunicamosmas.api.service;

import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.comunicamosmas.api.domain.EmailCampaignApi}.
 */
public interface EmailCampaignApiService {
    /**
     * Save a emailCampaignApi.
     *
     * @param emailCampaignApiDTO the entity to save.
     * @return the persisted entity.
     */
    EmailCampaignApiDTO save(EmailCampaignApiDTO emailCampaignApiDTO);

    /**
     * Partially updates a emailCampaignApi.
     *
     * @param emailCampaignApiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmailCampaignApiDTO> partialUpdate(EmailCampaignApiDTO emailCampaignApiDTO);

    /**
     * Get all the emailCampaignApis.
     *
     * @return the list of entities.
     */
    List<EmailCampaignApiDTO> findAll();

    /**
     * Get the "id" emailCampaignApi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailCampaignApiDTO> findOne(Long id);

    /**
     * Delete the "id" emailCampaignApi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
