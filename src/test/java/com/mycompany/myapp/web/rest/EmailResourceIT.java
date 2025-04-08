package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.EmailAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Email;
import com.mycompany.myapp.repository.EmailRepository;
import com.mycompany.myapp.service.dto.EmailDTO;
import com.mycompany.myapp.service.mapper.EmailMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailResourceIT {

    private static final String DEFAULT_ADRESSE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_STATUT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_STATUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_STATUT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_USAGE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_USAGE_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/emails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailMockMvc;

    private Email email;

    private Email insertedEmail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createEntity() {
        return new Email()
            .adresseEmail(DEFAULT_ADRESSE_EMAIL)
            .codeStatut(DEFAULT_CODE_STATUT)
            .dateStatut(DEFAULT_DATE_STATUT)
            .codeUsageEmail(DEFAULT_CODE_USAGE_EMAIL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Email createUpdatedEntity() {
        return new Email()
            .adresseEmail(UPDATED_ADRESSE_EMAIL)
            .codeStatut(UPDATED_CODE_STATUT)
            .dateStatut(UPDATED_DATE_STATUT)
            .codeUsageEmail(UPDATED_CODE_USAGE_EMAIL);
    }

    @BeforeEach
    void initTest() {
        email = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEmail != null) {
            emailRepository.delete(insertedEmail);
            insertedEmail = null;
        }
    }

    @Test
    @Transactional
    void createEmail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);
        var returnedEmailDTO = om.readValue(
            restEmailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmailDTO.class
        );

        // Validate the Email in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEmail = emailMapper.toEntity(returnedEmailDTO);
        assertEmailUpdatableFieldsEquals(returnedEmail, getPersistedEmail(returnedEmail));

        insertedEmail = returnedEmail;
    }

    @Test
    @Transactional
    void createEmailWithExistingId() throws Exception {
        // Create the Email with an existing ID
        email.setId(1L);
        EmailDTO emailDTO = emailMapper.toDto(email);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAdresseEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        email.setAdresseEmail(null);

        // Create the Email, which fails.
        EmailDTO emailDTO = emailMapper.toDto(email);

        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        email.setCodeStatut(null);

        // Create the Email, which fails.
        EmailDTO emailDTO = emailMapper.toDto(email);

        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        email.setDateStatut(null);

        // Create the Email, which fails.
        EmailDTO emailDTO = emailMapper.toDto(email);

        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeUsageEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        email.setCodeUsageEmail(null);

        // Create the Email, which fails.
        EmailDTO emailDTO = emailMapper.toDto(email);

        restEmailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmails() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get all the emailList
        restEmailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresseEmail").value(hasItem(DEFAULT_ADRESSE_EMAIL)))
            .andExpect(jsonPath("$.[*].codeStatut").value(hasItem(DEFAULT_CODE_STATUT)))
            .andExpect(jsonPath("$.[*].dateStatut").value(hasItem(DEFAULT_DATE_STATUT.toString())))
            .andExpect(jsonPath("$.[*].codeUsageEmail").value(hasItem(DEFAULT_CODE_USAGE_EMAIL)));
    }

    @Test
    @Transactional
    void getEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        // Get the email
        restEmailMockMvc
            .perform(get(ENTITY_API_URL_ID, email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(email.getId().intValue()))
            .andExpect(jsonPath("$.adresseEmail").value(DEFAULT_ADRESSE_EMAIL))
            .andExpect(jsonPath("$.codeStatut").value(DEFAULT_CODE_STATUT))
            .andExpect(jsonPath("$.dateStatut").value(DEFAULT_DATE_STATUT.toString()))
            .andExpect(jsonPath("$.codeUsageEmail").value(DEFAULT_CODE_USAGE_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingEmail() throws Exception {
        // Get the email
        restEmailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email
        Email updatedEmail = emailRepository.findById(email.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmail are not directly saved in db
        em.detach(updatedEmail);
        updatedEmail
            .adresseEmail(UPDATED_ADRESSE_EMAIL)
            .codeStatut(UPDATED_CODE_STATUT)
            .dateStatut(UPDATED_DATE_STATUT)
            .codeUsageEmail(UPDATED_CODE_USAGE_EMAIL);
        EmailDTO emailDTO = emailMapper.toDto(updatedEmail);

        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmailToMatchAllProperties(updatedEmail);
    }

    @Test
    @Transactional
    void putNonExistingEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailWithPatch() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email using partial update
        Email partialUpdatedEmail = new Email();
        partialUpdatedEmail.setId(email.getId());

        partialUpdatedEmail.codeUsageEmail(UPDATED_CODE_USAGE_EMAIL);

        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmailUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEmail, email), getPersistedEmail(email));
    }

    @Test
    @Transactional
    void fullUpdateEmailWithPatch() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the email using partial update
        Email partialUpdatedEmail = new Email();
        partialUpdatedEmail.setId(email.getId());

        partialUpdatedEmail
            .adresseEmail(UPDATED_ADRESSE_EMAIL)
            .codeStatut(UPDATED_CODE_STATUT)
            .dateStatut(UPDATED_DATE_STATUT)
            .codeUsageEmail(UPDATED_CODE_USAGE_EMAIL);

        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmail))
            )
            .andExpect(status().isOk());

        // Validate the Email in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmailUpdatableFieldsEquals(partialUpdatedEmail, getPersistedEmail(partialUpdatedEmail));
    }

    @Test
    @Transactional
    void patchNonExistingEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(emailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        email.setId(longCount.incrementAndGet());

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(emailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Email in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmail() throws Exception {
        // Initialize the database
        insertedEmail = emailRepository.saveAndFlush(email);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the email
        restEmailMockMvc
            .perform(delete(ENTITY_API_URL_ID, email.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return emailRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Email getPersistedEmail(Email email) {
        return emailRepository.findById(email.getId()).orElseThrow();
    }

    protected void assertPersistedEmailToMatchAllProperties(Email expectedEmail) {
        assertEmailAllPropertiesEquals(expectedEmail, getPersistedEmail(expectedEmail));
    }

    protected void assertPersistedEmailToMatchUpdatableProperties(Email expectedEmail) {
        assertEmailAllUpdatablePropertiesEquals(expectedEmail, getPersistedEmail(expectedEmail));
    }
}
