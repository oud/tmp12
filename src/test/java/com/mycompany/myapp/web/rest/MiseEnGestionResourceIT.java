package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.MiseEnGestionAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MiseEnGestion;
import com.mycompany.myapp.repository.MiseEnGestionRepository;
import com.mycompany.myapp.service.dto.MiseEnGestionDTO;
import com.mycompany.myapp.service.mapper.MiseEnGestionMapper;
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
 * Integration tests for the {@link MiseEnGestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MiseEnGestionResourceIT {

    private static final String DEFAULT_TYPE_MEG = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_MEG = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_OFFRE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_OFFRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_EFFET = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EFFET = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/mise-en-gestions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MiseEnGestionRepository miseEnGestionRepository;

    @Autowired
    private MiseEnGestionMapper miseEnGestionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMiseEnGestionMockMvc;

    private MiseEnGestion miseEnGestion;

    private MiseEnGestion insertedMiseEnGestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MiseEnGestion createEntity() {
        return new MiseEnGestion().typeMEG(DEFAULT_TYPE_MEG).codeOffre(DEFAULT_CODE_OFFRE).dateEffet(DEFAULT_DATE_EFFET);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MiseEnGestion createUpdatedEntity() {
        return new MiseEnGestion().typeMEG(UPDATED_TYPE_MEG).codeOffre(UPDATED_CODE_OFFRE).dateEffet(UPDATED_DATE_EFFET);
    }

    @BeforeEach
    void initTest() {
        miseEnGestion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMiseEnGestion != null) {
            miseEnGestionRepository.delete(insertedMiseEnGestion);
            insertedMiseEnGestion = null;
        }
    }

    @Test
    @Transactional
    void createMiseEnGestion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MiseEnGestion
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);
        var returnedMiseEnGestionDTO = om.readValue(
            restMiseEnGestionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miseEnGestionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MiseEnGestionDTO.class
        );

        // Validate the MiseEnGestion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMiseEnGestion = miseEnGestionMapper.toEntity(returnedMiseEnGestionDTO);
        assertMiseEnGestionUpdatableFieldsEquals(returnedMiseEnGestion, getPersistedMiseEnGestion(returnedMiseEnGestion));

        insertedMiseEnGestion = returnedMiseEnGestion;
    }

    @Test
    @Transactional
    void createMiseEnGestionWithExistingId() throws Exception {
        // Create the MiseEnGestion with an existing ID
        miseEnGestion.setId(1L);
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMiseEnGestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miseEnGestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MiseEnGestion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeMEGIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        miseEnGestion.setTypeMEG(null);

        // Create the MiseEnGestion, which fails.
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        restMiseEnGestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miseEnGestionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeOffreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        miseEnGestion.setCodeOffre(null);

        // Create the MiseEnGestion, which fails.
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        restMiseEnGestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miseEnGestionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateEffetIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        miseEnGestion.setDateEffet(null);

        // Create the MiseEnGestion, which fails.
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        restMiseEnGestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miseEnGestionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMiseEnGestions() throws Exception {
        // Initialize the database
        insertedMiseEnGestion = miseEnGestionRepository.saveAndFlush(miseEnGestion);

        // Get all the miseEnGestionList
        restMiseEnGestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(miseEnGestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeMEG").value(hasItem(DEFAULT_TYPE_MEG)))
            .andExpect(jsonPath("$.[*].codeOffre").value(hasItem(DEFAULT_CODE_OFFRE)))
            .andExpect(jsonPath("$.[*].dateEffet").value(hasItem(DEFAULT_DATE_EFFET.toString())));
    }

    @Test
    @Transactional
    void getMiseEnGestion() throws Exception {
        // Initialize the database
        insertedMiseEnGestion = miseEnGestionRepository.saveAndFlush(miseEnGestion);

        // Get the miseEnGestion
        restMiseEnGestionMockMvc
            .perform(get(ENTITY_API_URL_ID, miseEnGestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(miseEnGestion.getId().intValue()))
            .andExpect(jsonPath("$.typeMEG").value(DEFAULT_TYPE_MEG))
            .andExpect(jsonPath("$.codeOffre").value(DEFAULT_CODE_OFFRE))
            .andExpect(jsonPath("$.dateEffet").value(DEFAULT_DATE_EFFET.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMiseEnGestion() throws Exception {
        // Get the miseEnGestion
        restMiseEnGestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMiseEnGestion() throws Exception {
        // Initialize the database
        insertedMiseEnGestion = miseEnGestionRepository.saveAndFlush(miseEnGestion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the miseEnGestion
        MiseEnGestion updatedMiseEnGestion = miseEnGestionRepository.findById(miseEnGestion.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMiseEnGestion are not directly saved in db
        em.detach(updatedMiseEnGestion);
        updatedMiseEnGestion.typeMEG(UPDATED_TYPE_MEG).codeOffre(UPDATED_CODE_OFFRE).dateEffet(UPDATED_DATE_EFFET);
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(updatedMiseEnGestion);

        restMiseEnGestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, miseEnGestionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(miseEnGestionDTO))
            )
            .andExpect(status().isOk());

        // Validate the MiseEnGestion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMiseEnGestionToMatchAllProperties(updatedMiseEnGestion);
    }

    @Test
    @Transactional
    void putNonExistingMiseEnGestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miseEnGestion.setId(longCount.incrementAndGet());

        // Create the MiseEnGestion
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMiseEnGestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, miseEnGestionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(miseEnGestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MiseEnGestion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMiseEnGestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miseEnGestion.setId(longCount.incrementAndGet());

        // Create the MiseEnGestion
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMiseEnGestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(miseEnGestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MiseEnGestion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMiseEnGestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miseEnGestion.setId(longCount.incrementAndGet());

        // Create the MiseEnGestion
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMiseEnGestionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(miseEnGestionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MiseEnGestion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMiseEnGestionWithPatch() throws Exception {
        // Initialize the database
        insertedMiseEnGestion = miseEnGestionRepository.saveAndFlush(miseEnGestion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the miseEnGestion using partial update
        MiseEnGestion partialUpdatedMiseEnGestion = new MiseEnGestion();
        partialUpdatedMiseEnGestion.setId(miseEnGestion.getId());

        partialUpdatedMiseEnGestion.typeMEG(UPDATED_TYPE_MEG).codeOffre(UPDATED_CODE_OFFRE);

        restMiseEnGestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMiseEnGestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMiseEnGestion))
            )
            .andExpect(status().isOk());

        // Validate the MiseEnGestion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMiseEnGestionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMiseEnGestion, miseEnGestion),
            getPersistedMiseEnGestion(miseEnGestion)
        );
    }

    @Test
    @Transactional
    void fullUpdateMiseEnGestionWithPatch() throws Exception {
        // Initialize the database
        insertedMiseEnGestion = miseEnGestionRepository.saveAndFlush(miseEnGestion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the miseEnGestion using partial update
        MiseEnGestion partialUpdatedMiseEnGestion = new MiseEnGestion();
        partialUpdatedMiseEnGestion.setId(miseEnGestion.getId());

        partialUpdatedMiseEnGestion.typeMEG(UPDATED_TYPE_MEG).codeOffre(UPDATED_CODE_OFFRE).dateEffet(UPDATED_DATE_EFFET);

        restMiseEnGestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMiseEnGestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMiseEnGestion))
            )
            .andExpect(status().isOk());

        // Validate the MiseEnGestion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMiseEnGestionUpdatableFieldsEquals(partialUpdatedMiseEnGestion, getPersistedMiseEnGestion(partialUpdatedMiseEnGestion));
    }

    @Test
    @Transactional
    void patchNonExistingMiseEnGestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miseEnGestion.setId(longCount.incrementAndGet());

        // Create the MiseEnGestion
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMiseEnGestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, miseEnGestionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(miseEnGestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MiseEnGestion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMiseEnGestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miseEnGestion.setId(longCount.incrementAndGet());

        // Create the MiseEnGestion
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMiseEnGestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(miseEnGestionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MiseEnGestion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMiseEnGestion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        miseEnGestion.setId(longCount.incrementAndGet());

        // Create the MiseEnGestion
        MiseEnGestionDTO miseEnGestionDTO = miseEnGestionMapper.toDto(miseEnGestion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMiseEnGestionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(miseEnGestionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MiseEnGestion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMiseEnGestion() throws Exception {
        // Initialize the database
        insertedMiseEnGestion = miseEnGestionRepository.saveAndFlush(miseEnGestion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the miseEnGestion
        restMiseEnGestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, miseEnGestion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return miseEnGestionRepository.count();
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

    protected MiseEnGestion getPersistedMiseEnGestion(MiseEnGestion miseEnGestion) {
        return miseEnGestionRepository.findById(miseEnGestion.getId()).orElseThrow();
    }

    protected void assertPersistedMiseEnGestionToMatchAllProperties(MiseEnGestion expectedMiseEnGestion) {
        assertMiseEnGestionAllPropertiesEquals(expectedMiseEnGestion, getPersistedMiseEnGestion(expectedMiseEnGestion));
    }

    protected void assertPersistedMiseEnGestionToMatchUpdatableProperties(MiseEnGestion expectedMiseEnGestion) {
        assertMiseEnGestionAllUpdatablePropertiesEquals(expectedMiseEnGestion, getPersistedMiseEnGestion(expectedMiseEnGestion));
    }
}
