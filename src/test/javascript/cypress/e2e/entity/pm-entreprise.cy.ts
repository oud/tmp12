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

describe('PmEntreprise e2e test', () => {
  const pmEntreprisePageUrl = '/pm-entreprise';
  const pmEntreprisePageUrlPattern = new RegExp('/pm-entreprise(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const pmEntrepriseSample = {
    idEntrepriseRPG: 'towards immediate carefree',
    codePartenaireDistributeur: 'plain each',
    numeroSiretSiege: 'hmph',
    codeEtat: 'whoa nicely finally',
    libelleEtat: 'once',
    codeCategoriePersonne: 'uh-huh whenever than',
    libelleCategoriePersonne: 'efface',
    codeType: 'soliloquy while',
    dateCreationJuridique: '2025-04-07',
    dateEtat: '2025-04-08',
    dateFermetureJuridique: '2025-04-08',
    codeTrancheEffectif: 'apropos huzzah reasonable',
    libelleTrancheEffectif: 'eek stealthily along',
    dateCessationActivite: '2025-04-07',
    dateEffectifOfficiel: '2025-04-08',
    effectifOfficiel: 'after alongside',
    codeMotifCessationActivite: 'passionate adventurously pulp',
    siren: 'serenade eggplant',
    codeFormeJuridique: 'for',
    raisonSociale: 'sauerkraut the meh',
    codeCategorieJuridique: 'less buzzing',
    codeIDCC: 'instead',
    codeAPE: 'before efface tremendously',
  };

  let pmEntreprise;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/pm-entreprises+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/pm-entreprises').as('postEntityRequest');
    cy.intercept('DELETE', '/api/pm-entreprises/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (pmEntreprise) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/pm-entreprises/${pmEntreprise.id}`,
      }).then(() => {
        pmEntreprise = undefined;
      });
    }
  });

  it('PmEntreprises menu should load PmEntreprises page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('pm-entreprise');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PmEntreprise').should('exist');
    cy.url().should('match', pmEntreprisePageUrlPattern);
  });

  describe('PmEntreprise page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(pmEntreprisePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PmEntreprise page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/pm-entreprise/new$'));
        cy.getEntityCreateUpdateHeading('PmEntreprise');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEntreprisePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/pm-entreprises',
          body: pmEntrepriseSample,
        }).then(({ body }) => {
          pmEntreprise = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/pm-entreprises+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [pmEntreprise],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(pmEntreprisePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PmEntreprise page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('pmEntreprise');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEntreprisePageUrlPattern);
      });

      it('edit button click should load edit PmEntreprise page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PmEntreprise');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEntreprisePageUrlPattern);
      });

      it('edit button click should load edit PmEntreprise page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PmEntreprise');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEntreprisePageUrlPattern);
      });

      it('last delete button click should delete instance of PmEntreprise', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('pmEntreprise').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', pmEntreprisePageUrlPattern);

        pmEntreprise = undefined;
      });
    });
  });

  describe('new PmEntreprise page', () => {
    beforeEach(() => {
      cy.visit(`${pmEntreprisePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PmEntreprise');
    });

    it('should create an instance of PmEntreprise', () => {
      cy.get(`[data-cy="idEntrepriseRPG"]`).type('uselessly');
      cy.get(`[data-cy="idEntrepriseRPG"]`).should('have.value', 'uselessly');

      cy.get(`[data-cy="codePartenaireDistributeur"]`).type('unlike tidy');
      cy.get(`[data-cy="codePartenaireDistributeur"]`).should('have.value', 'unlike tidy');

      cy.get(`[data-cy="numeroSiretSiege"]`).type('seafood biodegradable');
      cy.get(`[data-cy="numeroSiretSiege"]`).should('have.value', 'seafood biodegradable');

      cy.get(`[data-cy="codeEtat"]`).type('massage unaware');
      cy.get(`[data-cy="codeEtat"]`).should('have.value', 'massage unaware');

      cy.get(`[data-cy="libelleEtat"]`).type('um apud until');
      cy.get(`[data-cy="libelleEtat"]`).should('have.value', 'um apud until');

      cy.get(`[data-cy="codeCategoriePersonne"]`).type('conceal numeracy pick');
      cy.get(`[data-cy="codeCategoriePersonne"]`).should('have.value', 'conceal numeracy pick');

      cy.get(`[data-cy="libelleCategoriePersonne"]`).type('for eggplant summarise');
      cy.get(`[data-cy="libelleCategoriePersonne"]`).should('have.value', 'for eggplant summarise');

      cy.get(`[data-cy="codeType"]`).type('that chapel reborn');
      cy.get(`[data-cy="codeType"]`).should('have.value', 'that chapel reborn');

      cy.get(`[data-cy="dateCreationJuridique"]`).type('2025-04-07');
      cy.get(`[data-cy="dateCreationJuridique"]`).blur();
      cy.get(`[data-cy="dateCreationJuridique"]`).should('have.value', '2025-04-07');

      cy.get(`[data-cy="dateEtat"]`).type('2025-04-07');
      cy.get(`[data-cy="dateEtat"]`).blur();
      cy.get(`[data-cy="dateEtat"]`).should('have.value', '2025-04-07');

      cy.get(`[data-cy="dateFermetureJuridique"]`).type('2025-04-08');
      cy.get(`[data-cy="dateFermetureJuridique"]`).blur();
      cy.get(`[data-cy="dateFermetureJuridique"]`).should('have.value', '2025-04-08');

      cy.get(`[data-cy="codeTrancheEffectif"]`).type('whoever ceramic');
      cy.get(`[data-cy="codeTrancheEffectif"]`).should('have.value', 'whoever ceramic');

      cy.get(`[data-cy="libelleTrancheEffectif"]`).type('train');
      cy.get(`[data-cy="libelleTrancheEffectif"]`).should('have.value', 'train');

      cy.get(`[data-cy="dateCessationActivite"]`).type('2025-04-08');
      cy.get(`[data-cy="dateCessationActivite"]`).blur();
      cy.get(`[data-cy="dateCessationActivite"]`).should('have.value', '2025-04-08');

      cy.get(`[data-cy="dateEffectifOfficiel"]`).type('2025-04-07');
      cy.get(`[data-cy="dateEffectifOfficiel"]`).blur();
      cy.get(`[data-cy="dateEffectifOfficiel"]`).should('have.value', '2025-04-07');

      cy.get(`[data-cy="effectifOfficiel"]`).type('whenever however indeed');
      cy.get(`[data-cy="effectifOfficiel"]`).should('have.value', 'whenever however indeed');

      cy.get(`[data-cy="codeMotifCessationActivite"]`).type('verbally hmph indeed');
      cy.get(`[data-cy="codeMotifCessationActivite"]`).should('have.value', 'verbally hmph indeed');

      cy.get(`[data-cy="siren"]`).type('gee for');
      cy.get(`[data-cy="siren"]`).should('have.value', 'gee for');

      cy.get(`[data-cy="codeFormeJuridique"]`).type('typewriter convection actually');
      cy.get(`[data-cy="codeFormeJuridique"]`).should('have.value', 'typewriter convection actually');

      cy.get(`[data-cy="raisonSociale"]`).type('carelessly yuck well');
      cy.get(`[data-cy="raisonSociale"]`).should('have.value', 'carelessly yuck well');

      cy.get(`[data-cy="codeCategorieJuridique"]`).type('provision moor');
      cy.get(`[data-cy="codeCategorieJuridique"]`).should('have.value', 'provision moor');

      cy.get(`[data-cy="codeIDCC"]`).type('furthermore never');
      cy.get(`[data-cy="codeIDCC"]`).should('have.value', 'furthermore never');

      cy.get(`[data-cy="codeAPE"]`).type('inveigle');
      cy.get(`[data-cy="codeAPE"]`).should('have.value', 'inveigle');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        pmEntreprise = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', pmEntreprisePageUrlPattern);
    });
  });
});
