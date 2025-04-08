package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.AdresseAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Adresse;
import com.mycompany.myapp.repository.AdresseRepository;
import com.mycompany.myapp.service.dto.AdresseDTO;
import com.mycompany.myapp.service.mapper.AdresseMapper;
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
 * Integration tests for the {@link AdresseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdresseResourceIT {

    private static final String DEFAULT_CODE_PAYS_ISO = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PAYS_ISO = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DERNIERE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIERE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIBELLE_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_LIGNE_1 = "AAAAAAAAAA";
    private static final String UPDATED_LIGNE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LIGNE_2 = "AAAAAAAAAA";
    private static final String UPDATED_LIGNE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LIGNE_3 = "AAAAAAAAAA";
    private static final String UPDATED_LIGNE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_LIGNE_4 = "AAAAAAAAAA";
    private static final String UPDATED_LIGNE_4 = "BBBBBBBBBB";

    private static final String DEFAULT_LIGNE_5 = "AAAAAAAAAA";
    private static final String UPDATED_LIGNE_5 = "BBBBBBBBBB";

    private static final String DEFAULT_LIGNE_6 = "AAAAAAAAAA";
    private static final String UPDATED_LIGNE_6 = "BBBBBBBBBB";

    private static final String DEFAULT_LIGNE_7 = "AAAAAAAAAA";
    private static final String UPDATED_LIGNE_7 = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_COURRIERS_PND = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COURRIERS_PND = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_USAGE_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_USAGE_ADRESSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private AdresseMapper adresseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    private Adresse insertedAdresse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createEntity() {
        return new Adresse()
            .codePaysISO(DEFAULT_CODE_PAYS_ISO)
            .codePostal(DEFAULT_CODE_POSTAL)
            .dateDerniereModification(DEFAULT_DATE_DERNIERE_MODIFICATION)
            .libelleCommune(DEFAULT_LIBELLE_COMMUNE)
            .ligne1(DEFAULT_LIGNE_1)
            .ligne2(DEFAULT_LIGNE_2)
            .ligne3(DEFAULT_LIGNE_3)
            .ligne4(DEFAULT_LIGNE_4)
            .ligne5(DEFAULT_LIGNE_5)
            .ligne6(DEFAULT_LIGNE_6)
            .ligne7(DEFAULT_LIGNE_7)
            .nombreCourriersPND(DEFAULT_NOMBRE_COURRIERS_PND)
            .codeUsageAdresse(DEFAULT_CODE_USAGE_ADRESSE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createUpdatedEntity() {
        return new Adresse()
            .codePaysISO(UPDATED_CODE_PAYS_ISO)
            .codePostal(UPDATED_CODE_POSTAL)
            .dateDerniereModification(UPDATED_DATE_DERNIERE_MODIFICATION)
            .libelleCommune(UPDATED_LIBELLE_COMMUNE)
            .ligne1(UPDATED_LIGNE_1)
            .ligne2(UPDATED_LIGNE_2)
            .ligne3(UPDATED_LIGNE_3)
            .ligne4(UPDATED_LIGNE_4)
            .ligne5(UPDATED_LIGNE_5)
            .ligne6(UPDATED_LIGNE_6)
            .ligne7(UPDATED_LIGNE_7)
            .nombreCourriersPND(UPDATED_NOMBRE_COURRIERS_PND)
            .codeUsageAdresse(UPDATED_CODE_USAGE_ADRESSE);
    }

    @BeforeEach
    void initTest() {
        adresse = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAdresse != null) {
            adresseRepository.delete(insertedAdresse);
            insertedAdresse = null;
        }
    }

    @Test
    @Transactional
    void createAdresse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);
        var returnedAdresseDTO = om.readValue(
            restAdresseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdresseDTO.class
        );

        // Validate the Adresse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAdresse = adresseMapper.toEntity(returnedAdresseDTO);
        assertAdresseUpdatableFieldsEquals(returnedAdresse, getPersistedAdresse(returnedAdresse));

        insertedAdresse = returnedAdresse;
    }

    @Test
    @Transactional
    void createAdresseWithExistingId() throws Exception {
        // Create the Adresse with an existing ID
        adresse.setId(1L);
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodePaysISOIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setCodePaysISO(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodePostalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setCodePostal(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateDerniereModificationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setDateDerniereModification(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleCommuneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setLibelleCommune(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLigne1IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setLigne1(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLigne2IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setLigne2(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLigne3IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setLigne3(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLigne4IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setLigne4(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLigne5IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setLigne5(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLigne6IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setLigne6(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLigne7IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setLigne7(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreCourriersPNDIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setNombreCourriersPND(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeUsageAdresseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adresse.setCodeUsageAdresse(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdresses() throws Exception {
        // Initialize the database
        insertedAdresse = adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList
        restAdresseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].codePaysISO").value(hasItem(DEFAULT_CODE_PAYS_ISO)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].dateDerniereModification").value(hasItem(DEFAULT_DATE_DERNIERE_MODIFICATION.toString())))
            .andExpect(jsonPath("$.[*].libelleCommune").value(hasItem(DEFAULT_LIBELLE_COMMUNE)))
            .andExpect(jsonPath("$.[*].ligne1").value(hasItem(DEFAULT_LIGNE_1)))
            .andExpect(jsonPath("$.[*].ligne2").value(hasItem(DEFAULT_LIGNE_2)))
            .andExpect(jsonPath("$.[*].ligne3").value(hasItem(DEFAULT_LIGNE_3)))
            .andExpect(jsonPath("$.[*].ligne4").value(hasItem(DEFAULT_LIGNE_4)))
            .andExpect(jsonPath("$.[*].ligne5").value(hasItem(DEFAULT_LIGNE_5)))
            .andExpect(jsonPath("$.[*].ligne6").value(hasItem(DEFAULT_LIGNE_6)))
            .andExpect(jsonPath("$.[*].ligne7").value(hasItem(DEFAULT_LIGNE_7)))
            .andExpect(jsonPath("$.[*].nombreCourriersPND").value(hasItem(DEFAULT_NOMBRE_COURRIERS_PND)))
            .andExpect(jsonPath("$.[*].codeUsageAdresse").value(hasItem(DEFAULT_CODE_USAGE_ADRESSE)));
    }

    @Test
    @Transactional
    void getAdresse() throws Exception {
        // Initialize the database
        insertedAdresse = adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc
            .perform(get(ENTITY_API_URL_ID, adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.codePaysISO").value(DEFAULT_CODE_PAYS_ISO))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.dateDerniereModification").value(DEFAULT_DATE_DERNIERE_MODIFICATION.toString()))
            .andExpect(jsonPath("$.libelleCommune").value(DEFAULT_LIBELLE_COMMUNE))
            .andExpect(jsonPath("$.ligne1").value(DEFAULT_LIGNE_1))
            .andExpect(jsonPath("$.ligne2").value(DEFAULT_LIGNE_2))
            .andExpect(jsonPath("$.ligne3").value(DEFAULT_LIGNE_3))
            .andExpect(jsonPath("$.ligne4").value(DEFAULT_LIGNE_4))
            .andExpect(jsonPath("$.ligne5").value(DEFAULT_LIGNE_5))
            .andExpect(jsonPath("$.ligne6").value(DEFAULT_LIGNE_6))
            .andExpect(jsonPath("$.ligne7").value(DEFAULT_LIGNE_7))
            .andExpect(jsonPath("$.nombreCourriersPND").value(DEFAULT_NOMBRE_COURRIERS_PND))
            .andExpect(jsonPath("$.codeUsageAdresse").value(DEFAULT_CODE_USAGE_ADRESSE));
    }

    @Test
    @Transactional
    void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdresse() throws Exception {
        // Initialize the database
        insertedAdresse = adresseRepository.saveAndFlush(adresse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findById(adresse.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdresse are not directly saved in db
        em.detach(updatedAdresse);
        updatedAdresse
            .codePaysISO(UPDATED_CODE_PAYS_ISO)
            .codePostal(UPDATED_CODE_POSTAL)
            .dateDerniereModification(UPDATED_DATE_DERNIERE_MODIFICATION)
            .libelleCommune(UPDATED_LIBELLE_COMMUNE)
            .ligne1(UPDATED_LIGNE_1)
            .ligne2(UPDATED_LIGNE_2)
            .ligne3(UPDATED_LIGNE_3)
            .ligne4(UPDATED_LIGNE_4)
            .ligne5(UPDATED_LIGNE_5)
            .ligne6(UPDATED_LIGNE_6)
            .ligne7(UPDATED_LIGNE_7)
            .nombreCourriersPND(UPDATED_NOMBRE_COURRIERS_PND)
            .codeUsageAdresse(UPDATED_CODE_USAGE_ADRESSE);
        AdresseDTO adresseDTO = adresseMapper.toDto(updatedAdresse);

        restAdresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdresseToMatchAllProperties(updatedAdresse);
    }

    @Test
    @Transactional
    void putNonExistingAdresse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresse.setId(longCount.incrementAndGet());

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adresseDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdresse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresse.setId(longCount.incrementAndGet());

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdresse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresse.setId(longCount.incrementAndGet());

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdresseWithPatch() throws Exception {
        // Initialize the database
        insertedAdresse = adresseRepository.saveAndFlush(adresse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresse using partial update
        Adresse partialUpdatedAdresse = new Adresse();
        partialUpdatedAdresse.setId(adresse.getId());

        partialUpdatedAdresse
            .codePaysISO(UPDATED_CODE_PAYS_ISO)
            .libelleCommune(UPDATED_LIBELLE_COMMUNE)
            .ligne4(UPDATED_LIGNE_4)
            .ligne5(UPDATED_LIGNE_5);

        restAdresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresse))
            )
            .andExpect(status().isOk());

        // Validate the Adresse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresseUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAdresse, adresse), getPersistedAdresse(adresse));
    }

    @Test
    @Transactional
    void fullUpdateAdresseWithPatch() throws Exception {
        // Initialize the database
        insertedAdresse = adresseRepository.saveAndFlush(adresse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adresse using partial update
        Adresse partialUpdatedAdresse = new Adresse();
        partialUpdatedAdresse.setId(adresse.getId());

        partialUpdatedAdresse
            .codePaysISO(UPDATED_CODE_PAYS_ISO)
            .codePostal(UPDATED_CODE_POSTAL)
            .dateDerniereModification(UPDATED_DATE_DERNIERE_MODIFICATION)
            .libelleCommune(UPDATED_LIBELLE_COMMUNE)
            .ligne1(UPDATED_LIGNE_1)
            .ligne2(UPDATED_LIGNE_2)
            .ligne3(UPDATED_LIGNE_3)
            .ligne4(UPDATED_LIGNE_4)
            .ligne5(UPDATED_LIGNE_5)
            .ligne6(UPDATED_LIGNE_6)
            .ligne7(UPDATED_LIGNE_7)
            .nombreCourriersPND(UPDATED_NOMBRE_COURRIERS_PND)
            .codeUsageAdresse(UPDATED_CODE_USAGE_ADRESSE);

        restAdresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdresse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdresse))
            )
            .andExpect(status().isOk());

        // Validate the Adresse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdresseUpdatableFieldsEquals(partialUpdatedAdresse, getPersistedAdresse(partialUpdatedAdresse));
    }

    @Test
    @Transactional
    void patchNonExistingAdresse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresse.setId(longCount.incrementAndGet());

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adresseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdresse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresse.setId(longCount.incrementAndGet());

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adresseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdresse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adresse.setId(longCount.incrementAndGet());

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdresseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(adresseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adresse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdresse() throws Exception {
        // Initialize the database
        insertedAdresse = adresseRepository.saveAndFlush(adresse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adresse
        restAdresseMockMvc
            .perform(delete(ENTITY_API_URL_ID, adresse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adresseRepository.count();
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

    protected Adresse getPersistedAdresse(Adresse adresse) {
        return adresseRepository.findById(adresse.getId()).orElseThrow();
    }

    protected void assertPersistedAdresseToMatchAllProperties(Adresse expectedAdresse) {
        assertAdresseAllPropertiesEquals(expectedAdresse, getPersistedAdresse(expectedAdresse));
    }

    protected void assertPersistedAdresseToMatchUpdatableProperties(Adresse expectedAdresse) {
        assertAdresseAllUpdatablePropertiesEquals(expectedAdresse, getPersistedAdresse(expectedAdresse));
    }
}
