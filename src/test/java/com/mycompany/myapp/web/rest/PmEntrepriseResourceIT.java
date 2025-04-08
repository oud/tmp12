package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.PmEntrepriseAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PmEntreprise;
import com.mycompany.myapp.repository.PmEntrepriseRepository;
import com.mycompany.myapp.service.dto.PmEntrepriseDTO;
import com.mycompany.myapp.service.mapper.PmEntrepriseMapper;
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
 * Integration tests for the {@link PmEntrepriseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PmEntrepriseResourceIT {

    private static final String DEFAULT_ID_ENTREPRISE_RPG = "AAAAAAAAAA";
    private static final String UPDATED_ID_ENTREPRISE_RPG = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_PARTENAIRE_DISTRIBUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARTENAIRE_DISTRIBUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_SIRET_SIEGE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SIRET_SIEGE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_CATEGORIE_PERSONNE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_CATEGORIE_PERSONNE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_CATEGORIE_PERSONNE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CATEGORIE_PERSONNE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION_JURIDIQUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION_JURIDIQUE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_ETAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ETAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FERMETURE_JURIDIQUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FERMETURE_JURIDIQUE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_TRANCHE_EFFECTIF = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TRANCHE_EFFECTIF = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TRANCHE_EFFECTIF = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TRANCHE_EFFECTIF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CESSATION_ACTIVITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CESSATION_ACTIVITE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EFFECTIF_OFFICIEL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EFFECTIF_OFFICIEL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EFFECTIF_OFFICIEL = "AAAAAAAAAA";
    private static final String UPDATED_EFFECTIF_OFFICIEL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_MOTIF_CESSATION_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_MOTIF_CESSATION_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_FORME_JURIDIQUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_FORME_JURIDIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_CATEGORIE_JURIDIQUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_CATEGORIE_JURIDIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_IDCC = "AAAAAAAAAA";
    private static final String UPDATED_CODE_IDCC = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_APE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_APE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pm-entreprises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PmEntrepriseRepository pmEntrepriseRepository;

    @Autowired
    private PmEntrepriseMapper pmEntrepriseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPmEntrepriseMockMvc;

    private PmEntreprise pmEntreprise;

    private PmEntreprise insertedPmEntreprise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PmEntreprise createEntity() {
        return new PmEntreprise()
            .idEntrepriseRPG(DEFAULT_ID_ENTREPRISE_RPG)
            .codePartenaireDistributeur(DEFAULT_CODE_PARTENAIRE_DISTRIBUTEUR)
            .numeroSiretSiege(DEFAULT_NUMERO_SIRET_SIEGE)
            .codeEtat(DEFAULT_CODE_ETAT)
            .libelleEtat(DEFAULT_LIBELLE_ETAT)
            .codeCategoriePersonne(DEFAULT_CODE_CATEGORIE_PERSONNE)
            .libelleCategoriePersonne(DEFAULT_LIBELLE_CATEGORIE_PERSONNE)
            .codeType(DEFAULT_CODE_TYPE)
            .dateCreationJuridique(DEFAULT_DATE_CREATION_JURIDIQUE)
            .dateEtat(DEFAULT_DATE_ETAT)
            .dateFermetureJuridique(DEFAULT_DATE_FERMETURE_JURIDIQUE)
            .codeTrancheEffectif(DEFAULT_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(DEFAULT_LIBELLE_TRANCHE_EFFECTIF)
            .dateCessationActivite(DEFAULT_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(DEFAULT_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(DEFAULT_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(DEFAULT_CODE_MOTIF_CESSATION_ACTIVITE)
            .siren(DEFAULT_SIREN)
            .codeFormeJuridique(DEFAULT_CODE_FORME_JURIDIQUE)
            .raisonSociale(DEFAULT_RAISON_SOCIALE)
            .codeCategorieJuridique(DEFAULT_CODE_CATEGORIE_JURIDIQUE)
            .codeIDCC(DEFAULT_CODE_IDCC)
            .codeAPE(DEFAULT_CODE_APE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PmEntreprise createUpdatedEntity() {
        return new PmEntreprise()
            .idEntrepriseRPG(UPDATED_ID_ENTREPRISE_RPG)
            .codePartenaireDistributeur(UPDATED_CODE_PARTENAIRE_DISTRIBUTEUR)
            .numeroSiretSiege(UPDATED_NUMERO_SIRET_SIEGE)
            .codeEtat(UPDATED_CODE_ETAT)
            .libelleEtat(UPDATED_LIBELLE_ETAT)
            .codeCategoriePersonne(UPDATED_CODE_CATEGORIE_PERSONNE)
            .libelleCategoriePersonne(UPDATED_LIBELLE_CATEGORIE_PERSONNE)
            .codeType(UPDATED_CODE_TYPE)
            .dateCreationJuridique(UPDATED_DATE_CREATION_JURIDIQUE)
            .dateEtat(UPDATED_DATE_ETAT)
            .dateFermetureJuridique(UPDATED_DATE_FERMETURE_JURIDIQUE)
            .codeTrancheEffectif(UPDATED_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(UPDATED_LIBELLE_TRANCHE_EFFECTIF)
            .dateCessationActivite(UPDATED_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(UPDATED_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(UPDATED_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(UPDATED_CODE_MOTIF_CESSATION_ACTIVITE)
            .siren(UPDATED_SIREN)
            .codeFormeJuridique(UPDATED_CODE_FORME_JURIDIQUE)
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .codeCategorieJuridique(UPDATED_CODE_CATEGORIE_JURIDIQUE)
            .codeIDCC(UPDATED_CODE_IDCC)
            .codeAPE(UPDATED_CODE_APE);
    }

    @BeforeEach
    void initTest() {
        pmEntreprise = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPmEntreprise != null) {
            pmEntrepriseRepository.delete(insertedPmEntreprise);
            insertedPmEntreprise = null;
        }
    }

    @Test
    @Transactional
    void createPmEntreprise() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PmEntreprise
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);
        var returnedPmEntrepriseDTO = om.readValue(
            restPmEntrepriseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PmEntrepriseDTO.class
        );

        // Validate the PmEntreprise in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPmEntreprise = pmEntrepriseMapper.toEntity(returnedPmEntrepriseDTO);
        assertPmEntrepriseUpdatableFieldsEquals(returnedPmEntreprise, getPersistedPmEntreprise(returnedPmEntreprise));

        insertedPmEntreprise = returnedPmEntreprise;
    }

    @Test
    @Transactional
    void createPmEntrepriseWithExistingId() throws Exception {
        // Create the PmEntreprise with an existing ID
        pmEntreprise.setId(1L);
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PmEntreprise in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdEntrepriseRPGIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setIdEntrepriseRPG(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodePartenaireDistributeurIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodePartenaireDistributeur(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroSiretSiegeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setNumeroSiretSiege(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeEtatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeEtat(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleEtatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setLibelleEtat(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeCategoriePersonneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeCategoriePersonne(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleCategoriePersonneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setLibelleCategoriePersonne(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeType(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreationJuridiqueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setDateCreationJuridique(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateEtatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setDateEtat(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFermetureJuridiqueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setDateFermetureJuridique(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeTrancheEffectifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeTrancheEffectif(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleTrancheEffectifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setLibelleTrancheEffectif(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCessationActiviteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setDateCessationActivite(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateEffectifOfficielIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setDateEffectifOfficiel(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffectifOfficielIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setEffectifOfficiel(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeMotifCessationActiviteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeMotifCessationActivite(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSirenIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setSiren(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeFormeJuridiqueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeFormeJuridique(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRaisonSocialeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setRaisonSociale(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeCategorieJuridiqueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeCategorieJuridique(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIDCCIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeIDCC(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeAPEIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEntreprise.setCodeAPE(null);

        // Create the PmEntreprise, which fails.
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        restPmEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPmEntreprises() throws Exception {
        // Initialize the database
        insertedPmEntreprise = pmEntrepriseRepository.saveAndFlush(pmEntreprise);

        // Get all the pmEntrepriseList
        restPmEntrepriseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pmEntreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEntrepriseRPG").value(hasItem(DEFAULT_ID_ENTREPRISE_RPG)))
            .andExpect(jsonPath("$.[*].codePartenaireDistributeur").value(hasItem(DEFAULT_CODE_PARTENAIRE_DISTRIBUTEUR)))
            .andExpect(jsonPath("$.[*].numeroSiretSiege").value(hasItem(DEFAULT_NUMERO_SIRET_SIEGE)))
            .andExpect(jsonPath("$.[*].codeEtat").value(hasItem(DEFAULT_CODE_ETAT)))
            .andExpect(jsonPath("$.[*].libelleEtat").value(hasItem(DEFAULT_LIBELLE_ETAT)))
            .andExpect(jsonPath("$.[*].codeCategoriePersonne").value(hasItem(DEFAULT_CODE_CATEGORIE_PERSONNE)))
            .andExpect(jsonPath("$.[*].libelleCategoriePersonne").value(hasItem(DEFAULT_LIBELLE_CATEGORIE_PERSONNE)))
            .andExpect(jsonPath("$.[*].codeType").value(hasItem(DEFAULT_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].dateCreationJuridique").value(hasItem(DEFAULT_DATE_CREATION_JURIDIQUE.toString())))
            .andExpect(jsonPath("$.[*].dateEtat").value(hasItem(DEFAULT_DATE_ETAT.toString())))
            .andExpect(jsonPath("$.[*].dateFermetureJuridique").value(hasItem(DEFAULT_DATE_FERMETURE_JURIDIQUE.toString())))
            .andExpect(jsonPath("$.[*].codeTrancheEffectif").value(hasItem(DEFAULT_CODE_TRANCHE_EFFECTIF)))
            .andExpect(jsonPath("$.[*].libelleTrancheEffectif").value(hasItem(DEFAULT_LIBELLE_TRANCHE_EFFECTIF)))
            .andExpect(jsonPath("$.[*].dateCessationActivite").value(hasItem(DEFAULT_DATE_CESSATION_ACTIVITE.toString())))
            .andExpect(jsonPath("$.[*].dateEffectifOfficiel").value(hasItem(DEFAULT_DATE_EFFECTIF_OFFICIEL.toString())))
            .andExpect(jsonPath("$.[*].effectifOfficiel").value(hasItem(DEFAULT_EFFECTIF_OFFICIEL)))
            .andExpect(jsonPath("$.[*].codeMotifCessationActivite").value(hasItem(DEFAULT_CODE_MOTIF_CESSATION_ACTIVITE)))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN)))
            .andExpect(jsonPath("$.[*].codeFormeJuridique").value(hasItem(DEFAULT_CODE_FORME_JURIDIQUE)))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE)))
            .andExpect(jsonPath("$.[*].codeCategorieJuridique").value(hasItem(DEFAULT_CODE_CATEGORIE_JURIDIQUE)))
            .andExpect(jsonPath("$.[*].codeIDCC").value(hasItem(DEFAULT_CODE_IDCC)))
            .andExpect(jsonPath("$.[*].codeAPE").value(hasItem(DEFAULT_CODE_APE)));
    }

    @Test
    @Transactional
    void getPmEntreprise() throws Exception {
        // Initialize the database
        insertedPmEntreprise = pmEntrepriseRepository.saveAndFlush(pmEntreprise);

        // Get the pmEntreprise
        restPmEntrepriseMockMvc
            .perform(get(ENTITY_API_URL_ID, pmEntreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pmEntreprise.getId().intValue()))
            .andExpect(jsonPath("$.idEntrepriseRPG").value(DEFAULT_ID_ENTREPRISE_RPG))
            .andExpect(jsonPath("$.codePartenaireDistributeur").value(DEFAULT_CODE_PARTENAIRE_DISTRIBUTEUR))
            .andExpect(jsonPath("$.numeroSiretSiege").value(DEFAULT_NUMERO_SIRET_SIEGE))
            .andExpect(jsonPath("$.codeEtat").value(DEFAULT_CODE_ETAT))
            .andExpect(jsonPath("$.libelleEtat").value(DEFAULT_LIBELLE_ETAT))
            .andExpect(jsonPath("$.codeCategoriePersonne").value(DEFAULT_CODE_CATEGORIE_PERSONNE))
            .andExpect(jsonPath("$.libelleCategoriePersonne").value(DEFAULT_LIBELLE_CATEGORIE_PERSONNE))
            .andExpect(jsonPath("$.codeType").value(DEFAULT_CODE_TYPE))
            .andExpect(jsonPath("$.dateCreationJuridique").value(DEFAULT_DATE_CREATION_JURIDIQUE.toString()))
            .andExpect(jsonPath("$.dateEtat").value(DEFAULT_DATE_ETAT.toString()))
            .andExpect(jsonPath("$.dateFermetureJuridique").value(DEFAULT_DATE_FERMETURE_JURIDIQUE.toString()))
            .andExpect(jsonPath("$.codeTrancheEffectif").value(DEFAULT_CODE_TRANCHE_EFFECTIF))
            .andExpect(jsonPath("$.libelleTrancheEffectif").value(DEFAULT_LIBELLE_TRANCHE_EFFECTIF))
            .andExpect(jsonPath("$.dateCessationActivite").value(DEFAULT_DATE_CESSATION_ACTIVITE.toString()))
            .andExpect(jsonPath("$.dateEffectifOfficiel").value(DEFAULT_DATE_EFFECTIF_OFFICIEL.toString()))
            .andExpect(jsonPath("$.effectifOfficiel").value(DEFAULT_EFFECTIF_OFFICIEL))
            .andExpect(jsonPath("$.codeMotifCessationActivite").value(DEFAULT_CODE_MOTIF_CESSATION_ACTIVITE))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN))
            .andExpect(jsonPath("$.codeFormeJuridique").value(DEFAULT_CODE_FORME_JURIDIQUE))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE))
            .andExpect(jsonPath("$.codeCategorieJuridique").value(DEFAULT_CODE_CATEGORIE_JURIDIQUE))
            .andExpect(jsonPath("$.codeIDCC").value(DEFAULT_CODE_IDCC))
            .andExpect(jsonPath("$.codeAPE").value(DEFAULT_CODE_APE));
    }

    @Test
    @Transactional
    void getNonExistingPmEntreprise() throws Exception {
        // Get the pmEntreprise
        restPmEntrepriseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPmEntreprise() throws Exception {
        // Initialize the database
        insertedPmEntreprise = pmEntrepriseRepository.saveAndFlush(pmEntreprise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pmEntreprise
        PmEntreprise updatedPmEntreprise = pmEntrepriseRepository.findById(pmEntreprise.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPmEntreprise are not directly saved in db
        em.detach(updatedPmEntreprise);
        updatedPmEntreprise
            .idEntrepriseRPG(UPDATED_ID_ENTREPRISE_RPG)
            .codePartenaireDistributeur(UPDATED_CODE_PARTENAIRE_DISTRIBUTEUR)
            .numeroSiretSiege(UPDATED_NUMERO_SIRET_SIEGE)
            .codeEtat(UPDATED_CODE_ETAT)
            .libelleEtat(UPDATED_LIBELLE_ETAT)
            .codeCategoriePersonne(UPDATED_CODE_CATEGORIE_PERSONNE)
            .libelleCategoriePersonne(UPDATED_LIBELLE_CATEGORIE_PERSONNE)
            .codeType(UPDATED_CODE_TYPE)
            .dateCreationJuridique(UPDATED_DATE_CREATION_JURIDIQUE)
            .dateEtat(UPDATED_DATE_ETAT)
            .dateFermetureJuridique(UPDATED_DATE_FERMETURE_JURIDIQUE)
            .codeTrancheEffectif(UPDATED_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(UPDATED_LIBELLE_TRANCHE_EFFECTIF)
            .dateCessationActivite(UPDATED_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(UPDATED_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(UPDATED_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(UPDATED_CODE_MOTIF_CESSATION_ACTIVITE)
            .siren(UPDATED_SIREN)
            .codeFormeJuridique(UPDATED_CODE_FORME_JURIDIQUE)
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .codeCategorieJuridique(UPDATED_CODE_CATEGORIE_JURIDIQUE)
            .codeIDCC(UPDATED_CODE_IDCC)
            .codeAPE(UPDATED_CODE_APE);
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(updatedPmEntreprise);

        restPmEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pmEntrepriseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pmEntrepriseDTO))
            )
            .andExpect(status().isOk());

        // Validate the PmEntreprise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPmEntrepriseToMatchAllProperties(updatedPmEntreprise);
    }

    @Test
    @Transactional
    void putNonExistingPmEntreprise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEntreprise.setId(longCount.incrementAndGet());

        // Create the PmEntreprise
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPmEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pmEntrepriseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pmEntrepriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PmEntreprise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPmEntreprise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEntreprise.setId(longCount.incrementAndGet());

        // Create the PmEntreprise
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPmEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pmEntrepriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PmEntreprise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPmEntreprise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEntreprise.setId(longCount.incrementAndGet());

        // Create the PmEntreprise
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPmEntrepriseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PmEntreprise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePmEntrepriseWithPatch() throws Exception {
        // Initialize the database
        insertedPmEntreprise = pmEntrepriseRepository.saveAndFlush(pmEntreprise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pmEntreprise using partial update
        PmEntreprise partialUpdatedPmEntreprise = new PmEntreprise();
        partialUpdatedPmEntreprise.setId(pmEntreprise.getId());

        partialUpdatedPmEntreprise
            .idEntrepriseRPG(UPDATED_ID_ENTREPRISE_RPG)
            .codePartenaireDistributeur(UPDATED_CODE_PARTENAIRE_DISTRIBUTEUR)
            .numeroSiretSiege(UPDATED_NUMERO_SIRET_SIEGE)
            .libelleEtat(UPDATED_LIBELLE_ETAT)
            .codeCategoriePersonne(UPDATED_CODE_CATEGORIE_PERSONNE)
            .libelleCategoriePersonne(UPDATED_LIBELLE_CATEGORIE_PERSONNE)
            .dateEtat(UPDATED_DATE_ETAT)
            .dateFermetureJuridique(UPDATED_DATE_FERMETURE_JURIDIQUE)
            .dateCessationActivite(UPDATED_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(UPDATED_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(UPDATED_EFFECTIF_OFFICIEL)
            .codeFormeJuridique(UPDATED_CODE_FORME_JURIDIQUE)
            .codeAPE(UPDATED_CODE_APE);

        restPmEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPmEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPmEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the PmEntreprise in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPmEntrepriseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPmEntreprise, pmEntreprise),
            getPersistedPmEntreprise(pmEntreprise)
        );
    }

    @Test
    @Transactional
    void fullUpdatePmEntrepriseWithPatch() throws Exception {
        // Initialize the database
        insertedPmEntreprise = pmEntrepriseRepository.saveAndFlush(pmEntreprise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pmEntreprise using partial update
        PmEntreprise partialUpdatedPmEntreprise = new PmEntreprise();
        partialUpdatedPmEntreprise.setId(pmEntreprise.getId());

        partialUpdatedPmEntreprise
            .idEntrepriseRPG(UPDATED_ID_ENTREPRISE_RPG)
            .codePartenaireDistributeur(UPDATED_CODE_PARTENAIRE_DISTRIBUTEUR)
            .numeroSiretSiege(UPDATED_NUMERO_SIRET_SIEGE)
            .codeEtat(UPDATED_CODE_ETAT)
            .libelleEtat(UPDATED_LIBELLE_ETAT)
            .codeCategoriePersonne(UPDATED_CODE_CATEGORIE_PERSONNE)
            .libelleCategoriePersonne(UPDATED_LIBELLE_CATEGORIE_PERSONNE)
            .codeType(UPDATED_CODE_TYPE)
            .dateCreationJuridique(UPDATED_DATE_CREATION_JURIDIQUE)
            .dateEtat(UPDATED_DATE_ETAT)
            .dateFermetureJuridique(UPDATED_DATE_FERMETURE_JURIDIQUE)
            .codeTrancheEffectif(UPDATED_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(UPDATED_LIBELLE_TRANCHE_EFFECTIF)
            .dateCessationActivite(UPDATED_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(UPDATED_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(UPDATED_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(UPDATED_CODE_MOTIF_CESSATION_ACTIVITE)
            .siren(UPDATED_SIREN)
            .codeFormeJuridique(UPDATED_CODE_FORME_JURIDIQUE)
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .codeCategorieJuridique(UPDATED_CODE_CATEGORIE_JURIDIQUE)
            .codeIDCC(UPDATED_CODE_IDCC)
            .codeAPE(UPDATED_CODE_APE);

        restPmEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPmEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPmEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the PmEntreprise in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPmEntrepriseUpdatableFieldsEquals(partialUpdatedPmEntreprise, getPersistedPmEntreprise(partialUpdatedPmEntreprise));
    }

    @Test
    @Transactional
    void patchNonExistingPmEntreprise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEntreprise.setId(longCount.incrementAndGet());

        // Create the PmEntreprise
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPmEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pmEntrepriseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pmEntrepriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PmEntreprise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPmEntreprise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEntreprise.setId(longCount.incrementAndGet());

        // Create the PmEntreprise
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPmEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pmEntrepriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PmEntreprise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPmEntreprise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEntreprise.setId(longCount.incrementAndGet());

        // Create the PmEntreprise
        PmEntrepriseDTO pmEntrepriseDTO = pmEntrepriseMapper.toDto(pmEntreprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPmEntrepriseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pmEntrepriseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PmEntreprise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePmEntreprise() throws Exception {
        // Initialize the database
        insertedPmEntreprise = pmEntrepriseRepository.saveAndFlush(pmEntreprise);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pmEntreprise
        restPmEntrepriseMockMvc
            .perform(delete(ENTITY_API_URL_ID, pmEntreprise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pmEntrepriseRepository.count();
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

    protected PmEntreprise getPersistedPmEntreprise(PmEntreprise pmEntreprise) {
        return pmEntrepriseRepository.findById(pmEntreprise.getId()).orElseThrow();
    }

    protected void assertPersistedPmEntrepriseToMatchAllProperties(PmEntreprise expectedPmEntreprise) {
        assertPmEntrepriseAllPropertiesEquals(expectedPmEntreprise, getPersistedPmEntreprise(expectedPmEntreprise));
    }

    protected void assertPersistedPmEntrepriseToMatchUpdatableProperties(PmEntreprise expectedPmEntreprise) {
        assertPmEntrepriseAllUpdatablePropertiesEquals(expectedPmEntreprise, getPersistedPmEntreprise(expectedPmEntreprise));
    }
}
