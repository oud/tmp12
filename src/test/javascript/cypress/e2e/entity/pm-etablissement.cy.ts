import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('PmEtablissement e2e test', () => {
  const pmEtablissementPageUrl = '/pm-etablissement';
  const pmEtablissementPageUrlPattern = new RegExp('/pm-etablissement(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const pmEtablissementSample = {
    idEtablissementRPG: 'duh',
    codePartenaireDistributeur: 'warp naughty why',
    numeroSiretSiege: 'stingy vicinity',
    codeEtat: 'slowly',
    libelleEtat: 'idealistic',
    codeCategoriePersonne: 'big huge wing',
    libelleCategoriePersonne: 'beep climb',
    codeType: 'meanwhile',
    dateCreationJuridique: 'unless',
    dateEtat: 'freight among idolized',
    dateFermetureJuridique: 'sharply inferior however',
    codeIDCC: 'fax',
    codeTrancheEffectif: 'guard',
    libelleTrancheEffectif: 'farmer manner wholly',
    dateCessationActivite: 'disclosure',
    dateEffectifOfficiel: 'probable',
    effectifOfficiel: 'when about',
    codeMotifCessationActivite: 'singe',
    siret: 'resolve whose duh',
    codeTypeEtablissement: 'now boohoo truthfully',
    libelleEnseigne: 'fooey yum yesterday',
    codeNIC: 'request',
  };

  let pmEtablissement;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/pm-etablissements+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/pm-etablissements').as('postEntityRequest');
    cy.intercept('DELETE', '/api/pm-etablissements/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (pmEtablissement) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/pm-etablissements/${pmEtablissement.id}`,
      }).then(() => {
        pmEtablissement = undefined;
      });
    }
  });

  it('PmEtablissements menu should load PmEtablissements page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('pm-etablissement');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PmEtablissement').should('exist');
    cy.url().should('match', pmEtablissementPageUrlPattern);
  });

  describe('PmEtablissement page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(pmEtablissementPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PmEtablissement page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/pm-etablissement/new$'));
        cy.getEntityCreateUpdateHeading('PmEtablissement');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEtablissementPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/pm-etablissements',
          body: pmEtablissementSample,
        }).then(({ body }) => {
          pmEtablissement = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/pm-etablissements+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [pmEtablissement],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(pmEtablissementPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PmEtablissement page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('pmEtablissement');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEtablissementPageUrlPattern);
      });

      it('edit button click should load edit PmEtablissement page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PmEtablissement');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEtablissementPageUrlPattern);
      });

      it('edit button click should load edit PmEtablissement page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PmEtablissement');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEtablissementPageUrlPattern);
      });

      it('last delete button click should delete instance of PmEtablissement', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('pmEtablissement').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEtablissementPageUrlPattern);

        pmEtablissement = undefined;
      });
    });
  });

  describe('new PmEtablissement page', () => {
    beforeEach(() => {
      cy.visit(`${pmEtablissementPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PmEtablissement');
    });

    it('should create an instance of PmEtablissement', () => {
      cy.get(`[data-cy="idEtablissementRPG"]`).type('positively lawmaker wherever');
      cy.get(`[data-cy="idEtablissementRPG"]`).should('have.value', 'positively lawmaker wherever');

      cy.get(`[data-cy="codePartenaireDistributeur"]`).type('confute for');
      cy.get(`[data-cy="codePartenaireDistributeur"]`).should('have.value', 'confute for');

      cy.get(`[data-cy="numeroSiretSiege"]`).type('nor heroine');
      cy.get(`[data-cy="numeroSiretSiege"]`).should('have.value', 'nor heroine');

      cy.get(`[data-cy="codeEtat"]`).type('french meh grizzled');
      cy.get(`[data-cy="codeEtat"]`).should('have.value', 'french meh grizzled');

      cy.get(`[data-cy="libelleEtat"]`).type('naturally whoever weird');
      cy.get(`[data-cy="libelleEtat"]`).should('have.value', 'naturally whoever weird');

      cy.get(`[data-cy="codeCategoriePersonne"]`).type('reorganisation');
      cy.get(`[data-cy="codeCategoriePersonne"]`).should('have.value', 'reorganisation');

      cy.get(`[data-cy="libelleCategoriePersonne"]`).type('grandson pillbox whoa');
      cy.get(`[data-cy="libelleCategoriePersonne"]`).should('have.value', 'grandson pillbox whoa');

      cy.get(`[data-cy="codeType"]`).type('frenetically aha');
      cy.get(`[data-cy="codeType"]`).should('have.value', 'frenetically aha');

      cy.get(`[data-cy="dateCreationJuridique"]`).type('seldom ha');
      cy.get(`[data-cy="dateCreationJuridique"]`).should('have.value', 'seldom ha');

      cy.get(`[data-cy="dateEtat"]`).type('yippee consequently');
      cy.get(`[data-cy="dateEtat"]`).should('have.value', 'yippee consequently');

      cy.get(`[data-cy="dateFermetureJuridique"]`).type('provided mount');
      cy.get(`[data-cy="dateFermetureJuridique"]`).should('have.value', 'provided mount');

      cy.get(`[data-cy="codeIDCC"]`).type('warmly provided abaft');
      cy.get(`[data-cy="codeIDCC"]`).should('have.value', 'warmly provided abaft');

      cy.get(`[data-cy="codeTrancheEffectif"]`).type('fidget knowledgeably middle');
      cy.get(`[data-cy="codeTrancheEffectif"]`).should('have.value', 'fidget knowledgeably middle');

      cy.get(`[data-cy="libelleTrancheEffectif"]`).type('give victoriously fooey');
      cy.get(`[data-cy="libelleTrancheEffectif"]`).should('have.value', 'give victoriously fooey');

      cy.get(`[data-cy="dateCessationActivite"]`).type('how yuck');
      cy.get(`[data-cy="dateCessationActivite"]`).should('have.value', 'how yuck');

      cy.get(`[data-cy="dateEffectifOfficiel"]`).type('phooey fooey');
      cy.get(`[data-cy="dateEffectifOfficiel"]`).should('have.value', 'phooey fooey');

      cy.get(`[data-cy="effectifOfficiel"]`).type('fisherman without monster');
      cy.get(`[data-cy="effectifOfficiel"]`).should('have.value', 'fisherman without monster');

      cy.get(`[data-cy="codeMotifCessationActivite"]`).type('seldom clonk');
      cy.get(`[data-cy="codeMotifCessationActivite"]`).should('have.value', 'seldom clonk');

      cy.get(`[data-cy="siret"]`).type('yearly compete');
      cy.get(`[data-cy="siret"]`).should('have.value', 'yearly compete');

      cy.get(`[data-cy="codeTypeEtablissement"]`).type('nudge');
      cy.get(`[data-cy="codeTypeEtablissement"]`).should('have.value', 'nudge');

      cy.get(`[data-cy="libelleEnseigne"]`).type('for topsail');
      cy.get(`[data-cy="libelleEnseigne"]`).should('have.value', 'for topsail');

      cy.get(`[data-cy="codeNIC"]`).type('bouncy');
      cy.get(`[data-cy="codeNIC"]`).should('have.value', 'bouncy');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        pmEtablissement = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', pmEtablissementPageUrlPattern);
    });
  });
});
