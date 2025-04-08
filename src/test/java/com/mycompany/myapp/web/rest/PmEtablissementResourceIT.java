package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.PmEtablissementAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PmEtablissement;
import com.mycompany.myapp.repository.PmEtablissementRepository;
import com.mycompany.myapp.service.dto.PmEtablissementDTO;
import com.mycompany.myapp.service.mapper.PmEtablissementMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link PmEtablissementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PmEtablissementResourceIT {

    private static final String DEFAULT_ID_ETABLISSEMENT_RPG = "AAAAAAAAAA";
    private static final String UPDATED_ID_ETABLISSEMENT_RPG = "BBBBBBBBBB";

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

    private static final String DEFAULT_DATE_CREATION_JURIDIQUE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_CREATION_JURIDIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_FERMETURE_JURIDIQUE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_FERMETURE_JURIDIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_IDCC = "AAAAAAAAAA";
    private static final String UPDATED_CODE_IDCC = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_TRANCHE_EFFECTIF = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TRANCHE_EFFECTIF = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TRANCHE_EFFECTIF = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TRANCHE_EFFECTIF = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_CESSATION_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_CESSATION_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_EFFECTIF_OFFICIEL = "AAAAAAAAAA";
    private static final String UPDATED_DATE_EFFECTIF_OFFICIEL = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECTIF_OFFICIEL = "AAAAAAAAAA";
    private static final String UPDATED_EFFECTIF_OFFICIEL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_MOTIF_CESSATION_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_MOTIF_CESSATION_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_SIRET = "AAAAAAAAAA";
    private static final String UPDATED_SIRET = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_TYPE_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_ENSEIGNE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ENSEIGNE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_NIC = "AAAAAAAAAA";
    private static final String UPDATED_CODE_NIC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pm-etablissements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PmEtablissementRepository pmEtablissementRepository;

    @Autowired
    private PmEtablissementMapper pmEtablissementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPmEtablissementMockMvc;

    private PmEtablissement pmEtablissement;

    private PmEtablissement insertedPmEtablissement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PmEtablissement createEntity() {
        return new PmEtablissement()
            .idEtablissementRPG(DEFAULT_ID_ETABLISSEMENT_RPG)
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
            .codeIDCC(DEFAULT_CODE_IDCC)
            .codeTrancheEffectif(DEFAULT_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(DEFAULT_LIBELLE_TRANCHE_EFFECTIF)
            .dateCessationActivite(DEFAULT_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(DEFAULT_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(DEFAULT_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(DEFAULT_CODE_MOTIF_CESSATION_ACTIVITE)
            .siret(DEFAULT_SIRET)
            .codeTypeEtablissement(DEFAULT_CODE_TYPE_ETABLISSEMENT)
            .libelleEnseigne(DEFAULT_LIBELLE_ENSEIGNE)
            .codeNIC(DEFAULT_CODE_NIC);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PmEtablissement createUpdatedEntity() {
        return new PmEtablissement()
            .idEtablissementRPG(UPDATED_ID_ETABLISSEMENT_RPG)
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
            .codeIDCC(UPDATED_CODE_IDCC)
            .codeTrancheEffectif(UPDATED_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(UPDATED_LIBELLE_TRANCHE_EFFECTIF)
            .dateCessationActivite(UPDATED_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(UPDATED_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(UPDATED_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(UPDATED_CODE_MOTIF_CESSATION_ACTIVITE)
            .siret(UPDATED_SIRET)
            .codeTypeEtablissement(UPDATED_CODE_TYPE_ETABLISSEMENT)
            .libelleEnseigne(UPDATED_LIBELLE_ENSEIGNE)
            .codeNIC(UPDATED_CODE_NIC);
    }

    @BeforeEach
    void initTest() {
        pmEtablissement = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPmEtablissement != null) {
            pmEtablissementRepository.delete(insertedPmEtablissement);
            insertedPmEtablissement = null;
        }
    }

    @Test
    @Transactional
    void createPmEtablissement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PmEtablissement
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);
        var returnedPmEtablissementDTO = om.readValue(
            restPmEtablissementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PmEtablissementDTO.class
        );

        // Validate the PmEtablissement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPmEtablissement = pmEtablissementMapper.toEntity(returnedPmEtablissementDTO);
        assertPmEtablissementUpdatableFieldsEquals(returnedPmEtablissement, getPersistedPmEtablissement(returnedPmEtablissement));

        insertedPmEtablissement = returnedPmEtablissement;
    }

    @Test
    @Transactional
    void createPmEtablissementWithExistingId() throws Exception {
        // Create the PmEtablissement with an existing ID
        pmEtablissement.setId(1L);
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PmEtablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdEtablissementRPGIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setIdEtablissementRPG(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodePartenaireDistributeurIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodePartenaireDistributeur(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroSiretSiegeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setNumeroSiretSiege(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeEtatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodeEtat(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleEtatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setLibelleEtat(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeCategoriePersonneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodeCategoriePersonne(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleCategoriePersonneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setLibelleCategoriePersonne(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodeType(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreationJuridiqueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setDateCreationJuridique(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateEtatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setDateEtat(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFermetureJuridiqueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setDateFermetureJuridique(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIDCCIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodeIDCC(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeTrancheEffectifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodeTrancheEffectif(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleTrancheEffectifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setLibelleTrancheEffectif(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCessationActiviteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setDateCessationActivite(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateEffectifOfficielIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setDateEffectifOfficiel(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEffectifOfficielIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setEffectifOfficiel(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeMotifCessationActiviteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodeMotifCessationActivite(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSiretIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setSiret(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeTypeEtablissementIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodeTypeEtablissement(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleEnseigneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setLibelleEnseigne(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeNICIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pmEtablissement.setCodeNIC(null);

        // Create the PmEtablissement, which fails.
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        restPmEtablissementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPmEtablissements() throws Exception {
        // Initialize the database
        insertedPmEtablissement = pmEtablissementRepository.saveAndFlush(pmEtablissement);

        // Get all the pmEtablissementList
        restPmEtablissementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pmEtablissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEtablissementRPG").value(hasItem(DEFAULT_ID_ETABLISSEMENT_RPG)))
            .andExpect(jsonPath("$.[*].codePartenaireDistributeur").value(hasItem(DEFAULT_CODE_PARTENAIRE_DISTRIBUTEUR)))
            .andExpect(jsonPath("$.[*].numeroSiretSiege").value(hasItem(DEFAULT_NUMERO_SIRET_SIEGE)))
            .andExpect(jsonPath("$.[*].codeEtat").value(hasItem(DEFAULT_CODE_ETAT)))
            .andExpect(jsonPath("$.[*].libelleEtat").value(hasItem(DEFAULT_LIBELLE_ETAT)))
            .andExpect(jsonPath("$.[*].codeCategoriePersonne").value(hasItem(DEFAULT_CODE_CATEGORIE_PERSONNE)))
            .andExpect(jsonPath("$.[*].libelleCategoriePersonne").value(hasItem(DEFAULT_LIBELLE_CATEGORIE_PERSONNE)))
            .andExpect(jsonPath("$.[*].codeType").value(hasItem(DEFAULT_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].dateCreationJuridique").value(hasItem(DEFAULT_DATE_CREATION_JURIDIQUE)))
            .andExpect(jsonPath("$.[*].dateEtat").value(hasItem(DEFAULT_DATE_ETAT)))
            .andExpect(jsonPath("$.[*].dateFermetureJuridique").value(hasItem(DEFAULT_DATE_FERMETURE_JURIDIQUE)))
            .andExpect(jsonPath("$.[*].codeIDCC").value(hasItem(DEFAULT_CODE_IDCC)))
            .andExpect(jsonPath("$.[*].codeTrancheEffectif").value(hasItem(DEFAULT_CODE_TRANCHE_EFFECTIF)))
            .andExpect(jsonPath("$.[*].libelleTrancheEffectif").value(hasItem(DEFAULT_LIBELLE_TRANCHE_EFFECTIF)))
            .andExpect(jsonPath("$.[*].dateCessationActivite").value(hasItem(DEFAULT_DATE_CESSATION_ACTIVITE)))
            .andExpect(jsonPath("$.[*].dateEffectifOfficiel").value(hasItem(DEFAULT_DATE_EFFECTIF_OFFICIEL)))
            .andExpect(jsonPath("$.[*].effectifOfficiel").value(hasItem(DEFAULT_EFFECTIF_OFFICIEL)))
            .andExpect(jsonPath("$.[*].codeMotifCessationActivite").value(hasItem(DEFAULT_CODE_MOTIF_CESSATION_ACTIVITE)))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET)))
            .andExpect(jsonPath("$.[*].codeTypeEtablissement").value(hasItem(DEFAULT_CODE_TYPE_ETABLISSEMENT)))
            .andExpect(jsonPath("$.[*].libelleEnseigne").value(hasItem(DEFAULT_LIBELLE_ENSEIGNE)))
            .andExpect(jsonPath("$.[*].codeNIC").value(hasItem(DEFAULT_CODE_NIC)));
    }

    @Test
    @Transactional
    void getPmEtablissement() throws Exception {
        // Initialize the database
        insertedPmEtablissement = pmEtablissementRepository.saveAndFlush(pmEtablissement);

        // Get the pmEtablissement
        restPmEtablissementMockMvc
            .perform(get(ENTITY_API_URL_ID, pmEtablissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pmEtablissement.getId().intValue()))
            .andExpect(jsonPath("$.idEtablissementRPG").value(DEFAULT_ID_ETABLISSEMENT_RPG))
            .andExpect(jsonPath("$.codePartenaireDistributeur").value(DEFAULT_CODE_PARTENAIRE_DISTRIBUTEUR))
            .andExpect(jsonPath("$.numeroSiretSiege").value(DEFAULT_NUMERO_SIRET_SIEGE))
            .andExpect(jsonPath("$.codeEtat").value(DEFAULT_CODE_ETAT))
            .andExpect(jsonPath("$.libelleEtat").value(DEFAULT_LIBELLE_ETAT))
            .andExpect(jsonPath("$.codeCategoriePersonne").value(DEFAULT_CODE_CATEGORIE_PERSONNE))
            .andExpect(jsonPath("$.libelleCategoriePersonne").value(DEFAULT_LIBELLE_CATEGORIE_PERSONNE))
            .andExpect(jsonPath("$.codeType").value(DEFAULT_CODE_TYPE))
            .andExpect(jsonPath("$.dateCreationJuridique").value(DEFAULT_DATE_CREATION_JURIDIQUE))
            .andExpect(jsonPath("$.dateEtat").value(DEFAULT_DATE_ETAT))
            .andExpect(jsonPath("$.dateFermetureJuridique").value(DEFAULT_DATE_FERMETURE_JURIDIQUE))
            .andExpect(jsonPath("$.codeIDCC").value(DEFAULT_CODE_IDCC))
            .andExpect(jsonPath("$.codeTrancheEffectif").value(DEFAULT_CODE_TRANCHE_EFFECTIF))
            .andExpect(jsonPath("$.libelleTrancheEffectif").value(DEFAULT_LIBELLE_TRANCHE_EFFECTIF))
            .andExpect(jsonPath("$.dateCessationActivite").value(DEFAULT_DATE_CESSATION_ACTIVITE))
            .andExpect(jsonPath("$.dateEffectifOfficiel").value(DEFAULT_DATE_EFFECTIF_OFFICIEL))
            .andExpect(jsonPath("$.effectifOfficiel").value(DEFAULT_EFFECTIF_OFFICIEL))
            .andExpect(jsonPath("$.codeMotifCessationActivite").value(DEFAULT_CODE_MOTIF_CESSATION_ACTIVITE))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET))
            .andExpect(jsonPath("$.codeTypeEtablissement").value(DEFAULT_CODE_TYPE_ETABLISSEMENT))
            .andExpect(jsonPath("$.libelleEnseigne").value(DEFAULT_LIBELLE_ENSEIGNE))
            .andExpect(jsonPath("$.codeNIC").value(DEFAULT_CODE_NIC));
    }

    @Test
    @Transactional
    void getNonExistingPmEtablissement() throws Exception {
        // Get the pmEtablissement
        restPmEtablissementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPmEtablissement() throws Exception {
        // Initialize the database
        insertedPmEtablissement = pmEtablissementRepository.saveAndFlush(pmEtablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pmEtablissement
        PmEtablissement updatedPmEtablissement = pmEtablissementRepository.findById(pmEtablissement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPmEtablissement are not directly saved in db
        em.detach(updatedPmEtablissement);
        updatedPmEtablissement
            .idEtablissementRPG(UPDATED_ID_ETABLISSEMENT_RPG)
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
            .codeIDCC(UPDATED_CODE_IDCC)
            .codeTrancheEffectif(UPDATED_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(UPDATED_LIBELLE_TRANCHE_EFFECTIF)
            .dateCessationActivite(UPDATED_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(UPDATED_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(UPDATED_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(UPDATED_CODE_MOTIF_CESSATION_ACTIVITE)
            .siret(UPDATED_SIRET)
            .codeTypeEtablissement(UPDATED_CODE_TYPE_ETABLISSEMENT)
            .libelleEnseigne(UPDATED_LIBELLE_ENSEIGNE)
            .codeNIC(UPDATED_CODE_NIC);
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(updatedPmEtablissement);

        restPmEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pmEtablissementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pmEtablissementDTO))
            )
            .andExpect(status().isOk());

        // Validate the PmEtablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPmEtablissementToMatchAllProperties(updatedPmEtablissement);
    }

    @Test
    @Transactional
    void putNonExistingPmEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEtablissement.setId(longCount.incrementAndGet());

        // Create the PmEtablissement
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPmEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pmEtablissementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pmEtablissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PmEtablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPmEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEtablissement.setId(longCount.incrementAndGet());

        // Create the PmEtablissement
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPmEtablissementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pmEtablissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PmEtablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPmEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEtablissement.setId(longCount.incrementAndGet());

        // Create the PmEtablissement
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPmEtablissementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PmEtablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePmEtablissementWithPatch() throws Exception {
        // Initialize the database
        insertedPmEtablissement = pmEtablissementRepository.saveAndFlush(pmEtablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pmEtablissement using partial update
        PmEtablissement partialUpdatedPmEtablissement = new PmEtablissement();
        partialUpdatedPmEtablissement.setId(pmEtablissement.getId());

        partialUpdatedPmEtablissement
            .codePartenaireDistributeur(UPDATED_CODE_PARTENAIRE_DISTRIBUTEUR)
            .libelleEtat(UPDATED_LIBELLE_ETAT)
            .codeCategoriePersonne(UPDATED_CODE_CATEGORIE_PERSONNE)
            .dateFermetureJuridique(UPDATED_DATE_FERMETURE_JURIDIQUE)
            .codeTrancheEffectif(UPDATED_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(UPDATED_LIBELLE_TRANCHE_EFFECTIF)
            .dateEffectifOfficiel(UPDATED_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(UPDATED_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(UPDATED_CODE_MOTIF_CESSATION_ACTIVITE)
            .siret(UPDATED_SIRET)
            .codeTypeEtablissement(UPDATED_CODE_TYPE_ETABLISSEMENT);

        restPmEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPmEtablissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPmEtablissement))
            )
            .andExpect(status().isOk());

        // Validate the PmEtablissement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPmEtablissementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPmEtablissement, pmEtablissement),
            getPersistedPmEtablissement(pmEtablissement)
        );
    }

    @Test
    @Transactional
    void fullUpdatePmEtablissementWithPatch() throws Exception {
        // Initialize the database
        insertedPmEtablissement = pmEtablissementRepository.saveAndFlush(pmEtablissement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pmEtablissement using partial update
        PmEtablissement partialUpdatedPmEtablissement = new PmEtablissement();
        partialUpdatedPmEtablissement.setId(pmEtablissement.getId());

        partialUpdatedPmEtablissement
            .idEtablissementRPG(UPDATED_ID_ETABLISSEMENT_RPG)
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
            .codeIDCC(UPDATED_CODE_IDCC)
            .codeTrancheEffectif(UPDATED_CODE_TRANCHE_EFFECTIF)
            .libelleTrancheEffectif(UPDATED_LIBELLE_TRANCHE_EFFECTIF)
            .dateCessationActivite(UPDATED_DATE_CESSATION_ACTIVITE)
            .dateEffectifOfficiel(UPDATED_DATE_EFFECTIF_OFFICIEL)
            .effectifOfficiel(UPDATED_EFFECTIF_OFFICIEL)
            .codeMotifCessationActivite(UPDATED_CODE_MOTIF_CESSATION_ACTIVITE)
            .siret(UPDATED_SIRET)
            .codeTypeEtablissement(UPDATED_CODE_TYPE_ETABLISSEMENT)
            .libelleEnseigne(UPDATED_LIBELLE_ENSEIGNE)
            .codeNIC(UPDATED_CODE_NIC);

        restPmEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPmEtablissement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPmEtablissement))
            )
            .andExpect(status().isOk());

        // Validate the PmEtablissement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPmEtablissementUpdatableFieldsEquals(
            partialUpdatedPmEtablissement,
            getPersistedPmEtablissement(partialUpdatedPmEtablissement)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPmEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEtablissement.setId(longCount.incrementAndGet());

        // Create the PmEtablissement
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPmEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pmEtablissementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pmEtablissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PmEtablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPmEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEtablissement.setId(longCount.incrementAndGet());

        // Create the PmEtablissement
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPmEtablissementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pmEtablissementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PmEtablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPmEtablissement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pmEtablissement.setId(longCount.incrementAndGet());

        // Create the PmEtablissement
        PmEtablissementDTO pmEtablissementDTO = pmEtablissementMapper.toDto(pmEtablissement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPmEtablissementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pmEtablissementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PmEtablissement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePmEtablissement() throws Exception {
        // Initialize the database
        insertedPmEtablissement = pmEtablissementRepository.saveAndFlush(pmEtablissement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pmEtablissement
        restPmEtablissementMockMvc
            .perform(delete(ENTITY_API_URL_ID, pmEtablissement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pmEtablissementRepository.count();
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

    protected PmEtablissement getPersistedPmEtablissement(PmEtablissement pmEtablissement) {
        return pmEtablissementRepository.findById(pmEtablissement.getId()).orElseThrow();
    }

    protected void assertPersistedPmEtablissementToMatchAllProperties(PmEtablissement expectedPmEtablissement) {
        assertPmEtablissementAllPropertiesEquals(expectedPmEtablissement, getPersistedPmEtablissement(expectedPmEtablissement));
    }

    protected void assertPersistedPmEtablissementToMatchUpdatableProperties(PmEtablissement expectedPmEtablissement) {
        assertPmEtablissementAllUpdatablePropertiesEquals(expectedPmEtablissement, getPersistedPmEtablissement(expectedPmEtablissement));
    }
}
