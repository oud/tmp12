package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.TelephoneAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Telephone;
import com.mycompany.myapp.repository.TelephoneRepository;
import com.mycompany.myapp.service.dto.TelephoneDTO;
import com.mycompany.myapp.service.mapper.TelephoneMapper;
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
 * Integration tests for the {@link TelephoneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelephoneResourceIT {

    private static final String DEFAULT_CODE_PAYS_ISO = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PAYS_ISO = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_TYPE_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_TELEPHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DERNIERE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIERE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_INDICATIF_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_INDICATIF_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_USAGE_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_USAGE_TELEPHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/telephones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private TelephoneMapper telephoneMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelephoneMockMvc;

    private Telephone telephone;

    private Telephone insertedTelephone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telephone createEntity() {
        return new Telephone()
            .codePaysISO(DEFAULT_CODE_PAYS_ISO)
            .codeTypeTelephone(DEFAULT_CODE_TYPE_TELEPHONE)
            .dateDerniereModification(DEFAULT_DATE_DERNIERE_MODIFICATION)
            .codeIndicatifPays(DEFAULT_CODE_INDICATIF_PAYS)
            .numeroTelephone(DEFAULT_NUMERO_TELEPHONE)
            .codeStatut(DEFAULT_CODE_STATUT)
            .dateStatut(DEFAULT_DATE_STATUT)
            .codeUsageTelephone(DEFAULT_CODE_USAGE_TELEPHONE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telephone createUpdatedEntity() {
        return new Telephone()
            .codePaysISO(UPDATED_CODE_PAYS_ISO)
            .codeTypeTelephone(UPDATED_CODE_TYPE_TELEPHONE)
            .dateDerniereModification(UPDATED_DATE_DERNIERE_MODIFICATION)
            .codeIndicatifPays(UPDATED_CODE_INDICATIF_PAYS)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .codeStatut(UPDATED_CODE_STATUT)
            .dateStatut(UPDATED_DATE_STATUT)
            .codeUsageTelephone(UPDATED_CODE_USAGE_TELEPHONE);
    }

    @BeforeEach
    void initTest() {
        telephone = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTelephone != null) {
            telephoneRepository.delete(insertedTelephone);
            insertedTelephone = null;
        }
    }

    @Test
    @Transactional
    void createTelephone() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);
        var returnedTelephoneDTO = om.readValue(
            restTelephoneMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TelephoneDTO.class
        );

        // Validate the Telephone in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTelephone = telephoneMapper.toEntity(returnedTelephoneDTO);
        assertTelephoneUpdatableFieldsEquals(returnedTelephone, getPersistedTelephone(returnedTelephone));

        insertedTelephone = returnedTelephone;
    }

    @Test
    @Transactional
    void createTelephoneWithExistingId() throws Exception {
        // Create the Telephone with an existing ID
        telephone.setId(1L);
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telephone in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodePaysISOIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        telephone.setCodePaysISO(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeTypeTelephoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        telephone.setCodeTypeTelephone(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateDerniereModificationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        telephone.setDateDerniereModification(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIndicatifPaysIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        telephone.setCodeIndicatifPays(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroTelephoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        telephone.setNumeroTelephone(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        telephone.setCodeStatut(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        telephone.setDateStatut(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeUsageTelephoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        telephone.setCodeUsageTelephone(null);

        // Create the Telephone, which fails.
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        restTelephoneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTelephones() throws Exception {
        // Initialize the database
        insertedTelephone = telephoneRepository.saveAndFlush(telephone);

        // Get all the telephoneList
        restTelephoneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telephone.getId().intValue())))
            .andExpect(jsonPath("$.[*].codePaysISO").value(hasItem(DEFAULT_CODE_PAYS_ISO)))
            .andExpect(jsonPath("$.[*].codeTypeTelephone").value(hasItem(DEFAULT_CODE_TYPE_TELEPHONE)))
            .andExpect(jsonPath("$.[*].dateDerniereModification").value(hasItem(DEFAULT_DATE_DERNIERE_MODIFICATION.toString())))
            .andExpect(jsonPath("$.[*].codeIndicatifPays").value(hasItem(DEFAULT_CODE_INDICATIF_PAYS)))
            .andExpect(jsonPath("$.[*].numeroTelephone").value(hasItem(DEFAULT_NUMERO_TELEPHONE)))
            .andExpect(jsonPath("$.[*].codeStatut").value(hasItem(DEFAULT_CODE_STATUT)))
            .andExpect(jsonPath("$.[*].dateStatut").value(hasItem(DEFAULT_DATE_STATUT)))
            .andExpect(jsonPath("$.[*].codeUsageTelephone").value(hasItem(DEFAULT_CODE_USAGE_TELEPHONE)));
    }

    @Test
    @Transactional
    void getTelephone() throws Exception {
        // Initialize the database
        insertedTelephone = telephoneRepository.saveAndFlush(telephone);

        // Get the telephone
        restTelephoneMockMvc
            .perform(get(ENTITY_API_URL_ID, telephone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telephone.getId().intValue()))
            .andExpect(jsonPath("$.codePaysISO").value(DEFAULT_CODE_PAYS_ISO))
            .andExpect(jsonPath("$.codeTypeTelephone").value(DEFAULT_CODE_TYPE_TELEPHONE))
            .andExpect(jsonPath("$.dateDerniereModification").value(DEFAULT_DATE_DERNIERE_MODIFICATION.toString()))
            .andExpect(jsonPath("$.codeIndicatifPays").value(DEFAULT_CODE_INDICATIF_PAYS))
            .andExpect(jsonPath("$.numeroTelephone").value(DEFAULT_NUMERO_TELEPHONE))
            .andExpect(jsonPath("$.codeStatut").value(DEFAULT_CODE_STATUT))
            .andExpect(jsonPath("$.dateStatut").value(DEFAULT_DATE_STATUT))
            .andExpect(jsonPath("$.codeUsageTelephone").value(DEFAULT_CODE_USAGE_TELEPHONE));
    }

    @Test
    @Transactional
    void getNonExistingTelephone() throws Exception {
        // Get the telephone
        restTelephoneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelephone() throws Exception {
        // Initialize the database
        insertedTelephone = telephoneRepository.saveAndFlush(telephone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telephone
        Telephone updatedTelephone = telephoneRepository.findById(telephone.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTelephone are not directly saved in db
        em.detach(updatedTelephone);
        updatedTelephone
            .codePaysISO(UPDATED_CODE_PAYS_ISO)
            .codeTypeTelephone(UPDATED_CODE_TYPE_TELEPHONE)
            .dateDerniereModification(UPDATED_DATE_DERNIERE_MODIFICATION)
            .codeIndicatifPays(UPDATED_CODE_INDICATIF_PAYS)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .codeStatut(UPDATED_CODE_STATUT)
            .dateStatut(UPDATED_DATE_STATUT)
            .codeUsageTelephone(UPDATED_CODE_USAGE_TELEPHONE);
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(updatedTelephone);

        restTelephoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telephoneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telephoneDTO))
            )
            .andExpect(status().isOk());

        // Validate the Telephone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTelephoneToMatchAllProperties(updatedTelephone);
    }

    @Test
    @Transactional
    void putNonExistingTelephone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telephone.setId(longCount.incrementAndGet());

        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telephoneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telephoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telephone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelephone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telephone.setId(longCount.incrementAndGet());

        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephoneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(telephoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telephone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelephone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telephone.setId(longCount.incrementAndGet());

        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephoneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telephone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelephoneWithPatch() throws Exception {
        // Initialize the database
        insertedTelephone = telephoneRepository.saveAndFlush(telephone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telephone using partial update
        Telephone partialUpdatedTelephone = new Telephone();
        partialUpdatedTelephone.setId(telephone.getId());

        partialUpdatedTelephone.codeStatut(UPDATED_CODE_STATUT).codeUsageTelephone(UPDATED_CODE_USAGE_TELEPHONE);

        restTelephoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelephone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTelephone))
            )
            .andExpect(status().isOk());

        // Validate the Telephone in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTelephoneUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTelephone, telephone),
            getPersistedTelephone(telephone)
        );
    }

    @Test
    @Transactional
    void fullUpdateTelephoneWithPatch() throws Exception {
        // Initialize the database
        insertedTelephone = telephoneRepository.saveAndFlush(telephone);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the telephone using partial update
        Telephone partialUpdatedTelephone = new Telephone();
        partialUpdatedTelephone.setId(telephone.getId());

        partialUpdatedTelephone
            .codePaysISO(UPDATED_CODE_PAYS_ISO)
            .codeTypeTelephone(UPDATED_CODE_TYPE_TELEPHONE)
            .dateDerniereModification(UPDATED_DATE_DERNIERE_MODIFICATION)
            .codeIndicatifPays(UPDATED_CODE_INDICATIF_PAYS)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .codeStatut(UPDATED_CODE_STATUT)
            .dateStatut(UPDATED_DATE_STATUT)
            .codeUsageTelephone(UPDATED_CODE_USAGE_TELEPHONE);

        restTelephoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelephone.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTelephone))
            )
            .andExpect(status().isOk());

        // Validate the Telephone in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTelephoneUpdatableFieldsEquals(partialUpdatedTelephone, getPersistedTelephone(partialUpdatedTelephone));
    }

    @Test
    @Transactional
    void patchNonExistingTelephone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telephone.setId(longCount.incrementAndGet());

        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telephoneDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(telephoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telephone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelephone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telephone.setId(longCount.incrementAndGet());

        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephoneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(telephoneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telephone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelephone() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        telephone.setId(longCount.incrementAndGet());

        // Create the Telephone
        TelephoneDTO telephoneDTO = telephoneMapper.toDto(telephone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelephoneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(telephoneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telephone in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelephone() throws Exception {
        // Initialize the database
        insertedTelephone = telephoneRepository.saveAndFlush(telephone);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the telephone
        restTelephoneMockMvc
            .perform(delete(ENTITY_API_URL_ID, telephone.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return telephoneRepository.count();
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

    protected Telephone getPersistedTelephone(Telephone telephone) {
        return telephoneRepository.findById(telephone.getId()).orElseThrow();
    }

    protected void assertPersistedTelephoneToMatchAllProperties(Telephone expectedTelephone) {
        assertTelephoneAllPropertiesEquals(expectedTelephone, getPersistedTelephone(expectedTelephone));
    }

    protected void assertPersistedTelephoneToMatchUpdatableProperties(Telephone expectedTelephone) {
        assertTelephoneAllUpdatablePropertiesEquals(expectedTelephone, getPersistedTelephone(expectedTelephone));
    }
}
