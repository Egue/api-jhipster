package com.comunicamosmas.api.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.comunicamosmas.api.IntegrationTest;
import com.comunicamosmas.api.domain.EmailCampaignApi;
import com.comunicamosmas.api.repository.EmailCampaignApiRepository;
import com.comunicamosmas.api.service.dto.EmailCampaignApiDTO;
import com.comunicamosmas.api.service.mapper.EmailCampaignApiMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmailCampaignApiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailCampaignApiResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/email-campaign-apis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmailCampaignApiRepository emailCampaignApiRepository;

    @Autowired
    private EmailCampaignApiMapper emailCampaignApiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailCampaignApiMockMvc;

    private EmailCampaignApi emailCampaignApi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailCampaignApi createEntity(EntityManager em) {
        EmailCampaignApi emailCampaignApi = new EmailCampaignApi().url(DEFAULT_URL).token(DEFAULT_TOKEN);
        return emailCampaignApi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailCampaignApi createUpdatedEntity(EntityManager em) {
        EmailCampaignApi emailCampaignApi = new EmailCampaignApi().url(UPDATED_URL).token(UPDATED_TOKEN);
        return emailCampaignApi;
    }

    @BeforeEach
    public void initTest() {
        emailCampaignApi = createEntity(em);
    }

    @Test
    @Transactional
    void createEmailCampaignApi() throws Exception {
        int databaseSizeBeforeCreate = emailCampaignApiRepository.findAll().size();
        // Create the EmailCampaignApi
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(emailCampaignApi);
        restEmailCampaignApiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeCreate + 1);
        EmailCampaignApi testEmailCampaignApi = emailCampaignApiList.get(emailCampaignApiList.size() - 1);
        assertThat(testEmailCampaignApi.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testEmailCampaignApi.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    void createEmailCampaignApiWithExistingId() throws Exception {
        // Create the EmailCampaignApi with an existing ID
        emailCampaignApi.setId(1L);
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(emailCampaignApi);

        int databaseSizeBeforeCreate = emailCampaignApiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailCampaignApiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmailCampaignApis() throws Exception {
        // Initialize the database
        emailCampaignApiRepository.saveAndFlush(emailCampaignApi);

        // Get all the emailCampaignApiList
        restEmailCampaignApiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailCampaignApi.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)));
    }

    @Test
    @Transactional
    void getEmailCampaignApi() throws Exception {
        // Initialize the database
        emailCampaignApiRepository.saveAndFlush(emailCampaignApi);

        // Get the emailCampaignApi
        restEmailCampaignApiMockMvc
            .perform(get(ENTITY_API_URL_ID, emailCampaignApi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailCampaignApi.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN));
    }

    @Test
    @Transactional
    void getNonExistingEmailCampaignApi() throws Exception {
        // Get the emailCampaignApi
        restEmailCampaignApiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmailCampaignApi() throws Exception {
        // Initialize the database
        emailCampaignApiRepository.saveAndFlush(emailCampaignApi);

        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();

        // Update the emailCampaignApi
        EmailCampaignApi updatedEmailCampaignApi = emailCampaignApiRepository.findById(emailCampaignApi.getId()).get();
        // Disconnect from session so that the updates on updatedEmailCampaignApi are not directly saved in db
        em.detach(updatedEmailCampaignApi);
        updatedEmailCampaignApi.url(UPDATED_URL).token(UPDATED_TOKEN);
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(updatedEmailCampaignApi);

        restEmailCampaignApiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailCampaignApiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
        EmailCampaignApi testEmailCampaignApi = emailCampaignApiList.get(emailCampaignApiList.size() - 1);
        assertThat(testEmailCampaignApi.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testEmailCampaignApi.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    void putNonExistingEmailCampaignApi() throws Exception {
        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();
        emailCampaignApi.setId(count.incrementAndGet());

        // Create the EmailCampaignApi
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(emailCampaignApi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailCampaignApiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailCampaignApiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmailCampaignApi() throws Exception {
        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();
        emailCampaignApi.setId(count.incrementAndGet());

        // Create the EmailCampaignApi
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(emailCampaignApi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailCampaignApiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmailCampaignApi() throws Exception {
        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();
        emailCampaignApi.setId(count.incrementAndGet());

        // Create the EmailCampaignApi
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(emailCampaignApi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailCampaignApiMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailCampaignApiWithPatch() throws Exception {
        // Initialize the database
        emailCampaignApiRepository.saveAndFlush(emailCampaignApi);

        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();

        // Update the emailCampaignApi using partial update
        EmailCampaignApi partialUpdatedEmailCampaignApi = new EmailCampaignApi();
        partialUpdatedEmailCampaignApi.setId(emailCampaignApi.getId());

        partialUpdatedEmailCampaignApi.url(UPDATED_URL);

        restEmailCampaignApiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailCampaignApi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmailCampaignApi))
            )
            .andExpect(status().isOk());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
        EmailCampaignApi testEmailCampaignApi = emailCampaignApiList.get(emailCampaignApiList.size() - 1);
        assertThat(testEmailCampaignApi.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testEmailCampaignApi.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    void fullUpdateEmailCampaignApiWithPatch() throws Exception {
        // Initialize the database
        emailCampaignApiRepository.saveAndFlush(emailCampaignApi);

        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();

        // Update the emailCampaignApi using partial update
        EmailCampaignApi partialUpdatedEmailCampaignApi = new EmailCampaignApi();
        partialUpdatedEmailCampaignApi.setId(emailCampaignApi.getId());

        partialUpdatedEmailCampaignApi.url(UPDATED_URL).token(UPDATED_TOKEN);

        restEmailCampaignApiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailCampaignApi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmailCampaignApi))
            )
            .andExpect(status().isOk());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
        EmailCampaignApi testEmailCampaignApi = emailCampaignApiList.get(emailCampaignApiList.size() - 1);
        assertThat(testEmailCampaignApi.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testEmailCampaignApi.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    void patchNonExistingEmailCampaignApi() throws Exception {
        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();
        emailCampaignApi.setId(count.incrementAndGet());

        // Create the EmailCampaignApi
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(emailCampaignApi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailCampaignApiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailCampaignApiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmailCampaignApi() throws Exception {
        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();
        emailCampaignApi.setId(count.incrementAndGet());

        // Create the EmailCampaignApi
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(emailCampaignApi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailCampaignApiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmailCampaignApi() throws Exception {
        int databaseSizeBeforeUpdate = emailCampaignApiRepository.findAll().size();
        emailCampaignApi.setId(count.incrementAndGet());

        // Create the EmailCampaignApi
        EmailCampaignApiDTO emailCampaignApiDTO = emailCampaignApiMapper.toDto(emailCampaignApi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailCampaignApiMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emailCampaignApiDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmailCampaignApi in the database
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmailCampaignApi() throws Exception {
        // Initialize the database
        emailCampaignApiRepository.saveAndFlush(emailCampaignApi);

        int databaseSizeBeforeDelete = emailCampaignApiRepository.findAll().size();

        // Delete the emailCampaignApi
        restEmailCampaignApiMockMvc
            .perform(delete(ENTITY_API_URL_ID, emailCampaignApi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailCampaignApi> emailCampaignApiList = emailCampaignApiRepository.findAll();
        assertThat(emailCampaignApiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
