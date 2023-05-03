package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.repository.EmailCampaignApiRepository;
import com.comunicamosmas.api.service.EmailCampaignApiService;
import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import com.comunicamosmas.api.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.comunicamosmas.api.domain.EmailCampaignApi}.
 */
@RestController
@RequestMapping("/api/controlmas")
public class EmailCampaignApiResource {

    private final Logger log = LoggerFactory.getLogger(EmailCampaignApiResource.class);

    private static final String ENTITY_NAME = "emailCampaignApi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailCampaignApiService emailCampaignApiService;

    private final EmailCampaignApiRepository emailCampaignApiRepository;

    public EmailCampaignApiResource(
        EmailCampaignApiService emailCampaignApiService,
        EmailCampaignApiRepository emailCampaignApiRepository
    ) {
        this.emailCampaignApiService = emailCampaignApiService;
        this.emailCampaignApiRepository = emailCampaignApiRepository;
    }

    /**
     * {@code POST  /email-campaign-apis} : Create a new emailCampaignApi.
     *
     * @param emailCampaignApiDTO the emailCampaignApiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailCampaignApiDTO, or with status {@code 400 (Bad Request)} if the emailCampaignApi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-campaign-apis")
    public ResponseEntity<EmailCampaignApiDTO> createEmailCampaignApi(@RequestBody EmailCampaignApiDTO emailCampaignApiDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmailCampaignApi : {}", emailCampaignApiDTO);
        if (emailCampaignApiDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailCampaignApi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailCampaignApiDTO result = emailCampaignApiService.save(emailCampaignApiDTO);
        return ResponseEntity
            .created(new URI("/api/email-campaign-apis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-campaign-apis/:id} : Updates an existing emailCampaignApi.
     *
     * @param id the id of the emailCampaignApiDTO to save.
     * @param emailCampaignApiDTO the emailCampaignApiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailCampaignApiDTO,
     * or with status {@code 400 (Bad Request)} if the emailCampaignApiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailCampaignApiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-campaign-apis/{id}")
    public ResponseEntity<EmailCampaignApiDTO> updateEmailCampaignApi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmailCampaignApiDTO emailCampaignApiDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmailCampaignApi : {}, {}", id, emailCampaignApiDTO);
        if (emailCampaignApiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailCampaignApiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailCampaignApiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmailCampaignApiDTO result = emailCampaignApiService.save(emailCampaignApiDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailCampaignApiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /email-campaign-apis/:id} : Partial updates given fields of an existing emailCampaignApi, field will ignore if it is null
     *
     * @param id the id of the emailCampaignApiDTO to save.
     * @param emailCampaignApiDTO the emailCampaignApiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailCampaignApiDTO,
     * or with status {@code 400 (Bad Request)} if the emailCampaignApiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the emailCampaignApiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the emailCampaignApiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/email-campaign-apis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmailCampaignApiDTO> partialUpdateEmailCampaignApi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmailCampaignApiDTO emailCampaignApiDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmailCampaignApi partially : {}, {}", id, emailCampaignApiDTO);
        if (emailCampaignApiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailCampaignApiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailCampaignApiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmailCampaignApiDTO> result = emailCampaignApiService.partialUpdate(emailCampaignApiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailCampaignApiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /email-campaign-apis} : get all the emailCampaignApis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailCampaignApis in body.
     */
    @GetMapping("/email-campaign-apis")
    public List<EmailCampaignApiDTO> getAllEmailCampaignApis() {
        log.debug("REST request to get all EmailCampaignApis");
        return emailCampaignApiService.findAll();
    }

    /**
     * {@code GET  /email-campaign-apis/:id} : get the "id" emailCampaignApi.
     *
     * @param id the id of the emailCampaignApiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailCampaignApiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-campaign-apis/{id}")
    public ResponseEntity<EmailCampaignApiDTO> getEmailCampaignApi(@PathVariable Long id) {
        log.debug("REST request to get EmailCampaignApi : {}", id);
        Optional<EmailCampaignApiDTO> emailCampaignApiDTO = emailCampaignApiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailCampaignApiDTO);
    }

    /**
     * {@code DELETE  /email-campaign-apis/:id} : delete the "id" emailCampaignApi.
     *
     * @param id the id of the emailCampaignApiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-campaign-apis/{id}")
    public ResponseEntity<Void> deleteEmailCampaignApi(@PathVariable Long id) {
        log.debug("REST request to delete EmailCampaignApi : {}", id);
        emailCampaignApiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
